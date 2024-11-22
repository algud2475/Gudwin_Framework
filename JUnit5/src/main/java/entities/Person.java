package entities;

import helpers.db.classic.Col;
import lombok.*;

//import javax.persistence.*;

/**
 * Сущность из БД для таблицы 'person'
 */
//@Entity(name = "person") //название таблицы в бд
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Col(name = "person_id") // самопис
    //@Column(name = "product_id") // hibernate
    private Long id;
    @Col(name = "first_name")
    private String name;
    @Col(name = "last_name")
    private String lastName;
    @Col(name = "middle_name")
    private String middleName;
}
