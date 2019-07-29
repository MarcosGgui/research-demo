package com.research.demo.service;

import com.research.demo.service.dto.ReportDataDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ReportData.
 */
public interface ReportDataService {

    /**
     * Save a reportData.
     *
     * @param reportDataDTO the entity to save
     * @return the persisted entity
     */
    ReportDataDTO save(ReportDataDTO reportDataDTO);

    /**
     * Get all the reportData.
     *
     * @return the list of entities
     */
    List<ReportDataDTO> findAll();


    /**
     * Get the "id" reportData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ReportDataDTO> findOne(Long id);

    /**
     * Delete the "id" reportData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
