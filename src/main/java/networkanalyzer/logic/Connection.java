package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Connection {
    private Node from;
    private Node to;
    private Float value;
}