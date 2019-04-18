package com.research.demo.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Field entity.
 */
public class FieldDTO implements Serializable {

    private Long id;

    private String name;

    private String displayName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        FieldDTO fieldDTO = (FieldDTO) o;
        if (fieldDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fieldDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FieldDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            "}";
    }
}
