package networkanalyzer.logic.algorithms;

import lombok.Getter;
import lombok.Setter;
import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;
import networkanalyzer.logic.algorithms.StrategySearch;

import java.util.List;

/**
 * klasa, odpowiada za ustawienie trybu przeszukiwania sieci
 */

@Getter
@Setter
public class ContextSearch {1

    /**
     * pole definiujace tryb przeszukiwanie grafu
     */
    private StrategySearch strategySearch;
    /**
     * lista wierzcholkow klasy Node
     */
    private List<Node> nodes;
    /**
     * lista polaczen klasy Connection
     */
    private List<Connection> connections;

    /**
     * metoda uruchamiajaca przeszukiwanie sciezki, zwraca wyniki klasy Result
     * @return
     */
    public Result doSearch(){
        strategySearch.setNodes(nodes);
        strategySearch.setConnections(connections);
        return strategySearch.executeSearch();
    }
}
