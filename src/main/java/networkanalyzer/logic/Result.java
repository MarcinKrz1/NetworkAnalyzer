package networkanalyzer.logic;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
/**
 * klasa reprezentujaca format odpowiedzi wykonania algorytmu znajdowania najkrotszej sciezki
 */
public class Result {
    /**
     * tablica identfikatorow wierzcholkow
     */
    private int[] node;
    /**
     * sumaryczny koszt sciezki w grafie
     */
    private int resultSum;

    /**
     * metoda definiujaca rozmiar tablicy
     * @param x
     */
    public void setAmount(int x){this.node = new int[x];}

    /**
     * metoda, przypisanie tablicy identyfikatora wierzcholka
     * @param x
     * @param node
     */
    public void setNode(int x,int node){this.node[x]=node;}

    /**
     * metoda ustawiajaca sumaryczny koszt
     * @param result
     */
    public void setResultSum(int result){this.resultSum = result;}
}