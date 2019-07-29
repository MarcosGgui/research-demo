package com.research.demo.web.rest;
import com.research.demo.service.ReportDataService;
import com.research.demo.web.rest.errors.BadRequestAlertException;
import com.research.demo.web.rest.util.HeaderUtil;
import com.research.demo.service.dto.ReportDataDTO;
import com.research.demo.service.dto.ReportDataCriteria;
import com.research.demo.service.ReportDataQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import javassist.expr.Instanceof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReportData.
 */
@RestController
@RequestMapping("/api")
public class ReportDataResource {

    private final Logger log = LoggerFactory.getLogger(ReportDataResource.class);

    private static final String ENTITY_NAME = "reportData";

    private final ReportDataService reportDataService;

    private final ReportDataQueryService reportDataQueryService;

    public ReportDataResource(ReportDataService reportDataService, ReportDataQueryService reportDataQueryService) {
        this.reportDataService = reportDataService;
        this.reportDataQueryService = reportDataQueryService;
    }

    /**
     * POST  /report-data : Create a new reportData.
     *
     * @param reportDataDTO the reportDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reportDataDTO, or with status 400 (Bad Request) if the reportData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/report-data")
    public ResponseEntity<ReportDataDTO> createReportData(@RequestBody ReportDataDTO reportDataDTO) throws URISyntaxException {
        log.debug("REST request to save ReportData : {}", reportDataDTO);
        if (reportDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportDataDTO result = reportDataService.save(reportDataDTO);
        return ResponseEntity.created(new URI("/api/report-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /report-data : Updates an existing reportData.
     *
     * @param reportDataDTO the reportDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reportDataDTO,
     * or with status 400 (Bad Request) if the reportDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the reportDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/report-data")
    public ResponseEntity<ReportDataDTO> updateReportData(@RequestBody ReportDataDTO reportDataDTO) throws URISyntaxException {
        log.debug("REST request to update ReportData : {}", reportDataDTO);
        if (reportDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportDataDTO result = reportDataService.save(reportDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reportDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /report-data : get all the reportData.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of reportData in body
     */
    @GetMapping("/report-data")
    public ResponseEntity<List<ReportDataDTO>> getAllReportData(ReportDataCriteria criteria) {
        log.debug("REST request to get ReportData by criteria: {}", criteria);
        List<ReportDataDTO> entityList = reportDataQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /report-data/count : count all the reportData.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/report-data/count")
    public ResponseEntity<Long> countReportData(ReportDataCriteria criteria) {
        log.debug("REST request to count ReportData by criteria: {}", criteria);
        return ResponseEntity.ok().body(reportDataQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /report-data/:id : get the "id" reportData.
     *
     * @param id the id of the reportDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reportDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/report-data/{id}")
    public ResponseEntity<ReportDataDTO> getReportData(@PathVariable Long id) {
        log.debug("REST request to get ReportData : {}", id);
        Optional<ReportDataDTO> reportDataDTO = reportDataService.findOne(id);
        ReportDataDTO dto = reportDataDTO.get();
        System.out.println(dto.getCronInfo());
        System.out.println("Class: " + dto.getCronInfo().getClass());
        return ResponseUtil.wrapOrNotFound(reportDataDTO);
    }

    /**
     * DELETE  /report-data/:id : delete the "id" reportData.
     *
     * @param id the id of the reportDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/report-data/{id}")
    public ResponseEntity<Void> deleteReportData(@PathVariable Long id) {
        log.debug("REST request to delete ReportData : {}", id);
        reportDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
