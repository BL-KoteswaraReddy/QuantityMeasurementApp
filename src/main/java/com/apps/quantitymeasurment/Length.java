package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;

public class Length {
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double value;
    private LengthUnit unit;

    //constructor to initialize length value and unit
    public Length(double value, LengthUnit unit)
    {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    //Convert length to base unit
    public double convertToBaseUnit()
    {
       return unit.convertToBaseUnit(value);
    }

    //compare two length objects for equality based on their values in the base unit
    public boolean compare(Length thatLenght)
    {
        return Double.compare(this.convertToBaseUnit(), thatLenght.convertToBaseUnit())==0;
    }

    @Override
    public boolean equals(Object object)
    {
       // System.out.println("checking");
        if(this == object)
            return true;

        if(object == null || getClass()!=object.getClass())
        {
            return false;
        }

        Length other = (Length) object;

        double thisInFeet = this.unit.convertToBaseUnit(this.value);

        double otherInFeet = other.unit.convertToBaseUnit(other.value);

        return Double.compare(thisInFeet, otherInFeet) == 0;
    }

    public Length add(Length other)
    {
        if(other == null)
            new IllegalArgumentException("Length cannot be null");

        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        double totalBase = thisInBase+otherInBase;
        double resultValue = totalBase/this.unit.convertFromBaseUnit(this.value);
        return new Length(resultValue, this.getUnit());

    }

    public String toString()
    {
        return value +", "+unit;
    }

    public static  void main(String args[])
    {
        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println("Are Lengths equal? "+length1.equals(length2));

        // Feet + Feet
        System.out.println(
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(2.0, LengthUnit.FEET)));

        // Feet + Inches
        System.out.println(
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(12.0, LengthUnit.INCHES)));

        // Inches + Feet
        System.out.println(
                new Length(12.0, LengthUnit.INCHES)
                        .add(new Length(1.0, LengthUnit.FEET)));

        // Yards + Feet
        System.out.println(
                new Length(1.0, LengthUnit.YARDS)
                        .add(new Length(3.0, LengthUnit.FEET)));

        // Inches + Yard
        System.out.println(
                new Length(36.0, LengthUnit.INCHES)
                        .add(new Length(1.0, LengthUnit.YARDS)));

        // Centimeters + Inches
        System.out.println(
                new Length(2.54, LengthUnit.CENTIMETERS)
                        .add(new Length(1.0, LengthUnit.INCHES)));

        // Identity Element
        System.out.println(
                new Length(5.0, LengthUnit.FEET)
                        .add(new Length(0.0, LengthUnit.INCHES)));

        // Negative Value
        System.out.println(
                new Length(5.0, LengthUnit.FEET)
                        .add(new Length(-2.0, LengthUnit.FEET)));
    }
    public Length add(Length other, LengthUnit targetUnit)
    {
        if(other == null)
            new IllegalArgumentException("Length cannot be null");

        if(targetUnit == null)
            new IllegalArgumentException("Target cannot be null");

        double thisInBase = this.unit.convertToBaseUnit(this.value);

        double otherInBase = other.unit.convertToBaseUnit(other.value);

        double totalInBase = thisInBase+otherInBase;

        double resultValue = totalInBase/targetUnit.getConversionFactor();

        return  new Length(resultValue, targetUnit);
    }

}
