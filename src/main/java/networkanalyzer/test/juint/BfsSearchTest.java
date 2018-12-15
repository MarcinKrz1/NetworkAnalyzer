package networkanalyzer.test.juint;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.algorithms.BfsSearch;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class BfsSearchTest {
    BfsSearch bs;
    static List<Node> nodes;
    static List<Connection>connections;

    @BeforeClass
    public static void init(){
        nodes = new ArrayList<Node>();
        connections = new ArrayList<Connection>();
    }

    @Test
    public void test1(){
        assertTrue(true);
    }

}