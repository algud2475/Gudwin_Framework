package entities;

import helpers.db.classic.Col;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Col(name = "FOOD_ID") // самопис
    private Integer foodId;
    @Col(name = "FOOD_NAME")
    private String foodName;
    @Col(name = "FOOD_TYPE")
    private String footType;
    @Col(name = "FOOD_EXOTIC")
    private BigDecimal foodExotic;
}
