package sharifullinruzil.crudapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sharifullinruzil.crudapp.config.MvcConfig;
import sharifullinruzil.crudapp.config.TestConfig;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.service.MealService;
import sharifullinruzil.crudapp.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sharifullinruzil.crudapp.MealTestData.*;

@SpringJUnitWebConfig(classes = {TestConfig.class, MvcConfig.class})
@Transactional
@Rollback
class RestMealControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper jacksonObjectMapper;

    private MealService mealService;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.jacksonObjectMapper = this.webApplicationContext.getBean(ObjectMapper.class);
        this.mealService = this.webApplicationContext.getBean(MealService.class);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/meals")).
                andExpect(status().isOk()).
                andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(content().json(jacksonObjectMapper.writeValueAsString(meals)));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/meals/{id}", MEAL1_ID)).
                andExpect(status().isOk()).
                andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(content().json(jacksonObjectMapper.writeValueAsString(meal1)));
    }

    @Test
    void getNonexistent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/meals/{id}", WRONG_MEAL_ID)).
                andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        Meal newMeal = getNew();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/rest/meals").
                contentType(MediaType.APPLICATION_JSON).
                content(jacksonObjectMapper.writeValueAsString(newMeal)));
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();
        Meal created = jacksonObjectMapper.readValue(jsonResponse, Meal.class);
        newMeal.setId(created.getId());

        assertThat(created).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test
    void createInvalid() throws Exception {
        Meal invalid = new Meal(null, null, "abc", 500);
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/meals").contentType(MediaType.APPLICATION_JSON).
                content(jacksonObjectMapper.writeValueAsString(invalid))).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithExistentDateTime() throws Exception {
        Meal newMeal = getNew();
        newMeal.setDateTime(meal2.getDateTime());
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/meals").contentType(MediaType.APPLICATION_JSON).
                content(jacksonObjectMapper.writeValueAsString(newMeal))).andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put("/rest/meals/{id}", updated.getId()).
                contentType(MediaType.APPLICATION_JSON).content(jacksonObjectMapper.writeValueAsString(updated))).
                andExpect(status().isNoContent());

        assertThat(mealService.get(updated.getId(), USER_ID)).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    void updateInvalid() throws Exception {
        Meal invalid = getUpdated();
        invalid.setDescription("");
        mockMvc.perform(MockMvcRequestBuilders.put("/rest/meals/{id}", invalid.getId()).
                contentType(MediaType.APPLICATION_JSON).content(jacksonObjectMapper.writeValueAsString(invalid)));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/meals/{id}", MEAL1_ID)).andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

}