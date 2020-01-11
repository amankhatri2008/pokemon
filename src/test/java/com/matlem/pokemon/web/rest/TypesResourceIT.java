package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.Types;
import com.matlem.pokemon.repository.TypesRepository;

import com.matlem.pokemon.service.TypesService;
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
 * Integration tests for the {@link TypesResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class TypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ID = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ID = "BBBBBBBBBB";

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    private TypesService typesService;

    /**
     * This repository is mocked in the com.matlem.pokemon.repository.search test package.
     *
     * @see com.matlem.pokemon.repository.search.TypesSearchRepositoryMockConfiguration
     */

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

    private MockMvc restTypesMockMvc;

    private Types types;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypesResource typesResource = new TypesResource(typesService);
        this.restTypesMockMvc = MockMvcBuilders.standaloneSetup(typesResource)
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
    public static Types createEntity(EntityManager em) {
        Types types = new Types()
            .name(DEFAULT_NAME)
            .nameId(DEFAULT_NAME_ID);
        return types;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Types createUpdatedEntity(EntityManager em) {
        Types types = new Types()
            .name(UPDATED_NAME)
            .nameId(UPDATED_NAME_ID);
        return types;
    }

    @BeforeEach
    public void initTest() {
        types = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypes() throws Exception {
        int databaseSizeBeforeCreate = typesRepository.findAll().size();

        // Create the Types
        restTypesMockMvc.perform(post("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(types)))
            .andExpect(status().isCreated());

        // Validate the Types in the database
        List<Types> typesList = typesRepository.findAll();
        assertThat(typesList).hasSize(databaseSizeBeforeCreate + 1);
        Types testTypes = typesList.get(typesList.size() - 1);
        assertThat(testTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypes.getNameId()).isEqualTo(DEFAULT_NAME_ID);

        // Validate the Types in Elasticsearch

    }

    @Test
    @Transactional
    public void createTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typesRepository.findAll().size();

        // Create the Types with an existing ID
        types.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypesMockMvc.perform(post("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(types)))
            .andExpect(status().isBadRequest());

        // Validate the Types in the database
        List<Types> typesList = typesRepository.findAll();
        assertThat(typesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Types in Elasticsearch

    }


    @Test
    @Transactional
    public void getAllTypes() throws Exception {
        // Initialize the database
        typesRepository.saveAndFlush(types);

        // Get all the typesList
        restTypesMockMvc.perform(get("/api/types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(types.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nameId").value(hasItem(DEFAULT_NAME_ID)));
    }

    @Test
    @Transactional
    public void getTypes() throws Exception {
        // Initialize the database
        typesRepository.saveAndFlush(types);

        // Get the types
        restTypesMockMvc.perform(get("/api/types/{id}", types.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(types.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nameId").value(DEFAULT_NAME_ID));
    }

    @Test
    @Transactional
    public void getNonExistingTypes() throws Exception {
        // Get the types
        restTypesMockMvc.perform(get("/api/types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypes() throws Exception {
        // Initialize the database
        typesService.save(types);
        // As the test used the service layer, reset the Elasticsearch mock repository


        int databaseSizeBeforeUpdate = typesRepository.findAll().size();

        // Update the types
        Types updatedTypes = typesRepository.findById(types.getId()).get();
        // Disconnect from session so that the updates on updatedTypes are not directly saved in db
        em.detach(updatedTypes);
        updatedTypes
            .name(UPDATED_NAME)
            .nameId(UPDATED_NAME_ID);

        restTypesMockMvc.perform(put("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypes)))
            .andExpect(status().isOk());

        // Validate the Types in the database
        List<Types> typesList = typesRepository.findAll();
        assertThat(typesList).hasSize(databaseSizeBeforeUpdate);
        Types testTypes = typesList.get(typesList.size() - 1);
        assertThat(testTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypes.getNameId()).isEqualTo(UPDATED_NAME_ID);

        // Validate the Types in Elasticsearch

    }

    @Test
    @Transactional
    public void updateNonExistingTypes() throws Exception {
        int databaseSizeBeforeUpdate = typesRepository.findAll().size();

        // Create the Types

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypesMockMvc.perform(put("/api/types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(types)))
            .andExpect(status().isBadRequest());

        // Validate the Types in the database
        List<Types> typesList = typesRepository.findAll();
        assertThat(typesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Types in Elasticsearch

    }

    @Test
    @Transactional
    public void deleteTypes() throws Exception {
        // Initialize the database
        typesService.save(types);

        int databaseSizeBeforeDelete = typesRepository.findAll().size();

        // Delete the types
        restTypesMockMvc.perform(delete("/api/types/{id}", types.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Types> typesList = typesRepository.findAll();
        assertThat(typesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Types in Elasticsearch

    }

}
