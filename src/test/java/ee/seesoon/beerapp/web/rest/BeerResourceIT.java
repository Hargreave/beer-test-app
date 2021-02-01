package ee.seesoon.beerapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ee.seesoon.beerapp.BeerTestApp;
import ee.seesoon.beerapp.domain.Beer;
import ee.seesoon.beerapp.repository.BeerRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BeerResource} REST controller.
 */
@SpringBootTest(classes = BeerTestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BeerResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_ALCOHOL_PERCENTAGE = 0D;
    private static final Double UPDATED_ALCOHOL_PERCENTAGE = 1D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADDED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADDED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADRESS_OF_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS_OF_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeerMockMvc;

    private Beer beer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beer createEntity(EntityManager em) {
        Beer beer = new Beer()
            .name(DEFAULT_NAME)
            .alcoholPercentage(DEFAULT_ALCOHOL_PERCENTAGE)
            .description(DEFAULT_DESCRIPTION)
            .addedDate(DEFAULT_ADDED_DATE)
            .adressOfOrigin(DEFAULT_ADRESS_OF_ORIGIN)
            .style(DEFAULT_STYLE)
            .comment(DEFAULT_COMMENT);
        return beer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beer createUpdatedEntity(EntityManager em) {
        Beer beer = new Beer()
            .name(UPDATED_NAME)
            .alcoholPercentage(UPDATED_ALCOHOL_PERCENTAGE)
            .description(UPDATED_DESCRIPTION)
            .addedDate(UPDATED_ADDED_DATE)
            .adressOfOrigin(UPDATED_ADRESS_OF_ORIGIN)
            .style(UPDATED_STYLE)
            .comment(UPDATED_COMMENT);
        return beer;
    }

    @BeforeEach
    public void initTest() {
        beer = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeer() throws Exception {
        int databaseSizeBeforeCreate = beerRepository.findAll().size();
        // Create the Beer
        restBeerMockMvc
            .perform(post("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isCreated());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeCreate + 1);
        Beer testBeer = beerList.get(beerList.size() - 1);
        assertThat(testBeer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBeer.getAlcoholPercentage()).isEqualTo(DEFAULT_ALCOHOL_PERCENTAGE);
        assertThat(testBeer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBeer.getAddedDate()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testBeer.getAdressOfOrigin()).isEqualTo(DEFAULT_ADRESS_OF_ORIGIN);
        assertThat(testBeer.getStyle()).isEqualTo(DEFAULT_STYLE);
        assertThat(testBeer.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createBeerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beerRepository.findAll().size();

        // Create the Beer with an existing ID
        beer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeerMockMvc
            .perform(post("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setName(null);

        // Create the Beer, which fails.

        restBeerMockMvc
            .perform(post("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAlcoholPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setAlcoholPercentage(null);

        // Create the Beer, which fails.

        restBeerMockMvc
            .perform(post("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setDescription(null);

        // Create the Beer, which fails.

        restBeerMockMvc
            .perform(post("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = beerRepository.findAll().size();
        // set the field null
        beer.setAddedDate(null);

        // Create the Beer, which fails.

        restBeerMockMvc
            .perform(post("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeers() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        // Get all the beerList
        restBeerMockMvc
            .perform(get("/api/beers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].alcoholPercentage").value(hasItem(DEFAULT_ALCOHOL_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].addedDate").value(hasItem(DEFAULT_ADDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].adressOfOrigin").value(hasItem(DEFAULT_ADRESS_OF_ORIGIN)))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }

    @Test
    @Transactional
    public void getBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        // Get the beer
        restBeerMockMvc
            .perform(get("/api/beers/{id}", beer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.alcoholPercentage").value(DEFAULT_ALCOHOL_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.addedDate").value(DEFAULT_ADDED_DATE.toString()))
            .andExpect(jsonPath("$.adressOfOrigin").value(DEFAULT_ADRESS_OF_ORIGIN))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    public void getNonExistingBeer() throws Exception {
        // Get the beer
        restBeerMockMvc.perform(get("/api/beers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        int databaseSizeBeforeUpdate = beerRepository.findAll().size();

        // Update the beer
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        // Disconnect from session so that the updates on updatedBeer are not directly saved in db
        em.detach(updatedBeer);
        updatedBeer
            .name(UPDATED_NAME)
            .alcoholPercentage(UPDATED_ALCOHOL_PERCENTAGE)
            .description(UPDATED_DESCRIPTION)
            .addedDate(UPDATED_ADDED_DATE)
            .adressOfOrigin(UPDATED_ADRESS_OF_ORIGIN)
            .style(UPDATED_STYLE)
            .comment(UPDATED_COMMENT);

        restBeerMockMvc
            .perform(put("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedBeer)))
            .andExpect(status().isOk());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeUpdate);
        Beer testBeer = beerList.get(beerList.size() - 1);
        assertThat(testBeer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBeer.getAlcoholPercentage()).isEqualTo(UPDATED_ALCOHOL_PERCENTAGE);
        assertThat(testBeer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBeer.getAddedDate()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testBeer.getAdressOfOrigin()).isEqualTo(UPDATED_ADRESS_OF_ORIGIN);
        assertThat(testBeer.getStyle()).isEqualTo(UPDATED_STYLE);
        assertThat(testBeer.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingBeer() throws Exception {
        int databaseSizeBeforeUpdate = beerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeerMockMvc
            .perform(put("/api/beers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beer)))
            .andExpect(status().isBadRequest());

        // Validate the Beer in the database
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeer() throws Exception {
        // Initialize the database
        beerRepository.saveAndFlush(beer);

        int databaseSizeBeforeDelete = beerRepository.findAll().size();

        // Delete the beer
        restBeerMockMvc
            .perform(delete("/api/beers/{id}", beer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beer> beerList = beerRepository.findAll();
        assertThat(beerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
