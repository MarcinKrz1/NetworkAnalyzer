package networkanalyzer.logic.algorithms;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Node;
import networkanalyzer.logic.Result;

import java.util.ArrayList;
import java.util.List;

public class GreedySearch extends StrategySearch {


    /**
     * metoda znajdujaca najkrotsza sciezke metoda BFS
     *
     * @return wynik w postacji identyfikatorow wierzcholkow oraz sumaryczny koszt najkrotszej sciezki w grafie
     */

    public Result executeSearch() {

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
