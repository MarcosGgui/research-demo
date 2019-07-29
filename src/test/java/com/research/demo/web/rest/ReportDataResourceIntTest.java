package com.research.demo.web.rest;

import com.research.demo.ResearchDemoApp;

import com.research.demo.domain.ReportData;
import com.research.demo.repository.ReportDataRepository;
import com.research.demo.service.ReportDataService;
import com.research.demo.service.dto.ReportDataDTO;
import com.research.demo.service.mapper.ReportDataMapper;
import com.research.demo.web.rest.errors.ExceptionTranslator;
import com.research.demo.service.dto.ReportDataCriteria;
import com.research.demo.service.ReportDataQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.research.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReportDataResource REST controller.
 *
 * @see ReportDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchDemoApp.class)
public class ReportDataResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ES_INDEX = "AAAAAAAAAA";
    private static final String UPDATED_ES_INDEX = "BBBBBBBBBB";

    private static final String DEFAULT_CRON_INFO = "AAAAAAAAAA";
    private static final String UPDATED_CRON_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    @Autowired
    private ReportDataRepository reportDataRepository;

    @Autowired
    private ReportDataMapper reportDataMapper;

    @Autowired
    private ReportDataService reportDataService;

    @Autowired
    private ReportDataQueryService reportDataQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReportDataMockMvc;

    private ReportData reportData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportDataResource reportDataResource = new ReportDataResource(reportDataService, reportDataQueryService);
        this.restReportDataMockMvc = MockMvcBuilders.standaloneSetup(reportDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportData createEntity(EntityManager em) {
        ReportData reportData = new ReportData()
            .name(DEFAULT_NAME)
            .esIndex(DEFAULT_ES_INDEX)
            .cronInfo(DEFAULT_CRON_INFO)
            .displayName(DEFAULT_DISPLAY_NAME);
        return reportData;
    }

    @Before
    public void initTest() {
        reportData = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportData() throws Exception {
        int databaseSizeBeforeCreate = reportDataRepository.findAll().size();

        // Create the ReportData
        ReportDataDTO reportDataDTO = reportDataMapper.toDto(reportData);
        restReportDataMockMvc.perform(post("/api/report-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportData in the database
        List<ReportData> reportDataList = reportDataRepository.findAll();
        assertThat(reportDataList).hasSize(databaseSizeBeforeCreate + 1);
        ReportData testReportData = reportDataList.get(reportDataList.size() - 1);
        assertThat(testReportData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReportData.getEsIndex()).isEqualTo(DEFAULT_ES_INDEX);
        assertThat(testReportData.getCronInfo()).isEqualTo(DEFAULT_CRON_INFO);
        assertThat(testReportData.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void createReportDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportDataRepository.findAll().size();

        // Create the ReportData with an existing ID
        reportData.setId(1L);
        ReportDataDTO reportDataDTO = reportDataMapper.toDto(reportData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportDataMockMvc.perform(post("/api/report-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportData in the database
        List<ReportData> reportDataList = reportDataRepository.findAll();
        assertThat(reportDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReportData() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList
        restReportDataMockMvc.perform(get("/api/report-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].esIndex").value(hasItem(DEFAULT_ES_INDEX.toString())))
            .andExpect(jsonPath("$.[*].cronInfo").value(hasItem(DEFAULT_CRON_INFO.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getReportData() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get the reportData
        restReportDataMockMvc.perform(get("/api/report-data/{id}", reportData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.esIndex").value(DEFAULT_ES_INDEX.toString()))
            .andExpect(jsonPath("$.cronInfo").value(DEFAULT_CRON_INFO.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllReportDataByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where name equals to DEFAULT_NAME
        defaultReportDataShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the reportDataList where name equals to UPDATED_NAME
        defaultReportDataShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportDataByNameIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReportDataShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the reportDataList where name equals to UPDATED_NAME
        defaultReportDataShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReportDataByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where name is not null
        defaultReportDataShouldBeFound("name.specified=true");

        // Get all the reportDataList where name is null
        defaultReportDataShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByEsIndexIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where esIndex equals to DEFAULT_ES_INDEX
        defaultReportDataShouldBeFound("esIndex.equals=" + DEFAULT_ES_INDEX);

        // Get all the reportDataList where esIndex equals to UPDATED_ES_INDEX
        defaultReportDataShouldNotBeFound("esIndex.equals=" + UPDATED_ES_INDEX);
    }

    @Test
    @Transactional
    public void getAllReportDataByEsIndexIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where esIndex in DEFAULT_ES_INDEX or UPDATED_ES_INDEX
        defaultReportDataShouldBeFound("esIndex.in=" + DEFAULT_ES_INDEX + "," + UPDATED_ES_INDEX);

        // Get all the reportDataList where esIndex equals to UPDATED_ES_INDEX
        defaultReportDataShouldNotBeFound("esIndex.in=" + UPDATED_ES_INDEX);
    }

    @Test
    @Transactional
    public void getAllReportDataByEsIndexIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where esIndex is not null
        defaultReportDataShouldBeFound("esIndex.specified=true");

        // Get all the reportDataList where esIndex is null
        defaultReportDataShouldNotBeFound("esIndex.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportDataByDisplayNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where displayName equals to DEFAULT_DISPLAY_NAME
        defaultReportDataShouldBeFound("displayName.equals=" + DEFAULT_DISPLAY_NAME);

        // Get all the reportDataList where displayName equals to UPDATED_DISPLAY_NAME
        defaultReportDataShouldNotBeFound("displayName.equals=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void getAllReportDataByDisplayNameIsInShouldWork() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where displayName in DEFAULT_DISPLAY_NAME or UPDATED_DISPLAY_NAME
        defaultReportDataShouldBeFound("displayName.in=" + DEFAULT_DISPLAY_NAME + "," + UPDATED_DISPLAY_NAME);

        // Get all the reportDataList where displayName equals to UPDATED_DISPLAY_NAME
        defaultReportDataShouldNotBeFound("displayName.in=" + UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void getAllReportDataByDisplayNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        // Get all the reportDataList where displayName is not null
        defaultReportDataShouldBeFound("displayName.specified=true");

        // Get all the reportDataList where displayName is null
        defaultReportDataShouldNotBeFound("displayName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReportDataShouldBeFound(String filter) throws Exception {
        restReportDataMockMvc.perform(get("/api/report-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].esIndex").value(hasItem(DEFAULT_ES_INDEX)))
            .andExpect(jsonPath("$.[*].cronInfo").value(hasItem(DEFAULT_CRON_INFO.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)));

        // Check, that the count call also returns 1
        restReportDataMockMvc.perform(get("/api/report-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReportDataShouldNotBeFound(String filter) throws Exception {
        restReportDataMockMvc.perform(get("/api/report-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportDataMockMvc.perform(get("/api/report-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReportData() throws Exception {
        // Get the reportData
        restReportDataMockMvc.perform(get("/api/report-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportData() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        int databaseSizeBeforeUpdate = reportDataRepository.findAll().size();

        // Update the reportData
        ReportData updatedReportData = reportDataRepository.findById(reportData.getId()).get();
        // Disconnect from session so that the updates on updatedReportData are not directly saved in db
        em.detach(updatedReportData);
        updatedReportData
            .name(UPDATED_NAME)
            .esIndex(UPDATED_ES_INDEX)
            .cronInfo(UPDATED_CRON_INFO)
            .displayName(UPDATED_DISPLAY_NAME);
        ReportDataDTO reportDataDTO = reportDataMapper.toDto(updatedReportData);

        restReportDataMockMvc.perform(put("/api/report-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataDTO)))
            .andExpect(status().isOk());

        // Validate the ReportData in the database
        List<ReportData> reportDataList = reportDataRepository.findAll();
        assertThat(reportDataList).hasSize(databaseSizeBeforeUpdate);
        ReportData testReportData = reportDataList.get(reportDataList.size() - 1);
        assertThat(testReportData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportData.getEsIndex()).isEqualTo(UPDATED_ES_INDEX);
        assertThat(testReportData.getCronInfo()).isEqualTo(UPDATED_CRON_INFO);
        assertThat(testReportData.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingReportData() throws Exception {
        int databaseSizeBeforeUpdate = reportDataRepository.findAll().size();

        // Create the ReportData
        ReportDataDTO reportDataDTO = reportDataMapper.toDto(reportData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportDataMockMvc.perform(put("/api/report-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportData in the database
        List<ReportData> reportDataList = reportDataRepository.findAll();
        assertThat(reportDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportData() throws Exception {
        // Initialize the database
        reportDataRepository.saveAndFlush(reportData);

        int databaseSizeBeforeDelete = reportDataRepository.findAll().size();

        // Delete the reportData
        restReportDataMockMvc.perform(delete("/api/report-data/{id}", reportData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReportData> reportDataList = reportDataRepository.findAll();
        assertThat(reportDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportData.class);
        ReportData reportData1 = new ReportData();
        reportData1.setId(1L);
        ReportData reportData2 = new ReportData();
        reportData2.setId(reportData1.getId());
        assertThat(reportData1).isEqualTo(reportData2);
        reportData2.setId(2L);
        assertThat(reportData1).isNotEqualTo(reportData2);
        reportData1.setId(null);
        assertThat(reportData1).isNotEqualTo(reportData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDataDTO.class);
        ReportDataDTO reportDataDTO1 = new ReportDataDTO();
        reportDataDTO1.setId(1L);
        ReportDataDTO reportDataDTO2 = new ReportDataDTO();
        assertThat(reportDataDTO1).isNotEqualTo(reportDataDTO2);
        reportDataDTO2.setId(reportDataDTO1.getId());
        assertThat(reportDataDTO1).isEqualTo(reportDataDTO2);
        reportDataDTO2.setId(2L);
        assertThat(reportDataDTO1).isNotEqualTo(reportDataDTO2);
        reportDataDTO1.setId(null);
        assertThat(reportDataDTO1).isNotEqualTo(reportDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportDataMapper.fromId(null)).isNull();
    }
}
