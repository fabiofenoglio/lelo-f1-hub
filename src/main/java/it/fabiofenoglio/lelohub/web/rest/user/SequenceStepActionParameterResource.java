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
import it.fabiofenoglio.lelohub.service.SequenceStepActionParameterQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepActionParameterService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionParameterDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionParameter}.
 */
@RestController
@RequestMapping("/api")
public class SequenceStepActionParameterResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionParameterResource.class);

    private static final String ENTITY_NAME = "sequenceStepActionParameter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepActionParameterService sequenceStepActionParameterService;

    private final SequenceStepActionParameterQueryService sequenceStepActionParameterQueryService;

    public SequenceStepActionParameterResource(SequenceStepActionParameterService sequenceStepActionParameterService, SequenceStepActionParameterQueryService sequenceStepActionParameterQueryService) {
        this.sequenceStepActionParameterService = sequenceStepActionParameterService;
        this.sequenceStepActionParameterQueryService = sequenceStepActionParameterQueryService;
    }

    /**
     * {@code POST  /sequence-step-action-parameters} : Create a new sequenceStepActionParameter.
     *
     * @param sequenceStepActionParameterDTO the sequenceStepActionParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepActionParameterDTO, or with status {@code 400 (Bad Request)} if the sequenceStepActionParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-action-parameters")
    public ResponseEntity<SequenceStepActionParameterDTO> createSequenceStepActionParameter(@Valid @RequestBody SequenceStepActionParameterDTO sequenceStepActionParameterDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepActionParameter : {}", sequenceStepActionParameterDTO);
        if (sequenceStepActionParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepActionParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepActionParameterDTO result = sequenceStepActionParameterService.save(sequenceStepActionParameterDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-action-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-action-parameters} : Updates an existing sequenceStepActionParameter.
     *
     * @param sequenceStepActionParameterDTO the sequenceStepActionParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepActionParameterDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepActionParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepActionParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-action-parameters")
    public ResponseEntity<SequenceStepActionParameterDTO> updateSequenceStepActionParameter(@Valid @RequestBody SequenceStepActionParameterDTO sequenceStepActionParameterDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepActionParameter : {}", sequenceStepActionParameterDTO);
        if (sequenceStepActionParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepActionParameterDTO result = sequenceStepActionParameterService.save(sequenceStepActionParameterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepActionParameterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-action-parameters} : get all the sequenceStepActionParameters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepActionParameters in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-action-parameters")
    public ResponseEntity<List<SequenceStepActionParameterDTO>> getAllSequenceStepActionParameters(SequenceStepActionParameterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepActionParameters by criteria: {}", criteria);
        Page<SequenceStepActionParameterDTO> page = sequenceStepActionParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-action-parameters/count} : count all the sequenceStepActionParameters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-step-action-parameters/count")
    public ResponseEntity<Long> countSequenceStepActionParameters(SequenceStepActionParameterCriteria criteria) {
        log.debug("REST request to count SequenceStepActionParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepActionParameterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-action-parameters/:id} : get the "id" sequenceStepActionParameter.
     *
     * @param id the id of the sequenceStepActionParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepActionParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-parameters/{id}")
    public ResponseEntity<SequenceStepActionParameterDTO> getSequenceStepActionParameter(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepActionParameter : {}", id);
        Optional<SequenceStepActionParameterDTO> sequenceStepActionParameterDTO = sequenceStepActionParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepActionParameterDTO);
    }

    /**
     * {@code DELETE  /sequence-step-action-parameters/:id} : delete the "id" sequenceStepActionParameter.
     *
     * @param id the id of the sequenceStepActionParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-action-parameters/{id}")
    public ResponseEntity<Void> deleteSequenceStepActionParameter(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepActionParameter : {}", id);
        sequenceStepActionParameterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
