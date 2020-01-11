package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.EncounterType;
import com.matlem.pokemon.repository.EncounterTypeRepository;
import com.matlem.pokemon.service.EncounterTypeService;
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
 * Integration tests for the {@link EncounterTypeResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class EncounterTypeResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EncounterTypeRepository encounterTypeRepository;

    @Autowired
    private EncounterTypeService encounterTypeService;

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

    private MockMvc restEncounterTypeMockMvc;

    private EncounterType encounterType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EncounterTypeResource encounterTypeResource = new EncounterTypeResource(encounterTypeService);
        this.restEncounterTypeMockMvc = MockMvcBuilders.standaloneSetup(encounterTypeResource)
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
    public static EncounterType createEntity(EntityManager em) {
        EncounterType encounterType = new EncounterType()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME);
        return encounterType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncounterType createUpdatedEntity(EntityManager em) {
        EncounterType encounterType = new EncounterType()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);
        return encounterType;
    }

    @BeforeEach
    public void initTest() {
        encounterType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncounterType() throws Exception {
        int databaseSizeBeforeCreate = encounterTypeRepository.findAll().size();

        // Create the EncounterType
        restEncounterTypeMockMvc.perform(post("/api/encounter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounterType)))
            .andExpect(status().isCreated());

        // Validate the EncounterType in the database
        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EncounterType testEncounterType = encounterTypeList.get(encounterTypeList.size() - 1);
        assertThat(testEncounterType.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testEncounterType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEncounterTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encounterTypeRepository.findAll().size();

        // Create the EncounterType with an existing ID
        encounterType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncounterTypeMockMvc.perform(post("/api/encounter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounterType)))
            .andExpect(status().isBadRequest());

        // Validate the EncounterType in the database
        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = encounterTypeRepository.findAll().size();
        // set the field null
        encounterType.setKey(null);

        // Create the EncounterType, which fails.

        restEncounterTypeMockMvc.perform(post("/api/encounter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounterType)))
            .andExpect(status().isBadRequest());

        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = encounterTypeRepository.findAll().size();
        // set the field null
        encounterType.setName(null);

        // Create the EncounterType, which fails.

        restEncounterTypeMockMvc.perform(post("/api/encounter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounterType)))
            .andExpect(status().isBadRequest());

        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEncounterTypes() throws Exception {
        // Initialize the database
        encounterTypeRepository.saveAndFlush(encounterType);

        // Get all the encounterTypeList
        restEncounterTypeMockMvc.perform(get("/api/encounter-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encounterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getEncounterType() throws Exception {
        // Initialize the database
        encounterTypeRepository.saveAndFlush(encounterType);

        // Get the encounterType
        restEncounterTypeMockMvc.perform(get("/api/encounter-types/{id}", encounterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encounterType.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingEncounterType() throws Exception {
        // Get the encounterType
        restEncounterTypeMockMvc.perform(get("/api/encounter-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncounterType() throws Exception {
        // Initialize the database
        encounterTypeService.save(encounterType);

        int databaseSizeBeforeUpdate = encounterTypeRepository.findAll().size();

        // Update the encounterType
        EncounterType updatedEncounterType = encounterTypeRepository.findById(encounterType.getId()).get();
        // Disconnect from session so that the updates on updatedEncounterType are not directly saved in db
        em.detach(updatedEncounterType);
        updatedEncounterType
            .key(UPDATED_KEY)
            .name(UPDATED_NAME);

        restEncounterTypeMockMvc.perform(put("/api/encounter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEncounterType)))
            .andExpect(status().isOk());

        // Validate the EncounterType in the database
        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeUpdate);
        EncounterType testEncounterType = encounterTypeList.get(encounterTypeList.size() - 1);
        assertThat(testEncounterType.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testEncounterType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEncounterType() throws Exception {
        int databaseSizeBeforeUpdate = encounterTypeRepository.findAll().size();

        // Create the EncounterType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncounterTypeMockMvc.perform(put("/api/encounter-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounterType)))
            .andExpect(status().isBadRequest());

        // Validate the EncounterType in the database
        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEncounterType() throws Exception {
        // Initialize the database
        encounterTypeService.save(encounterType);

        int databaseSizeBeforeDelete = encounterTypeRepository.findAll().size();

        // Delete the encounterType
        restEncounterTypeMockMvc.perform(delete("/api/encounter-types/{id}", encounterType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EncounterType> encounterTypeList = encounterTypeRepository.findAll();
        assertThat(encounterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
