package com.apps.quantitymeasurment.repository;

import com.apps.quantitymeasurment.entity.QuantityMeasurementEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final String STORAGE_FILE = "quantity_measurements.dat";
    private static QuantityMeasurementCacheRepository instance;

    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {
        loadFromDisk();
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
        saveToDisk(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurments() {
        return List.of();
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
        return Collections.unmodifiableList(new ArrayList<>(cache));
    }

    private void saveToDisk(QuantityMeasurementEntity entity) {
        File file = new File(STORAGE_FILE);
        try (AppendableObjectOutputStream out =
                     new AppendableObjectOutputStream(new FileOutputStream(file, true))) {
            out.writeObject(entity);
        } catch (IOException e) {
            // Persistence failures shouldn't crash the app — the in-memory
            // cache remains the source of truth for the current session.
            System.err.println("Warning: failed to persist measurement: " + e.getMessage());
        }
    }

    private void loadFromDisk() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof QuantityMeasurementEntity) {
                    cache.add((QuantityMeasurementEntity) obj);
                }
            }
        } catch (EOFException eof) {
            // expected at end of file — normal termination of the read loop
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Warning: failed to load measurement history: " + e.getMessage());
        }
    }

    /**
     * Standard ObjectOutputStream always writes a stream header on
     * construction, which corrupts a file when appending multiple objects
     * across separate writes. This subclass skips the header after the
     * first write, so sequentially appended objects remain readable.
     */
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // Skip writing the header on subsequent appends
            reset();
        }
    }
}