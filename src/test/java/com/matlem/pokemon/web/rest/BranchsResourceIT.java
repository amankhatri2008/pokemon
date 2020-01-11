package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.Branchs;
import com.matlem.pokemon.repository.BranchsRepository;
import com.matlem.pokemon.service.BranchsService;
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
 * Integration tests for the {@link BranchsResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class BranchsResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BranchsRepository branchsRepository;

    @Autowired
    private BranchsService branchsService;

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

    private MockMvc restBranchsMockMvc;

    private Branchs branchs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BranchsResource branchsResource = new BranchsResource(branchsService);
        this.restBranchsMockMvc = MockMvcBuilders.standaloneSetup(branchsResource)
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
    public static Branchs createEntity(EntityManager em) {
        Branchs branchs = new Branchs()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME);
        return branchs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Branchs createUpdatedEntity(EntityManager em) {
        Branchs branchs = new Branchs()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);
        return branchs;
    }

    @BeforeEach
    public void initTest() {
        branchs = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranchs() throws Exception {
        int databaseSizeBeforeCreate = branchsRepository.findAll().size();

        // Create the Branchs
        restBranchsMockMvc.perform(post("/api/branchs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchs)))
            .andExpect(status().isCreated());

        // Validate the Branchs in the database
        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeCreate + 1);
        Branchs testBranchs = branchsList.get(branchsList.size() - 1);
        assertThat(testBranchs.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testBranchs.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBranchsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchsRepository.findAll().size();

        // Create the Branchs with an existing ID
        branchs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchsMockMvc.perform(post("/api/branchs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchs)))
            .andExpect(status().isBadRequest());

        // Validate the Branchs in the database
        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchsRepository.findAll().size();
        // set the field null
        branchs.setKey(null);

        // Create the Branchs, which fails.

        restBranchsMockMvc.perform(post("/api/branchs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchs)))
            .andExpect(status().isBadRequest());

        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchsRepository.findAll().size();
        // set the field null
        branchs.setName(null);

        // Create the Branchs, which fails.

        restBranchsMockMvc.perform(post("/api/branchs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchs)))
            .andExpect(status().isBadRequest());

        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBranchs() throws Exception {
        // Initialize the database
        branchsRepository.saveAndFlush(branchs);

        // Get all the branchsList
        restBranchsMockMvc.perform(get("/api/branchs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchs.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getBranchs() throws Exception {
        // Initialize the database
        branchsRepository.saveAndFlush(branchs);

        // Get the branchs
        restBranchsMockMvc.perform(get("/api/branchs/{id}", branchs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(branchs.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBranchs() throws Exception {
        // Get the branchs
        restBranchsMockMvc.perform(get("/api/branchs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranchs() throws Exception {
        // Initialize the database
        branchsService.save(branchs);

        int databaseSizeBeforeUpdate = branchsRepository.findAll().size();

        // Update the branchs
        Branchs updatedBranchs = branchsRepository.findById(branchs.getId()).get();
        // Disconnect from session so that the updates on updatedBranchs are not directly saved in db
        em.detach(updatedBranchs);
        updatedBranchs
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);

        restBranchsMockMvc.perform(put("/api/branchs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranchs)))
            .andExpect(status().isOk());

        // Validate the Branchs in the database
        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeUpdate);
        Branchs testBranchs = branchsList.get(branchsList.size() - 1);
        assertThat(testBranchs.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testBranchs.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBranchs() throws Exception {
        int databaseSizeBeforeUpdate = branchsRepository.findAll().size();

        // Create the Branchs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBranchsMockMvc.perform(put("/api/branchs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchs)))
            .andExpect(status().isBadRequest());

        // Validate the Branchs in the database
        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBranchs() throws Exception {
        // Initialize the database
        branchsService.save(branchs);

        int databaseSizeBeforeDelete = branchsRepository.findAll().size();

        // Delete the branchs
        restBranchsMockMvc.perform(delete("/api/branchs/{id}", branchs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Branchs> branchsList = branchsRepository.findAll();
        assertThat(branchsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
