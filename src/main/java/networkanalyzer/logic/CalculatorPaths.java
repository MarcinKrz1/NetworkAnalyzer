package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.LinkedList;
import java.util.Stack;

import networkanalyzer.logic.HTTPError;

/**
 * struktura przechowujaca wierzchokek i koszt
 */
class vc {
    public int v, c;
}

/**
 * klasa odpowiadajaca za przechowywanie wlasnosci sieci oraz wyliczanie najkrotszych sciezek metoda zachlanna oraz BFS
 */
@Getter
@Setter
public class CalculatorPaths {
    /**
     * lista wierzcholkow
     */
    private List<Node> nodes;
    /**
     * lista polaczen
     */
    private List<Connection> connections;
    /**
     * wierzcholek o typie 'entry'
     */
    private Node entryNode;
    /**
     * wierzchołek o typie 'exit'
     */
    private Node exitNode;
    /**
     * indeks wierzcholka 'entry' na liscie wierzcholkow
     */
    private int entry = 0;
    /**
     * indeks wierzcholka 'exit' na liscie wierzcholkow
     */
    private int exit = 0;
    /**
     * zmienna czy znaleziono wierzcholek 'entry'
     */
    private boolean findEntry;
    /**
     * zmienna czy znaleziono wierzcholek 'exit'
     */
    private boolean findExit;
    /**
     * tablica przechowujaca id wierzcholka o podanym indeksie na liscie wierzcholkow
     */
    private int verMap[];

