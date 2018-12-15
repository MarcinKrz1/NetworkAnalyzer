package networkanalyzer.test.juint;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.algorithms.BfsSearch;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BfsSearchTest {
    BfsSearch bs;
    List<Node> nodes;
    List<Connection>connections;

    private void newNode(Integer id,String type){
        Node n = mock(Node.class);
        when(n.getId()).thenReturn(id);
        when(n.getType()).thenReturn(type);
        nodes.add(n);
    }

    private void newConn(int from, int to, int value){
        Connection c = mock(Connection.class);
        when(c.getFrom()).thenReturn(from);
        when(c.getTo()).thenReturn(to);
        when(c.getValue()).thenReturn(value);
        connections.add(c);
    }

    @Before
    public void init(){
        nodes = new ArrayList<Node>();
        connections = new ArrayList<Connection>();
    }

    @Test
    public void test1(){
        newNode(1,"entry");
        newNode(2,"entry");

        assertTrue(true);
    }

}