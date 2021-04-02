package sharifullinruzil.crudapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sharifullinruzil.crudapp.MealTestData;
import sharifullinruzil.crudapp.config.MvcConfig;
import sharifullinruzil.crudapp.config.TestConfig;
import sharifullinruzil.crudapp.domain.Meal;
import sharifullinruzil.crudapp.service.MealService;
import sharifullinruzil.crudapp.util.NotFoundException;

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
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rest/meals")).
                andExpect(status().isOk()).
                andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(content().json(jacksonObjectMapper.writeValueAsString(meals)));
    }

    @Test
    void get() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rest/meals/{id}", MEAL1_ID)).
                andExpect(status().isOk()).
                andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(content().json(jacksonObjectMapper.writeValueAsString(meal1)));
    }

    @Test
    void create() throws Exception {
        Meal aNew = getNew();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/meals").contentType(MediaType.APPLICATION_JSON).
                content(jacksonObjectMapper.writeValueAsString(aNew))).
                andExpect(status().isNoContent());

    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/rest/meals/{id}", updated.getId()).contentType(MediaType.APPLICATION_JSON).
                content(jacksonObjectMapper.writeValueAsString(updated))).
                andExpect(status().isNoContent());
        assertThat(mealService.get(updated.getId(), USER_ID)).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rest/meals/{id}", MEAL1_ID)).andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void deleteAbsent() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rest/meals/{id}", WRONG_MEAL_ID)).andExpect(status().isUnprocessableEntity());
    }

}