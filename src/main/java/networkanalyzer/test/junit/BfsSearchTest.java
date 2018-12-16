package networkanalyzer.test.junit;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;
import networkanalyzer.logic.algorithms.BfsSearch;
import org.apache.coyote.http11.Http11AprProtocol;
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

    @Test(expected = HTTPError.class)
    public void test1(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"entry");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,3);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
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
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test3(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,5,4);
        newConn(3,4,3);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test4(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,0);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
    }

    @Test(expected = HTTPError.class)
    public void test5(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(3,4,7);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
    }

    @Test
    public void test6(){
        newNode(1,"entry");
        newNode(2,"regular");
        newNode(3,"regular");
        newNode(4,"exit");
        newConn(1,2,2);
        newConn(2,3,4);
        newConn(3,4,3);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
        assertTrue(r.getResultSum()==9);
        assertTrue(r.getNode()[0]==1);
        assertTrue(r.getNode()[1]==2);
        assertTrue(r.getNode()[2]==3);
        assertTrue(r.getNode()[3]==4);
    }

    @Test
    public void test7(){
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
        newConn(2,3,1);
        newConn(3,6,1);
        newConn(3,4,3);
        newConn(4,2,3);
        bs = new BfsSearch(nodes,connections);
        Result r = bs.executeSearch();
        System.out.print(r.getResultSum());
        assertTrue(r.getResultSum()==6);
    }

    @Test
    public void test8(){
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
        System.out.print(r.getResultSum());
        assertTrue(r.getResultSum()==7);
    }
}