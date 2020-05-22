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
import it.fabiofenoglio.lelohub.domain.enumeration.ObjectAccessAuthorization;
import it.fabiofenoglio.lelohub.security.AuthoritiesConstants;
import it.fabiofenoglio.lelohub.service.SequenceService;
import it.fabiofenoglio.lelohub.service.SequenceUserRatingQueryService;
import it.fabiofenoglio.lelohub.service.SequenceUserRatingService;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingCriteria;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingUnvoteActionDTO;
import it.fabiofenoglio.lelohub.service.dto.SequenceUserRatingVoteActionDTO;
import it.fabiofenoglio.lelohub.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.fabiofenoglio.lelohub.domain.SequenceUserRating}.
 */
@RestController
@RequestMapping("/api")
public class SequenceUserRatingResource {

    private final Logger log = LoggerFactory.getLogger(SequenceUserRatingResource.class);

    private static final String ENTITY_NAME = "sequenceUserRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SequenceUserRatingService sequenceUserRatingService;

    private final SequenceService sequenceService;

    private final SequenceUserRatingQueryService sequenceUserRatingQueryService;

    public SequenceUserRatingResource(
    		SequenceUserRatingService sequenceUserRatingService,
    		SequenceUserRatingQueryService sequenceUserRatingQueryService,
    		SequenceService sequenceService ) {
        this.sequenceUserRatingService = sequenceUserRatingService;
        this.sequenceUserRatingQueryService = sequenceUserRatingQueryService;
        this.sequenceService = sequenceService;
    }


    @Secured ( AuthoritiesConstants.USER )
    @PostMapping("/sequence-user-ratings/vote")
    public ResponseEntity<SequenceUserRatingDTO> vote(@Valid @RequestBody SequenceUserRatingVoteActionDTO sequenceUserRatingDTO) throws URISyntaxException {
        log.debug("REST request to vote SequenceUserRating : {}", sequenceUserRatingDTO);
        
        this.sequenceService.assertAuthorization(sequenceUserRatingDTO.getSequenceId(), ObjectAccessAuthorization.READ);
        
        SequenceUserRatingDTO result = sequenceUserRatingService.userVote(sequenceUserRatingDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @Secured ( AuthoritiesConstants.USER )
    @PostMapping("/sequence-user-ratings/unvote")
    public ResponseEntity<Void> vote(@Valid @RequestBody SequenceUserRatingUnvoteActionDTO sequenceUserRatingDTO) throws URISyntaxException {
        log.debug("REST request to vote SequenceUserRating : {}", sequenceUserRatingDTO);
        
        this.sequenceService.assertAuthorization(sequenceUserRatingDTO.getSequenceId(), ObjectAccessAuthorization.READ);
        
        sequenceUserRatingService.userUnVote(sequenceUserRatingDTO);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * {@code POST  /sequence-user-ratings} : Create a new sequenceUserRating.
     *
     * @param sequenceUserRatingDTO the sequenceUserRatingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sequenceUserRatingDTO, or with status {@code 400 (Bad Request)} if the sequenceUserRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PostMapping("/sequence-user-ratings")
    public ResponseEntity<SequenceUserRatingDTO> createSequenceUserRating(@Valid @RequestBody SequenceUserRatingDTO sequenceUserRatingDTO) throws URISyntaxException {
        log.debug("REST request to save SequenceUserRating : {}", sequenceUserRatingDTO);
        if (sequenceUserRatingDTO.getId() != null) {
            throw new BadRequestAlertException("A new sequenceUserRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SequenceUserRatingDTO result = sequenceUserRatingService.save(sequenceUserRatingDTO);
        return ResponseEntity.created(new URI("/api/sequence-user-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sequence-user-ratings} : Updates an existing sequenceUserRating.
     *
     * @param sequenceUserRatingDTO the sequenceUserRatingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sequenceUserRatingDTO,
     * or with status {@code 400 (Bad Request)} if the sequenceUserRatingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sequenceUserRatingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @PutMapping("/sequence-user-ratings")
    public ResponseEntity<SequenceUserRatingDTO> updateSequenceUserRating(@Valid @RequestBody SequenceUserRatingDTO sequenceUserRatingDTO) throws URISyntaxException {
        log.debug("REST request to update SequenceUserRating : {}", sequenceUserRatingDTO);
        if (sequenceUserRatingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SequenceUserRatingDTO result = sequenceUserRatingService.save(sequenceUserRatingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sequenceUserRatingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sequence-user-ratings} : get all the sequenceUserRatings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sequenceUserRatings in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-user-ratings")
    public ResponseEntity<List<SequenceUserRatingDTO>> getAllSequenceUserRatings(SequenceUserRatingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SequenceUserRatings by criteria: {}", criteria);
        Page<SequenceUserRatingDTO> page = sequenceUserRatingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sequence-user-ratings/count} : count all the sequenceUserRatings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-user-ratings/count")
    public ResponseEntity<Long> countSequenceUserRatings(SequenceUserRatingCriteria criteria) {
        log.debug("REST request to count SequenceUserRatings by criteria: {}", criteria);
        return ResponseEntity.ok().body(sequenceUserRatingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sequence-user-ratings/:id} : get the "id" sequenceUserRating.
     *
     * @param id the id of the sequenceUserRatingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sequenceUserRatingDTO, or with status {@code 404 (Not Found)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @GetMapping("/sequence-user-ratings/{id}")
    public ResponseEntity<SequenceUserRatingDTO> getSequenceUserRating(@PathVariable Long id) {
        log.debug("REST request to get SequenceUserRating : {}", id);
        Optional<SequenceUserRatingDTO> sequenceUserRatingDTO = sequenceUserRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sequenceUserRatingDTO);
    }

    /**
     * {@code DELETE  /sequence-user-ratings/:id} : delete the "id" sequenceUserRating.
     *
     * @param id the id of the sequenceUserRatingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Secured ( AuthoritiesConstants.ADMIN )
    @DeleteMapping("/sequence-user-ratings/{id}")
    public ResponseEntity<Void> deleteSequenceUserRating(@PathVariable Long id) {
        log.debug("REST request to delete SequenceUserRating : {}", id);
        sequenceUserRatingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
