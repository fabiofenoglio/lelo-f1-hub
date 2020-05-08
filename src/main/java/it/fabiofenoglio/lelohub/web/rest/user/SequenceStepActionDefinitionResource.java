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
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinition}.
 */
@Secured ( AuthoritiesConstants.ADMIN )
@RestController
@RequestMapping("/api")
public class SequenceStepActionDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionDefinitionResource.class);

    private static final String ENTITY_NAME = "sequenceStepActionDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepActionDefinitionService sequenceStepActionDefinitionService;

    private final SequenceStepActionDefinitionQueryService sequenceStepActionDefinitionQueryService;

    public SequenceStepActionDefinitionResource(SequenceStepActionDefinitionService sequenceStepActionDefinitionService, SequenceStepActionDefinitionQueryService sequenceStepActionDefinitionQueryService) {
        this.sequenceStepActionDefinitionService = sequenceStepActionDefinitionService;
        this.sequenceStepActionDefinitionQueryService = sequenceStepActionDefinitionQueryService;
    }

    /**
     * {@code POST  /sequence-step-action-definitions} : Create a new sequenceStepActionDefinition.
     *
     * @param sequenceStepActionDefinitionDTO the sequenceStepActionDefinitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepActionDefinitionDTO, or with status {@code 400 (Bad Request)} if the sequenceStepActionDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-action-definitions")
    public ResponseEntity<SequenceStepActionDefinitionDTO> createSequenceStepActionDefinition(@Valid @RequestBody SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepActionDefinition : {}", sequenceStepActionDefinitionDTO);
        if (sequenceStepActionDefinitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepActionDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepActionDefinitionDTO result = sequenceStepActionDefinitionService.save(sequenceStepActionDefinitionDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-action-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-action-definitions} : Updates an existing sequenceStepActionDefinition.
     *
     * @param sequenceStepActionDefinitionDTO the sequenceStepActionDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepActionDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepActionDefinitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepActionDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-action-definitions")
    public ResponseEntity<SequenceStepActionDefinitionDTO> updateSequenceStepActionDefinition(@Valid @RequestBody SequenceStepActionDefinitionDTO sequenceStepActionDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepActionDefinition : {}", sequenceStepActionDefinitionDTO);
        if (sequenceStepActionDefinitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepActionDefinitionDTO result = sequenceStepActionDefinitionService.save(sequenceStepActionDefinitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepActionDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-action-definitions} : get all the sequenceStepActionDefinitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepActionDefinitions in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-definitions")
    public ResponseEntity<List<SequenceStepActionDefinitionDTO>> getAllSequenceStepActionDefinitions(SequenceStepActionDefinitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepActionDefinitions by criteria: {}", criteria);
        Page<SequenceStepActionDefinitionDTO> page = sequenceStepActionDefinitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-action-definitions/count} : count all the sequenceStepActionDefinitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-definitions/count")
    public ResponseEntity<Long> countSequenceStepActionDefinitions(SequenceStepActionDefinitionCriteria criteria) {
        log.debug("REST request to count SequenceStepActionDefinitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepActionDefinitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-action-definitions/:id} : get the "id" sequenceStepActionDefinition.
     *
     * @param id the id of the sequenceStepActionDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepActionDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-definitions/{id}")
    public ResponseEntity<SequenceStepActionDefinitionDTO> getSequenceStepActionDefinition(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepActionDefinition : {}", id);
        Optional<SequenceStepActionDefinitionDTO> sequenceStepActionDefinitionDTO = sequenceStepActionDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepActionDefinitionDTO);
    }

    /**
     * {@code DELETE  /sequence-step-action-definitions/:id} : delete the "id" sequenceStepActionDefinition.
     *
     * @param id the id of the sequenceStepActionDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-action-definitions/{id}")
    public ResponseEntity<Void> deleteSequenceStepActionDefinition(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepActionDefinition : {}", id);
        sequenceStepActionDefinitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
