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
import it.fabiofenoglio.lelohub.service.SequenceService;
import it.fabiofenoglio.lelohub.service.dto.SequenceCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceListDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceUpdateDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.Sequence}.
 */
@RestController
@RequestMapping("/api")
public class SequenceResource {

    private final Logger log = LoggerFactory.getLogger(SequenceResource.class);

    private static final String ENTITY_NAME = "sequence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceService sequenceService;

    public SequenceResource(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    /**
     * {@code POST  /sequences} : Create a new sequence.
     *
     * @param sequenceDTO the sequenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceDTO, or with status {@code 400 (Bad Request)} if the sequence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.USER )
    @PostMapping("/sequences")
    public ResponseEntity<SequenceDTO> createSequence(@Valid @RequestBody SequenceUpdateDTO sequenceDTO) throws URISyntaxException {
        log.debug("REST request to save Sequence : {}", sequenceDTO);
        if (sequenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequence cannot already have an ID", ENTITY_NAME, "idexists");
        }
		SequenceDTO result = sequenceService.create(sequenceDTO);
        return ResponseEntity.created(new URI("/api/sequences/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequences} : Updates an existing sequence.
     *
     * @param sequenceDTO the sequenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.USER )
    @PutMapping("/sequences")
    public ResponseEntity<SequenceDTO> updateSequence(@Valid @RequestBody SequenceUpdateDTO sequenceDTO) throws URISyntaxException {
        log.debug("REST request to update Sequence : {}", sequenceDTO);
        if (sequenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceDTO result = sequenceService.update(sequenceDTO);
        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequences} : get all the sequences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequences in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequences")
    public ResponseEntity<List<SequenceListDTO>> getAllSequences(SequenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Sequences by criteria: {}", criteria);
        Page<SequenceListDTO> page = sequenceService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequences/count} : count all the sequences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequences/count")
    public ResponseEntity<Long> countSequences(SequenceCriteria criteria) {
        log.debug("REST request to count Sequences by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequences/:id} : get the "id" sequence.
     *
     * @param id the id of the sequenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @GetMapping("/sequences/{id}")
    public ResponseEntity<SequenceDTO> getSequence(@PathVariable Long id) {
        log.debug("REST request to get Sequence : {}", id);
        Optional<SequenceDTO> sequenceDTO = sequenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceDTO);
    }

    /**
     * {@code DELETE  /sequences/:id} : delete the "id" sequence.
     *
     * @param id the id of the sequenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.USER )
    @DeleteMapping("/sequences/{id}")
    public ResponseEntity<Void> deleteSequence(@PathVariable Long id) {
        log.debug("REST request to delete Sequence : {}", id);
        sequenceService.delete(id);
        return ResponseEntity.noContent()
//        		.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
        		.build();
    }
    
    @Secured ( AuthoritiesConstants.USER )
    @PostMapping("/sequences/{id}/clone")
    public ResponseEntity<SequenceDTO> cloneSequence(@PathVariable Long id, @RequestBody SequenceUpdateDTO withPayload) throws URISyntaxException {
        log.debug("REST request to clone Sequence : {}", id);
        SequenceDTO result = sequenceService.clone(id, withPayload);
        return ResponseEntity.created(new URI("/api/sequences/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
