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


@Slf4j
@RestController
@RequestMapping("")
public class NetworkAnalyzerController {
    private boolean sendNodes=false;
    private boolean sendConnections=false;

    private final CalculatorPaths calculatorPaths = new CalculatorPaths();

    @PostMapping("/nodes")
    public ResponseEntity<List<Node>> postNodes(@Valid @RequestBody List<Node> nodes) {
        calculatorPaths.setNodes(nodes);
        log.info("Created {}", nodes.toString());
        sendNodes=true;
        return ResponseEntity.ok(nodes);
    }

    @PostMapping("/connections")
    public ResponseEntity<List<Connection>> putConnections(@RequestBody List<Connection> connections) {
        calculatorPaths.setConnections(connections);
        log.info("Created {}", connections.toString());
        sendConnections=true;
        return ResponseEntity.ok(connections);
    }

    @GetMapping("/bfs")
    public ResponseEntity<Result> Solution(){
        if(!sendNodes||!sendConnections)throw new HTTPError("First send nodes and connections!");
        return ResponseEntity.ok(calculatorPaths.BFS_path());
    }

    @GetMapping("/greedy")
    public ResponseEntity<Result> Solution2(){
        if(!sendNodes||!sendConnections)throw new HTTPError("First send nodes and connections!");
        return ResponseEntity.ok(calculatorPaths.Greedy_algorithm());
    }
}
