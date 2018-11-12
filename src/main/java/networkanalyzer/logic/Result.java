package networkanalyzer.logic;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Result {
    private int[] node;
    private int resultSum;
    public void setAmount(int x){this.node = new int[x];}
    public void setNode(int x,int node){this.node[x]=node;}
    public void setResultSum(int result){this.resultSum = result;}
}