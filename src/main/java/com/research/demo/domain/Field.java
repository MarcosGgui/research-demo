package com.research.demo.domain;

import java.util.Objects;

public class Field{

    private String name;

    private String displayName;

    public Field(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public Field() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Field field = (Field) o;
        return Objects.equals(name, field.name) &&
            Objects.equals(displayName, field.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, displayName);
    }
}
