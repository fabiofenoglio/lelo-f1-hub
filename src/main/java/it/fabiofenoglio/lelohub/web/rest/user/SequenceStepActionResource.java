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
import it.fabiofenoglio.lelohub.service.SequenceStepActionQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepActionService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepAction}.
 */
@RestController
@RequestMapping("/api")
public class SequenceStepActionResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionResource.class);

    private static final String ENTITY_NAME = "sequenceStepAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepActionService sequenceStepActionService;

    private final SequenceStepActionQueryService sequenceStepActionQueryService;

    public SequenceStepActionResource(SequenceStepActionService sequenceStepActionService, SequenceStepActionQueryService sequenceStepActionQueryService) {
        this.sequenceStepActionService = sequenceStepActionService;
        this.sequenceStepActionQueryService = sequenceStepActionQueryService;
    }

    /**
     * {@code POST  /sequence-step-actions} : Create a new sequenceStepAction.
     *
     * @param sequenceStepActionDTO the sequenceStepActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepActionDTO, or with status {@code 400 (Bad Request)} if the sequenceStepAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-actions")
    public ResponseEntity<SequenceStepActionDTO> createSequenceStepAction(@Valid @RequestBody SequenceStepActionDTO sequenceStepActionDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepAction : {}", sequenceStepActionDTO);
        if (sequenceStepActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepActionDTO result = sequenceStepActionService.save(sequenceStepActionDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-actions} : Updates an existing sequenceStepAction.
     *
     * @param sequenceStepActionDTO the sequenceStepActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepActionDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-actions")
    public ResponseEntity<SequenceStepActionDTO> updateSequenceStepAction(@Valid @RequestBody SequenceStepActionDTO sequenceStepActionDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepAction : {}", sequenceStepActionDTO);
        if (sequenceStepActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepActionDTO result = sequenceStepActionService.save(sequenceStepActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-actions} : get all the sequenceStepActions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepActions in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-actions")
    public ResponseEntity<List<SequenceStepActionDTO>> getAllSequenceStepActions(SequenceStepActionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepActions by criteria: {}", criteria);
        Page<SequenceStepActionDTO> page = sequenceStepActionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-actions/count} : count all the sequenceStepActions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-actions/count")
    public ResponseEntity<Long> countSequenceStepActions(SequenceStepActionCriteria criteria) {
        log.debug("REST request to count SequenceStepActions by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepActionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-actions/:id} : get the "id" sequenceStepAction.
     *
     * @param id the id of the sequenceStepActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepActionDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-actions/{id}")
    public ResponseEntity<SequenceStepActionDTO> getSequenceStepAction(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepAction : {}", id);
        Optional<SequenceStepActionDTO> sequenceStepActionDTO = sequenceStepActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepActionDTO);
    }

    /**
     * {@code DELETE  /sequence-step-actions/:id} : delete the "id" sequenceStepAction.
     *
     * @param id the id of the sequenceStepActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-actions/{id}")
    public ResponseEntity<Void> deleteSequenceStepAction(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepAction : {}", id);
        sequenceStepActionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
