package com.research.demo.parser.function;

/**
 * Define the functions can be used in the expression.
 *
 * @author marcosgui
 */
public abstract class Function{

    private String name;

    private int arguments;

    public Function(String name, int arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public Function(String name) {
        this.name = name;
    }


    /**
     * Apply the function
     */
    public abstract double apply(double... args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArguments() {
        return arguments;
    }

    public void setArguments(int arguments) {
        this.arguments = arguments;
    }

}
