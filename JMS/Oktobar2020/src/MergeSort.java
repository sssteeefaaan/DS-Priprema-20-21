
import java.util.LinkedList;
import java.util.List;

public class MergeSort {
    public List<Integer> first;
    public List<Integer> second;
    public int requestID;
    public int responseID;
    
    public MergeSort(int requestID)
    {
        this.first = null;
        this.second = null;
        this.requestID = requestID;
    }
    
    public MergeSort(int requestID, int responseID)
    {
        this.first = null;
        this.second = null;
        this.requestID = requestID;
        this.responseID = responseID;
    }
    
    public List<Integer> sort()
    {
        List<Integer> ret = new LinkedList<Integer>();
        int i = 0, j= 0;
        
        while(i < first.size() && j < second.size())
            ret.add(first.get(i) < second.get(j) ? first.get(i++) : second.get(j++));
        
        while(i < first.size())
            ret.add(first.get(i++));
        
        while(j < second.size())
            ret.add(second.get(j++));
        
        return ret;
    }
}
