package networkanalyzer.rest;

import lombok.extern.slf4j.Slf4j;
import networkanalyzer.logic.CalculatorPaths;
import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import networkanalyzer.logic.HTTPError;

/**
 * controller ktorego zadaniem jest obsluga requestow wprowadzanych przez wlasciceli sieci
 */

@Slf4j
@RestController
@RequestMapping("")
public class NetworkAnalyzerController {
    private boolean sendNodes=false;
    private boolean sendConnections=false;

    private final CalculatorPaths calculatorPaths = new CalculatorPaths();

    /**
     * metoda obslugujaca request POST ktory powinien przekazac liste wierzcholkow grafu
     * @param nodes body requesta w ktorym znajduje sie lista wierzcholkow
     * @return zwracana jest lista obiektow typu Node
     */
    @PostMapping("/nodes")
    public ResponseEntity<List<Node>> postNodes(@Valid @RequestBody List<Node> nodes) {
        calculatorPaths.setNodes(nodes);
        log.info("Created {}", nodes.toString());
        sendNodes=true;
        return ResponseEntity.ok(nodes);
    }

    /**
     *  metoda obslugujaca request POST ktory powinien przekazac liste polaczen miedzy wierzcholkami grafu
     * @param connections body requesta w ktorym znajduje sie lista polaczen
     * @return zwracana jest lista obiektow typu Connection
     */
    @PostMapping("/connections")
    public ResponseEntity<List<Connection>> putConnections(@RequestBody List<Connection> connections) {
        calculatorPaths.setConnections(connections);
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
        return ResponseEntity.ok(calculatorPaths.BFS_path());
    }

    /**
     * metoda obslugujaca request GET zwracajac rozwiazanie najkrotszej sciezki grafu metoda algorytmu zachlannego
     * @return zwracane jest rozwiazanie w postacji listy id oraz sumaryczny koszt znalezionej sciezki
     */
    @GetMapping("/greedy")
    public ResponseEntity<Result> Solution2(){
        if(!sendNodes||!sendConnections)throw new HTTPError("First send nodes and connections!");
        return ResponseEntity.ok(calculatorPaths.Greedy_algorithm());
    }
}