    /**
     * metoda znajdujaca wierzcholki entry i exit
     **/
    private void findEntryExitNode() {
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

    /**
     * metoda znajdujaca najkrotsza sciezke metoda BFS
     *
     * @return wynik w postacji identyfikatorow wierzcholkow oraz sumaryczny koszt najkrotszej sciezki w grafie
     */
    public Result BFS_path() {
        ArrayList<Node> bfs = new ArrayList<Node>();
        int vertices = nodes.size();
        for (Connection c : connections)
            if (c.getValue() < 1) throw new HTTPError("Edge cost must be >=1");

        ArrayList<vc>[] neighbour = new ArrayList[vertices];
        for (int i = 0; i < neighbour.length; i++) neighbour[i] = new ArrayList<vc>();

        Result resultr = new Result();
        int sum = 0;

        findEntryExitNode();
        if (!findEntry || !findExit)
            throw new HTTPError("No entry or exit node!");

        for (Connection c : connections) {
            boolean findv1 = false, findv2 = false;
            int i1 = 0, v1 = c.getFrom();
            int i2 = 0, v2 = c.getTo();
            for (int i = 0; i < verMap.length; i++) {
                if (v1 == verMap[i]) {
                    i1 = i;
                    findv1 = true;
                }
                if (v2 == verMap[i]) {
                    i2 = i;
                    findv2 = true;
                }
            }
            if (findv1 && findv2) {
                vc tmpvc = new vc();
                tmpvc.v = i2;
                tmpvc.c = c.getValue();
                neighbour[i1].add(tmpvc);
            } else throw new HTTPError("Doesn't exists vertex in node list!");
        }

        //bfs short algoruthm
        int[] prev_vertices = new int[vertices];
        for (int i = 0; i < prev_vertices.length; i++) prev_vertices[i] = -1;

        LinkedList<Integer> child = new LinkedList<Integer>();
        child.addLast(entry);
        prev_vertices[entry] = -10;
        boolean isLoop = true;
        while (!child.isEmpty() && isLoop) {
            int v = child.getFirst();
            boolean again = false;
            child.removeFirst();
            for (vc vn : neighbour[v])
                if (vn.c > 1) {
                    again = true;
                    vn.c--;
                } else if (prev_vertices[vn.v] == -1) {
                    child.addLast(vn.v);
                    prev_vertices[vn.v] = v;
                    if (vn.v == exit) {
                        isLoop = false;
                        break;
                    }
                }
            if (again) child.addLast(v);
        }

        //get solution
        Stack<Integer> result = new Stack<Integer>();
        int idx = exit;
        result.push(idx);
        while (prev_vertices[idx] > -1) {
            result.push(prev_vertices[idx]);
            idx = prev_vertices[idx];
        }
        if (result.size() < 2)
            throw new HTTPError("Path doesnt't exists!");

        while (!result.empty())
            bfs.add(nodes.get(result.pop()));

        resultr.setAmount(bfs.size());
        int i = 0;
        for (Node n : bfs) {
            resultr.setNode(i, n.getId());
            if (bfs.indexOf(n) != (bfs.size() - 1))
                for (Connection c : connections) {
                    if (c.getFrom() == n.getId() && c.getTo() == bfs.get(bfs.indexOf(n) + 1).getId())
                        sum += c.getValue();
                }
            i++;
        }
        resultr.setResultSum(sum);

        return resultr;
    }

    /**
     * metoda znajdujaca najkrotsza sciezke metoda BFS
     *
     * @return wynik w postacji identyfikatorow wierzcholkow oraz sumaryczny koszt najkrotszej sciezki w grafie
     */
    public Result Greedy_algorithm() {

        boolean isExit = false;
        int minVal, minId, element = 0, sum = 0;
        List<Node> result = new ArrayList();
        List<Connection>[] pomConnections = new ArrayList[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) pomConnections[i] = new ArrayList<Connection>();
        Result resultr = new Result();

        for (Connection c : connections) {
            for (int i = 0; i < nodes.size(); i++) {
                if (c.getFrom() == nodes.get(i).getId())
                    pomConnections[i].add(c);
            }
        }

        //Find Entry and Exit node if not, program final with error
        findEntryExitNode();
        if (!findEntry || !findExit)
            throw new HTTPError("No entry or exit node!");

        result.add(entryNode);
        while (!isExit) {
            if (!pomConnections[nodes.indexOf(result.get(result.size() - 1))].isEmpty()) {
                minVal = pomConnections[nodes.indexOf(result.get(result.size() - 1))].get(0).getValue();
                minId = pomConnections[nodes.indexOf(result.get(result.size() - 1))].get(0).getTo();

                for (Connection c : pomConnections[nodes.indexOf(result.get(result.size() - 1))]) {
                    if (minVal > c.getValue()) {//get element with the smallest value and number id
                        minVal = c.getValue();
                        minId = c.getTo();
                    }
                }

                for (int i = 0; i < verMap.length; i++) {
                    if (verMap[i] == minId) element = i;
                }

                if (nodes.get(element).getType().equals("exit")) {
                    isExit = true;
                    result.add(nodes.get(element));

                } else if (result.contains(nodes.get(element))) {
                    List<Connection> temp = new ArrayList<Connection>(pomConnections[nodes.indexOf(result.get(result.size() - 1))]);
                    for (Connection c : temp) {
                        if (c.getTo() == minId) {
                            pomConnections[nodes.indexOf(result.get(result.size() - 1))].remove(c);
                        }
                    }
                } else {
                    pomConnections[element].clear();
                    for (Connection c : connections) {
                        if (c.getFrom() == nodes.get(element).getId())
                            pomConnections[element].add(c);
                    }
                    result.add(nodes.get(element));
                }
            } else if (!result.get(result.size() - 1).getType().equals("entry")) {

                List<Connection> temp = new ArrayList<Connection>(pomConnections[nodes.indexOf(result.get(result.size() - 2))]);
                for (Connection c : temp) {
                    if (c.getTo() == result.get(result.size() - 1).getId()) {
                        pomConnections[nodes.indexOf(result.get(result.size() - 2))].remove(c);
                    }
                }
                result.remove(result.size() - 1);

            } else {
                throw new HTTPError("Path doesnt't exists!");
            }
        }
        resultr.setAmount(result.size());
        int i = 0;
        for (Node n : result) {
            resultr.setNode(i, n.getId());
            if (result.indexOf(n) != (result.size() - 1))
                for (Connection c : connections) {
                    if (c.getFrom() == n.getId() && c.getTo() == result.get(result.indexOf(n) + 1).getId())
                        sum += c.getValue();
                }
            i++;
        }
        resultr.setResultSum(sum);

        return resultr;
    }
}
