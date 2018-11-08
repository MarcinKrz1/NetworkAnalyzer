package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.LinkedList;
import java.util.Stack;

class vc{
    public int v,c;
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class HTTPError extends RuntimeException {
    public HTTPError(String message) {
        super(message);
    }
}

@Getter @Setter
public class CalculatorPaths {
    private List<Node> nodes;
    private List<Connection> connections;

    public List<Node> BFS_path() {
        ArrayList<Node>bfs = new ArrayList<Node>();
        int sumCost = 0, vertices = nodes.size(), entry=0, exit=0;
        boolean findEntry = false, findExit = false;
        for (Connection c : connections) sumCost += c.getValue();
        int verMap[] = new int[vertices];

        ArrayList<vc>[] neighbour = new ArrayList[vertices] ;
        for(int i=0;i<neighbour.length;i++)neighbour[i]= new ArrayList<vc>();

        ArrayList<Integer>[] neighbour2 = new ArrayList[vertices + sumCost] ;
        for(int i=0;i<neighbour2.length;i++)neighbour2[i]= new ArrayList<Integer>();

        for (int i = 0; i < verMap.length; i++) {
            Node tmpNode = nodes.get(i);
            verMap[i] = tmpNode.getId();
            if (tmpNode.getType().equals("entry")) {
                if(findEntry)return null;
                findEntry = true;
                entry = i;
            }
            if (tmpNode.getType().equals("exit")) {
                if(findExit)return null;
                findExit = true;
                exit = i;
            }
        }

        if(!findEntry||!findExit)
            throw new HTTPError("No entry or exit node!");

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
            else throw new HTTPError("Doesn't exists vertex in node list!");
        }

        //rebuild network
        int idx=vertices;
        for(int i=0;i<vertices;i++){
            for(vc vn:neighbour[i]){
                if(vn.c>1){
                    neighbour2[i].add(idx);
                    vn.c--;
                    while (vn.c>1){
                        neighbour2[idx].add(idx+1);
                        vn.c--;
                        idx++;
                    }
                    neighbour2[idx].add(vn.v);
                    idx++;
                }else{
                    neighbour2[i].add(vn.v);
                }
            }
        }

        //bfs short algoruthm
        int[] prev_vertices = new int[vertices+sumCost];
        for(int i=0;i<prev_vertices.length;i++)prev_vertices[i]=-1;

        LinkedList<Integer> child = new LinkedList<Integer>();
        child.addLast(entry);
        prev_vertices[entry]=-10;
        boolean isLoop=true;
        while(!child.isEmpty()&&isLoop){
            int v=child.getFirst();
            child.removeFirst();
            for(int vn:neighbour2[v])
                if (prev_vertices[vn] == -1) {
                    child.addLast(vn);
                    prev_vertices[vn] = v;
                    if (vn == exit) {
                        isLoop = false;
                        break;
                    }
                }
            System.out.println(v);
        }
        //get solution
        Stack<Integer> result = new Stack<Integer>();
        idx = exit;
        result.push(idx);
        while (prev_vertices[idx]>-1){
            result.push(prev_vertices[idx]);
            idx = prev_vertices[idx];
        }
        if(result.size()<2)
            throw new HTTPError("Path doesnt't exists!");

        while(!result.empty()) {
            int vi = result.pop();
            if(vi<vertices){
                bfs.add(nodes.get(vi));
            }
        }
        return bfs;
    }
}
