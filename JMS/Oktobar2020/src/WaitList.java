
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Stefan
 */
public class WaitList {

    public int reqId;
    public ArrayList<Integer> first;
    public ArrayList<Integer> second;

    public WaitList(int reqId) {
        this.reqId = reqId;
        first = null;
        second = null;
    }

}
