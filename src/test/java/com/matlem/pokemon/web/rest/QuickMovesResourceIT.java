package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.QuickMoves;
import com.matlem.pokemon.repository.QuickMovesRepository;
import com.matlem.pokemon.service.QuickMovesService;
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
 * Integration tests for the {@link QuickMovesResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class QuickMovesResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private QuickMovesRepository quickMovesRepository;

    @Autowired
    private QuickMovesService quickMovesService;

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

    private MockMvc restQuickMovesMockMvc;

    private QuickMoves quickMoves;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuickMovesResource quickMovesResource = new QuickMovesResource(quickMovesService);
        this.restQuickMovesMockMvc = MockMvcBuilders.standaloneSetup(quickMovesResource)
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
    public static QuickMoves createEntity(EntityManager em) {
        QuickMoves quickMoves = new QuickMoves()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME);
        return quickMoves;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuickMoves createUpdatedEntity(EntityManager em) {
        QuickMoves quickMoves = new QuickMoves()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);
        return quickMoves;
    }

    @BeforeEach
    public void initTest() {
        quickMoves = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuickMoves() throws Exception {
        int databaseSizeBeforeCreate = quickMovesRepository.findAll().size();

        // Create the QuickMoves
        restQuickMovesMockMvc.perform(post("/api/quick-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quickMoves)))
            .andExpect(status().isCreated());

        // Validate the QuickMoves in the database
        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeCreate + 1);
        QuickMoves testQuickMoves = quickMovesList.get(quickMovesList.size() - 1);
        assertThat(testQuickMoves.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testQuickMoves.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createQuickMovesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quickMovesRepository.findAll().size();

        // Create the QuickMoves with an existing ID
        quickMoves.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuickMovesMockMvc.perform(post("/api/quick-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quickMoves)))
            .andExpect(status().isBadRequest());

        // Validate the QuickMoves in the database
        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = quickMovesRepository.findAll().size();
        // set the field null
        quickMoves.setKey(null);

        // Create the QuickMoves, which fails.

        restQuickMovesMockMvc.perform(post("/api/quick-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quickMoves)))
            .andExpect(status().isBadRequest());

        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = quickMovesRepository.findAll().size();
        // set the field null
        quickMoves.setName(null);

        // Create the QuickMoves, which fails.

        restQuickMovesMockMvc.perform(post("/api/quick-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quickMoves)))
            .andExpect(status().isBadRequest());

        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuickMoves() throws Exception {
        // Initialize the database
        quickMovesRepository.saveAndFlush(quickMoves);

        // Get all the quickMovesList
        restQuickMovesMockMvc.perform(get("/api/quick-moves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quickMoves.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getQuickMoves() throws Exception {
        // Initialize the database
        quickMovesRepository.saveAndFlush(quickMoves);

        // Get the quickMoves
        restQuickMovesMockMvc.perform(get("/api/quick-moves/{id}", quickMoves.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quickMoves.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingQuickMoves() throws Exception {
        // Get the quickMoves
        restQuickMovesMockMvc.perform(get("/api/quick-moves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuickMoves() throws Exception {
        // Initialize the database
        quickMovesService.save(quickMoves);

        int databaseSizeBeforeUpdate = quickMovesRepository.findAll().size();

        // Update the quickMoves
        QuickMoves updatedQuickMoves = quickMovesRepository.findById(quickMoves.getId()).get();
        // Disconnect from session so that the updates on updatedQuickMoves are not directly saved in db
        em.detach(updatedQuickMoves);
        updatedQuickMoves
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);

        restQuickMovesMockMvc.perform(put("/api/quick-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuickMoves)))
            .andExpect(status().isOk());

        // Validate the QuickMoves in the database
        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeUpdate);
        QuickMoves testQuickMoves = quickMovesList.get(quickMovesList.size() - 1);
        assertThat(testQuickMoves.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testQuickMoves.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingQuickMoves() throws Exception {
        int databaseSizeBeforeUpdate = quickMovesRepository.findAll().size();

        // Create the QuickMoves

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuickMovesMockMvc.perform(put("/api/quick-moves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quickMoves)))
            .andExpect(status().isBadRequest());

        // Validate the QuickMoves in the database
        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuickMoves() throws Exception {
        // Initialize the database
        quickMovesService.save(quickMoves);

        int databaseSizeBeforeDelete = quickMovesRepository.findAll().size();

        // Delete the quickMoves
        restQuickMovesMockMvc.perform(delete("/api/quick-moves/{id}", quickMoves.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuickMoves> quickMovesList = quickMovesRepository.findAll();
        assertThat(quickMovesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
