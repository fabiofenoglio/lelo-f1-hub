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
import it.fabiofenoglio.lelohub.service.SequenceStepConditionQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepConditionService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepConditionDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepCondition}.
 */
@RestController
@RequestMapping("/api")
public class SequenceStepConditionResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepConditionResource.class);

    private static final String ENTITY_NAME = "sequenceStepCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepConditionService sequenceStepConditionService;

    private final SequenceStepConditionQueryService sequenceStepConditionQueryService;

    public SequenceStepConditionResource(SequenceStepConditionService sequenceStepConditionService, SequenceStepConditionQueryService sequenceStepConditionQueryService) {
        this.sequenceStepConditionService = sequenceStepConditionService;
        this.sequenceStepConditionQueryService = sequenceStepConditionQueryService;
    }

    /**
     * {@code POST  /sequence-step-conditions} : Create a new sequenceStepCondition.
     *
     * @param sequenceStepConditionDTO the sequenceStepConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepConditionDTO, or with status {@code 400 (Bad Request)} if the sequenceStepCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-conditions")
    public ResponseEntity<SequenceStepConditionDTO> createSequenceStepCondition(@Valid @RequestBody SequenceStepConditionDTO sequenceStepConditionDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepCondition : {}", sequenceStepConditionDTO);
        if (sequenceStepConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepConditionDTO result = sequenceStepConditionService.save(sequenceStepConditionDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-conditions} : Updates an existing sequenceStepCondition.
     *
     * @param sequenceStepConditionDTO the sequenceStepConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepConditionDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-conditions")
    public ResponseEntity<SequenceStepConditionDTO> updateSequenceStepCondition(@Valid @RequestBody SequenceStepConditionDTO sequenceStepConditionDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepCondition : {}", sequenceStepConditionDTO);
        if (sequenceStepConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepConditionDTO result = sequenceStepConditionService.save(sequenceStepConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepConditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-conditions} : get all the sequenceStepConditions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepConditions in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-conditions")
    public ResponseEntity<List<SequenceStepConditionDTO>> getAllSequenceStepConditions(SequenceStepConditionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepConditions by criteria: {}", criteria);
        Page<SequenceStepConditionDTO> page = sequenceStepConditionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-conditions/count} : count all the sequenceStepConditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-conditions/count")
    public ResponseEntity<Long> countSequenceStepConditions(SequenceStepConditionCriteria criteria) {
        log.debug("REST request to count SequenceStepConditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepConditionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-conditions/:id} : get the "id" sequenceStepCondition.
     *
     * @param id the id of the sequenceStepConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-conditions/{id}")
    public ResponseEntity<SequenceStepConditionDTO> getSequenceStepCondition(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepCondition : {}", id);
        Optional<SequenceStepConditionDTO> sequenceStepConditionDTO = sequenceStepConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepConditionDTO);
    }

    /**
     * {@code DELETE  /sequence-step-conditions/:id} : delete the "id" sequenceStepCondition.
     *
     * @param id the id of the sequenceStepConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-conditions/{id}")
    public ResponseEntity<Void> deleteSequenceStepCondition(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepCondition : {}", id);
        sequenceStepConditionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
