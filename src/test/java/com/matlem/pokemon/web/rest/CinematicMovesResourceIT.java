package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.CinematicMoves;
import com.matlem.pokemon.repository.CinematicMovesRepository;
import com.matlem.pokemon.service.CinematicMovesService;
import com.matlem.pokemon.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.matlem.pokemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CinematicMovesResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class CinematicMovesResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CinematicMovesRepository cinematicMovesRepository;

    @Autowired
    private CinematicMovesService cinematicMovesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCinematicMovesMockMvc;

    private CinematicMoves cinematicMoves;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CinematicMovesResource cinematicMovesResource = new CinematicMovesResource(cinematicMovesService);
        this.restCinematicMovesMockMvc = MockMvcBuilders.standaloneSetup(cinematicMovesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CinematicMoves createEntity(EntityManager em) {
        CinematicMoves cinematicMoves = new CinematicMoves()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME);
        return cinematicMoves;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CinematicMoves createUpdatedEntity(EntityManager em) {
        CinematicMoves cinematicMoves = new CinematicMoves()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);
        return cinematicMoves;
    }

    @BeforeEach
    public void initTest() {
        cinematicMoves = createEntity(em);
    }

    @Test
    @Transactional
    public void createCinematicMoves() throws Exception {
        int databaseSizeBeforeCreate = cinematicMovesRepository.findAll().size();

        // Create the CinematicMoves
        restCinematicMovesMockMvc.perform(post("/api/cinematic-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinematicMoves)))
            .andExpect(status().isCreated());

        // Validate the CinematicMoves in the database
        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeCreate + 1);
        CinematicMoves testCinematicMoves = cinematicMovesList.get(cinematicMovesList.size() - 1);
        assertThat(testCinematicMoves.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testCinematicMoves.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCinematicMovesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cinematicMovesRepository.findAll().size();

        // Create the CinematicMoves with an existing ID
        cinematicMoves.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCinematicMovesMockMvc.perform(post("/api/cinematic-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinematicMoves)))
            .andExpect(status().isBadRequest());

        // Validate the CinematicMoves in the database
        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cinematicMovesRepository.findAll().size();
        // set the field null
        cinematicMoves.setKey(null);

        // Create the CinematicMoves, which fails.

        restCinematicMovesMockMvc.perform(post("/api/cinematic-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinematicMoves)))
            .andExpect(status().isBadRequest());

        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cinematicMovesRepository.findAll().size();
        // set the field null
        cinematicMoves.setName(null);

        // Create the CinematicMoves, which fails.

        restCinematicMovesMockMvc.perform(post("/api/cinematic-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinematicMoves)))
            .andExpect(status().isBadRequest());

        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCinematicMoves() throws Exception {
        // Initialize the database
        cinematicMovesRepository.saveAndFlush(cinematicMoves);

        // Get all the cinematicMovesList
        restCinematicMovesMockMvc.perform(get("/api/cinematic-moves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cinematicMoves.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCinematicMoves() throws Exception {
        // Initialize the database
        cinematicMovesRepository.saveAndFlush(cinematicMoves);

        // Get the cinematicMoves
        restCinematicMovesMockMvc.perform(get("/api/cinematic-moves/{id}", cinematicMoves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cinematicMoves.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingCinematicMoves() throws Exception {
        // Get the cinematicMoves
        restCinematicMovesMockMvc.perform(get("/api/cinematic-moves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCinematicMoves() throws Exception {
        // Initialize the database
        cinematicMovesService.save(cinematicMoves);

        int databaseSizeBeforeUpdate = cinematicMovesRepository.findAll().size();

        // Update the cinematicMoves
        CinematicMoves updatedCinematicMoves = cinematicMovesRepository.findById(cinematicMoves.getId()).get();
        // Disconnect from session so that the updates on updatedCinematicMoves are not directly saved in db
        em.detach(updatedCinematicMoves);
        updatedCinematicMoves
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);

        restCinematicMovesMockMvc.perform(put("/api/cinematic-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCinematicMoves)))
            .andExpect(status().isOk());

        // Validate the CinematicMoves in the database
        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeUpdate);
        CinematicMoves testCinematicMoves = cinematicMovesList.get(cinematicMovesList.size() - 1);
        assertThat(testCinematicMoves.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testCinematicMoves.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCinematicMoves() throws Exception {
        int databaseSizeBeforeUpdate = cinematicMovesRepository.findAll().size();

        // Create the CinematicMoves

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCinematicMovesMockMvc.perform(put("/api/cinematic-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinematicMoves)))
            .andExpect(status().isBadRequest());

        // Validate the CinematicMoves in the database
        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCinematicMoves() throws Exception {
        // Initialize the database
        cinematicMovesService.save(cinematicMoves);

        int databaseSizeBeforeDelete = cinematicMovesRepository.findAll().size();

        // Delete the cinematicMoves
        restCinematicMovesMockMvc.perform(delete("/api/cinematic-moves/{id}", cinematicMoves.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CinematicMoves> cinematicMovesList = cinematicMovesRepository.findAll();
        assertThat(cinematicMovesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
