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
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionParameterQueryService;
import it.fabiofenoglio.lelohub.service.SequenceStepActionDefinitionParameterService;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceStepActionDefinitionParameterDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceStepActionDefinitionParameter}.
 */
@RestController
@RequestMapping("/api")
public class SequenceStepActionDefinitionParameterResource {

    private final Logger log = LoggerFactory.getLogger(SequenceStepActionDefinitionParameterResource.class);

    private static final String ENTITY_NAME = "sequenceStepActionDefinitionParameter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceStepActionDefinitionParameterService sequenceStepActionDefinitionParameterService;

    private final SequenceStepActionDefinitionParameterQueryService sequenceStepActionDefinitionParameterQueryService;

    public SequenceStepActionDefinitionParameterResource(SequenceStepActionDefinitionParameterService sequenceStepActionDefinitionParameterService, SequenceStepActionDefinitionParameterQueryService sequenceStepActionDefinitionParameterQueryService) {
        this.sequenceStepActionDefinitionParameterService = sequenceStepActionDefinitionParameterService;
        this.sequenceStepActionDefinitionParameterQueryService = sequenceStepActionDefinitionParameterQueryService;
    }

    /**
     * {@code POST  /sequence-step-action-definition-parameters} : Create a new sequenceStepActionDefinitionParameter.
     *
     * @param sequenceStepActionDefinitionParameterDTO the sequenceStepActionDefinitionParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceStepActionDefinitionParameterDTO, or with status {@code 400 (Bad Request)} if the sequenceStepActionDefinitionParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-step-action-definition-parameters")
    public ResponseEntity<SequenceStepActionDefinitionParameterDTO> createSequenceStepActionDefinitionParameter(@Valid @RequestBody SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceStepActionDefinitionParameter : {}", sequenceStepActionDefinitionParameterDTO);
        if (sequenceStepActionDefinitionParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceStepActionDefinitionParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceStepActionDefinitionParameterDTO result = sequenceStepActionDefinitionParameterService.save(sequenceStepActionDefinitionParameterDTO);
        return ResponseEntity.created(new URI("/api/sequence-step-action-definition-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-step-action-definition-parameters} : Updates an existing sequenceStepActionDefinitionParameter.
     *
     * @param sequenceStepActionDefinitionParameterDTO the sequenceStepActionDefinitionParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceStepActionDefinitionParameterDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceStepActionDefinitionParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceStepActionDefinitionParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-step-action-definition-parameters")
    public ResponseEntity<SequenceStepActionDefinitionParameterDTO> updateSequenceStepActionDefinitionParameter(@Valid @RequestBody SequenceStepActionDefinitionParameterDTO sequenceStepActionDefinitionParameterDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceStepActionDefinitionParameter : {}", sequenceStepActionDefinitionParameterDTO);
        if (sequenceStepActionDefinitionParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceStepActionDefinitionParameterDTO result = sequenceStepActionDefinitionParameterService.save(sequenceStepActionDefinitionParameterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceStepActionDefinitionParameterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-step-action-definition-parameters} : get all the sequenceStepActionDefinitionParameters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceStepActionDefinitionParameters in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-definition-parameters")
    public ResponseEntity<List<SequenceStepActionDefinitionParameterDTO>> getAllSequenceStepActionDefinitionParameters(SequenceStepActionDefinitionParameterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceStepActionDefinitionParameters by criteria: {}", criteria);
        Page<SequenceStepActionDefinitionParameterDTO> page = sequenceStepActionDefinitionParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-step-action-definition-parameters/count} : count all the sequenceStepActionDefinitionParameters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-definition-parameters/count")
    public ResponseEntity<Long> countSequenceStepActionDefinitionParameters(SequenceStepActionDefinitionParameterCriteria criteria) {
        log.debug("REST request to count SequenceStepActionDefinitionParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceStepActionDefinitionParameterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-step-action-definition-parameters/:id} : get the "id" sequenceStepActionDefinitionParameter.
     *
     * @param id the id of the sequenceStepActionDefinitionParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceStepActionDefinitionParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequence-step-action-definition-parameters/{id}")
    public ResponseEntity<SequenceStepActionDefinitionParameterDTO> getSequenceStepActionDefinitionParameter(@PathVariable Long id) {
        log.debug("REST request to get SequenceStepActionDefinitionParameter : {}", id);
        Optional<SequenceStepActionDefinitionParameterDTO> sequenceStepActionDefinitionParameterDTO = sequenceStepActionDefinitionParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceStepActionDefinitionParameterDTO);
    }

    /**
     * {@code DELETE  /sequence-step-action-definition-parameters/:id} : delete the "id" sequenceStepActionDefinitionParameter.
     *
     * @param id the id of the sequenceStepActionDefinitionParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-step-action-definition-parameters/{id}")
    public ResponseEntity<Void> deleteSequenceStepActionDefinitionParameter(@PathVariable Long id) {
        log.debug("REST request to delete SequenceStepActionDefinitionParameter : {}", id);
        sequenceStepActionDefinitionParameterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
