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
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionDefinitionService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDefinitionDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepConditionDefinition}.
 */
@Secured ( AuthoritiesConstants.ADMIN )
@RestController
@RequestMapping("/api")
public class SequenceStepConditionDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionDefinitionResource.class);

    private static final String ENTITY_NAME = "sequenceStepConditionDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepConditionDefinitionService sequenceStepConditionDefinitionService;

    private final SequenceStepConditionDefinitionQueryService sequenceStepConditionDefinitionQueryService;

    public SequenceStepConditionDefinitionResource(SequenceStepConditionDefinitionService sequenceStepConditionDefinitionService, SequenceStepConditionDefinitionQueryService sequenceStepConditionDefinitionQueryService) {
        this.sequenceStepConditionDefinitionService = sequenceStepConditionDefinitionService;
        this.sequenceStepConditionDefinitionQueryService = sequenceStepConditionDefinitionQueryService;
    }

    /**
     * {@code POST  /sequence-step-condition-definitions} : Create a new sequenceStepConditionDefinition.
     *
     * @param sequenceStepConditionDefinitionDTO the sequenceStepConditionDefinitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepConditionDefinitionDTO, or with status {@code 400 (Bad Request)} if the sequenceStepConditionDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-condition-definitions")
    public ResponseEntity<SequenceStepConditionDefinitionDTO> createSequenceStepConditionDefinition(@Valid @RequestBody SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepConditionDefinition : {}", sequenceStepConditionDefinitionDTO);
        if (sequenceStepConditionDefinitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepConditionDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepConditionDefinitionDTO result = sequenceStepConditionDefinitionService.save(sequenceStepConditionDefinitionDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-condition-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-condition-definitions} : Updates an existing sequenceStepConditionDefinition.
     *
     * @param sequenceStepConditionDefinitionDTO the sequenceStepConditionDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepConditionDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepConditionDefinitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepConditionDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-condition-definitions")
    public ResponseEntity<SequenceStepConditionDefinitionDTO> updateSequenceStepConditionDefinition(@Valid @RequestBody SequenceStepConditionDefinitionDTO sequenceStepConditionDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepConditionDefinition : {}", sequenceStepConditionDefinitionDTO);
        if (sequenceStepConditionDefinitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepConditionDefinitionDTO result = sequenceStepConditionDefinitionService.save(sequenceStepConditionDefinitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepConditionDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-condition-definitions} : get all the sequenceStepConditionDefinitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepConditionDefinitions in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-definitions")
    public ResponseEntity<List<SequenceStepConditionDefinitionDTO>> getAllSequenceStepConditionDefinitions(SequenceStepConditionDefinitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepConditionDefinitions by criteria: {}", criteria);
        Page<SequenceStepConditionDefinitionDTO> page = sequenceStepConditionDefinitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-condition-definitions/count} : count all the sequenceStepConditionDefinitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-definitions/count")
    public ResponseEntity<Long> countSequenceStepConditionDefinitions(SequenceStepConditionDefinitionCriteria criteria) {
        log.debug("REST request to count SequenceStepConditionDefinitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepConditionDefinitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-condition-definitions/:id} : get the "id" sequenceStepConditionDefinition.
     *
     * @param id the id of the sequenceStepConditionDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepConditionDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-condition-definitions/{id}")
    public ResponseEntity<SequenceStepConditionDefinitionDTO> getSequenceStepConditionDefinition(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepConditionDefinition : {}", id);
        Optional<SequenceStepConditionDefinitionDTO> sequenceStepConditionDefinitionDTO = sequenceStepConditionDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepConditionDefinitionDTO);
    }

    /**
     * {@code DELETE  /sequence-step-condition-definitions/:id} : delete the "id" sequenceStepConditionDefinition.
     *
     * @param id the id of the sequenceStepConditionDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-condition-definitions/{id}")
    public ResponseEntity<Void> deleteSequenceStepConditionDefinition(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepConditionDefinition : {}", id);
        sequenceStepConditionDefinitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
