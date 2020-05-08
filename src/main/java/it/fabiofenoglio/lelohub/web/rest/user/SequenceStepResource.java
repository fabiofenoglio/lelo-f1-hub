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
import it.fabiofenoglio.lelohub.service.SequenceStepQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStep}.
 */
@RestController
@RequestMapping("/api")
public class SequenceStepResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepResource.class);

    private static final String ENTITY_NAME = "sequenceStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepService sequenceStepService;

    private final SequenceStepQueryService sequenceStepQueryService;

    public SequenceStepResource(SequenceStepService sequenceStepService, SequenceStepQueryService sequenceStepQueryService) {
        this.sequenceStepService = sequenceStepService;
        this.sequenceStepQueryService = sequenceStepQueryService;
    }

    /**
     * {@code POST  /sequence-steps} : Create a new sequenceStep.
     *
     * @param sequenceStepDTO the sequenceStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepDTO, or with status {@code 400 (Bad Request)} if the sequenceStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-steps")
    public ResponseEntity<SequenceStepDTO> createSequenceStep(@Valid @RequestBody SequenceStepDTO sequenceStepDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStep : {}", sequenceStepDTO);
        if (sequenceStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepDTO result = sequenceStepService.save(sequenceStepDTO);
        return ResponseEntity.created(new URI("/api/sequence-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-steps} : Updates an existing sequenceStep.
     *
     * @param sequenceStepDTO the sequenceStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-steps")
    public ResponseEntity<SequenceStepDTO> updateSequenceStep(@Valid @RequestBody SequenceStepDTO sequenceStepDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStep : {}", sequenceStepDTO);
        if (sequenceStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepDTO result = sequenceStepService.save(sequenceStepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-steps} : get all the sequenceSteps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceSteps in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-steps")
    public ResponseEntity<List<SequenceStepDTO>> getAllSequenceSteps(SequenceStepCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceSteps by criteria: {}", criteria);
        Page<SequenceStepDTO> page = sequenceStepQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-steps/count} : count all the sequenceSteps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-steps/count")
    public ResponseEntity<Long> countSequenceSteps(SequenceStepCriteria criteria) {
        log.debug("REST request to count SequenceSteps by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-steps/:id} : get the "id" sequenceStep.
     *
     * @param id the id of the sequenceStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-steps/{id}")
    public ResponseEntity<SequenceStepDTO> getSequenceStep(@PathVariable Long id) {
        log.debug("REST request to get SequenceStep : {}", id);
        Optional<SequenceStepDTO> sequenceStepDTO = sequenceStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepDTO);
    }

    /**
     * {@code DELETE  /sequence-steps/:id} : delete the "id" sequenceStep.
     *
     * @param id the id of the sequenceStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-steps/{id}")
    public ResponseEntity<Void> deleteSequenceStep(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStep : {}", id);
        sequenceStepService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
