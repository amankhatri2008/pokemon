package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.CostToEvolve;
import com.matlem.pokemon.repository.CostToEvolveRepository;
import com.matlem.pokemon.service.CostToEvolveService;
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
 * Integration tests for the {@link CostToEvolveResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class CostToEvolveResourceIT {

    private static final Integer DEFAULT_CANDY_COST = 1;
    private static final Integer UPDATED_CANDY_COST = 2;

    private static final Integer DEFAULT_KM_BUDDY_DISTANCE = 1;
    private static final Integer UPDATED_KM_BUDDY_DISTANCE = 2;

    @Autowired
    private CostToEvolveRepository costToEvolveRepository;

    @Autowired
    private CostToEvolveService costToEvolveService;

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

    private MockMvc restCostToEvolveMockMvc;

    private CostToEvolve costToEvolve;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostToEvolveResource costToEvolveResource = new CostToEvolveResource(costToEvolveService);
        this.restCostToEvolveMockMvc = MockMvcBuilders.standaloneSetup(costToEvolveResource)
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
    public static CostToEvolve createEntity(EntityManager em) {
        CostToEvolve costToEvolve = new CostToEvolve()
            .candyCost(DEFAULT_CANDY_COST)
            .kmBuddyDistance(DEFAULT_KM_BUDDY_DISTANCE);
        return costToEvolve;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostToEvolve createUpdatedEntity(EntityManager em) {
        CostToEvolve costToEvolve = new CostToEvolve()
            .candyCost(UPDATED_CANDY_COST)
            .kmBuddyDistance(UPDATED_KM_BUDDY_DISTANCE);
        return costToEvolve;
    }

    @BeforeEach
    public void initTest() {
        costToEvolve = createEntity(em);
    }

    @Test
    @Transactional
    public void createCostToEvolve() throws Exception {
        int databaseSizeBeforeCreate = costToEvolveRepository.findAll().size();

        // Create the CostToEvolve
        restCostToEvolveMockMvc.perform(post("/api/cost-to-evolves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costToEvolve)))
            .andExpect(status().isCreated());

        // Validate the CostToEvolve in the database
        List<CostToEvolve> costToEvolveList = costToEvolveRepository.findAll();
        assertThat(costToEvolveList).hasSize(databaseSizeBeforeCreate + 1);
        CostToEvolve testCostToEvolve = costToEvolveList.get(costToEvolveList.size() - 1);
        assertThat(testCostToEvolve.getCandyCost()).isEqualTo(DEFAULT_CANDY_COST);
        assertThat(testCostToEvolve.getKmBuddyDistance()).isEqualTo(DEFAULT_KM_BUDDY_DISTANCE);
    }

    @Test
    @Transactional
    public void createCostToEvolveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costToEvolveRepository.findAll().size();

        // Create the CostToEvolve with an existing ID
        costToEvolve.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostToEvolveMockMvc.perform(post("/api/cost-to-evolves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costToEvolve)))
            .andExpect(status().isBadRequest());

        // Validate the CostToEvolve in the database
        List<CostToEvolve> costToEvolveList = costToEvolveRepository.findAll();
        assertThat(costToEvolveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCostToEvolves() throws Exception {
        // Initialize the database
        costToEvolveRepository.saveAndFlush(costToEvolve);

        // Get all the costToEvolveList
        restCostToEvolveMockMvc.perform(get("/api/cost-to-evolves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costToEvolve.getId().intValue())))
            .andExpect(jsonPath("$.[*].candyCost").value(hasItem(DEFAULT_CANDY_COST)))
            .andExpect(jsonPath("$.[*].kmBuddyDistance").value(hasItem(DEFAULT_KM_BUDDY_DISTANCE)));
    }
    
    @Test
    @Transactional
    public void getCostToEvolve() throws Exception {
        // Initialize the database
        costToEvolveRepository.saveAndFlush(costToEvolve);

        // Get the costToEvolve
        restCostToEvolveMockMvc.perform(get("/api/cost-to-evolves/{id}", costToEvolve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costToEvolve.getId().intValue()))
            .andExpect(jsonPath("$.candyCost").value(DEFAULT_CANDY_COST))
            .andExpect(jsonPath("$.kmBuddyDistance").value(DEFAULT_KM_BUDDY_DISTANCE));
    }

    @Test
    @Transactional
    public void getNonExistingCostToEvolve() throws Exception {
        // Get the costToEvolve
        restCostToEvolveMockMvc.perform(get("/api/cost-to-evolves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCostToEvolve() throws Exception {
        // Initialize the database
        costToEvolveService.save(costToEvolve);

        int databaseSizeBeforeUpdate = costToEvolveRepository.findAll().size();

        // Update the costToEvolve
        CostToEvolve updatedCostToEvolve = costToEvolveRepository.findById(costToEvolve.getId()).get();
        // Disconnect from session so that the updates on updatedCostToEvolve are not directly saved in db
        em.detach(updatedCostToEvolve);
        updatedCostToEvolve
            .candyCost(UPDATED_CANDY_COST)
            .kmBuddyDistance(UPDATED_KM_BUDDY_DISTANCE);

        restCostToEvolveMockMvc.perform(put("/api/cost-to-evolves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCostToEvolve)))
            .andExpect(status().isOk());

        // Validate the CostToEvolve in the database
        List<CostToEvolve> costToEvolveList = costToEvolveRepository.findAll();
        assertThat(costToEvolveList).hasSize(databaseSizeBeforeUpdate);
        CostToEvolve testCostToEvolve = costToEvolveList.get(costToEvolveList.size() - 1);
        assertThat(testCostToEvolve.getCandyCost()).isEqualTo(UPDATED_CANDY_COST);
        assertThat(testCostToEvolve.getKmBuddyDistance()).isEqualTo(UPDATED_KM_BUDDY_DISTANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingCostToEvolve() throws Exception {
        int databaseSizeBeforeUpdate = costToEvolveRepository.findAll().size();

        // Create the CostToEvolve

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostToEvolveMockMvc.perform(put("/api/cost-to-evolves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costToEvolve)))
            .andExpect(status().isBadRequest());

        // Validate the CostToEvolve in the database
        List<CostToEvolve> costToEvolveList = costToEvolveRepository.findAll();
        assertThat(costToEvolveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCostToEvolve() throws Exception {
        // Initialize the database
        costToEvolveService.save(costToEvolve);

        int databaseSizeBeforeDelete = costToEvolveRepository.findAll().size();

        // Delete the costToEvolve
        restCostToEvolveMockMvc.perform(delete("/api/cost-to-evolves/{id}", costToEvolve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CostToEvolve> costToEvolveList = costToEvolveRepository.findAll();
        assertThat(costToEvolveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
