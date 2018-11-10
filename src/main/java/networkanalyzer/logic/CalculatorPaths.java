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

        for (int i = 0; i < verMap.length; i++) {
            Node tmpNode = nodes.get(i);
            verMap[i] = tmpNode.getId();
            if (tmpNode.getType().equals("entry")) {
                if(findEntry)throw new HTTPError("Find 2 entry node!");
                findEntry = true;
                entry = i;
            }
            if (tmpNode.getType().equals("exit")) {
                if(findExit)throw new HTTPError("Find 2 exit node!");
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

        //bfs short algoruthm
        int[] prev_vertices = new int[vertices+sumCost];
        for(int i=0;i<prev_vertices.length;i++)prev_vertices[i]=-1;

        LinkedList<Integer> child = new LinkedList<Integer>();
        child.addLast(entry);
        prev_vertices[entry]=-10;
        boolean isLoop=true;
        while(!child.isEmpty()&&isLoop){
            int v=child.getFirst();
            boolean again=false;
            child.removeFirst();
            for(vc vn:neighbour[v])
                if(vn.c>1){
                    again=true;
                    vn.c--;
                }
                else if (prev_vertices[vn.v] == -1) {
                    child.addLast(vn.v);
                    prev_vertices[vn.v] = v;
                    if (vn.v == exit) {
                        isLoop = false;
                        break;
                    }
                }
            child.addLast(v);
        }
        //get solution
        Stack<Integer> result = new Stack<Integer>();
        int idx = exit;
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
