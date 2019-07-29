package com.research.demo.service.impl;

import com.research.demo.service.ReportDataService;
import com.research.demo.domain.ReportData;
import com.research.demo.repository.ReportDataRepository;
import com.research.demo.service.dto.ReportDataDTO;
import com.research.demo.service.mapper.ReportDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ReportData.
 */
@Service
@Transactional
public class ReportDataServiceImpl implements ReportDataService {

    private final Logger log = LoggerFactory.getLogger(ReportDataServiceImpl.class);

    private final ReportDataRepository reportDataRepository;

    private final ReportDataMapper reportDataMapper;

    public ReportDataServiceImpl(ReportDataRepository reportDataRepository, ReportDataMapper reportDataMapper) {
        this.reportDataRepository = reportDataRepository;
        this.reportDataMapper = reportDataMapper;
    }

    /**
     * Save a reportData.
     *
     * @param reportDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReportDataDTO save(ReportDataDTO reportDataDTO) {
        log.debug("Request to save ReportData : {}", reportDataDTO);
        ReportData reportData = reportDataMapper.toEntity(reportDataDTO);
        reportData = reportDataRepository.save(reportData);
        return reportDataMapper.toDto(reportData);
    }

    /**
     * Get all the reportData.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReportDataDTO> findAll() {
        log.debug("Request to get all ReportData");
        return reportDataRepository.findAll().stream()
            .map(reportDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one reportData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReportDataDTO> findOne(Long id) {
        log.debug("Request to get ReportData : {}", id);
        return reportDataRepository.findById(id)
            .map(reportDataMapper::toDto);
    }

    /**
     * Delete the reportData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportData : {}", id);        reportDataRepository.deleteById(id);
    }
}
