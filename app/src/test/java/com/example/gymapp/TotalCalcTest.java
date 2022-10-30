package com.example.gymapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TotalCalcTest {

    @Test
    public void addPTfee() {
        assertEquals(java.util.Optional.of(5000.0), java.util.Optional.of(TotalCalc.addPTfee(2500.0)));
        assertEquals(java.util.Optional.of(10500.0), java.util.Optional.of(TotalCalc.addPTfee(8000.0)));
        assertEquals(java.util.Optional.of(14500.0), java.util.Optional.of(TotalCalc.addPTfee(12000.0)));
    }
}