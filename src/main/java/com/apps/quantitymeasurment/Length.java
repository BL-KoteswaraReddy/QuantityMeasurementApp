package com.apps.quantitymeasurment;

import com.apps.quantitymeasurment.enums.LengthUnit;

public class Length {
    private double value;
    private LengthUnit unit;

    //constructor to initialize length value and unit
    public Length(double value, LengthUnit unit)
    {
        this.value = value;
        this.unit = unit;
    }

    public static double convert(double v, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if(!Double.isFinite(v))
        {
            throw new IllegalArgumentException("Value must be finite");
        }

        if(sourceUnit == null || targetUnit == null)
        {
            throw new IllegalArgumentException("values cannot be null");
        }

        double baseValue = v*sourceUnit.getConversionFactor();

        return baseValue/ targetUnit.getConversionFactor();
    }

    //Convert length to base unit
    public double convertToBaseUnit()
    {
       return value*unit.getConversionFactor();
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

        double thisInFeet = this.value*this.unit.getConversionFactor();

        double otherInFeet = other.value*other.unit.getConversionFactor();

        return Double.compare(thisInFeet, otherInFeet) == 0;
    }

    public static  void main(String args[])
    {
        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println("Are Lengths equal? "+length1.equals(length2));


    }

}
