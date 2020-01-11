package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.Pokemon;
import com.matlem.pokemon.repository.PokemonRepository;
import com.matlem.pokemon.service.PokemonService;
import com.matlem.pokemon.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.matlem.pokemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PokemonResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class PokemonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_ANIMATION_TIME_0 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_0 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_1 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_1 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_2 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_2 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_3 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_3 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_4 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_4 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_5 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_5 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_6 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_6 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_7 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_7 = 2F;

    private static final Float DEFAULT_ANIMATION_TIME_8 = 1F;
    private static final Float UPDATED_ANIMATION_TIME_8 = 2F;

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final Float DEFAULT_MODEL_HEIGHT = 1F;
    private static final Float UPDATED_MODEL_HEIGHT = 2F;

    private static final Float DEFAULT_KM_BUDDY_DISTANCE = 1F;
    private static final Float UPDATED_KM_BUDDY_DISTANCE = 2F;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Float DEFAULT_MODEL_SCALE = 1F;
    private static final Float UPDATED_MODEL_SCALE = 2F;

    private static final Float DEFAULT_MAX_CP = 1F;
    private static final Float UPDATED_MAX_CP = 2F;

    private static final Float DEFAULT_CYLINDER_GROUND = 1F;
    private static final Float UPDATED_CYLINDER_GROUND = 2F;

    private static final Float DEFAULT_CYLINDER_RADIUS = 1F;
    private static final Float UPDATED_CYLINDER_RADIUS = 2F;

    private static final Float DEFAULT_DISK_RADIUS = 1F;
    private static final Float UPDATED_DISK_RADIUS = 2F;

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CINEMATIC_MOVES_ID = 1;
    private static final Integer UPDATED_CINEMATIC_MOVES_ID = 2;

    private static final Integer DEFAULT_BASE_ATTACK = 1;
    private static final Integer UPDATED_BASE_ATTACK = 2;

    private static final Integer DEFAULT_BASE_DEFENSE = 1;
    private static final Integer UPDATED_BASE_DEFENSE = 2;

    private static final Integer DEFAULT_BASE_STAMINA = 1;
    private static final Integer UPDATED_BASE_STAMINA = 2;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Mock
    private PokemonRepository pokemonRepositoryMock;

    @Mock
    private PokemonService pokemonServiceMock;

    @Autowired
    private PokemonService pokemonService;

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

    private MockMvc restPokemonMockMvc;

    private Pokemon pokemon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PokemonResource pokemonResource = new PokemonResource(pokemonService);
        this.restPokemonMockMvc = MockMvcBuilders.standaloneSetup(pokemonResource)
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
    public static Pokemon createEntity(EntityManager em) {
        Pokemon pokemon = new Pokemon()
            .name(DEFAULT_NAME)
            .animationTime0(DEFAULT_ANIMATION_TIME_0)
            .animationTime1(DEFAULT_ANIMATION_TIME_1)
            .animationTime2(DEFAULT_ANIMATION_TIME_2)
            .animationTime3(DEFAULT_ANIMATION_TIME_3)
            .animationTime4(DEFAULT_ANIMATION_TIME_4)
            .animationTime5(DEFAULT_ANIMATION_TIME_5)
            .animationTime6(DEFAULT_ANIMATION_TIME_6)
            .animationTime7(DEFAULT_ANIMATION_TIME_7)
            .animationTime8(DEFAULT_ANIMATION_TIME_8)
            .height(DEFAULT_HEIGHT)
            .modelHeight(DEFAULT_MODEL_HEIGHT)
            .kmBuddyDistance(DEFAULT_KM_BUDDY_DISTANCE)
            .weight(DEFAULT_WEIGHT)
            .modelScale(DEFAULT_MODEL_SCALE)
            .maxCP(DEFAULT_MAX_CP)
            .cylinderGround(DEFAULT_CYLINDER_GROUND)
            .cylinderRadius(DEFAULT_CYLINDER_RADIUS)
            .diskRadius(DEFAULT_DISK_RADIUS)
            .key(DEFAULT_KEY)
            .cinematicMovesId(DEFAULT_CINEMATIC_MOVES_ID)
            .baseAttack(DEFAULT_BASE_ATTACK)
            .baseDefense(DEFAULT_BASE_DEFENSE)
            .baseStamina(DEFAULT_BASE_STAMINA);
        return pokemon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pokemon createUpdatedEntity(EntityManager em) {
        Pokemon pokemon = new Pokemon()
            .name(UPDATED_NAME)
            .animationTime0(UPDATED_ANIMATION_TIME_0)
            .animationTime1(UPDATED_ANIMATION_TIME_1)
            .animationTime2(UPDATED_ANIMATION_TIME_2)
            .animationTime3(UPDATED_ANIMATION_TIME_3)
            .animationTime4(UPDATED_ANIMATION_TIME_4)
            .animationTime5(UPDATED_ANIMATION_TIME_5)
            .animationTime6(UPDATED_ANIMATION_TIME_6)
            .animationTime7(UPDATED_ANIMATION_TIME_7)
            .animationTime8(UPDATED_ANIMATION_TIME_8)
            .height(UPDATED_HEIGHT)
            .modelHeight(UPDATED_MODEL_HEIGHT)
            .kmBuddyDistance(UPDATED_KM_BUDDY_DISTANCE)
            .weight(UPDATED_WEIGHT)
            .modelScale(UPDATED_MODEL_SCALE)
            .maxCP(UPDATED_MAX_CP)
            .cylinderGround(UPDATED_CYLINDER_GROUND)
            .cylinderRadius(UPDATED_CYLINDER_RADIUS)
            .diskRadius(UPDATED_DISK_RADIUS)
            .key(UPDATED_KEY)
            .cinematicMovesId(UPDATED_CINEMATIC_MOVES_ID)
            .baseAttack(UPDATED_BASE_ATTACK)
            .baseDefense(UPDATED_BASE_DEFENSE)
            .baseStamina(UPDATED_BASE_STAMINA);
        return pokemon;
    }

    @BeforeEach
    public void initTest() {
        pokemon = createEntity(em);
    }

    @Test
    @Transactional
    public void createPokemon() throws Exception {
        int databaseSizeBeforeCreate = pokemonRepository.findAll().size();

        // Create the Pokemon
        restPokemonMockMvc.perform(post("/api/pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pokemon)))
            .andExpect(status().isCreated());

        // Validate the Pokemon in the database
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        assertThat(pokemonList).hasSize(databaseSizeBeforeCreate + 1);
        Pokemon testPokemon = pokemonList.get(pokemonList.size() - 1);
        assertThat(testPokemon.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPokemon.getAnimationTime0()).isEqualTo(DEFAULT_ANIMATION_TIME_0);
        assertThat(testPokemon.getAnimationTime1()).isEqualTo(DEFAULT_ANIMATION_TIME_1);
        assertThat(testPokemon.getAnimationTime2()).isEqualTo(DEFAULT_ANIMATION_TIME_2);
        assertThat(testPokemon.getAnimationTime3()).isEqualTo(DEFAULT_ANIMATION_TIME_3);
        assertThat(testPokemon.getAnimationTime4()).isEqualTo(DEFAULT_ANIMATION_TIME_4);
        assertThat(testPokemon.getAnimationTime5()).isEqualTo(DEFAULT_ANIMATION_TIME_5);
        assertThat(testPokemon.getAnimationTime6()).isEqualTo(DEFAULT_ANIMATION_TIME_6);
        assertThat(testPokemon.getAnimationTime7()).isEqualTo(DEFAULT_ANIMATION_TIME_7);
        assertThat(testPokemon.getAnimationTime8()).isEqualTo(DEFAULT_ANIMATION_TIME_8);
        assertThat(testPokemon.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPokemon.getModelHeight()).isEqualTo(DEFAULT_MODEL_HEIGHT);
        assertThat(testPokemon.getKmBuddyDistance()).isEqualTo(DEFAULT_KM_BUDDY_DISTANCE);
        assertThat(testPokemon.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testPokemon.getModelScale()).isEqualTo(DEFAULT_MODEL_SCALE);
        assertThat(testPokemon.getMaxCP()).isEqualTo(DEFAULT_MAX_CP);
        assertThat(testPokemon.getCylinderGround()).isEqualTo(DEFAULT_CYLINDER_GROUND);
        assertThat(testPokemon.getCylinderRadius()).isEqualTo(DEFAULT_CYLINDER_RADIUS);
        assertThat(testPokemon.getDiskRadius()).isEqualTo(DEFAULT_DISK_RADIUS);
        assertThat(testPokemon.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testPokemon.getCinematicMovesId()).isEqualTo(DEFAULT_CINEMATIC_MOVES_ID);
        assertThat(testPokemon.getBaseAttack()).isEqualTo(DEFAULT_BASE_ATTACK);
        assertThat(testPokemon.getBaseDefense()).isEqualTo(DEFAULT_BASE_DEFENSE);
        assertThat(testPokemon.getBaseStamina()).isEqualTo(DEFAULT_BASE_STAMINA);
    }

    @Test
    @Transactional
    public void createPokemonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pokemonRepository.findAll().size();

        // Create the Pokemon with an existing ID
        pokemon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPokemonMockMvc.perform(post("/api/pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pokemon)))
            .andExpect(status().isBadRequest());

        // Validate the Pokemon in the database
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        assertThat(pokemonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPokemons() throws Exception {
        // Initialize the database
        pokemonRepository.saveAndFlush(pokemon);

        // Get all the pokemonList
        restPokemonMockMvc.perform(get("/api/pokemons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pokemon.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].animationTime0").value(hasItem(DEFAULT_ANIMATION_TIME_0.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime1").value(hasItem(DEFAULT_ANIMATION_TIME_1.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime2").value(hasItem(DEFAULT_ANIMATION_TIME_2.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime3").value(hasItem(DEFAULT_ANIMATION_TIME_3.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime4").value(hasItem(DEFAULT_ANIMATION_TIME_4.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime5").value(hasItem(DEFAULT_ANIMATION_TIME_5.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime6").value(hasItem(DEFAULT_ANIMATION_TIME_6.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime7").value(hasItem(DEFAULT_ANIMATION_TIME_7.doubleValue())))
            .andExpect(jsonPath("$.[*].animationTime8").value(hasItem(DEFAULT_ANIMATION_TIME_8.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].modelHeight").value(hasItem(DEFAULT_MODEL_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].kmBuddyDistance").value(hasItem(DEFAULT_KM_BUDDY_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].modelScale").value(hasItem(DEFAULT_MODEL_SCALE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxCP").value(hasItem(DEFAULT_MAX_CP.doubleValue())))
            .andExpect(jsonPath("$.[*].cylinderGround").value(hasItem(DEFAULT_CYLINDER_GROUND.doubleValue())))
            .andExpect(jsonPath("$.[*].cylinderRadius").value(hasItem(DEFAULT_CYLINDER_RADIUS.doubleValue())))
            .andExpect(jsonPath("$.[*].diskRadius").value(hasItem(DEFAULT_DISK_RADIUS.doubleValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].cinematicMovesId").value(hasItem(DEFAULT_CINEMATIC_MOVES_ID)))
            .andExpect(jsonPath("$.[*].baseAttack").value(hasItem(DEFAULT_BASE_ATTACK)))
            .andExpect(jsonPath("$.[*].baseDefense").value(hasItem(DEFAULT_BASE_DEFENSE)))
            .andExpect(jsonPath("$.[*].baseStamina").value(hasItem(DEFAULT_BASE_STAMINA)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPokemonsWithEagerRelationshipsIsEnabled() throws Exception {
        PokemonResource pokemonResource = new PokemonResource(pokemonServiceMock);
        when(pokemonServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPokemonMockMvc = MockMvcBuilders.standaloneSetup(pokemonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPokemonMockMvc.perform(get("/api/pokemons?eagerload=true"))
        .andExpect(status().isOk());

        verify(pokemonServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPokemonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PokemonResource pokemonResource = new PokemonResource(pokemonServiceMock);
            when(pokemonServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPokemonMockMvc = MockMvcBuilders.standaloneSetup(pokemonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPokemonMockMvc.perform(get("/api/pokemons?eagerload=true"))
        .andExpect(status().isOk());

            verify(pokemonServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPokemon() throws Exception {
        // Initialize the database
        pokemonRepository.saveAndFlush(pokemon);

        // Get the pokemon
        restPokemonMockMvc.perform(get("/api/pokemons/{id}", pokemon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pokemon.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.animationTime0").value(DEFAULT_ANIMATION_TIME_0.doubleValue()))
            .andExpect(jsonPath("$.animationTime1").value(DEFAULT_ANIMATION_TIME_1.doubleValue()))
            .andExpect(jsonPath("$.animationTime2").value(DEFAULT_ANIMATION_TIME_2.doubleValue()))
            .andExpect(jsonPath("$.animationTime3").value(DEFAULT_ANIMATION_TIME_3.doubleValue()))
            .andExpect(jsonPath("$.animationTime4").value(DEFAULT_ANIMATION_TIME_4.doubleValue()))
            .andExpect(jsonPath("$.animationTime5").value(DEFAULT_ANIMATION_TIME_5.doubleValue()))
            .andExpect(jsonPath("$.animationTime6").value(DEFAULT_ANIMATION_TIME_6.doubleValue()))
            .andExpect(jsonPath("$.animationTime7").value(DEFAULT_ANIMATION_TIME_7.doubleValue()))
            .andExpect(jsonPath("$.animationTime8").value(DEFAULT_ANIMATION_TIME_8.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.modelHeight").value(DEFAULT_MODEL_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.kmBuddyDistance").value(DEFAULT_KM_BUDDY_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.modelScale").value(DEFAULT_MODEL_SCALE.doubleValue()))
            .andExpect(jsonPath("$.maxCP").value(DEFAULT_MAX_CP.doubleValue()))
            .andExpect(jsonPath("$.cylinderGround").value(DEFAULT_CYLINDER_GROUND.doubleValue()))
            .andExpect(jsonPath("$.cylinderRadius").value(DEFAULT_CYLINDER_RADIUS.doubleValue()))
            .andExpect(jsonPath("$.diskRadius").value(DEFAULT_DISK_RADIUS.doubleValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.cinematicMovesId").value(DEFAULT_CINEMATIC_MOVES_ID))
            .andExpect(jsonPath("$.baseAttack").value(DEFAULT_BASE_ATTACK))
            .andExpect(jsonPath("$.baseDefense").value(DEFAULT_BASE_DEFENSE))
            .andExpect(jsonPath("$.baseStamina").value(DEFAULT_BASE_STAMINA));
    }

    @Test
    @Transactional
    public void getNonExistingPokemon() throws Exception {
        // Get the pokemon
        restPokemonMockMvc.perform(get("/api/pokemons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePokemon() throws Exception {
        // Initialize the database
        pokemonService.save(pokemon);

        int databaseSizeBeforeUpdate = pokemonRepository.findAll().size();

        // Update the pokemon
        Pokemon updatedPokemon = pokemonRepository.findById(pokemon.getId()).get();
        // Disconnect from session so that the updates on updatedPokemon are not directly saved in db
        em.detach(updatedPokemon);
        updatedPokemon
            .name(UPDATED_NAME)
            .animationTime0(UPDATED_ANIMATION_TIME_0)
            .animationTime1(UPDATED_ANIMATION_TIME_1)
            .animationTime2(UPDATED_ANIMATION_TIME_2)
            .animationTime3(UPDATED_ANIMATION_TIME_3)
            .animationTime4(UPDATED_ANIMATION_TIME_4)
            .animationTime5(UPDATED_ANIMATION_TIME_5)
            .animationTime6(UPDATED_ANIMATION_TIME_6)
            .animationTime7(UPDATED_ANIMATION_TIME_7)
            .animationTime8(UPDATED_ANIMATION_TIME_8)
            .height(UPDATED_HEIGHT)
            .modelHeight(UPDATED_MODEL_HEIGHT)
            .kmBuddyDistance(UPDATED_KM_BUDDY_DISTANCE)
            .weight(UPDATED_WEIGHT)
            .modelScale(UPDATED_MODEL_SCALE)
            .maxCP(UPDATED_MAX_CP)
            .cylinderGround(UPDATED_CYLINDER_GROUND)
            .cylinderRadius(UPDATED_CYLINDER_RADIUS)
            .diskRadius(UPDATED_DISK_RADIUS)
            .key(UPDATED_KEY)
            .cinematicMovesId(UPDATED_CINEMATIC_MOVES_ID)
            .baseAttack(UPDATED_BASE_ATTACK)
            .baseDefense(UPDATED_BASE_DEFENSE)
            .baseStamina(UPDATED_BASE_STAMINA);

        restPokemonMockMvc.perform(put("/api/pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPokemon)))
            .andExpect(status().isOk());

        // Validate the Pokemon in the database
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        assertThat(pokemonList).hasSize(databaseSizeBeforeUpdate);
        Pokemon testPokemon = pokemonList.get(pokemonList.size() - 1);
        assertThat(testPokemon.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPokemon.getAnimationTime0()).isEqualTo(UPDATED_ANIMATION_TIME_0);
        assertThat(testPokemon.getAnimationTime1()).isEqualTo(UPDATED_ANIMATION_TIME_1);
        assertThat(testPokemon.getAnimationTime2()).isEqualTo(UPDATED_ANIMATION_TIME_2);
        assertThat(testPokemon.getAnimationTime3()).isEqualTo(UPDATED_ANIMATION_TIME_3);
        assertThat(testPokemon.getAnimationTime4()).isEqualTo(UPDATED_ANIMATION_TIME_4);
        assertThat(testPokemon.getAnimationTime5()).isEqualTo(UPDATED_ANIMATION_TIME_5);
        assertThat(testPokemon.getAnimationTime6()).isEqualTo(UPDATED_ANIMATION_TIME_6);
        assertThat(testPokemon.getAnimationTime7()).isEqualTo(UPDATED_ANIMATION_TIME_7);
        assertThat(testPokemon.getAnimationTime8()).isEqualTo(UPDATED_ANIMATION_TIME_8);
        assertThat(testPokemon.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPokemon.getModelHeight()).isEqualTo(UPDATED_MODEL_HEIGHT);
        assertThat(testPokemon.getKmBuddyDistance()).isEqualTo(UPDATED_KM_BUDDY_DISTANCE);
        assertThat(testPokemon.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPokemon.getModelScale()).isEqualTo(UPDATED_MODEL_SCALE);
        assertThat(testPokemon.getMaxCP()).isEqualTo(UPDATED_MAX_CP);
        assertThat(testPokemon.getCylinderGround()).isEqualTo(UPDATED_CYLINDER_GROUND);
        assertThat(testPokemon.getCylinderRadius()).isEqualTo(UPDATED_CYLINDER_RADIUS);
        assertThat(testPokemon.getDiskRadius()).isEqualTo(UPDATED_DISK_RADIUS);
        assertThat(testPokemon.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testPokemon.getCinematicMovesId()).isEqualTo(UPDATED_CINEMATIC_MOVES_ID);
        assertThat(testPokemon.getBaseAttack()).isEqualTo(UPDATED_BASE_ATTACK);
        assertThat(testPokemon.getBaseDefense()).isEqualTo(UPDATED_BASE_DEFENSE);
        assertThat(testPokemon.getBaseStamina()).isEqualTo(UPDATED_BASE_STAMINA);
    }

    @Test
    @Transactional
    public void updateNonExistingPokemon() throws Exception {
        int databaseSizeBeforeUpdate = pokemonRepository.findAll().size();

        // Create the Pokemon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPokemonMockMvc.perform(put("/api/pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pokemon)))
            .andExpect(status().isBadRequest());

        // Validate the Pokemon in the database
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        assertThat(pokemonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePokemon() throws Exception {
        // Initialize the database
        pokemonService.save(pokemon);

        int databaseSizeBeforeDelete = pokemonRepository.findAll().size();

        // Delete the pokemon
        restPokemonMockMvc.perform(delete("/api/pokemons/{id}", pokemon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pokemon> pokemonList = pokemonRepository.findAll();
        assertThat(pokemonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
