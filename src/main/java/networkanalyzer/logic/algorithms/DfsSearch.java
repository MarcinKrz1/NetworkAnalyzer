package networkanalyzer.logic.algorithms;

import networkanalyzer.logic.Connection;
import networkanalyzer.logic.HTTPError;
import networkanalyzer.logic.Result;
import networkanalyzer.logic.Node;

import java.util.ArrayList;
import java.util.List;

public class DfsSearch extends StrategySearch{

    private List<vc>[] neighbour;
    private List<Integer> tempRes, result;
    private int tempSum, sum;
    private int[] visited;


    public Result executeSearch() {

        for (Connection c : connections)
            if (c.getValue() < 1) {throw new HTTPError("Edge cost must be >=1");}

        findEntryExitNode();
        if(!findEntry || !findExit) {throw new HTTPError("No entry or exit node!");}

        int maks=0;
        for (Node c : nodes) {
            if (c.getId() > maks) {maks = c.getId();}
        }

        neighbour = new ArrayList[maks+1];
        for(int i=0 ; i<maks+1 ; i++) {
            neighbour[i] = new ArrayList<vc>();
        }
        for(Connection c : connections) {
            vc vcTemp = new vc();
            vcTemp.v = c.getTo();
            vcTemp.c = c.getValue();
            neighbour[c.getFrom()].add(vcTemp);
        }

        result = new ArrayList<Integer>();
        result.add(entryNode.getId());
        sum = 1000000;

        tempRes = new ArrayList<Integer>();
        tempRes.add(entryNode.getId());
        tempSum = 0;

        visited = new int[maks+1];
        for (int i=0 ; i<visited.length ; i++) {visited[i] = 0;}

        dfs(entryNode.getId());

        /*boolean end = false;
        while(!end) {

            for(int i)
        }*/
        Result finalRes = new Result();
        finalRes.setAmount(result.size());
        for (int i=0 ; i<result.size() ; i++) {
            finalRes.setNode(i, result.get(i));
        }
        finalRes.setResultSum(sum);

        return finalRes;
    }

    private void dfs(int idNode) {
        System.out.println(idNode);
        if (idNode == exitNode.getId()) {
            if (tempSum < sum) {
                result.clear();
                result.addAll(tempRes);
                sum = tempSum;
            }
        }
        else {
            //for (int i=0 ; i<neighbour[idNode].size() ; i++) {
            for (vc next : neighbour[idNode]) {
                if(visited[next.v] == 0) {
                    tempSum += next.c;
                    visited[next.v] = 1;
                    tempRes.add(next.v);
                    dfs(next.v);
                    tempRes.remove(tempRes.size()-1);
                    visited[next.v] = 0;
                    tempSum -= next.c;
                }
            }
        }
    }

    class vc {
        int v, c;
    }
}
