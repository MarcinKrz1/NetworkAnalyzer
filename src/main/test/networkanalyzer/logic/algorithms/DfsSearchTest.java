package networkanalyzer.logic.algorithms;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Result;
import networkanalyzer.logic.algorithms.StrategySearch;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DfsSearchTest {

    DfsSearchTest dfs;
    List<Node> nodes;
    List<Connection> connections;

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
    public void init() {
        nodes = new ArrayList<Node>();
        connections = new ArrayList<Node>();
    }

    @Test(expected = HTTPError.class)
    public void test1() {
        newNode(1, "entry");
        newNode(2, "regular");
        newNode(3, "entry");
        newNode(4, "exit");
        newConn(1,2,3);
        newConn(2,3,4);
        newConn(3,4,3);
        dfs = new DfsSearch(nodes,connections);
        Result r = dfs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test2() {
        newNode(1, "entry");
        newNode(2, "exit");
        newNode(3, "regular");
        newNode(4, "exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,4);
        dfs = new DfsSearch(nodes,connections);
        Result r = dfs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test3() {
        newNode(1, "regular");
        newNode(2, "regular");
        newNode(3, "regular");
        newNode(4, "regular");
        newConn(1,2,1);
        newConn(2,3,1);
        newConn(3,4,1);
        dfs = new DfsSearch(nodes,connections);
        Result r = dfs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test4() {
        newNode(1, "entry");
        newNode(2, "regular");
        newNode(3, "regular");
        newNode(4, "exit");
        newConn(1,2,2);
        newConn(2,34,5);
        newConn(3,4,1);
        dfs = new DfsSearch(nodes,connections);
        Result r = dfs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test5() {
        newNode(1, "entry");
        newNode(2, "regular");
        newNode(3, "regular");
        newNode(4, "exit");
        newConn(1,2,3);
        newConn(2,3,-1);
        newConn(3,4,6);
        dfs = new DfsSearch(nodes,connections);
        Result r = dfs.executeSearch();
    }

    @Test
    public void test6() {
        newNode(1, "entry");
        newNode(2, "regular");
        newNode(3, "regular");
        newNode(4, "regular");
        newNode(5, "regular");
        newNode(6, "regular");
        newNode(7, "exit");
        newConn(1,2,2);
        newConn(1,3,3);
        newConn(2,4,4);
        newConn(2, 5, 5);
        newConn(3, 6, 2);
        newConn(4, 7, 6);
        newConn(5, 7, 3);
        newConn(6, 7, 2);
        dfs = new DfsSearch(nodes,connections);
        Result r = dfs.executeSearch();
        assertTrue(r.getResultSum() == 7);
        assertTrue(r.getNode()[0] == 1);
        assertTrue(r.getNode()[1] == 3);
        assertTrue(r.getNode()[2] == 6);
        assertTrue(r.getNode()[3] == 7);
    }

    @Test
    public void test6(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newNode(5,"regular");
        newNode(6,"regular");
        newConn(1,5,3);
        newConn(5,6,3);
        newConn(6,4,1);
        newConn(6,1,6);
        newConn(1,2,3);
        newConn(2,3,3);
        newConn(3,6,1);
        newConn(3,4,3);
        newConn(4,2,3);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
        assertTrue(r.getResultSum()==7);
        assertTrue(r.getNode()[0] == 1);
        assertTrue(r.getNode()[1] == 5);
        assertTrue(r.getNode()[2] == 6);
        assertTrue(r.getNode()[3] == 4);
    }


}