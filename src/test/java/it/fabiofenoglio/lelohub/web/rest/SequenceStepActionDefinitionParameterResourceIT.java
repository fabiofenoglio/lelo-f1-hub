package it.fabiofenoglio.lelohub.web.rest;

import it.fabiofenoglio.lelohub.LeloHubApp;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition;
import it.fabiofenoglio.lelohub.repository.SequenceStepActionDefinitionParameterRepository;
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionParameterService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionDefinitionParameterMapper;
import it.fabiofenoglio.lelohub.web.rest.user.SequenceStepActionDefinitionParameterResource;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterCriteria;
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionParameterQueryService;

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

import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionGeneration;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionDefinitionParameterType;
import it.fabiofenoglio.lelohub.domain.enumeration.SequenceStepActionEvaluationEngine;
/**
 * Integration tests for the {@link SequenceStepActionDefinitionParameterResource} REST controller.
 */
@SpringBootTest(classes = LeloHubApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SequenceStepActionDefinitionParameterResourceIT {

    private static final SequenceStepActionGeneration DEFAULT_GENERATION = SequenceStepActionGeneration.GEN1;
    private static final SequenceStepActionGeneration UPDATED_GENERATION = SequenceStepActionGeneration.GEN1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final SequenceStepActionDefinitionParameterType DEFAULT_TYPE = SequenceStepActionDefinitionParameterType.STRING;
    private static final SequenceStepActionDefinitionParameterType UPDATED_TYPE = SequenceStepActionDefinitionParameterType.NUMBER;

    private static final SequenceStepActionEvaluationEngine DEFAULT_EVALUATION_ENGINE = SequenceStepActionEvaluationEngine.DEFAULT_ENGINE;
    private static final SequenceStepActionEvaluationEngine UPDATED_EVALUATION_ENGINE = SequenceStepActionEvaluationEngine.DEFAULT_ENGINE;

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
    private SequenceStepActionDefinitionParameterRepository sequenceStepActionDefinitionParameterRepository;

    @Autowired
    private SequenceStepActionDefinitionParameterMapper sequenceStepActionDefinitionParameterMapper;

    @Autowired
    private SequenceStepActionDefinitionParameterService sequenceStepActionDefinitionParameterService;

    @Autowired
    private SequenceStepActionDefinitionParameterQueryService sequenceStepActionDefinitionParameterQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenceStepActionDefinitionParameterMockMvc;

    private SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepActionDefinitionParameter createEntity(EntityManager em) {
        SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter = new SequenceStepActionDefinitionParameter()
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
        SequenceStepActionDefinition sequenceStepActionDefinition;
        if (TestUtil.findAll(em, SequenceStepActionDefinition.class).isEmpty()) {
            sequenceStepActionDefinition = SequenceStepActionDefinitionResourceIT.createEntity(em);
            em.persist(sequenceStepActionDefinition);
            em.flush();
        } else {
            sequenceStepActionDefinition = TestUtil.findAll(em, SequenceStepActionDefinition.class).get(0);
        }
        sequenceStepActionDefinitionParameter.setDefinition(sequenceStepActionDefinition);
        return sequenceStepActionDefinitionParameter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepActionDefinitionParameter createUpdatedEntity(EntityManager em) {
        SequenceStepActionDefinitionParameter sequenceStepActionDefinitionParameter = new SequenceStepActionDefinitionParameter()
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
        SequenceStepActionDefinition sequenceStepActionDefinition;
        if (TestUtil.findAll(em, SequenceStepActionDefinition.class).isEmpty()) {
            sequenceStepActionDefinition = SequenceStepActionDefinitionResourceIT.createUpdatedEntity(em);
            em.persist(sequenceStepActionDefinition);
            em.flush();
        } else {
            sequenceStepActionDefinition = TestUtil.findAll(em, SequenceStepActionDefinition.class).get(0);
        }
        sequenceStepActionDefinitionParameter.setDefinition(sequenceStepActionDefinition);
        return sequenceStepActionDefinitionParameter;
    }

    @BeforeEach
    public void initTest() {
        sequenceStepActionDefinitionParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createSequenceStepActionDefinitionParameter() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepActionDefinitionParameterRepository.findAll().size();

        // Create the SequenceStepActionDefinitionParameter
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);
        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isCreated());

        // Validate the SequenceStepActionDefinitionParameter in the database
        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceStepActionDefinitionParameter testSequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameterList.get(sequenceStepActionDefinitionParameterList.size() - 1);
        assertThat(testSequenceStepActionDefinitionParameter.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testSequenceStepActionDefinitionParameter.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSequenceStepActionDefinitionParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSequenceStepActionDefinitionParameter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSequenceStepActionDefinitionParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSequenceStepActionDefinitionParameter.getEvaluationEngine()).isEqualTo(DEFAULT_EVALUATION_ENGINE);
        assertThat(testSequenceStepActionDefinitionParameter.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testSequenceStepActionDefinitionParameter.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testSequenceStepActionDefinitionParameter.getStepValue()).isEqualTo(DEFAULT_STEP_VALUE);
        assertThat(testSequenceStepActionDefinitionParameter.getMinLength()).isEqualTo(DEFAULT_MIN_LENGTH);
        assertThat(testSequenceStepActionDefinitionParameter.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void createSequenceStepActionDefinitionParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepActionDefinitionParameterRepository.findAll().size();

        // Create the SequenceStepActionDefinitionParameter with an existing ID
        sequenceStepActionDefinitionParameter.setId(1L);
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepActionDefinitionParameter in the database
        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinitionParameter.setGeneration(null);

        // Create the SequenceStepActionDefinitionParameter, which fails.
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinitionParameter.setCode(null);

        // Create the SequenceStepActionDefinitionParameter, which fails.
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinitionParameter.setName(null);

        // Create the SequenceStepActionDefinitionParameter, which fails.
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinitionParameter.setType(null);

        // Create the SequenceStepActionDefinitionParameter, which fails.
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEvaluationEngineIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionParameterRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinitionParameter.setEvaluationEngine(null);

        // Create the SequenceStepActionDefinitionParameter, which fails.
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        restSequenceStepActionDefinitionParameterMockMvc.perform(post("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParameters() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepActionDefinitionParameter.getId().intValue())))
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
    public void getSequenceStepActionDefinitionParameter() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get the sequenceStepActionDefinitionParameter
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters/{id}", sequenceStepActionDefinitionParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceStepActionDefinitionParameter.getId().intValue()))
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
    public void getSequenceStepActionDefinitionParametersByIdFiltering() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        Long id = sequenceStepActionDefinitionParameter.getId();

        defaultSequenceStepActionDefinitionParameterShouldBeFound("id.equals=" + id);
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("id.notEquals=" + id);

        defaultSequenceStepActionDefinitionParameterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenceStepActionDefinitionParameterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByGenerationIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where generation equals to DEFAULT_GENERATION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("generation.equals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepActionDefinitionParameterList where generation equals to UPDATED_GENERATION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("generation.equals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByGenerationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where generation not equals to DEFAULT_GENERATION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("generation.notEquals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepActionDefinitionParameterList where generation not equals to UPDATED_GENERATION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("generation.notEquals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByGenerationIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where generation in DEFAULT_GENERATION or UPDATED_GENERATION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("generation.in=" + DEFAULT_GENERATION + "," + UPDATED_GENERATION);

        // Get all the sequenceStepActionDefinitionParameterList where generation equals to UPDATED_GENERATION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("generation.in=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByGenerationIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where generation is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("generation.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where generation is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("generation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where code equals to DEFAULT_CODE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionParameterList where code equals to UPDATED_CODE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where code not equals to DEFAULT_CODE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionParameterList where code not equals to UPDATED_CODE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the sequenceStepActionDefinitionParameterList where code equals to UPDATED_CODE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where code is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("code.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where code is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByCodeContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where code contains DEFAULT_CODE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionParameterList where code contains UPDATED_CODE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where code does not contain DEFAULT_CODE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionParameterList where code does not contain UPDATED_CODE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where name equals to DEFAULT_NAME
        defaultSequenceStepActionDefinitionParameterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the sequenceStepActionDefinitionParameterList where name equals to UPDATED_NAME
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where name not equals to DEFAULT_NAME
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the sequenceStepActionDefinitionParameterList where name not equals to UPDATED_NAME
        defaultSequenceStepActionDefinitionParameterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSequenceStepActionDefinitionParameterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the sequenceStepActionDefinitionParameterList where name equals to UPDATED_NAME
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where name is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("name.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where name is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByNameContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where name contains DEFAULT_NAME
        defaultSequenceStepActionDefinitionParameterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the sequenceStepActionDefinitionParameterList where name contains UPDATED_NAME
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where name does not contain DEFAULT_NAME
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the sequenceStepActionDefinitionParameterList where name does not contain UPDATED_NAME
        defaultSequenceStepActionDefinitionParameterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where description equals to DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionParameterList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where description not equals to DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionParameterList where description not equals to UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionParameterList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where description is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("description.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where description is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where description contains DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionParameterList where description contains UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where description does not contain DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionParameterList where description does not contain UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionParameterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where type equals to DEFAULT_TYPE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the sequenceStepActionDefinitionParameterList where type equals to UPDATED_TYPE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where type not equals to DEFAULT_TYPE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the sequenceStepActionDefinitionParameterList where type not equals to UPDATED_TYPE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the sequenceStepActionDefinitionParameterList where type equals to UPDATED_TYPE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where type is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("type.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where type is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByEvaluationEngineIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine equals to DEFAULT_EVALUATION_ENGINE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("evaluationEngine.equals=" + DEFAULT_EVALUATION_ENGINE);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine equals to UPDATED_EVALUATION_ENGINE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("evaluationEngine.equals=" + UPDATED_EVALUATION_ENGINE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByEvaluationEngineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine not equals to DEFAULT_EVALUATION_ENGINE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("evaluationEngine.notEquals=" + DEFAULT_EVALUATION_ENGINE);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine not equals to UPDATED_EVALUATION_ENGINE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("evaluationEngine.notEquals=" + UPDATED_EVALUATION_ENGINE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByEvaluationEngineIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine in DEFAULT_EVALUATION_ENGINE or UPDATED_EVALUATION_ENGINE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("evaluationEngine.in=" + DEFAULT_EVALUATION_ENGINE + "," + UPDATED_EVALUATION_ENGINE);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine equals to UPDATED_EVALUATION_ENGINE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("evaluationEngine.in=" + UPDATED_EVALUATION_ENGINE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByEvaluationEngineIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("evaluationEngine.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where evaluationEngine is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("evaluationEngine.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue equals to DEFAULT_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.equals=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue equals to UPDATED_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.equals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue not equals to DEFAULT_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.notEquals=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue not equals to UPDATED_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.notEquals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue in DEFAULT_MIN_VALUE or UPDATED_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.in=" + DEFAULT_MIN_VALUE + "," + UPDATED_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue equals to UPDATED_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.in=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where minValue is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is greater than or equal to DEFAULT_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.greaterThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is greater than or equal to UPDATED_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.greaterThanOrEqual=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is less than or equal to DEFAULT_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.lessThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is less than or equal to SMALLER_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.lessThanOrEqual=" + SMALLER_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is less than DEFAULT_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.lessThan=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is less than UPDATED_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.lessThan=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is greater than DEFAULT_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minValue.greaterThan=" + DEFAULT_MIN_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where minValue is greater than SMALLER_MIN_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minValue.greaterThan=" + SMALLER_MIN_VALUE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue equals to DEFAULT_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.equals=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue equals to UPDATED_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.equals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue not equals to DEFAULT_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.notEquals=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue not equals to UPDATED_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.notEquals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue in DEFAULT_MAX_VALUE or UPDATED_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.in=" + DEFAULT_MAX_VALUE + "," + UPDATED_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue equals to UPDATED_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.in=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is greater than or equal to DEFAULT_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.greaterThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is greater than or equal to UPDATED_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.greaterThanOrEqual=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is less than or equal to DEFAULT_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.lessThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is less than or equal to SMALLER_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.lessThanOrEqual=" + SMALLER_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is less than DEFAULT_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.lessThan=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is less than UPDATED_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.lessThan=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is greater than DEFAULT_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxValue.greaterThan=" + DEFAULT_MAX_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where maxValue is greater than SMALLER_MAX_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxValue.greaterThan=" + SMALLER_MAX_VALUE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue equals to DEFAULT_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.equals=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue equals to UPDATED_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.equals=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue not equals to DEFAULT_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.notEquals=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue not equals to UPDATED_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.notEquals=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue in DEFAULT_STEP_VALUE or UPDATED_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.in=" + DEFAULT_STEP_VALUE + "," + UPDATED_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue equals to UPDATED_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.in=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is greater than or equal to DEFAULT_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.greaterThanOrEqual=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is greater than or equal to UPDATED_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.greaterThanOrEqual=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is less than or equal to DEFAULT_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.lessThanOrEqual=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is less than or equal to SMALLER_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.lessThanOrEqual=" + SMALLER_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is less than DEFAULT_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.lessThan=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is less than UPDATED_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.lessThan=" + UPDATED_STEP_VALUE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByStepValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is greater than DEFAULT_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("stepValue.greaterThan=" + DEFAULT_STEP_VALUE);

        // Get all the sequenceStepActionDefinitionParameterList where stepValue is greater than SMALLER_STEP_VALUE
        defaultSequenceStepActionDefinitionParameterShouldBeFound("stepValue.greaterThan=" + SMALLER_STEP_VALUE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength equals to DEFAULT_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.equals=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength equals to UPDATED_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.equals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength not equals to DEFAULT_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.notEquals=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength not equals to UPDATED_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.notEquals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength in DEFAULT_MIN_LENGTH or UPDATED_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.in=" + DEFAULT_MIN_LENGTH + "," + UPDATED_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength equals to UPDATED_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.in=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where minLength is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is greater than or equal to DEFAULT_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.greaterThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is greater than or equal to UPDATED_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.greaterThanOrEqual=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is less than or equal to DEFAULT_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.lessThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is less than or equal to SMALLER_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.lessThanOrEqual=" + SMALLER_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is less than DEFAULT_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.lessThan=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is less than UPDATED_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.lessThan=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMinLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is greater than DEFAULT_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("minLength.greaterThan=" + DEFAULT_MIN_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where minLength is greater than SMALLER_MIN_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("minLength.greaterThan=" + SMALLER_MIN_LENGTH);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength equals to DEFAULT_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.equals=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength equals to UPDATED_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.equals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength not equals to DEFAULT_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.notEquals=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength not equals to UPDATED_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.notEquals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength in DEFAULT_MAX_LENGTH or UPDATED_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.in=" + DEFAULT_MAX_LENGTH + "," + UPDATED_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength equals to UPDATED_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.in=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is not null
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.specified=true");

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is null
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is greater than or equal to DEFAULT_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.greaterThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is greater than or equal to UPDATED_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.greaterThanOrEqual=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is less than or equal to DEFAULT_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.lessThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is less than or equal to SMALLER_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.lessThanOrEqual=" + SMALLER_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is less than DEFAULT_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.lessThan=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is less than UPDATED_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.lessThan=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByMaxLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is greater than DEFAULT_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("maxLength.greaterThan=" + DEFAULT_MAX_LENGTH);

        // Get all the sequenceStepActionDefinitionParameterList where maxLength is greater than SMALLER_MAX_LENGTH
        defaultSequenceStepActionDefinitionParameterShouldBeFound("maxLength.greaterThan=" + SMALLER_MAX_LENGTH);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionParametersByDefinitionIsEqualToSomething() throws Exception {
        // Get already existing entity
        SequenceStepActionDefinition definition = sequenceStepActionDefinitionParameter.getDefinition();
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);
        Long definitionId = definition.getId();

        // Get all the sequenceStepActionDefinitionParameterList where definition equals to definitionId
        defaultSequenceStepActionDefinitionParameterShouldBeFound("definitionId.equals=" + definitionId);

        // Get all the sequenceStepActionDefinitionParameterList where definition equals to definitionId + 1
        defaultSequenceStepActionDefinitionParameterShouldNotBeFound("definitionId.equals=" + (definitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenceStepActionDefinitionParameterShouldBeFound(String filter) throws Exception {
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepActionDefinitionParameter.getId().intValue())))
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
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenceStepActionDefinitionParameterShouldNotBeFound(String filter) throws Exception {
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSequenceStepActionDefinitionParameter() throws Exception {
        // Get the sequenceStepActionDefinitionParameter
        restSequenceStepActionDefinitionParameterMockMvc.perform(get("/api/sequence-step-action-definition-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSequenceStepActionDefinitionParameter() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        int databaseSizeBeforeUpdate = sequenceStepActionDefinitionParameterRepository.findAll().size();

        // Update the sequenceStepActionDefinitionParameter
        SequenceStepActionDefinitionParameter updatedSequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameterRepository.findById(sequenceStepActionDefinitionParameter.getId()).get();
        // Disconnect from session so that the updates on updatedSequenceStepActionDefinitionParameter are not directly saved in db
        em.detach(updatedSequenceStepActionDefinitionParameter);
        updatedSequenceStepActionDefinitionParameter
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
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(updatedSequenceStepActionDefinitionParameter);

        restSequenceStepActionDefinitionParameterMockMvc.perform(put("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isOk());

        // Validate the SequenceStepActionDefinitionParameter in the database
        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeUpdate);
        SequenceStepActionDefinitionParameter testSequenceStepActionDefinitionParameter = sequenceStepActionDefinitionParameterList.get(sequenceStepActionDefinitionParameterList.size() - 1);
        assertThat(testSequenceStepActionDefinitionParameter.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testSequenceStepActionDefinitionParameter.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSequenceStepActionDefinitionParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSequenceStepActionDefinitionParameter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSequenceStepActionDefinitionParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSequenceStepActionDefinitionParameter.getEvaluationEngine()).isEqualTo(UPDATED_EVALUATION_ENGINE);
        assertThat(testSequenceStepActionDefinitionParameter.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testSequenceStepActionDefinitionParameter.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testSequenceStepActionDefinitionParameter.getStepValue()).isEqualTo(UPDATED_STEP_VALUE);
        assertThat(testSequenceStepActionDefinitionParameter.getMinLength()).isEqualTo(UPDATED_MIN_LENGTH);
        assertThat(testSequenceStepActionDefinitionParameter.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void updateNonExistingSequenceStepActionDefinitionParameter() throws Exception {
        int databaseSizeBeforeUpdate = sequenceStepActionDefinitionParameterRepository.findAll().size();

        // Create the SequenceStepActionDefinitionParameter
        SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterMapper.toDto(sequenceStepActionDefinitionParameter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceStepActionDefinitionParameterMockMvc.perform(put("/api/sequence-step-action-definition-parameters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionParameterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepActionDefinitionParameter in the database
        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSequenceStepActionDefinitionParameter() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionParameterRepository.saveAndFlush(sequenceStepActionDefinitionParameter);

        int databaseSizeBeforeDelete = sequenceStepActionDefinitionParameterRepository.findAll().size();

        // Delete the sequenceStepActionDefinitionParameter
        restSequenceStepActionDefinitionParameterMockMvc.perform(delete("/api/sequence-step-action-definition-parameters/{id}", sequenceStepActionDefinitionParameter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceStepActionDefinitionParameter> sequenceStepActionDefinitionParameterList = sequenceStepActionDefinitionParameterRepository.findAll();
        assertThat(sequenceStepActionDefinitionParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
