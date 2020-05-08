package it.fabiofenoglio.lelohub.web.rest.user;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.fabiofenoglio.lelohub.security.AuthoritiesConstants;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionParameterQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionParameterService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinitionParameter}.
 */
@Secured ( AuthoritiesConstants.ADMIN )
@RestController
@RequestMapping("/api")
public class SequenceStepConditionDefinitionParameterResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionDefinitionParameterResource.class);

    private static final String ENTITY_NAME = "sequenceStepConditionDefinitionParameter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepConditionDefinitionParameterService sequenceStepConditionDefinitionParameterService;

    private final SequenceStepConditionDefinitionParameterQueryService sequenceStepConditionDefinitionParameterQueryService;

    public SequenceStepConditionDefinitionParameterResource(SequenceStepConditionDefinitionParameterService sequenceStepConditionDefinitionParameterService, SequenceStepConditionDefinitionParameterQueryService sequenceStepConditionDefinitionParameterQueryService) {
        this.sequenceStepConditionDefinitionParameterService = sequenceStepConditionDefinitionParameterService;
        this.sequenceStepConditionDefinitionParameterQueryService = sequenceStepConditionDefinitionParameterQueryService;
    }

    /**
     * {@code POST  /sequence-step-condition-definition-parameters} : Create a new sequenceStepConditionDefinitionParameter.
     *
     * @param sequenceStepConditionDefinitionParameterDTO the sequenceStepConditionDefinitionParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepConditionDefinitionParameterDTO, or with status {@code 400 (Bad Request)} if the sequenceStepConditionDefinitionParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-condition-definition-parameters")
    public ResponseEntity<SequenceStepConditionDefinitionParameterDTO> createSequenceStepConditionDefinitionParameter(@Valid @RequestBody SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepConditionDefinitionParameter : {}", sequenceStepConditionDefinitionParameterDTO);
        if (sequenceStepConditionDefinitionParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepConditionDefinitionParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepConditionDefinitionParameterDTO result = sequenceStepConditionDefinitionParameterService.save(sequenceStepConditionDefinitionParameterDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-condition-definition-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-condition-definition-parameters} : Updates an existing sequenceStepConditionDefinitionParameter.
     *
     * @param sequenceStepConditionDefinitionParameterDTO the sequenceStepConditionDefinitionParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepConditionDefinitionParameterDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepConditionDefinitionParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepConditionDefinitionParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-condition-definition-parameters")
    public ResponseEntity<SequenceStepConditionDefinitionParameterDTO> updateSequenceStepConditionDefinitionParameter(@Valid @RequestBody SequenceStepConditionDefinitionParameterDTO sequenceStepConditionDefinitionParameterDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepConditionDefinitionParameter : {}", sequenceStepConditionDefinitionParameterDTO);
        if (sequenceStepConditionDefinitionParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepConditionDefinitionParameterDTO result = sequenceStepConditionDefinitionParameterService.save(sequenceStepConditionDefinitionParameterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepConditionDefinitionParameterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-condition-definition-parameters} : get all the sequenceStepConditionDefinitionParameters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepConditionDefinitionParameters in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-definition-parameters")
    public ResponseEntity<List<SequenceStepConditionDefinitionParameterDTO>> getAllSequenceStepConditionDefinitionParameters(SequenceStepConditionDefinitionParameterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepConditionDefinitionParameters by criteria: {}", criteria);
        Page<SequenceStepConditionDefinitionParameterDTO> page = sequenceStepConditionDefinitionParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-condition-definition-parameters/count} : count all the sequenceStepConditionDefinitionParameters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-definition-parameters/count")
    public ResponseEntity<Long> countSequenceStepConditionDefinitionParameters(SequenceStepConditionDefinitionParameterCriteria criteria) {
        log.debug("REST request to count SequenceStepConditionDefinitionParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepConditionDefinitionParameterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-condition-definition-parameters/:id} : get the "id" sequenceStepConditionDefinitionParameter.
     *
     * @param id the id of the sequenceStepConditionDefinitionParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepConditionDefinitionParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-definition-parameters/{id}")
    public ResponseEntity<SequenceStepConditionDefinitionParameterDTO> getSequenceStepConditionDefinitionParameter(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepConditionDefinitionParameter : {}", id);
        Optional<SequenceStepConditionDefinitionParameterDTO> sequenceStepConditionDefinitionParameterDTO = sequenceStepConditionDefinitionParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepConditionDefinitionParameterDTO);
    }

    /**
     * {@code DELETE  /sequence-step-condition-definition-parameters/:id} : delete the "id" sequenceStepConditionDefinitionParameter.
     *
     * @param id the id of the sequenceStepConditionDefinitionParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-condition-definition-parameters/{id}")
    public ResponseEntity<Void> deleteSequenceStepConditionDefinitionParameter(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepConditionDefinitionParameter : {}", id);
        sequenceStepConditionDefinitionParameterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
