package networkanalyzer.logic.algorithms;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GreedySearchTest {
    GreedySearch greedySearch;
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

    @Test(expected = HTTPError.class)
    public void test1(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"entry");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,3);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test2(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"regular");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,3);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test3(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"exit");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,3);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test4(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(3,4,7);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
    }

    @Test
    public void test5(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,3);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
        assertTrue(r.getResultSum()==9);
        assertTrue(r.getNode()[0]==1);
        assertTrue(r.getNode()[1]==2);
        assertTrue(r.getNode()[2]==3);
        assertTrue(r.getNode()[3]==4);
    }


    @Test
    public void test6(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"regular");
        newNode(5,"regular");
        newNode(6,"exit");
        newNode(7,"regular");
        newConn(1,2,3);
        newConn(2,3,2);
        newConn(3,1,6);
        newConn(1,4,3);
        newConn(3,6,7);
        newConn(4,5,1);
        newConn(5,3,1);
        newConn(5,6,2);
        newConn(6,4,3);
        newConn(1,7,1);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
        assertTrue(r.getResultSum()==12);
        assertTrue(r.getNode()[0]==1);
        assertTrue(r.getNode()[1]==2);
        assertTrue(r.getNode()[2]==3);
        assertTrue(r.getNode()[3]==6);
    }


    @Test
    public void test7(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"regular");
        newNode(5,"regular");
        newNode(6,"exit");
        newNode(7,"regular");
        newConn(1,2,4);
        newConn(2,3,2);
        newConn(3,1,6);
        newConn(1,4,3);
        newConn(3,6,7);
        newConn(4,5,1);
        newConn(5,3,1);
        newConn(5,6,2);
        newConn(6,4,3);
        newConn(1,7,1);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
        assertTrue(r.getResultSum()==12);
        assertTrue(r.getNode()[0]==1);
        assertTrue(r.getNode()[1]==4);
        assertTrue(r.getNode()[2]==5);
        assertTrue(r.getNode()[3]==3);
        assertTrue(r.getNode()[4]==6);
    }

    @Test
    public void test8(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(-4,"regular");
        newNode(5,"regular");
        newNode(6,"exit");
        newNode(7,"regular");
        newConn(1,2,4);
        newConn(2,3,2);
        newConn(3,1,6);
        newConn(1,-4,3);
        newConn(3,6,7);
        newConn(-4,5,1);
        newConn(5,3,1);
        newConn(5,6,2);
        newConn(6,-4,3);
        newConn(1,7,1);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
        assertTrue(r.getResultSum()==12);
        assertTrue(r.getNode()[0]==1);
        assertTrue(r.getNode()[1]==-4);
        assertTrue(r.getNode()[2]==5);
        assertTrue(r.getNode()[3]==3);
        assertTrue(r.getNode()[4]==6);
    }

    @Test
    public void test9(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"regular");
        newNode(5,"regular");
        newNode(6,"exit");
        newNode(7,"regular");
        newConn(1,2,4);
        newConn(2,3,2);
        newConn(3,1,6);
        newConn(1,4,3);
        newConn(3,6,7);
        newConn(4,5,1);
        newConn(5,3,3);
        newConn(5,6,2);
        newConn(6,4,3);
        newConn(1,7,1);
        greedySearch = new GreedySearch(nodes,connections);
        Result r = greedySearch.executeSearch();
        assertTrue(r.getResultSum()==6);
        assertTrue(r.getNode()[0]==1);
        assertTrue(r.getNode()[1]==4);
        assertTrue(r.getNode()[2]==5);
        assertTrue(r.getNode()[3]==6);
    }
}