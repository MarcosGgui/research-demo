package com.research.demo.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.research.demo.domain.ReportData;
import com.research.demo.domain.*; // for static metamodels
import com.research.demo.repository.ReportDataRepository;
import com.research.demo.service.dto.ReportDataCriteria;
import com.research.demo.service.dto.ReportDataDTO;
import com.research.demo.service.mapper.ReportDataMapper;

/**
 * Service for executing complex queries for ReportData entities in the database.
 * The main input is a {@link ReportDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReportDataDTO} or a {@link Page} of {@link ReportDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReportDataQueryService extends QueryService<ReportData> {

    private final Logger log = LoggerFactory.getLogger(ReportDataQueryService.class);

    private final ReportDataRepository reportDataRepository;

    private final ReportDataMapper reportDataMapper;

    public ReportDataQueryService(ReportDataRepository reportDataRepository, ReportDataMapper reportDataMapper) {
        this.reportDataRepository = reportDataRepository;
        this.reportDataMapper = reportDataMapper;
    }

    /**
     * Return a {@link List} of {@link ReportDataDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReportDataDTO> findByCriteria(ReportDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReportData> specification = createSpecification(criteria);
        return reportDataMapper.toDto(reportDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReportDataDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportDataDTO> findByCriteria(ReportDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReportData> specification = createSpecification(criteria);
        return reportDataRepository.findAll(specification, page)
            .map(reportDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReportDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReportData> specification = createSpecification(criteria);
        return reportDataRepository.count(specification);
    }

    /**
     * Function to convert ReportDataCriteria to a {@link Specification}
     */
    private Specification<ReportData> createSpecification(ReportDataCriteria criteria) {
        Specification<ReportData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReportData_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ReportData_.name));
            }
            if (criteria.getEsIndex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEsIndex(), ReportData_.esIndex));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), ReportData_.displayName));
            }
        }
        return specification;
    }
}
