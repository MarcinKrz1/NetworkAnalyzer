package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Connection {
    private int from;
    private int to;
    private int value;

    public int getValue() {return value;}
    public int getFrom() {return from;}
    public int getTo() {return to;}
}