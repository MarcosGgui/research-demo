package com.research.demo.domain;

/**
 * Define the custom functions
 *
 * @author marcosgui
 */
public class Functions{

    private static final int INDEX_AVG = 0;

    private static final int INDEX_SUM = 1;

    private static final int INDEX_MIN = 2;

    private static final int INDEX_MAX = 3;

    private static final int INDEX_COUNT = 4;

    private static final Function[] BUILT_FUNCTIONS = new Function[5];

    static {
        BUILT_FUNCTIONS[INDEX_AVG] = new Function("avg"){
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        BUILT_FUNCTIONS[INDEX_SUM] = new Function("sum"){
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        BUILT_FUNCTIONS[INDEX_MIN] = new Function("min"){
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        BUILT_FUNCTIONS[INDEX_MAX] = new Function("max"){
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        BUILT_FUNCTIONS[INDEX_COUNT] = new Function("count"){
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
    }

    public static Function getBuiltFunctions(String functionName) {
        String name = functionName.toLowerCase();
        switch (name) {
            case "avg":
                return BUILT_FUNCTIONS[INDEX_AVG];
            case "sum":
                return BUILT_FUNCTIONS[INDEX_SUM];
            case "min":
                return BUILT_FUNCTIONS[INDEX_MIN];
            case "max":
                return BUILT_FUNCTIONS[INDEX_MAX];
            case "count":
                return BUILT_FUNCTIONS[INDEX_COUNT];
            default:
                return null;
        }
    }

}
