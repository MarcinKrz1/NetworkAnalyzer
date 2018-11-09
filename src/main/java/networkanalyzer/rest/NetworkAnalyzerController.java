package networkanalyzer.rest;

import lombok.extern.slf4j.Slf4j;
import networkanalyzer.logic.CalculatorPaths;
import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("")
public class NetworkAnalyzerController {

    CalculatorPaths calculatorPaths = new CalculatorPaths();

    @RequestMapping(method = RequestMethod.POST, value = "/nodes")
    public ResponseEntity<List<Node>> postNodes(@Valid @RequestBody List<Node> nodes) {
        calculatorPaths.setNodes(nodes);
        log.info("Created {}", nodes.toString());
        return ResponseEntity.ok(nodes);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/connections")
    public ResponseEntity<List<Connection>> putConnections(@RequestBody List<Connection> connections) {
        calculatorPaths.setConnections(connections);
        log.info("Created {}", connections.toString());
        return ResponseEntity.ok(connections);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bfs")
    public ResponseEntity<List<Node>> Solution(){
        return ResponseEntity.ok(calculatorPaths.BFS_path());
    }
}
