package org.vin.locations.web.rest;

import org.vin.locations.AbstractCassandraTest;
import org.vin.locations.LocationsCasReactApp;

import org.vin.locations.domain.Status;
import org.vin.locations.repository.StatusRepository;
import org.vin.locations.service.StatusService;
import org.vin.locations.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StatusResource REST controller.
 *
 * @see StatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LocationsCasReactApp.class)
public class StatusResourceIntTest extends AbstractCassandraTest {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TRUNCATED = false;
    private static final Boolean UPDATED_IS_TRUNCATED = true;

    private static final Long DEFAULT_IN_REPLY_TO_STATUS_ID = 1L;
    private static final Long UPDATED_IN_REPLY_TO_STATUS_ID = 2L;

    private static final Long DEFAULT_IN_REPLY_TO_USER_ID = 1L;
    private static final Long UPDATED_IN_REPLY_TO_USER_ID = 2L;

    private static final Boolean DEFAULT_IS_FAVORITED = false;
    private static final Boolean UPDATED_IS_FAVORITED = true;

    private static final Boolean DEFAULT_IS_RETWEETED = false;
    private static final Boolean UPDATED_IS_RETWEETED = true;

    private static final Integer DEFAULT_FAVORITE_COUNT = 1;
    private static final Integer UPDATED_FAVORITE_COUNT = 2;

    private static final String DEFAULT_IN_REPLY_TO_SCREEN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IN_REPLY_TO_SCREEN_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_RETWEET_COUNT = 1L;
    private static final Long UPDATED_RETWEET_COUNT = 2L;

    private static final Boolean DEFAULT_IS_POSSIBLY_SENSITIVE = false;
    private static final Boolean UPDATED_IS_POSSIBLY_SENSITIVE = true;

    private static final String DEFAULT_LANGU = "AAAAAAAAAA";
    private static final String UPDATED_LANGU = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTRIBUTORS_I_DS = 1L;
    private static final Long UPDATED_CONTRIBUTORS_I_DS = 2L;

    private static final Long DEFAULT_CURRENT_USER_RETWEET_ID = 1L;
    private static final Long UPDATED_CURRENT_USER_RETWEET_ID = 2L;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restStatusMockMvc;

