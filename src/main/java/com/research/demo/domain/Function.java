package com.research.demo.domain;

/**
 * Function Entity
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
