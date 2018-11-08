package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

class vc{
    public int v,c;
}

@Getter @Setter
public class CalculatorPaths {
    private List<Node> nodes;
    private List<Connection> connections;

    public void BFS_path() {
        int sumCost = 0, vertices = nodes.size(), entry, exit;
        boolean findEntry = false, findExit = false;
        for (Connection c : connections) sumCost += c.getValue();
        int verMap[] = new int[vertices];
        ArrayList<vc>[] neighbour = new ArrayList[vertices + sumCost] ;
        for(int i=0;i<neighbour.length;i++)neighbour[i]= new ArrayList<vc>();
        for (int i = 0; i < verMap.length; i++) {
            Node tmpNode = nodes.get(i);

            verMap[i] = tmpNode.getId();

            if (tmpNode.getType().equals("entry")) {
                if(findEntry)return;
                findEntry = true;
                entry = i;
            }
            if (tmpNode.getType().equals("exit")) {
                if(findExit)return;
                findExit = true;
                exit = i;
            }
        }

        if(!findEntry||!findExit)return;//throw error
        for(Connection c:connections) {
            boolean findv1=false,findv2=false;
            int i1=0, v1 = c.getFrom();
            int i2=0, v2 = c.getTo();
            for (int i = 0; i < verMap.length; i++) {
                if (v1 == verMap[i]){
                    i1 = i;
                    findv1=true;
                }
                if (v2 == verMap[i]){
                    i2 = i;
                    findv2=true;
                }
            }
            if(findv1&&findv2){
                vc tmpvc = new vc();
                tmpvc.v = i2;
                tmpvc.c = c.getValue();
                neighbour[i1].add(tmpvc);
            }
            else System.out.println("doesnt exist vertives in node list");
        }
        for(int i=0;i<neighbour.length;i++){
            for(vc tmp:neighbour[i]) System.out.println(verMap[i]+":"+verMap[tmp.v]+",c:"+tmp.c);
        }
    }
}
