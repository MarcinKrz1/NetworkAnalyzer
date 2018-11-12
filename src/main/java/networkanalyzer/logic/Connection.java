package networkanalyzer.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * klasa reprezentujaca polaczenie miedzy wierzcholkami
 */
@Getter
@Setter
@ToString
public class Connection {
    /**
     *  z jakiego węzła
     */
    private int from;
    /**
     *  do jakiego węzła
     */
    private int to;
    /**
     * wartość, która w zależności od dziedziny biznesowej może oznaczać np. dystans, przepustowość, itd.
     */
    private int value;

    public int getValue() {return value;}
    public int getFrom() {return from;}
    public int getTo() {return to;}
}