package networkanalyzer.logic.algorithms;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * struktura przechowujaca wierzchokek i koszt
 */
class vc {
    public int v, c;
}

public class BfsSearch extends StrategySearch{

    public BfsSearch(){}
    /**
     * konstuktor ustawienie listy nodow i connections, potrzebne do testow
     * @param nodes
     * @param connections
     */
    public BfsSearch(List<Node> nodes,List<Connection> connections){
        this.nodes=nodes;
        this.connections=connections;
    }

    /**
     * metoda znajdujaca najkrotsza sciezke metoda BFS
     *
     * @return wynik w postacji identyfikatorow wierzcholkow oraz sumaryczny koszt najkrotszej sciezki w grafie
     */
    public Result executeSearch() {
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
            System.out.println("n:"+n.getId());
            resultr.setNode(i, n.getId());
            if (bfs.indexOf(n) != (bfs.size() - 1))
                for (Connection c : connections) {
                    if (c.getFrom() == n.getId() && c.getTo() == bfs.get(bfs.indexOf(n) + 1).getId())
                        sum += c.getValue();
                        System.out.println("v:"+c.getValue());
                }
            i++;
        }
        resultr.setResultSum(sum);

        return resultr;
    }
}
