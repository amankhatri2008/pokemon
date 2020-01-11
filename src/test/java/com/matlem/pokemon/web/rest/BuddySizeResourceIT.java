package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.BuddySize;
import com.matlem.pokemon.repository.BuddySizeRepository;
import com.matlem.pokemon.service.BuddySizeService;
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
 * Integration tests for the {@link BuddySizeResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class BuddySizeResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BuddySizeRepository buddySizeRepository;

    @Autowired
    private BuddySizeService buddySizeService;

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

    private MockMvc restBuddySizeMockMvc;

    private BuddySize buddySize;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuddySizeResource buddySizeResource = new BuddySizeResource(buddySizeService);
        this.restBuddySizeMockMvc = MockMvcBuilders.standaloneSetup(buddySizeResource)
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
    public static BuddySize createEntity(EntityManager em) {
        BuddySize buddySize = new BuddySize()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME);
        return buddySize;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuddySize createUpdatedEntity(EntityManager em) {
        BuddySize buddySize = new BuddySize()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);
        return buddySize;
    }

    @BeforeEach
    public void initTest() {
        buddySize = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuddySize() throws Exception {
        int databaseSizeBeforeCreate = buddySizeRepository.findAll().size();

        // Create the BuddySize
        restBuddySizeMockMvc.perform(post("/api/buddy-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buddySize)))
            .andExpect(status().isCreated());

        // Validate the BuddySize in the database
        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeCreate + 1);
        BuddySize testBuddySize = buddySizeList.get(buddySizeList.size() - 1);
        assertThat(testBuddySize.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testBuddySize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBuddySizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buddySizeRepository.findAll().size();

        // Create the BuddySize with an existing ID
        buddySize.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuddySizeMockMvc.perform(post("/api/buddy-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buddySize)))
            .andExpect(status().isBadRequest());

        // Validate the BuddySize in the database
        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = buddySizeRepository.findAll().size();
        // set the field null
        buddySize.setKey(null);

        // Create the BuddySize, which fails.

        restBuddySizeMockMvc.perform(post("/api/buddy-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buddySize)))
            .andExpect(status().isBadRequest());

        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = buddySizeRepository.findAll().size();
        // set the field null
        buddySize.setName(null);

        // Create the BuddySize, which fails.

        restBuddySizeMockMvc.perform(post("/api/buddy-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buddySize)))
            .andExpect(status().isBadRequest());

        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuddySizes() throws Exception {
        // Initialize the database
        buddySizeRepository.saveAndFlush(buddySize);

        // Get all the buddySizeList
        restBuddySizeMockMvc.perform(get("/api/buddy-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buddySize.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getBuddySize() throws Exception {
        // Initialize the database
        buddySizeRepository.saveAndFlush(buddySize);

        // Get the buddySize
        restBuddySizeMockMvc.perform(get("/api/buddy-sizes/{id}", buddySize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buddySize.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingBuddySize() throws Exception {
        // Get the buddySize
        restBuddySizeMockMvc.perform(get("/api/buddy-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuddySize() throws Exception {
        // Initialize the database
        buddySizeService.save(buddySize);

        int databaseSizeBeforeUpdate = buddySizeRepository.findAll().size();

        // Update the buddySize
        BuddySize updatedBuddySize = buddySizeRepository.findById(buddySize.getId()).get();
        // Disconnect from session so that the updates on updatedBuddySize are not directly saved in db
        em.detach(updatedBuddySize);
        updatedBuddySize
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);

        restBuddySizeMockMvc.perform(put("/api/buddy-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBuddySize)))
            .andExpect(status().isOk());

        // Validate the BuddySize in the database
        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeUpdate);
        BuddySize testBuddySize = buddySizeList.get(buddySizeList.size() - 1);
        assertThat(testBuddySize.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testBuddySize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBuddySize() throws Exception {
        int databaseSizeBeforeUpdate = buddySizeRepository.findAll().size();

        // Create the BuddySize

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuddySizeMockMvc.perform(put("/api/buddy-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buddySize)))
            .andExpect(status().isBadRequest());

        // Validate the BuddySize in the database
        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBuddySize() throws Exception {
        // Initialize the database
        buddySizeService.save(buddySize);

        int databaseSizeBeforeDelete = buddySizeRepository.findAll().size();

        // Delete the buddySize
        restBuddySizeMockMvc.perform(delete("/api/buddy-sizes/{id}", buddySize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuddySize> buddySizeList = buddySizeRepository.findAll();
        assertThat(buddySizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
