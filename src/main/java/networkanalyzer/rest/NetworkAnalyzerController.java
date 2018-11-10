package networkanalyzer.rest;

import lombok.extern.slf4j.Slf4j;
import networkanalyzer.logic.CalculatorPaths;
import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("")
public class NetworkAnalyzerController {

    private final CalculatorPaths calculatorPaths = new CalculatorPaths();

    @PostMapping("/nodes")
    public ResponseEntity<List<Node>> postNodes(@Valid @RequestBody List<Node> nodes) {
        calculatorPaths.setNodes(nodes);
        log.info("Created {}", nodes.toString());
        return ResponseEntity.ok(nodes);
    }

    @PostMapping("/connections")
    public ResponseEntity<List<Connection>> putConnections(@RequestBody List<Connection> connections) {
        calculatorPaths.setConnections(connections);
        log.info("Created {}", connections.toString());
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/bfs")
    public ResponseEntity<List<Node>> Solution() {
        return ResponseEntity.ok(calculatorPaths.BFS_path());
    }

    @GetMapping("/greedy")
    public ResponseEntity<List<Node>> Solution2() {
        return ResponseEntity.ok(calculatorPaths.Greedy_algorithm());
    }
}
