package sharifullinruzil.crudapp.util;

import org.junit.jupiter.api.Test;
import sharifullinruzil.crudapp.dto.MealDto;

import java.util.List;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;
import static sharifullinruzil.crudapp.MealTestData.*;

class MealsUtilTest {

    @Test
    void getConvertedToDto() {
        List<Boolean> expected = List.of(false, false, false, false, false, false, true, true, true);
        List<Boolean> actual = MealsUtil.getConvertedToDto(meals, 2000).
                stream().map(MealDto::isExcess).collect(toList());
        assertThat(actual).isEqualTo(expected);
    }
}