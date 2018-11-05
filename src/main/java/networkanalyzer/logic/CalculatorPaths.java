package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CalculatorPaths {
    private List<Node> nodes;
    private List<Connection> connections;
}
