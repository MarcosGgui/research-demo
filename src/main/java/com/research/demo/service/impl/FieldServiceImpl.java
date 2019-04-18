package com.research.demo.service.impl;

import com.research.demo.service.FieldService;
import com.research.demo.domain.Field;
import com.research.demo.repository.FieldRepository;
import com.research.demo.service.dto.FieldDTO;
import com.research.demo.service.mapper.FieldMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Field.
 */
@Service
@Transactional
public class FieldServiceImpl implements FieldService {

    private final Logger log = LoggerFactory.getLogger(FieldServiceImpl.class);

    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    public FieldServiceImpl(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    /**
     * Save a field.
     *
     * @param fieldDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FieldDTO save(FieldDTO fieldDTO) {
        log.debug("Request to save Field : {}", fieldDTO);
        Field field = fieldMapper.toEntity(fieldDTO);
        field = fieldRepository.save(field);
        return fieldMapper.toDto(field);
    }

    /**
     * Get all the fields.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FieldDTO> findAll() {
        log.debug("Request to get all Fields");
        return fieldRepository.findAll().stream()
            .map(fieldMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one field by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FieldDTO> findOne(Long id) {
        log.debug("Request to get Field : {}", id);
        return fieldRepository.findById(id)
            .map(fieldMapper::toDto);
    }

    /**
     * Delete the field by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Field : {}", id);        fieldRepository.deleteById(id);
    }
}
