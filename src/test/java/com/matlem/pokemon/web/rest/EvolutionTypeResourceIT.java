package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.EvolutionType;
import com.matlem.pokemon.repository.EvolutionTypeRepository;
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
 * Integration tests for the {@link EvolutionTypeResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class EvolutionTypeResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EvolutionTypeRepository evolutionTypeRepository;

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

    private MockMvc restEvolutionTypeMockMvc;

    private EvolutionType evolutionType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvolutionTypeResource evolutionTypeResource = new EvolutionTypeResource(evolutionTypeRepository);
        this.restEvolutionTypeMockMvc = MockMvcBuilders.standaloneSetup(evolutionTypeResource)
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
    public static EvolutionType createEntity(EntityManager em) {
        EvolutionType evolutionType = new EvolutionType()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME);
        return evolutionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvolutionType createUpdatedEntity(EntityManager em) {
        EvolutionType evolutionType = new EvolutionType()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);
        return evolutionType;
    }

    @BeforeEach
    public void initTest() {
        evolutionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvolutionType() throws Exception {
        int databaseSizeBeforeCreate = evolutionTypeRepository.findAll().size();

        // Create the EvolutionType
        restEvolutionTypeMockMvc.perform(post("/api/evolution-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionType)))
            .andExpect(status().isCreated());

        // Validate the EvolutionType in the database
        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EvolutionType testEvolutionType = evolutionTypeList.get(evolutionTypeList.size() - 1);
        assertThat(testEvolutionType.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testEvolutionType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEvolutionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evolutionTypeRepository.findAll().size();

        // Create the EvolutionType with an existing ID
        evolutionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvolutionTypeMockMvc.perform(post("/api/evolution-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionType)))
            .andExpect(status().isBadRequest());

        // Validate the EvolutionType in the database
        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = evolutionTypeRepository.findAll().size();
        // set the field null
        evolutionType.setKey(null);

        // Create the EvolutionType, which fails.

        restEvolutionTypeMockMvc.perform(post("/api/evolution-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionType)))
            .andExpect(status().isBadRequest());

        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = evolutionTypeRepository.findAll().size();
        // set the field null
        evolutionType.setName(null);

        // Create the EvolutionType, which fails.

        restEvolutionTypeMockMvc.perform(post("/api/evolution-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionType)))
            .andExpect(status().isBadRequest());

        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvolutionTypes() throws Exception {
        // Initialize the database
        evolutionTypeRepository.saveAndFlush(evolutionType);

        // Get all the evolutionTypeList
        restEvolutionTypeMockMvc.perform(get("/api/evolution-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evolutionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getEvolutionType() throws Exception {
        // Initialize the database
        evolutionTypeRepository.saveAndFlush(evolutionType);

        // Get the evolutionType
        restEvolutionTypeMockMvc.perform(get("/api/evolution-types/{id}", evolutionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evolutionType.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingEvolutionType() throws Exception {
        // Get the evolutionType
        restEvolutionTypeMockMvc.perform(get("/api/evolution-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvolutionType() throws Exception {
        // Initialize the database
        evolutionTypeRepository.saveAndFlush(evolutionType);

        int databaseSizeBeforeUpdate = evolutionTypeRepository.findAll().size();

        // Update the evolutionType
        EvolutionType updatedEvolutionType = evolutionTypeRepository.findById(evolutionType.getId()).get();
        // Disconnect from session so that the updates on updatedEvolutionType are not directly saved in db
        em.detach(updatedEvolutionType);
        updatedEvolutionType
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);

        restEvolutionTypeMockMvc.perform(put("/api/evolution-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvolutionType)))
            .andExpect(status().isOk());

        // Validate the EvolutionType in the database
        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeUpdate);
        EvolutionType testEvolutionType = evolutionTypeList.get(evolutionTypeList.size() - 1);
        assertThat(testEvolutionType.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testEvolutionType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEvolutionType() throws Exception {
        int databaseSizeBeforeUpdate = evolutionTypeRepository.findAll().size();

        // Create the EvolutionType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvolutionTypeMockMvc.perform(put("/api/evolution-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evolutionType)))
            .andExpect(status().isBadRequest());

        // Validate the EvolutionType in the database
        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvolutionType() throws Exception {
        // Initialize the database
        evolutionTypeRepository.saveAndFlush(evolutionType);

        int databaseSizeBeforeDelete = evolutionTypeRepository.findAll().size();

        // Delete the evolutionType
        restEvolutionTypeMockMvc.perform(delete("/api/evolution-types/{id}", evolutionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvolutionType> evolutionTypeList = evolutionTypeRepository.findAll();
        assertThat(evolutionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
