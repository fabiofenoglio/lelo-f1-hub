package it.fabiofenoglio.lelohub.web.rest;

import it.fabiofenoglio.lelohub.LeloHubApp;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition;
import it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter;
import it.fabiofenoglio.lelohub.repository.SequenceStepActionDefinitionRepository;
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionDTO;
import it.fabiofenoglio.lelohub.service.mapper.SequenceStepActionDefinitionMapper;
import it.fabiofenoglio.lelohub.web.rest.user.SequenceStepActionDefinitionResource;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionCriteria;
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionQueryService;

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
/**
 * Integration tests for the {@link SequenceStepActionDefinitionResource} REST controller.
 */
@SpringBootTest(classes = LeloHubApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SequenceStepActionDefinitionResourceIT {

    private static final SequenceStepActionGeneration DEFAULT_GENERATION = SequenceStepActionGeneration.GEN1;
    private static final SequenceStepActionGeneration UPDATED_GENERATION = SequenceStepActionGeneration.GEN1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SequenceStepActionDefinitionRepository sequenceStepActionDefinitionRepository;

    @Autowired
    private SequenceStepActionDefinitionMapper sequenceStepActionDefinitionMapper;

    @Autowired
    private SequenceStepActionDefinitionService sequenceStepActionDefinitionService;

    @Autowired
    private SequenceStepActionDefinitionQueryService sequenceStepActionDefinitionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenceStepActionDefinitionMockMvc;

    private SequenceStepActionDefinition sequenceStepActionDefinition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepActionDefinition createEntity(EntityManager em) {
        SequenceStepActionDefinition sequenceStepActionDefinition = new SequenceStepActionDefinition()
            .generation(DEFAULT_GENERATION)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return sequenceStepActionDefinition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceStepActionDefinition createUpdatedEntity(EntityManager em) {
        SequenceStepActionDefinition sequenceStepActionDefinition = new SequenceStepActionDefinition()
            .generation(UPDATED_GENERATION)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return sequenceStepActionDefinition;
    }

    @BeforeEach
    public void initTest() {
        sequenceStepActionDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createSequenceStepActionDefinition() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepActionDefinitionRepository.findAll().size();

        // Create the SequenceStepActionDefinition
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinition);
        restSequenceStepActionDefinitionMockMvc.perform(post("/api/sequence-step-action-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the SequenceStepActionDefinition in the database
        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceStepActionDefinition testSequenceStepActionDefinition = sequenceStepActionDefinitionList.get(sequenceStepActionDefinitionList.size() - 1);
        assertThat(testSequenceStepActionDefinition.getGeneration()).isEqualTo(DEFAULT_GENERATION);
        assertThat(testSequenceStepActionDefinition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSequenceStepActionDefinition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSequenceStepActionDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sequenceStepActionDefinitionRepository.findAll().size();

        // Create the SequenceStepActionDefinition with an existing ID
        sequenceStepActionDefinition.setId(1L);
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceStepActionDefinitionMockMvc.perform(post("/api/sequence-step-action-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepActionDefinition in the database
        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinition.setGeneration(null);

        // Create the SequenceStepActionDefinition, which fails.
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinition);

        restSequenceStepActionDefinitionMockMvc.perform(post("/api/sequence-step-action-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sequenceStepActionDefinitionRepository.findAll().size();
        // set the field null
        sequenceStepActionDefinition.setCode(null);

        // Create the SequenceStepActionDefinition, which fails.
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinition);

        restSequenceStepActionDefinitionMockMvc.perform(post("/api/sequence-step-action-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitions() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepActionDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSequenceStepActionDefinition() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get the sequenceStepActionDefinition
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions/{id}", sequenceStepActionDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceStepActionDefinition.getId().intValue()))
            .andExpect(jsonPath("$.generation").value(DEFAULT_GENERATION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getSequenceStepActionDefinitionsByIdFiltering() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        Long id = sequenceStepActionDefinition.getId();

        defaultSequenceStepActionDefinitionShouldBeFound("id.equals=" + id);
        defaultSequenceStepActionDefinitionShouldNotBeFound("id.notEquals=" + id);

        defaultSequenceStepActionDefinitionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenceStepActionDefinitionShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenceStepActionDefinitionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenceStepActionDefinitionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByGenerationIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where generation equals to DEFAULT_GENERATION
        defaultSequenceStepActionDefinitionShouldBeFound("generation.equals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepActionDefinitionList where generation equals to UPDATED_GENERATION
        defaultSequenceStepActionDefinitionShouldNotBeFound("generation.equals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByGenerationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where generation not equals to DEFAULT_GENERATION
        defaultSequenceStepActionDefinitionShouldNotBeFound("generation.notEquals=" + DEFAULT_GENERATION);

        // Get all the sequenceStepActionDefinitionList where generation not equals to UPDATED_GENERATION
        defaultSequenceStepActionDefinitionShouldBeFound("generation.notEquals=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByGenerationIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where generation in DEFAULT_GENERATION or UPDATED_GENERATION
        defaultSequenceStepActionDefinitionShouldBeFound("generation.in=" + DEFAULT_GENERATION + "," + UPDATED_GENERATION);

        // Get all the sequenceStepActionDefinitionList where generation equals to UPDATED_GENERATION
        defaultSequenceStepActionDefinitionShouldNotBeFound("generation.in=" + UPDATED_GENERATION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByGenerationIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where generation is not null
        defaultSequenceStepActionDefinitionShouldBeFound("generation.specified=true");

        // Get all the sequenceStepActionDefinitionList where generation is null
        defaultSequenceStepActionDefinitionShouldNotBeFound("generation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where code equals to DEFAULT_CODE
        defaultSequenceStepActionDefinitionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionList where code equals to UPDATED_CODE
        defaultSequenceStepActionDefinitionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where code not equals to DEFAULT_CODE
        defaultSequenceStepActionDefinitionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionList where code not equals to UPDATED_CODE
        defaultSequenceStepActionDefinitionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSequenceStepActionDefinitionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the sequenceStepActionDefinitionList where code equals to UPDATED_CODE
        defaultSequenceStepActionDefinitionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where code is not null
        defaultSequenceStepActionDefinitionShouldBeFound("code.specified=true");

        // Get all the sequenceStepActionDefinitionList where code is null
        defaultSequenceStepActionDefinitionShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where code contains DEFAULT_CODE
        defaultSequenceStepActionDefinitionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionList where code contains UPDATED_CODE
        defaultSequenceStepActionDefinitionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where code does not contain DEFAULT_CODE
        defaultSequenceStepActionDefinitionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the sequenceStepActionDefinitionList where code does not contain UPDATED_CODE
        defaultSequenceStepActionDefinitionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where description equals to DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where description not equals to DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionList where description not equals to UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionList where description equals to UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where description is not null
        defaultSequenceStepActionDefinitionShouldBeFound("description.specified=true");

        // Get all the sequenceStepActionDefinitionList where description is null
        defaultSequenceStepActionDefinitionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where description contains DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionList where description contains UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        // Get all the sequenceStepActionDefinitionList where description does not contain DEFAULT_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the sequenceStepActionDefinitionList where description does not contain UPDATED_DESCRIPTION
        defaultSequenceStepActionDefinitionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSequenceStepActionDefinitionsByParametersIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);
        SequenceStepActionDefinitionParameter parameters = SequenceStepActionDefinitionParameterResourceIT.createEntity(em);
        em.persist(parameters);
        em.flush();
        sequenceStepActionDefinition.addParameters(parameters);
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);
        Long parametersId = parameters.getId();

        // Get all the sequenceStepActionDefinitionList where parameters equals to parametersId
        defaultSequenceStepActionDefinitionShouldBeFound("parametersId.equals=" + parametersId);

        // Get all the sequenceStepActionDefinitionList where parameters equals to parametersId + 1
        defaultSequenceStepActionDefinitionShouldNotBeFound("parametersId.equals=" + (parametersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenceStepActionDefinitionShouldBeFound(String filter) throws Exception {
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceStepActionDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].generation").value(hasItem(DEFAULT_GENERATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenceStepActionDefinitionShouldNotBeFound(String filter) throws Exception {
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSequenceStepActionDefinition() throws Exception {
        // Get the sequenceStepActionDefinition
        restSequenceStepActionDefinitionMockMvc.perform(get("/api/sequence-step-action-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSequenceStepActionDefinition() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        int databaseSizeBeforeUpdate = sequenceStepActionDefinitionRepository.findAll().size();

        // Update the sequenceStepActionDefinition
        SequenceStepActionDefinition updatedSequenceStepActionDefinition = sequenceStepActionDefinitionRepository.findById(sequenceStepActionDefinition.getId()).get();
        // Disconnect from session so that the updates on updatedSequenceStepActionDefinition are not directly saved in db
        em.detach(updatedSequenceStepActionDefinition);
        updatedSequenceStepActionDefinition
            .generation(UPDATED_GENERATION)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionMapper.toDto(updatedSequenceStepActionDefinition);

        restSequenceStepActionDefinitionMockMvc.perform(put("/api/sequence-step-action-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionDTO)))
            .andExpect(status().isOk());

        // Validate the SequenceStepActionDefinition in the database
        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeUpdate);
        SequenceStepActionDefinition testSequenceStepActionDefinition = sequenceStepActionDefinitionList.get(sequenceStepActionDefinitionList.size() - 1);
        assertThat(testSequenceStepActionDefinition.getGeneration()).isEqualTo(UPDATED_GENERATION);
        assertThat(testSequenceStepActionDefinition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSequenceStepActionDefinition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSequenceStepActionDefinition() throws Exception {
        int databaseSizeBeforeUpdate = sequenceStepActionDefinitionRepository.findAll().size();

        // Create the SequenceStepActionDefinition
        SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionMapper.toDto(sequenceStepActionDefinition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceStepActionDefinitionMockMvc.perform(put("/api/sequence-step-action-definitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sequenceStepActionDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SequenceStepActionDefinition in the database
        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSequenceStepActionDefinition() throws Exception {
        // Initialize the database
        sequenceStepActionDefinitionRepository.saveAndFlush(sequenceStepActionDefinition);

        int databaseSizeBeforeDelete = sequenceStepActionDefinitionRepository.findAll().size();

        // Delete the sequenceStepActionDefinition
        restSequenceStepActionDefinitionMockMvc.perform(delete("/api/sequence-step-action-definitions/{id}", sequenceStepActionDefinition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceStepActionDefinition> sequenceStepActionDefinitionList = sequenceStepActionDefinitionRepository.findAll();
        assertThat(sequenceStepActionDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
