package it.fabiofenoglio.lelohub.web.rest;

import it.fabiofenoglio.lelohub.LeloHubApp;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition;
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionDefinitionParameterRepository;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionParameterService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionDefinitionParameterMapper;
import it.fabiofenoglio.lelohub.web.rest.user.SequenceStepConditionDefinitionParameterResource;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterCriteria;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionParameterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepConditionEvaluationEngine;
/**
 * Integration tests for the {@link SequenceStepConditionDefinitionParameterResource} REST controller.
 */
@SpringBootTest(classes = LeloHubApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SequenceStepConditionDefinitionParameterResourceIT {

    private static final SequenceStepConditionGeneration DEFAULT_GENERATION = SequenceStepConditionGeneration.GEN1;
    private static final SequenceStepConditionGeneration UPDATED_GENERATION = SequenceStepConditionGeneration.GEN1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final SequenceStepConditionDefinitionParameterType DEFAULT_TYPE = SequenceStepConditionDefinitionParameterType.STRING;
    private static final SequenceStepConditionDefinitionParameterType UPDATED_TYPE = SequenceStepConditionDefinitionParameterType.NUMBER;

    private static final SequenceStepConditionEvaluationEngine DEFAULT_EVALUATION_ENGINE = SequenceStepConditionEvaluationEngine.DEFAULT_ENGINE;
    private static final SequenceStepConditionEvaluationEngine UPDATED_EVALUATION_ENGINE = SequenceStepConditionEvaluationEngine.DEFAULT_ENGINE;

    private static final Double DEFAULT_MIN_VALUE = 1D;
    private static final Double UPDATED_MIN_VALUE = 2D;
    private static final Double SMALLER_MIN_VALUE = 1D - 1D;

    private static final Double DEFAULT_MAX_VALUE = 1D;
    private static final Double UPDATED_MAX_VALUE = 2D;
    private static final Double SMALLER_MAX_VALUE = 1D - 1D;

    private static final Double DEFAULT_STEP_VALUE = 1D;
    private static final Double UPDATED_STEP_VALUE = 2D;
    private static final Double SMALLER_STEP_VALUE = 1D - 1D;

    private static final Integer DEFAULT_MIN_LENGTH = 1;
    private static final Integer UPDATED_MIN_LENGTH = 2;
    private static final Integer SMALLER_MIN_LENGTH = 1 - 1;

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;
    private static final Integer SMALLER_MAX_LENGTH = 1 - 1;

    @Autowired
    private SequenceStepConditionDefinitionParameterRepository sequenceStepConditionDefinitionParameterRepository;

    @Autowired
    private SequenceStepConditionDefinitionParameterMapper sequenceStepConditionDefinitionParameterMapper;

    @Autowired
    private SequenceStepConditionDefinitionParameterService sequenceStepConditionDefinitionParameterService;

    @Autowired
    private SequenceStepConditionDefinitionParameterQueryService sequenceStepConditionDefinitionParameterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenceStepConditionDefinitionParameterMockMvc;

    private SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepConditionDefinitionParameter createEntity(EntityManager em) {
        SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter = new SequenceStepConditionDefinitionParameter()
            .generation(DEFAULT_GENERATION)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .evaluationEngine(DEFAULT_EVALUATION_ENGINE)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
            .stepValue(DEFAULT_STEP_VALUE)
            .minLength(DEFAULT_MIN_LENGTH)
            .maxLength(DEFAULT_MAX_LENGTH);
        // Add required entity
        SequenceStepConditionDefinition sequenceStepConditionDefinition;
        if (TestUtil.findAll(em, SequenceStepConditionDefinition.class).isEmpty()) {
            sequenceStepConditionDefinition = SequenceStepConditionDefinitionResourceIT.createEntity(em);
            em.persist(sequenceStepConditionDefinition);
            em.flush();
        } else {
            sequenceStepConditionDefinition = TestUtil.findAll(em, SequenceStepConditionDefinition.class).get(0);
        }
        sequenceStepConditionDefinitionParameter.setDefinition(sequenceStepConditionDefinition);
        return sequenceStepConditionDefinitionParameter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepConditionDefinitionParameter createUpdatedEntity(EntityManager em) {
        SequenceStepConditionDefinitionParameter sequenceStepConditionDefinitionParameter = new SequenceStepConditionDefinitionParameter()
            .generation(UPDATED_GENERATION)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .evaluationEngine(UPDATED_EVALUATION_ENGINE)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .stepValue(UPDATED_STEP_VALUE)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH);
        // Add required entity
        SequenceStepConditionDefinition sequenceStepConditionDefinition;
        if (TestUtil.findAll(em, SequenceStepConditionDefinition.class).isEmpty()) {
            sequenceStepConditionDefinition = SequenceStepConditionDefinitionResourceIT.createUpdatedEntity(em);
            em.persist(sequenceStepConditionDefinition);
            em.flush();
        } else {
            sequenceStepConditionDefinition = TestUtil.findAll(em, SequenceStepConditionDefinition.class).get(0);
        }
        sequenceStepConditionDefinitionParameter.setDefinition(sequenceStepConditionDefinition);
        return sequenceStepConditionDefinitionParameter;
    }

    @BeforeEach
    public void initTest() {
        sequenceStepConditionDefinitionParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createSequenceStepConditionDefinitionParameter() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepConditionDefinitionParameterRepository.findAll().size();

        // Create the SequenceStepConditionDefinitionParameter
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);
        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isCreated());

        // Validate the SequenceStepConditionDefinitionParameter in the database
        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceStepConditionDefinitionParameter testSequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameterList.get(sequenceStepConditionDefinitionParameterList.size() - 1);
        assertThat(testSequenceStepConditionDefinitionParameter.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testSequenceStepConditionDefinitionParameter.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSequenceStepConditionDefinitionParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSequenceStepConditionDefinitionParameter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSequenceStepConditionDefinitionParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSequenceStepConditionDefinitionParameter.getEvaluationEngine()).isEqualTo(DEFAULT_EVALUATION_ENGINE);
        assertThat(testSequenceStepConditionDefinitionParameter.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testSequenceStepConditionDefinitionParameter.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testSequenceStepConditionDefinitionParameter.getStepValue()).isEqualTo(DEFAULT_STEP_VALUE);
        assertThat(testSequenceStepConditionDefinitionParameter.getMinLength()).isEqualTo(DEFAULT_MIN_LENGTH);
        assertThat(testSequenceStepConditionDefinitionParameter.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void createSequenceStepConditionDefinitionParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepConditionDefinitionParameterRepository.findAll().size();

        // Create the SequenceStepConditionDefinitionParameter with an existing ID
        sequenceStepConditionDefinitionParameter.setId(1L);
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepConditionDefinitionParameter in the database
        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinitionParameter.setGeneration(null);

        // Create the SequenceStepConditionDefinitionParameter, which fails.
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinitionParameter.setCode(null);

        // Create the SequenceStepConditionDefinitionParameter, which fails.
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinitionParameter.setName(null);

        // Create the SequenceStepConditionDefinitionParameter, which fails.
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinitionParameter.setType(null);

        // Create the SequenceStepConditionDefinitionParameter, which fails.
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEvaluationEngineIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinitionParameter.setEvaluationEngine(null);

        // Create the SequenceStepConditionDefinitionParameter, which fails.
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        restSequenceStepConditionDefinitionParameterMockMvc.perform(post("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParameters() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepConditionDefinitionParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].evaluationEngine").value(hasItem(DEFAULT_EVALUATION_ENGINE.toString())))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stepValue").value(hasItem(DEFAULT_STEP_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)));
    }
    
    @Test
    @Transactional
    public void getSequenceStepConditionDefinitionParameter() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get the sequenceStepConditionDefinitionParameter
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters/{id}", sequenceStepConditionDefinitionParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceStepConditionDefinitionParameter.getId().intValue()))
            .andExpect(jsonPath("$.generation").value(DEFAULT_GENERATION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.evaluationEngine").value(DEFAULT_EVALUATION_ENGINE.toString()))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE.doubleValue()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.doubleValue()))
            .andExpect(jsonPath("$.stepValue").value(DEFAULT_STEP_VALUE.doubleValue()))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH));
    }


    @Test
    @Transactional
    public void getSequenceStepConditionDefinitionParametersByIdFiltering() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        Long id = sequenceStepConditionDefinitionParameter.getId();

        defaultSequenceStepConditionDefinitionParameterShouldBeFound("id.equals=" + id);
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("id.notEquals=" + id);

        defaultSequenceStepConditionDefinitionParameterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenceStepConditionDefinitionParameterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByGenerationIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where generation equals to DEFAULT_GENERATION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("generation.equals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepConditionDefinitionParameterList where generation equals to UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("generation.equals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByGenerationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where generation not equals to DEFAULT_GENERATION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("generation.notEquals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepConditionDefinitionParameterList where generation not equals to UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("generation.notEquals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByGenerationIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where generation in DEFAULT_GENERATION or UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("generation.in=" + DEFAULT_GENERATION + "," + UPDATED_GENERATION);

        // Get all the sequenceStepConditionDefinitionParameterList where generation equals to UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("generation.in=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByGenerationIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where generation is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("generation.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where generation is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("generation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where code equals to DEFAULT_CODE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionParameterList where code equals to UPDATED_CODE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where code not equals to DEFAULT_CODE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionParameterList where code not equals to UPDATED_CODE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the sequenceStepConditionDefinitionParameterList where code equals to UPDATED_CODE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where code is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("code.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where code is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByCodeContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where code contains DEFAULT_CODE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionParameterList where code contains UPDATED_CODE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where code does not contain DEFAULT_CODE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionParameterList where code does not contain UPDATED_CODE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where name equals to DEFAULT_NAME
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the sequenceStepConditionDefinitionParameterList where name equals to UPDATED_NAME
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where name not equals to DEFAULT_NAME
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the sequenceStepConditionDefinitionParameterList where name not equals to UPDATED_NAME
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the sequenceStepConditionDefinitionParameterList where name equals to UPDATED_NAME
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where name is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("name.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where name is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByNameContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where name contains DEFAULT_NAME
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the sequenceStepConditionDefinitionParameterList where name contains UPDATED_NAME
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where name does not contain DEFAULT_NAME
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the sequenceStepConditionDefinitionParameterList where name does not contain UPDATED_NAME
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where description equals to DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionParameterList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where description not equals to DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionParameterList where description not equals to UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionParameterList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where description is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("description.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where description is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where description contains DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionParameterList where description contains UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where description does not contain DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionParameterList where description does not contain UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where type equals to DEFAULT_TYPE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the sequenceStepConditionDefinitionParameterList where type equals to UPDATED_TYPE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where type not equals to DEFAULT_TYPE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the sequenceStepConditionDefinitionParameterList where type not equals to UPDATED_TYPE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the sequenceStepConditionDefinitionParameterList where type equals to UPDATED_TYPE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where type is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("type.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where type is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByEvaluationEngineIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine equals to DEFAULT_EVALUATION_ENGINE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("evaluationEngine.equals=" + DEFAULT_EVALUATION_ENGINE);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine equals to UPDATED_EVALUATION_ENGINE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("evaluationEngine.equals=" + UPDATED_EVALUATION_ENGINE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByEvaluationEngineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine not equals to DEFAULT_EVALUATION_ENGINE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("evaluationEngine.notEquals=" + DEFAULT_EVALUATION_ENGINE);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine not equals to UPDATED_EVALUATION_ENGINE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("evaluationEngine.notEquals=" + UPDATED_EVALUATION_ENGINE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByEvaluationEngineIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine in DEFAULT_EVALUATION_ENGINE or UPDATED_EVALUATION_ENGINE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("evaluationEngine.in=" + DEFAULT_EVALUATION_ENGINE + "," + UPDATED_EVALUATION_ENGINE);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine equals to UPDATED_EVALUATION_ENGINE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("evaluationEngine.in=" + UPDATED_EVALUATION_ENGINE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByEvaluationEngineIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("evaluationEngine.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where evaluationEngine is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("evaluationEngine.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue equals to DEFAULT_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.equals=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue equals to UPDATED_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.equals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue not equals to DEFAULT_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.notEquals=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue not equals to UPDATED_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.notEquals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue in DEFAULT_MIN_VALUE or UPDATED_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.in=" + DEFAULT_MIN_VALUE + "," + UPDATED_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue equals to UPDATED_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.in=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is greater than or equal to DEFAULT_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.greaterThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is greater than or equal to UPDATED_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.greaterThanOrEqual=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is less than or equal to DEFAULT_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.lessThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is less than or equal to SMALLER_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.lessThanOrEqual=" + SMALLER_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is less than DEFAULT_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.lessThan=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is less than UPDATED_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.lessThan=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is greater than DEFAULT_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minValue.greaterThan=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where minValue is greater than SMALLER_MIN_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minValue.greaterThan=" + SMALLER_MIN_VALUE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue equals to DEFAULT_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.equals=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue equals to UPDATED_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.equals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue not equals to DEFAULT_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.notEquals=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue not equals to UPDATED_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.notEquals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue in DEFAULT_MAX_VALUE or UPDATED_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.in=" + DEFAULT_MAX_VALUE + "," + UPDATED_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue equals to UPDATED_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.in=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is greater than or equal to DEFAULT_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.greaterThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is greater than or equal to UPDATED_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.greaterThanOrEqual=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is less than or equal to DEFAULT_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.lessThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is less than or equal to SMALLER_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.lessThanOrEqual=" + SMALLER_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is less than DEFAULT_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.lessThan=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is less than UPDATED_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.lessThan=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is greater than DEFAULT_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxValue.greaterThan=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where maxValue is greater than SMALLER_MAX_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxValue.greaterThan=" + SMALLER_MAX_VALUE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue equals to DEFAULT_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.equals=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue equals to UPDATED_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.equals=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue not equals to DEFAULT_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.notEquals=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue not equals to UPDATED_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.notEquals=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue in DEFAULT_STEP_VALUE or UPDATED_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.in=" + DEFAULT_STEP_VALUE + "," + UPDATED_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue equals to UPDATED_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.in=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is greater than or equal to DEFAULT_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.greaterThanOrEqual=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is greater than or equal to UPDATED_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.greaterThanOrEqual=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is less than or equal to DEFAULT_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.lessThanOrEqual=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is less than or equal to SMALLER_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.lessThanOrEqual=" + SMALLER_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is less than DEFAULT_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.lessThan=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is less than UPDATED_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.lessThan=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByStepValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is greater than DEFAULT_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("stepValue.greaterThan=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepConditionDefinitionParameterList where stepValue is greater than SMALLER_STEP_VALUE
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("stepValue.greaterThan=" + SMALLER_STEP_VALUE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength equals to DEFAULT_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.equals=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength equals to UPDATED_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.equals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength not equals to DEFAULT_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.notEquals=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength not equals to UPDATED_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.notEquals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength in DEFAULT_MIN_LENGTH or UPDATED_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.in=" + DEFAULT_MIN_LENGTH + "," + UPDATED_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength equals to UPDATED_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.in=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is greater than or equal to DEFAULT_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.greaterThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is greater than or equal to UPDATED_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.greaterThanOrEqual=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is less than or equal to DEFAULT_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.lessThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is less than or equal to SMALLER_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.lessThanOrEqual=" + SMALLER_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is less than DEFAULT_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.lessThan=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is less than UPDATED_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.lessThan=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMinLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is greater than DEFAULT_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("minLength.greaterThan=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where minLength is greater than SMALLER_MIN_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("minLength.greaterThan=" + SMALLER_MIN_LENGTH);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength equals to DEFAULT_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.equals=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength equals to UPDATED_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.equals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength not equals to DEFAULT_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.notEquals=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength not equals to UPDATED_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.notEquals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength in DEFAULT_MAX_LENGTH or UPDATED_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.in=" + DEFAULT_MAX_LENGTH + "," + UPDATED_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength equals to UPDATED_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.in=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is not null
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.specified=true");

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is null
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is greater than or equal to DEFAULT_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.greaterThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is greater than or equal to UPDATED_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.greaterThanOrEqual=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is less than or equal to DEFAULT_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.lessThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is less than or equal to SMALLER_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.lessThanOrEqual=" + SMALLER_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is less than DEFAULT_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.lessThan=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is less than UPDATED_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.lessThan=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByMaxLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is greater than DEFAULT_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("maxLength.greaterThan=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepConditionDefinitionParameterList where maxLength is greater than SMALLER_MAX_LENGTH
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("maxLength.greaterThan=" + SMALLER_MAX_LENGTH);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionParametersByDefinitionIsEqualToSomething() throws Exception {
        // Get already existing entity
        SequenceStepConditionDefinition definition = sequenceStepConditionDefinitionParameter.getDefinition();
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);
        Long definitionId = definition.getId();

        // Get all the sequenceStepConditionDefinitionParameterList where definition equals to definitionId
        defaultSequenceStepConditionDefinitionParameterShouldBeFound("definitionId.equals=" + definitionId);

        // Get all the sequenceStepConditionDefinitionParameterList where definition equals to definitionId + 1
        defaultSequenceStepConditionDefinitionParameterShouldNotBeFound("definitionId.equals=" + (definitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenceStepConditionDefinitionParameterShouldBeFound(String filter) throws Exception {
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepConditionDefinitionParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].evaluationEngine").value(hasItem(DEFAULT_EVALUATION_ENGINE.toString())))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stepValue").value(hasItem(DEFAULT_STEP_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)));

        // Check, that the count call also returns 1
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenceStepConditionDefinitionParameterShouldNotBeFound(String filter) throws Exception {
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSequenceStepConditionDefinitionParameter() throws Exception {
        // Get the sequenceStepConditionDefinitionParameter
        restSequenceStepConditionDefinitionParameterMockMvc.perform(get("/api/sequence-step-condition-definition-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSequenceStepConditionDefinitionParameter() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        int databaseSizeBeforeUpdate = sequenceStepConditionDefinitionParameterRepository.findAll().size();

        // Update the sequenceStepConditionDefinitionParameter
        SequenceStepConditionDefinitionParameter updatedSequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameterRepository.findById(sequenceStepConditionDefinitionParameter.getId()).get();
        // Disconnect from session so that the updates on updatedSequenceStepConditionDefinitionParameter are not directly saved in db
        em.detach(updatedSequenceStepConditionDefinitionParameter);
        updatedSequenceStepConditionDefinitionParameter
            .generation(UPDATED_GENERATION)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .evaluationEngine(UPDATED_EVALUATION_ENGINE)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .stepValue(UPDATED_STEP_VALUE)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH);
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(updatedSequenceStepConditionDefinitionParameter);

        restSequenceStepConditionDefinitionParameterMockMvc.perform(put("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isOk());

        // Validate the SequenceStepConditionDefinitionParameter in the database
        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeUpdate);
        SequenceStepConditionDefinitionParameter testSequenceStepConditionDefinitionParameter = sequenceStepConditionDefinitionParameterList.get(sequenceStepConditionDefinitionParameterList.size() - 1);
        assertThat(testSequenceStepConditionDefinitionParameter.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testSequenceStepConditionDefinitionParameter.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSequenceStepConditionDefinitionParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSequenceStepConditionDefinitionParameter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSequenceStepConditionDefinitionParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSequenceStepConditionDefinitionParameter.getEvaluationEngine()).isEqualTo(UPDATED_EVALUATION_ENGINE);
        assertThat(testSequenceStepConditionDefinitionParameter.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testSequenceStepConditionDefinitionParameter.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testSequenceStepConditionDefinitionParameter.getStepValue()).isEqualTo(UPDATED_STEP_VALUE);
        assertThat(testSequenceStepConditionDefinitionParameter.getMinLength()).isEqualTo(UPDATED_MIN_LENGTH);
        assertThat(testSequenceStepConditionDefinitionParameter.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void updateNonExistingSequenceStepConditionDefinitionParameter() throws Exception {
        int databaseSizeBeforeUpdate = sequenceStepConditionDefinitionParameterRepository.findAll().size();

        // Create the SequenceStepConditionDefinitionParameter
        SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterMapper.toDto(sequenceStepConditionDefinitionParameter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceStepConditionDefinitionParameterMockMvc.perform(put("/api/sequence-step-condition-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepConditionDefinitionParameter in the database
        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSequenceStepConditionDefinitionParameter() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionParameterRepository.saveAndFlush(sequenceStepConditionDefinitionParameter);

        int databaseSizeBeforeDelete = sequenceStepConditionDefinitionParameterRepository.findAll().size();

        // Delete the sequenceStepConditionDefinitionParameter
        restSequenceStepConditionDefinitionParameterMockMvc.perform(delete("/api/sequence-step-condition-definition-parameters/{id}", sequenceStepConditionDefinitionParameter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceStepConditionDefinitionParameter> sequenceStepConditionDefinitionParameterList = sequenceStepConditionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepConditionDefinitionParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
