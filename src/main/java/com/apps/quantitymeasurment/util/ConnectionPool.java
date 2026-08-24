package com.apps.quantitymeasurment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.apps.quantitymeasurment.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple thread-safe JDBC connection pool. Real production systems would
 * use HikariCP or similar; this hand-rolled version exists to teach the
 * underlying mechanics (why pooling matters, what "acquire/release" means).
 */
public class ConnectionPool {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;

    private final BlockingQueue<Connection> pool;
    private final int maxSize;
    private final AtomicInteger activeConnections = new AtomicInteger(0);

    private ConnectionPool() {
        this.maxSize = ApplicationConfig.getPoolMaxSize();
        this.pool = new ArrayBlockingQueue<>(maxSize);
        initializePool();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initializePool() {
        try {
            Class.forName(ApplicationConfig.getDbDriver());
            for (int i = 0; i < maxSize; i++) {
                pool.offer(createConnection());
            }
            logger.info("Connection pool initialized with {} connections", maxSize);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("JDBC driver not found: " + ApplicationConfig.getDbDriver(), e);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    ApplicationConfig.getDbUrl(),
                    ApplicationConfig.getDbUsername(),
                    ApplicationConfig.getDbPassword());
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create database connection", e);
        }
    }

    public Connection acquireConnection() {
        try {
            Connection conn = pool.poll(5, TimeUnit.SECONDS);
            if (conn == null) {
                throw new DatabaseException("Connection pool exhausted: no connection available within timeout");
            }
            activeConnections.incrementAndGet();
            return conn;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DatabaseException("Interrupted while waiting for connection", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            pool.offer(connection);
            activeConnections.decrementAndGet();
        }
    }

    public String getPoolStatistics() {
        return String.format("Pool size: %d, Active: %d, Idle: %d",
                maxSize, activeConnections.get(), pool.size());
    }

    public void shutdown() {
        pool.forEach(conn -> {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warn("Error closing connection during shutdown", e);
            }
        });
        pool.clear();
        logger.info("Connection pool shut down");
    }
}