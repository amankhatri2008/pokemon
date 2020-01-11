package com.matlem.pokemon.web.rest;

import com.matlem.pokemon.PokemonApp;
import com.matlem.pokemon.domain.Gender;
import com.matlem.pokemon.repository.GenderRepository;
import com.matlem.pokemon.service.GenderService;
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
import java.math.BigDecimal;
import java.util.List;

import static com.matlem.pokemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GenderResource} REST controller.
 */
@SpringBootTest(classes = PokemonApp.class)
public class GenderResourceIT {

    private static final BigDecimal DEFAULT_MALE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MALE_PERCENT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FEMALE_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FEMALE_PERCENT = new BigDecimal(2);

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private GenderService genderService;

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

    private MockMvc restGenderMockMvc;

    private Gender gender;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GenderResource genderResource = new GenderResource(genderService);
        this.restGenderMockMvc = MockMvcBuilders.standaloneSetup(genderResource)
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
    public static Gender createEntity(EntityManager em) {
        Gender gender = new Gender()
            .malePercent(DEFAULT_MALE_PERCENT)
            .femalePercent(DEFAULT_FEMALE_PERCENT);
        return gender;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gender createUpdatedEntity(EntityManager em) {
        Gender gender = new Gender()
            .malePercent(UPDATED_MALE_PERCENT)
            .femalePercent(UPDATED_FEMALE_PERCENT);
        return gender;
    }

    @BeforeEach
    public void initTest() {
        gender = createEntity(em);
    }

    @Test
    @Transactional
    public void createGender() throws Exception {
        int databaseSizeBeforeCreate = genderRepository.findAll().size();

        // Create the Gender
        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isCreated());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeCreate + 1);
        Gender testGender = genderList.get(genderList.size() - 1);
        assertThat(testGender.getMalePercent()).isEqualTo(DEFAULT_MALE_PERCENT);
        assertThat(testGender.getFemalePercent()).isEqualTo(DEFAULT_FEMALE_PERCENT);
    }

    @Test
    @Transactional
    public void createGenderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = genderRepository.findAll().size();

        // Create the Gender with an existing ID
        gender.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isBadRequest());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMalePercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderRepository.findAll().size();
        // set the field null
        gender.setMalePercent(null);

        // Create the Gender, which fails.

        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isBadRequest());

        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFemalePercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = genderRepository.findAll().size();
        // set the field null
        gender.setFemalePercent(null);

        // Create the Gender, which fails.

        restGenderMockMvc.perform(post("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isBadRequest());

        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGenders() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

        // Get all the genderList
        restGenderMockMvc.perform(get("/api/genders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gender.getId().intValue())))
            .andExpect(jsonPath("$.[*].malePercent").value(hasItem(DEFAULT_MALE_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].femalePercent").value(hasItem(DEFAULT_FEMALE_PERCENT.intValue())));
    }

    @Test
    @Transactional
    public void getGender() throws Exception {
        // Initialize the database
        genderRepository.saveAndFlush(gender);

        // Get the gender
        restGenderMockMvc.perform(get("/api/genders/{id}", gender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gender.getId().intValue()))
            .andExpect(jsonPath("$.malePercent").value(DEFAULT_MALE_PERCENT.intValue()))
            .andExpect(jsonPath("$.femalePercent").value(DEFAULT_FEMALE_PERCENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGender() throws Exception {
        // Get the gender
        restGenderMockMvc.perform(get("/api/genders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGender() throws Exception {
        // Initialize the database
        genderService.save(gender);

        int databaseSizeBeforeUpdate = genderRepository.findAll().size();

        // Update the gender
        Gender updatedGender = genderRepository.findById(gender.getId()).get();
        // Disconnect from session so that the updates on updatedGender are not directly saved in db
        em.detach(updatedGender);
        updatedGender
            .malePercent(UPDATED_MALE_PERCENT)
            .femalePercent(UPDATED_FEMALE_PERCENT);

        restGenderMockMvc.perform(put("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGender)))
            .andExpect(status().isOk());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeUpdate);
        Gender testGender = genderList.get(genderList.size() - 1);
        assertThat(testGender.getMalePercent()).isEqualTo(UPDATED_MALE_PERCENT);
        assertThat(testGender.getFemalePercent()).isEqualTo(UPDATED_FEMALE_PERCENT);
    }

    @Test
    @Transactional
    public void updateNonExistingGender() throws Exception {
        int databaseSizeBeforeUpdate = genderRepository.findAll().size();

        // Create the Gender

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenderMockMvc.perform(put("/api/genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gender)))
            .andExpect(status().isBadRequest());

        // Validate the Gender in the database
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGender() throws Exception {
        // Initialize the database
        genderService.save(gender);

        int databaseSizeBeforeDelete = genderRepository.findAll().size();

        // Delete the gender
        restGenderMockMvc.perform(delete("/api/genders/{id}", gender.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gender> genderList = genderRepository.findAll();
        assertThat(genderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
