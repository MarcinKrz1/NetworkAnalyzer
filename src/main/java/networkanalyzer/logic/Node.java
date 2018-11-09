package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@ToString
public class Node {

    @NotNull(message = "musisz podac ID")
    private Integer id;
    private String name;
    @NotNull(message = "musisz podac type")
    private String type;
    // private List<Connection> incoming;
    // private List<Connection> outgoing;
    public int getId() {return id;}
    public String getType() {return type;}
}
