package networkanalyzer.rest;

import lombok.extern.slf4j.Slf4j;
import networkanalyzer.logic.*;
import networkanalyzer.logic.algorithms.BfsSearch;
import networkanalyzer.logic.algorithms.ContextSearch;
import networkanalyzer.logic.algorithms.GreedySearch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * controller ktorego zadaniem jest obsluga requestow wprowadzanych przez wlasciceli sieci
 */

@Slf4j
@RestController
@RequestMapping("")
public class NetworkAnalyzerController {
    /**
     * zmienna 'bool' czy wyslano wierzcholki
     */
    private boolean sendNodes=false;
    /**
     * zmienna 'bool' czy wyslano polaczenia
     */
    private boolean sendConnections=false;


    private final ContextSearch contextSearch = new ContextSearch();

    /**
     * metoda obslugujaca request POST ktory powinien przekazac liste wierzcholkow grafu
     * @param nodes body requesta w ktorym znajduje sie lista wierzcholkow
     * @return zwracana jest lista obiektow typu Node
     */
    @PutMapping("/nodes")
    public ResponseEntity<List<Node>> postNodes(@Valid @RequestBody List<Node> nodes) {
        contextSearch.setNodes(nodes);
        log.info("Created {}", nodes.toString());
        sendNodes=true;
        return ResponseEntity.ok(nodes);
    }

    /**
     *  metoda obslugujaca request POST ktory powinien przekazac liste polaczen miedzy wierzcholkami grafu
     * @param connections body requesta w ktorym znajduje sie lista polaczen
     * @return zwracana jest lista obiektow typu Connection
     */
    @PutMapping("/connections")
    public ResponseEntity<List<Connection>> putConnections(@RequestBody List<Connection> connections) {
        contextSearch.setConnections(connections);
        log.info("Created {}", connections.toString());
        sendConnections=true;
        return ResponseEntity.ok(connections);
    }

    /**
     * metoda obslugujaca request GET zwracajac rozwiazanie najkrotszej sciezki grafu metoda BFS
     * @return zwracane jest rozwiazanie w postacji listy id oraz sumaryczny koszt znalezionej sciezki
     */
    @GetMapping("/bfs")
    public ResponseEntity<Result> Solution(){
        if(!sendNodes||!sendConnections)throw new HTTPError("First send nodes and connections!");
        contextSearch.setStrategySearch(new BfsSearch());
        return ResponseEntity.ok(contextSearch.doSearch());
    }

    /**
     * metoda obslugujaca request GET zwracajac rozwiazanie najkrotszej sciezki grafu metoda algorytmu zachlannego
     * @return zwracane jest rozwiazanie w postacji listy id oraz sumaryczny koszt znalezionej sciezki
     */
    @GetMapping("/greedy")
    public ResponseEntity<Result> Solution2(){
        if(!sendNodes||!sendConnections)throw new HTTPError("First send nodes and connections!");
        contextSearch.setStrategySearch(new GreedySearch());
        return ResponseEntity.ok(contextSearch.doSearch());
    }
}
