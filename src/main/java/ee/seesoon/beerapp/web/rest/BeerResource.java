package ee.seesoon.beerapp.web.rest;

import ee.seesoon.beerapp.domain.Beer;
import ee.seesoon.beerapp.repository.BeerRepository;
import ee.seesoon.beerapp.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link ee.seesoon.beerapp.domain.Beer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BeerResource {
    private final Logger log = LoggerFactory.getLogger(BeerResource.class);

    private static final String ENTITY_NAME = "beer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeerRepository beerRepository;

    public BeerResource(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    /**
     * {@code POST  /beers} : Create a new beer.
     *
     * @param beer the beer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beer, or with status {@code 400 (Bad Request)} if the beer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beers")
    public ResponseEntity<Beer> createBeer(@Valid @RequestBody Beer beer) throws URISyntaxException {
        log.debug("REST request to save Beer : {}", beer);
        if (beer.getId() != null) {
            throw new BadRequestAlertException("A new beer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Beer result = beerRepository.save(beer);
        return ResponseEntity
            .created(new URI("/api/beers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beers} : Updates an existing beer.
     *
     * @param beer the beer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beer,
     * or with status {@code 400 (Bad Request)} if the beer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beers")
    public ResponseEntity<Beer> updateBeer(@Valid @RequestBody Beer beer) throws URISyntaxException {
        log.debug("REST request to update Beer : {}", beer);
        if (beer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Beer result = beerRepository.save(beer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /beers} : get all the beers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beers in body.
     */
    @GetMapping("/beers")
    public List<Beer> getAllBeers() {
        log.debug("REST request to get all Beers");
        return beerRepository.findAll();
    }

    /**
     * {@code GET  /beers/:id} : get the "id" beer.
     *
     * @param id the id of the beer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beers/{id}")
    public ResponseEntity<Beer> getBeer(@PathVariable Long id) {
        log.debug("REST request to get Beer : {}", id);
        Optional<Beer> beer = beerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beer);
    }

    /**
     * {@code DELETE  /beers/:id} : delete the "id" beer.
     *
     * @param id the id of the beer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beers/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        log.debug("REST request to delete Beer : {}", id);
        beerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
