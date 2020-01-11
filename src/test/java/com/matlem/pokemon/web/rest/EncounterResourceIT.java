package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.Encounter;
import com.matlem.pokemon.repository.EncounterRepository;
import com.matlem.pokemon.service.EncounterService;
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
 * Integration tests for the {@link EncounterResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class EncounterResourceIT {

    private static final Float DEFAULT_ATTACK_PROBABILITY = 1F;
    private static final Float UPDATED_ATTACK_PROBABILITY = 2F;

    private static final Float DEFAULT_ATTACK_TIMER = 1F;
    private static final Float UPDATED_ATTACK_TIMER = 2F;

    private static final Float DEFAULT_BASE_FLEE_RATE = 1F;
    private static final Float UPDATED_BASE_FLEE_RATE = 2F;

    private static final Float DEFAULT_BASE_CAPTURE_RATE = 1F;
    private static final Float UPDATED_BASE_CAPTURE_RATE = 2F;

    private static final Float DEFAULT_CAMERA_DISTANCE = 1F;
    private static final Float UPDATED_CAMERA_DISTANCE = 2F;

    private static final Float DEFAULT_COLLISION_RADIUS = 1F;
    private static final Float UPDATED_COLLISION_RADIUS = 2F;

    private static final Float DEFAULT_DODGE_DISTANCE = 1F;
    private static final Float UPDATED_DODGE_DISTANCE = 2F;

    private static final Float DEFAULT_DODGE_PROBABILITY = 1F;
    private static final Float UPDATED_DODGE_PROBABILITY = 2F;

    private static final Float DEFAULT_JUMP_TIME = 1F;
    private static final Float UPDATED_JUMP_TIME = 2F;

    private static final Float DEFAULT_MAX_POKEMON_ACTION_FREQUENCY = 1F;
    private static final Float UPDATED_MAX_POKEMON_ACTION_FREQUENCY = 2F;

    private static final Float DEFAULT_MIN_POKEMON_ACTION_FREQUENCY = 1F;
    private static final Float UPDATED_MIN_POKEMON_ACTION_FREQUENCY = 2F;

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private EncounterService encounterService;

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

    private MockMvc restEncounterMockMvc;

    private Encounter encounter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EncounterResource encounterResource = new EncounterResource(encounterService);
        this.restEncounterMockMvc = MockMvcBuilders.standaloneSetup(encounterResource)
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
    public static Encounter createEntity(EntityManager em) {
        Encounter encounter = new Encounter()
            .attackProbability(DEFAULT_ATTACK_PROBABILITY)
            .attackTimer(DEFAULT_ATTACK_TIMER)
            .baseFleeRate(DEFAULT_BASE_FLEE_RATE)
            .baseCaptureRate(DEFAULT_BASE_CAPTURE_RATE)
            .cameraDistance(DEFAULT_CAMERA_DISTANCE)
            .collisionRadius(DEFAULT_COLLISION_RADIUS)
            .dodgeDistance(DEFAULT_DODGE_DISTANCE)
            .dodgeProbability(DEFAULT_DODGE_PROBABILITY)
            .jumpTime(DEFAULT_JUMP_TIME)
            .maxPokemonActionFrequency(DEFAULT_MAX_POKEMON_ACTION_FREQUENCY)
            .minPokemonActionFrequency(DEFAULT_MIN_POKEMON_ACTION_FREQUENCY);
        return encounter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encounter createUpdatedEntity(EntityManager em) {
        Encounter encounter = new Encounter()
            .attackProbability(UPDATED_ATTACK_PROBABILITY)
            .attackTimer(UPDATED_ATTACK_TIMER)
            .baseFleeRate(UPDATED_BASE_FLEE_RATE)
            .baseCaptureRate(UPDATED_BASE_CAPTURE_RATE)
            .cameraDistance(UPDATED_CAMERA_DISTANCE)
            .collisionRadius(UPDATED_COLLISION_RADIUS)
            .dodgeDistance(UPDATED_DODGE_DISTANCE)
            .dodgeProbability(UPDATED_DODGE_PROBABILITY)
            .jumpTime(UPDATED_JUMP_TIME)
            .maxPokemonActionFrequency(UPDATED_MAX_POKEMON_ACTION_FREQUENCY)
            .minPokemonActionFrequency(UPDATED_MIN_POKEMON_ACTION_FREQUENCY);
        return encounter;
    }

    @BeforeEach
    public void initTest() {
        encounter = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncounter() throws Exception {
        int databaseSizeBeforeCreate = encounterRepository.findAll().size();

        // Create the Encounter
        restEncounterMockMvc.perform(post("/api/encounters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounter)))
            .andExpect(status().isCreated());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeCreate + 1);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getAttackProbability()).isEqualTo(DEFAULT_ATTACK_PROBABILITY);
        assertThat(testEncounter.getAttackTimer()).isEqualTo(DEFAULT_ATTACK_TIMER);
        assertThat(testEncounter.getBaseFleeRate()).isEqualTo(DEFAULT_BASE_FLEE_RATE);
        assertThat(testEncounter.getBaseCaptureRate()).isEqualTo(DEFAULT_BASE_CAPTURE_RATE);
        assertThat(testEncounter.getCameraDistance()).isEqualTo(DEFAULT_CAMERA_DISTANCE);
        assertThat(testEncounter.getCollisionRadius()).isEqualTo(DEFAULT_COLLISION_RADIUS);
        assertThat(testEncounter.getDodgeDistance()).isEqualTo(DEFAULT_DODGE_DISTANCE);
        assertThat(testEncounter.getDodgeProbability()).isEqualTo(DEFAULT_DODGE_PROBABILITY);
        assertThat(testEncounter.getJumpTime()).isEqualTo(DEFAULT_JUMP_TIME);
        assertThat(testEncounter.getMaxPokemonActionFrequency()).isEqualTo(DEFAULT_MAX_POKEMON_ACTION_FREQUENCY);
        assertThat(testEncounter.getMinPokemonActionFrequency()).isEqualTo(DEFAULT_MIN_POKEMON_ACTION_FREQUENCY);
    }

    @Test
    @Transactional
    public void createEncounterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encounterRepository.findAll().size();

        // Create the Encounter with an existing ID
        encounter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncounterMockMvc.perform(post("/api/encounters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounter)))
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEncounters() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList
        restEncounterMockMvc.perform(get("/api/encounters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encounter.getId().intValue())))
            .andExpect(jsonPath("$.[*].attackProbability").value(hasItem(DEFAULT_ATTACK_PROBABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].attackTimer").value(hasItem(DEFAULT_ATTACK_TIMER.doubleValue())))
            .andExpect(jsonPath("$.[*].baseFleeRate").value(hasItem(DEFAULT_BASE_FLEE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].baseCaptureRate").value(hasItem(DEFAULT_BASE_CAPTURE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].cameraDistance").value(hasItem(DEFAULT_CAMERA_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].collisionRadius").value(hasItem(DEFAULT_COLLISION_RADIUS.doubleValue())))
            .andExpect(jsonPath("$.[*].dodgeDistance").value(hasItem(DEFAULT_DODGE_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].dodgeProbability").value(hasItem(DEFAULT_DODGE_PROBABILITY.doubleValue())))
            .andExpect(jsonPath("$.[*].jumpTime").value(hasItem(DEFAULT_JUMP_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].maxPokemonActionFrequency").value(hasItem(DEFAULT_MAX_POKEMON_ACTION_FREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].minPokemonActionFrequency").value(hasItem(DEFAULT_MIN_POKEMON_ACTION_FREQUENCY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get the encounter
        restEncounterMockMvc.perform(get("/api/encounters/{id}", encounter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encounter.getId().intValue()))
            .andExpect(jsonPath("$.attackProbability").value(DEFAULT_ATTACK_PROBABILITY.doubleValue()))
            .andExpect(jsonPath("$.attackTimer").value(DEFAULT_ATTACK_TIMER.doubleValue()))
            .andExpect(jsonPath("$.baseFleeRate").value(DEFAULT_BASE_FLEE_RATE.doubleValue()))
            .andExpect(jsonPath("$.baseCaptureRate").value(DEFAULT_BASE_CAPTURE_RATE.doubleValue()))
            .andExpect(jsonPath("$.cameraDistance").value(DEFAULT_CAMERA_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.collisionRadius").value(DEFAULT_COLLISION_RADIUS.doubleValue()))
            .andExpect(jsonPath("$.dodgeDistance").value(DEFAULT_DODGE_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.dodgeProbability").value(DEFAULT_DODGE_PROBABILITY.doubleValue()))
            .andExpect(jsonPath("$.jumpTime").value(DEFAULT_JUMP_TIME.doubleValue()))
            .andExpect(jsonPath("$.maxPokemonActionFrequency").value(DEFAULT_MAX_POKEMON_ACTION_FREQUENCY.doubleValue()))
            .andExpect(jsonPath("$.minPokemonActionFrequency").value(DEFAULT_MIN_POKEMON_ACTION_FREQUENCY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEncounter() throws Exception {
        // Get the encounter
        restEncounterMockMvc.perform(get("/api/encounters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncounter() throws Exception {
        // Initialize the database
        encounterService.save(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter
        Encounter updatedEncounter = encounterRepository.findById(encounter.getId()).get();
        // Disconnect from session so that the updates on updatedEncounter are not directly saved in db
        em.detach(updatedEncounter);
        updatedEncounter
            .attackProbability(UPDATED_ATTACK_PROBABILITY)
            .attackTimer(UPDATED_ATTACK_TIMER)
            .baseFleeRate(UPDATED_BASE_FLEE_RATE)
            .baseCaptureRate(UPDATED_BASE_CAPTURE_RATE)
            .cameraDistance(UPDATED_CAMERA_DISTANCE)
            .collisionRadius(UPDATED_COLLISION_RADIUS)
            .dodgeDistance(UPDATED_DODGE_DISTANCE)
            .dodgeProbability(UPDATED_DODGE_PROBABILITY)
            .jumpTime(UPDATED_JUMP_TIME)
            .maxPokemonActionFrequency(UPDATED_MAX_POKEMON_ACTION_FREQUENCY)
            .minPokemonActionFrequency(UPDATED_MIN_POKEMON_ACTION_FREQUENCY);

        restEncounterMockMvc.perform(put("/api/encounters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEncounter)))
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getAttackProbability()).isEqualTo(UPDATED_ATTACK_PROBABILITY);
        assertThat(testEncounter.getAttackTimer()).isEqualTo(UPDATED_ATTACK_TIMER);
        assertThat(testEncounter.getBaseFleeRate()).isEqualTo(UPDATED_BASE_FLEE_RATE);
        assertThat(testEncounter.getBaseCaptureRate()).isEqualTo(UPDATED_BASE_CAPTURE_RATE);
        assertThat(testEncounter.getCameraDistance()).isEqualTo(UPDATED_CAMERA_DISTANCE);
        assertThat(testEncounter.getCollisionRadius()).isEqualTo(UPDATED_COLLISION_RADIUS);
        assertThat(testEncounter.getDodgeDistance()).isEqualTo(UPDATED_DODGE_DISTANCE);
        assertThat(testEncounter.getDodgeProbability()).isEqualTo(UPDATED_DODGE_PROBABILITY);
        assertThat(testEncounter.getJumpTime()).isEqualTo(UPDATED_JUMP_TIME);
        assertThat(testEncounter.getMaxPokemonActionFrequency()).isEqualTo(UPDATED_MAX_POKEMON_ACTION_FREQUENCY);
        assertThat(testEncounter.getMinPokemonActionFrequency()).isEqualTo(UPDATED_MIN_POKEMON_ACTION_FREQUENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Create the Encounter

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncounterMockMvc.perform(put("/api/encounters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encounter)))
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEncounter() throws Exception {
        // Initialize the database
        encounterService.save(encounter);

        int databaseSizeBeforeDelete = encounterRepository.findAll().size();

        // Delete the encounter
        restEncounterMockMvc.perform(delete("/api/encounters/{id}", encounter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
