
import java.io.Serializable;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class SortStep implements Serializable {
    public LinkedList<Integer> array;
    public int originalSize;
    public int joinID;
    public int splitID;
    
    public SortStep()
    {}
    public SortStep(LinkedList<Integer> array, int originalSize, int joinID, int splitID)
    {
        this.array = array;
        this.originalSize = originalSize;
        this.joinID = joinID;
        this.splitID = splitID;
    }
}
