package test;

import static org.junit.Assert.*;

import org.junit.Test;

import alg.Matrix;

public class MatrixTest {

    @Test
    public void clrs() {
        assertEquals("((0(12))((34)5))", 
                Matrix.planMatrixMult(new int[]{30, 35, 15, 5, 10, 20, 25}));
    }

}
