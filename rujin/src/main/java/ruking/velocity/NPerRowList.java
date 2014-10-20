package ruking.velocity;

import java.util.Iterator;
import java.util.List;

public class NPerRowList
{
    private List list;
    private Iterator it;
    private int numPerRow;
    private int num;
    
    public NPerRowList(List list, int numPerRow)
    {
        this.list = list;
        it = list.iterator();
        this.numPerRow = numPerRow;
        this.num = 0;
    }
    
    public int getRowCount()
    {
        if (list.size() % numPerRow == 0)
        {
            return list.size() / numPerRow;
        }
        else
        {
            return (list.size() / numPerRow) + 1;
        }
    }

    public int getItemNum()
    {
    	return this.num;
    }
    
    public boolean hasNext()
    {
        return it.hasNext();
    }
    
    public Object next()
    {
        if (it.hasNext())
        {
        	this.num++;
            return it.next();
        }
        else
        {
            return null;
        }
    }
    
    public int size()
    {
    	return list.size();
    }
}
