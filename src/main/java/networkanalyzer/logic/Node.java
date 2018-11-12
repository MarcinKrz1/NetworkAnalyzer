package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * klasa reprezentujaca wiercholek grafu
 */
//@Validated
@Getter
@Setter
@ToString
public class Node {
    /**
     * unikalny identyfikator
     */
    private Integer id;
    /**
     * opcjonalny tekst opisujący węzeł
     */
    private String name;
    /**
     * typ wierzcholka: 'entry', 'exit', 'regular'
     */
    private String type;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
