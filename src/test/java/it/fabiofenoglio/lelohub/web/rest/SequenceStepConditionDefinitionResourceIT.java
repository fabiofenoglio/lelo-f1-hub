package it.fabiofenoglio.lelohub.web.rest;

import it.fabiofenoglio.lelohub.LeloHubApp;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition;
import it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter;
import it.fabiofenoglio.lelohub.repository.SequenceStepConditionDefinitionRepository;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepConditionDefinitionMapper;
import it.fabiofenoglio.lelohub.web.rest.user.SequenceStepConditionDefinitionResource;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionCriteria;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionQueryService;

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
/**
 * Integration tests for the {@link SequenceStepConditionDefinitionResource} REST controller.
 */
@SpringBootTest(classes = LeloHubApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SequenceStepConditionDefinitionResourceIT {

    private static final SequenceStepConditionGeneration DEFAULT_GENERATION = SequenceStepConditionGeneration.GEN1;
    private static final SequenceStepConditionGeneration UPDATED_GENERATION = SequenceStepConditionGeneration.GEN1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SequenceStepConditionDefinitionRepository sequenceStepConditionDefinitionRepository;

    @Autowired
    private SequenceStepConditionDefinitionMapper sequenceStepConditionDefinitionMapper;

    @Autowired
    private SequenceStepConditionDefinitionService sequenceStepConditionDefinitionService;

    @Autowired
    private SequenceStepConditionDefinitionQueryService sequenceStepConditionDefinitionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenceStepConditionDefinitionMockMvc;

    private SequenceStepConditionDefinition sequenceStepConditionDefinition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepConditionDefinition createEntity(EntityManager em) {
        SequenceStepConditionDefinition sequenceStepConditionDefinition = new SequenceStepConditionDefinition()
            .generation(DEFAULT_GENERATION)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return sequenceStepConditionDefinition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepConditionDefinition createUpdatedEntity(EntityManager em) {
        SequenceStepConditionDefinition sequenceStepConditionDefinition = new SequenceStepConditionDefinition()
            .generation(UPDATED_GENERATION)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return sequenceStepConditionDefinition;
    }

    @BeforeEach
    public void initTest() {
        sequenceStepConditionDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createSequenceStepConditionDefinition() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepConditionDefinitionRepository.findAll().size();

        // Create the SequenceStepConditionDefinition
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinition);
        restSequenceStepConditionDefinitionMockMvc.perform(post("/api/sequence-step-condition-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the SequenceStepConditionDefinition in the database
        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceStepConditionDefinition testSequenceStepConditionDefinition = sequenceStepConditionDefinitionList.get(sequenceStepConditionDefinitionList.size() - 1);
        assertThat(testSequenceStepConditionDefinition.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testSequenceStepConditionDefinition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSequenceStepConditionDefinition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSequenceStepConditionDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepConditionDefinitionRepository.findAll().size();

        // Create the SequenceStepConditionDefinition with an existing ID
        sequenceStepConditionDefinition.setId(1L);
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceStepConditionDefinitionMockMvc.perform(post("/api/sequence-step-condition-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepConditionDefinition in the database
        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinition.setGeneration(null);

        // Create the SequenceStepConditionDefinition, which fails.
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinition);

        restSequenceStepConditionDefinitionMockMvc.perform(post("/api/sequence-step-condition-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepConditionDefinitionRepository.findAll().size();
        // set the field null
        sequenceStepConditionDefinition.setCode(null);

        // Create the SequenceStepConditionDefinition, which fails.
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinition);

        restSequenceStepConditionDefinitionMockMvc.perform(post("/api/sequence-step-condition-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitions() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepConditionDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSequenceStepConditionDefinition() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get the sequenceStepConditionDefinition
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions/{id}", sequenceStepConditionDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceStepConditionDefinition.getId().intValue()))
            .andExpect(jsonPath("$.generation").value(DEFAULT_GENERATION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getSequenceStepConditionDefinitionsByIdFiltering() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        Long id = sequenceStepConditionDefinition.getId();

        defaultSequenceStepConditionDefinitionShouldBeFound("id.equals=" + id);
        defaultSequenceStepConditionDefinitionShouldNotBeFound("id.notEquals=" + id);

        defaultSequenceStepConditionDefinitionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenceStepConditionDefinitionShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenceStepConditionDefinitionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenceStepConditionDefinitionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByGenerationIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where generation equals to DEFAULT_GENERATION
        defaultSequenceStepConditionDefinitionShouldBeFound("generation.equals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepConditionDefinitionList where generation equals to UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("generation.equals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByGenerationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where generation not equals to DEFAULT_GENERATION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("generation.notEquals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepConditionDefinitionList where generation not equals to UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionShouldBeFound("generation.notEquals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByGenerationIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where generation in DEFAULT_GENERATION or UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionShouldBeFound("generation.in=" + DEFAULT_GENERATION + "," + UPDATED_GENERATION);

        // Get all the sequenceStepConditionDefinitionList where generation equals to UPDATED_GENERATION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("generation.in=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByGenerationIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where generation is not null
        defaultSequenceStepConditionDefinitionShouldBeFound("generation.specified=true");

        // Get all the sequenceStepConditionDefinitionList where generation is null
        defaultSequenceStepConditionDefinitionShouldNotBeFound("generation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where code equals to DEFAULT_CODE
        defaultSequenceStepConditionDefinitionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionList where code equals to UPDATED_CODE
        defaultSequenceStepConditionDefinitionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where code not equals to DEFAULT_CODE
        defaultSequenceStepConditionDefinitionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionList where code not equals to UPDATED_CODE
        defaultSequenceStepConditionDefinitionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSequenceStepConditionDefinitionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the sequenceStepConditionDefinitionList where code equals to UPDATED_CODE
        defaultSequenceStepConditionDefinitionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where code is not null
        defaultSequenceStepConditionDefinitionShouldBeFound("code.specified=true");

        // Get all the sequenceStepConditionDefinitionList where code is null
        defaultSequenceStepConditionDefinitionShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where code contains DEFAULT_CODE
        defaultSequenceStepConditionDefinitionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionList where code contains UPDATED_CODE
        defaultSequenceStepConditionDefinitionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where code does not contain DEFAULT_CODE
        defaultSequenceStepConditionDefinitionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the sequenceStepConditionDefinitionList where code does not contain UPDATED_CODE
        defaultSequenceStepConditionDefinitionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where description equals to DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where description not equals to DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionList where description not equals to UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where description is not null
        defaultSequenceStepConditionDefinitionShouldBeFound("description.specified=true");

        // Get all the sequenceStepConditionDefinitionList where description is null
        defaultSequenceStepConditionDefinitionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where description contains DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionList where description contains UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        // Get all the sequenceStepConditionDefinitionList where description does not contain DEFAULT_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepConditionDefinitionList where description does not contain UPDATED_DESCRIPTION
        defaultSequenceStepConditionDefinitionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSequenceStepConditionDefinitionsByParametersIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);
        SequenceStepConditionDefinitionParameter parameters = SequenceStepConditionDefinitionParameterResourceIT.createEntity(em);
        em.persist(parameters);
        em.flush();
        sequenceStepConditionDefinition.addParameters(parameters);
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);
        Long parametersId = parameters.getId();

        // Get all the sequenceStepConditionDefinitionList where parameters equals to parametersId
        defaultSequenceStepConditionDefinitionShouldBeFound("parametersId.equals=" + parametersId);

        // Get all the sequenceStepConditionDefinitionList where parameters equals to parametersId + 1
        defaultSequenceStepConditionDefinitionShouldNotBeFound("parametersId.equals=" + (parametersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenceStepConditionDefinitionShouldBeFound(String filter) throws Exception {
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepConditionDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenceStepConditionDefinitionShouldNotBeFound(String filter) throws Exception {
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSequenceStepConditionDefinition() throws Exception {
        // Get the sequenceStepConditionDefinition
        restSequenceStepConditionDefinitionMockMvc.perform(get("/api/sequence-step-condition-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSequenceStepConditionDefinition() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        int databaseSizeBeforeUpdate = sequenceStepConditionDefinitionRepository.findAll().size();

        // Update the sequenceStepConditionDefinition
        SequenceStepConditionDefinition updatedSequenceStepConditionDefinition = sequenceStepConditionDefinitionRepository.findById(sequenceStepConditionDefinition.getId()).get();
        // Disconnect from session so that the updates on updatedSequenceStepConditionDefinition are not directly saved in db
        em.detach(updatedSequenceStepConditionDefinition);
        updatedSequenceStepConditionDefinition
            .generation(UPDATED_GENERATION)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionMapper.toDto(updatedSequenceStepConditionDefinition);

        restSequenceStepConditionDefinitionMockMvc.perform(put("/api/sequence-step-condition-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionDTO)))
            .andExpect(status().isOk());

        // Validate the SequenceStepConditionDefinition in the database
        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeUpdate);
        SequenceStepConditionDefinition testSequenceStepConditionDefinition = sequenceStepConditionDefinitionList.get(sequenceStepConditionDefinitionList.size() - 1);
        assertThat(testSequenceStepConditionDefinition.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testSequenceStepConditionDefinition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSequenceStepConditionDefinition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSequenceStepConditionDefinition() throws Exception {
        int databaseSizeBeforeUpdate = sequenceStepConditionDefinitionRepository.findAll().size();

        // Create the SequenceStepConditionDefinition
        SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionMapper.toDto(sequenceStepConditionDefinition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceStepConditionDefinitionMockMvc.perform(put("/api/sequence-step-condition-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepConditionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepConditionDefinition in the database
        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSequenceStepConditionDefinition() throws Exception {
        // Initialize the database
        sequenceStepConditionDefinitionRepository.saveAndFlush(sequenceStepConditionDefinition);

        int databaseSizeBeforeDelete = sequenceStepConditionDefinitionRepository.findAll().size();

        // Delete the sequenceStepConditionDefinition
        restSequenceStepConditionDefinitionMockMvc.perform(delete("/api/sequence-step-condition-definitions/{id}", sequenceStepConditionDefinition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceStepConditionDefinition> sequenceStepConditionDefinitionList = sequenceStepConditionDefinitionRepository.findAll();
        assertThat(sequenceStepConditionDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
