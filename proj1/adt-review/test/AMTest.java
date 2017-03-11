package test;

import impl.ArrayMap;


public class AMTest extends MapTest {

    protected void reset() {
        testMap = new ArrayMap<String, String>();
    }
}
