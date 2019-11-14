package com.example.rample;

public class BounceInterpolator {
    private double mAmplitude = 1;
    private double mFrequency = 10;

    /* Constructor
    com.example.rample.BounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }*/

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}