    private Status status;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatusResource statusResource = new StatusResource(statusService);
        this.restStatusMockMvc = MockMvcBuilders.standaloneSetup(statusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createEntity() {
        Status status = new Status()
            .createdAt(DEFAULT_CREATED_AT)
            .text(DEFAULT_TEXT)
            .source(DEFAULT_SOURCE)
            .isTruncated(DEFAULT_IS_TRUNCATED)
            .inReplyToStatusId(DEFAULT_IN_REPLY_TO_STATUS_ID)
            .inReplyToUserId(DEFAULT_IN_REPLY_TO_USER_ID)
            .isFavorited(DEFAULT_IS_FAVORITED)
            .isRetweeted(DEFAULT_IS_RETWEETED)
            .favoriteCount(DEFAULT_FAVORITE_COUNT)
            .inReplyToScreenName(DEFAULT_IN_REPLY_TO_SCREEN_NAME)
            .retweetCount(DEFAULT_RETWEET_COUNT)
            .isPossiblySensitive(DEFAULT_IS_POSSIBLY_SENSITIVE)
            .langu(DEFAULT_LANGU)
            .contributorsIDs(DEFAULT_CONTRIBUTORS_I_DS)
            .currentUserRetweetId(DEFAULT_CURRENT_USER_RETWEET_ID);
        return status;
    }

    @Before
    public void initTest() {
        statusRepository.deleteAll();
        status = createEntity();
    }

    @Test
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStatus.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testStatus.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testStatus.isIsTruncated()).isEqualTo(DEFAULT_IS_TRUNCATED);
        assertThat(testStatus.getInReplyToStatusId()).isEqualTo(DEFAULT_IN_REPLY_TO_STATUS_ID);
        assertThat(testStatus.getInReplyToUserId()).isEqualTo(DEFAULT_IN_REPLY_TO_USER_ID);
        assertThat(testStatus.isIsFavorited()).isEqualTo(DEFAULT_IS_FAVORITED);
        assertThat(testStatus.isIsRetweeted()).isEqualTo(DEFAULT_IS_RETWEETED);
        assertThat(testStatus.getFavoriteCount()).isEqualTo(DEFAULT_FAVORITE_COUNT);
        assertThat(testStatus.getInReplyToScreenName()).isEqualTo(DEFAULT_IN_REPLY_TO_SCREEN_NAME);
        assertThat(testStatus.getRetweetCount()).isEqualTo(DEFAULT_RETWEET_COUNT);
        assertThat(testStatus.isIsPossiblySensitive()).isEqualTo(DEFAULT_IS_POSSIBLY_SENSITIVE);
        assertThat(testStatus.getLangu()).isEqualTo(DEFAULT_LANGU);
        assertThat(testStatus.getContributorsIDs()).isEqualTo(DEFAULT_CONTRIBUTORS_I_DS);
        assertThat(testStatus.getCurrentUserRetweetId()).isEqualTo(DEFAULT_CURRENT_USER_RETWEET_ID);
    }

    @Test
    public void createStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status with an existing ID
        status.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get all the statusList
        restStatusMockMvc.perform(get("/api/statuses"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].isTruncated").value(hasItem(DEFAULT_IS_TRUNCATED.booleanValue())))
            .andExpect(jsonPath("$.[*].inReplyToStatusId").value(hasItem(DEFAULT_IN_REPLY_TO_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].inReplyToUserId").value(hasItem(DEFAULT_IN_REPLY_TO_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].isFavorited").value(hasItem(DEFAULT_IS_FAVORITED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRetweeted").value(hasItem(DEFAULT_IS_RETWEETED.booleanValue())))
            .andExpect(jsonPath("$.[*].favoriteCount").value(hasItem(DEFAULT_FAVORITE_COUNT)))
            .andExpect(jsonPath("$.[*].inReplyToScreenName").value(hasItem(DEFAULT_IN_REPLY_TO_SCREEN_NAME.toString())))
            .andExpect(jsonPath("$.[*].retweetCount").value(hasItem(DEFAULT_RETWEET_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].isPossiblySensitive").value(hasItem(DEFAULT_IS_POSSIBLY_SENSITIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].langu").value(hasItem(DEFAULT_LANGU.toString())))
            .andExpect(jsonPath("$.[*].contributorsIDs").value(hasItem(DEFAULT_CONTRIBUTORS_I_DS.intValue())))
            .andExpect(jsonPath("$.[*].currentUserRetweetId").value(hasItem(DEFAULT_CURRENT_USER_RETWEET_ID.intValue())));
    }

    @Test
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId().toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.isTruncated").value(DEFAULT_IS_TRUNCATED.booleanValue()))
            .andExpect(jsonPath("$.inReplyToStatusId").value(DEFAULT_IN_REPLY_TO_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.inReplyToUserId").value(DEFAULT_IN_REPLY_TO_USER_ID.intValue()))
            .andExpect(jsonPath("$.isFavorited").value(DEFAULT_IS_FAVORITED.booleanValue()))
            .andExpect(jsonPath("$.isRetweeted").value(DEFAULT_IS_RETWEETED.booleanValue()))
            .andExpect(jsonPath("$.favoriteCount").value(DEFAULT_FAVORITE_COUNT))
            .andExpect(jsonPath("$.inReplyToScreenName").value(DEFAULT_IN_REPLY_TO_SCREEN_NAME.toString()))
            .andExpect(jsonPath("$.retweetCount").value(DEFAULT_RETWEET_COUNT.intValue()))
            .andExpect(jsonPath("$.isPossiblySensitive").value(DEFAULT_IS_POSSIBLY_SENSITIVE.booleanValue()))
            .andExpect(jsonPath("$.langu").value(DEFAULT_LANGU.toString()))
            .andExpect(jsonPath("$.contributorsIDs").value(DEFAULT_CONTRIBUTORS_I_DS.intValue()))
            .andExpect(jsonPath("$.currentUserRetweetId").value(DEFAULT_CURRENT_USER_RETWEET_ID.intValue()));
    }

    @Test
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findOne(status.getId());
        updatedStatus
            .createdAt(UPDATED_CREATED_AT)
            .text(UPDATED_TEXT)
            .source(UPDATED_SOURCE)
            .isTruncated(UPDATED_IS_TRUNCATED)
            .inReplyToStatusId(UPDATED_IN_REPLY_TO_STATUS_ID)
            .inReplyToUserId(UPDATED_IN_REPLY_TO_USER_ID)
            .isFavorited(UPDATED_IS_FAVORITED)
            .isRetweeted(UPDATED_IS_RETWEETED)
            .favoriteCount(UPDATED_FAVORITE_COUNT)
            .inReplyToScreenName(UPDATED_IN_REPLY_TO_SCREEN_NAME)
            .retweetCount(UPDATED_RETWEET_COUNT)
            .isPossiblySensitive(UPDATED_IS_POSSIBLY_SENSITIVE)
            .langu(UPDATED_LANGU)
            .contributorsIDs(UPDATED_CONTRIBUTORS_I_DS)
            .currentUserRetweetId(UPDATED_CURRENT_USER_RETWEET_ID);

        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatus)))
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStatus.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testStatus.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testStatus.isIsTruncated()).isEqualTo(UPDATED_IS_TRUNCATED);
        assertThat(testStatus.getInReplyToStatusId()).isEqualTo(UPDATED_IN_REPLY_TO_STATUS_ID);
        assertThat(testStatus.getInReplyToUserId()).isEqualTo(UPDATED_IN_REPLY_TO_USER_ID);
        assertThat(testStatus.isIsFavorited()).isEqualTo(UPDATED_IS_FAVORITED);
        assertThat(testStatus.isIsRetweeted()).isEqualTo(UPDATED_IS_RETWEETED);
        assertThat(testStatus.getFavoriteCount()).isEqualTo(UPDATED_FAVORITE_COUNT);
        assertThat(testStatus.getInReplyToScreenName()).isEqualTo(UPDATED_IN_REPLY_TO_SCREEN_NAME);
        assertThat(testStatus.getRetweetCount()).isEqualTo(UPDATED_RETWEET_COUNT);
        assertThat(testStatus.isIsPossiblySensitive()).isEqualTo(UPDATED_IS_POSSIBLY_SENSITIVE);
        assertThat(testStatus.getLangu()).isEqualTo(UPDATED_LANGU);
        assertThat(testStatus.getContributorsIDs()).isEqualTo(UPDATED_CONTRIBUTORS_I_DS);
        assertThat(testStatus.getCurrentUserRetweetId()).isEqualTo(UPDATED_CURRENT_USER_RETWEET_ID);
    }

    @Test
    public void updateNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Create the Status

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Get the status
        restStatusMockMvc.perform(delete("/api/statuses/{id}", status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Status.class);
        Status status1 = new Status();
        status1.setId(UUID.randomUUID());
        Status status2 = new Status();
        status2.setId(status1.getId());
        assertThat(status1).isEqualTo(status2);
        status2.setId(UUID.randomUUID());
        assertThat(status1).isNotEqualTo(status2);
        status1.setId(null);
        assertThat(status1).isNotEqualTo(status2);
    }
}
