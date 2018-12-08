package networkanalyzer.logic.algorithms;

import lombok.Getter;
import lombok.Setter;
import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;
import networkanalyzer.logic.algorithms.StrategySearch;

import java.util.List;

@Getter
@Setter
public class ContextSearch {

    private StrategySearch strategySearch;
    private List<Node> nodes;
    private List<Connection> connections;


    public Result doSearch(){
        strategySearch.setNodes(nodes);
        strategySearch.setConnections(connections);
        return strategySearch.executeSearch();
    }
}
