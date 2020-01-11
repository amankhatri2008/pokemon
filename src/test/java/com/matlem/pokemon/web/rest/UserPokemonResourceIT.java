package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.UserPokemon;
import com.matlem.pokemon.repository.UserPokemonRepository;
import com.matlem.pokemon.service.UserPokemonService;
import com.matlem.pokemon.service.UserService;
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
 * Integration tests for the {@link UserPokemonResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class UserPokemonResourceIT {

    @Autowired
    private UserPokemonRepository userPokemonRepository;

    @Mock
    private UserPokemonRepository userPokemonRepositoryMock;

    @Mock
    private UserPokemonService userPokemonServiceMock;

    @Autowired
    private UserPokemonService userPokemonService;

    @Autowired
    private UserService userService;

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

    private MockMvc restUserPokemonMockMvc;

    private UserPokemon userPokemon;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPokemonResource userPokemonResource = new UserPokemonResource(userPokemonService, userService);
        this.restUserPokemonMockMvc = MockMvcBuilders.standaloneSetup(userPokemonResource)
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
    public static UserPokemon createEntity(EntityManager em) {
        UserPokemon userPokemon = new UserPokemon();
        return userPokemon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPokemon createUpdatedEntity(EntityManager em) {
        UserPokemon userPokemon = new UserPokemon();
        return userPokemon;
    }

    @BeforeEach
    public void initTest() {
        userPokemon = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPokemon() throws Exception {
        int databaseSizeBeforeCreate = userPokemonRepository.findAll().size();

        // Create the UserPokemon
        restUserPokemonMockMvc.perform(post("/api/user-pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokemon)))
            .andExpect(status().isCreated());

        // Validate the UserPokemon in the database
        List<UserPokemon> userPokemonList = userPokemonRepository.findAll();
        assertThat(userPokemonList).hasSize(databaseSizeBeforeCreate + 1);
        UserPokemon testUserPokemon = userPokemonList.get(userPokemonList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserPokemonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPokemonRepository.findAll().size();

        // Create the UserPokemon with an existing ID
        userPokemon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPokemonMockMvc.perform(post("/api/user-pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokemon)))
            .andExpect(status().isBadRequest());

        // Validate the UserPokemon in the database
        List<UserPokemon> userPokemonList = userPokemonRepository.findAll();
        assertThat(userPokemonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserPokemons() throws Exception {
        // Initialize the database
        userPokemonRepository.saveAndFlush(userPokemon);

        // Get all the userPokemonList
        restUserPokemonMockMvc.perform(get("/api/user-pokemons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPokemon.getId().intValue())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserPokemonsWithEagerRelationshipsIsEnabled() throws Exception {
        UserPokemonResource userPokemonResource = new UserPokemonResource(userPokemonServiceMock,userService);
        when(userPokemonServiceMock.findAllWithEagerRelationships(any(),any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserPokemonMockMvc = MockMvcBuilders.standaloneSetup(userPokemonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserPokemonMockMvc.perform(get("/api/user-pokemons?eagerload=true"))
        .andExpect(status().isOk());

        verify(userPokemonServiceMock, times(1)).findAllWithEagerRelationships(any(),any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserPokemonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserPokemonResource userPokemonResource = new UserPokemonResource(userPokemonServiceMock,userService);
            when(userPokemonServiceMock.findAllWithEagerRelationships(any(),any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserPokemonMockMvc = MockMvcBuilders.standaloneSetup(userPokemonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserPokemonMockMvc.perform(get("/api/user-pokemons?eagerload=true"))
        .andExpect(status().isOk());

            verify(userPokemonServiceMock, times(1)).findAllWithEagerRelationships(any(),any());
    }

    @Test
    @Transactional
    public void getUserPokemon() throws Exception {
        // Initialize the database
        userPokemonRepository.saveAndFlush(userPokemon);

        // Get the userPokemon
        restUserPokemonMockMvc.perform(get("/api/user-pokemons/{id}", userPokemon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPokemon.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPokemon() throws Exception {
        // Get the userPokemon
        restUserPokemonMockMvc.perform(get("/api/user-pokemons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPokemon() throws Exception {
        // Initialize the database
        userPokemonService.save(userPokemon);

        int databaseSizeBeforeUpdate = userPokemonRepository.findAll().size();

        // Update the userPokemon
        UserPokemon updatedUserPokemon = userPokemonRepository.findById(userPokemon.getId()).get();
        // Disconnect from session so that the updates on updatedUserPokemon are not directly saved in db
        em.detach(updatedUserPokemon);

        restUserPokemonMockMvc.perform(put("/api/user-pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPokemon)))
            .andExpect(status().isOk());

        // Validate the UserPokemon in the database
        List<UserPokemon> userPokemonList = userPokemonRepository.findAll();
        assertThat(userPokemonList).hasSize(databaseSizeBeforeUpdate);
        UserPokemon testUserPokemon = userPokemonList.get(userPokemonList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPokemon() throws Exception {
        int databaseSizeBeforeUpdate = userPokemonRepository.findAll().size();

        // Create the UserPokemon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPokemonMockMvc.perform(put("/api/user-pokemons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokemon)))
            .andExpect(status().isBadRequest());

        // Validate the UserPokemon in the database
        List<UserPokemon> userPokemonList = userPokemonRepository.findAll();
        assertThat(userPokemonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserPokemon() throws Exception {
        // Initialize the database
        userPokemonService.save(userPokemon);

        int databaseSizeBeforeDelete = userPokemonRepository.findAll().size();

        // Delete the userPokemon
        restUserPokemonMockMvc.perform(delete("/api/user-pokemons/{id}", userPokemon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPokemon> userPokemonList = userPokemonRepository.findAll();
        assertThat(userPokemonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
