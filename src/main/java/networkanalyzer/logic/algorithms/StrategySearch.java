package networkanalyzer.logic.algorithms;

import lombok.Getter;
import lombok.Setter;
import networkanalyzer.logic.Connection;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;

import java.util.List;

@Getter
@Setter
public abstract class StrategySearch {

    /**
     * lista wierzcholkow
     */
    protected List<Node> nodes;
    /**
     * lista polaczen
     */
    protected List<Connection> connections;
    /**
     * wierzcholek o typie 'entry'
     */
    protected Node entryNode;
    /**
     * wierzchołek o typie 'exit'
     */
    protected Node exitNode;
    /**
     * indeks wierzcholka 'entry' na liscie wierzcholkow
     */
    protected int entry = 0;
    /**
     * indeks wierzcholka 'exit' na liscie wierzcholkow
     */
    protected int exit = 0;
    /**
     * zmienna czy znaleziono wierzcholek 'entry'
     */
    protected boolean findEntry;
    /**
     * zmienna czy znaleziono wierzcholek 'exit'
     */
    protected boolean findExit;
    /**
     * tablica przechowujaca id wierzcholka o podanym indeksie na liscie wierzcholkow
     */
    protected int verMap[];


    /**
     * metoda znajdujaca wierzcholki entry i exit
     **/
    protected void findEntryExitNode() {
        verMap = new int[nodes.size()];
        findEntry = false;
        findExit = false;
        for (int i = 0; i < nodes.size(); i++) {
            Node tmpNode = nodes.get(i);
            verMap[i] = tmpNode.getId();
            if (tmpNode.getType().equals("entry")) {
                if (findEntry) throw new HTTPError("Find 2 entry node!");
                findEntry = true;
                entryNode = tmpNode;
                entry = i;
            }
            if (tmpNode.getType().equals("exit")) {
                if (findExit) throw new HTTPError("Find 2 exit node!");
                findExit = true;
                exitNode = tmpNode;
                exit = i;
            }
        }
    }



    public abstract Result executeSearch();
}
