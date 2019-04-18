package com.research.demo.service.mapper;

import com.research.demo.domain.*;
import com.research.demo.service.dto.FieldDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Field and its DTO FieldDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FieldMapper extends EntityMapper<FieldDTO, Field> {



    default Field fromId(Long id) {
        if (id == null) {
            return null;
        }
        Field field = new Field();
        field.setId(id);
        return field;
    }
}
