package Tools;

import Method.MidsideNode;
import java.util.Comparator;

/**
 * Created by shiqun on 16/7/11.
 */
public class Compare implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        MidsideNode s1 = (MidsideNode) o1;
        MidsideNode s2 = (MidsideNode) o2;
        if(s1.Upper - s2.Upper <0)
            return 1;
        else if((s1.Upper -s2.Upper)>0)
            return -1;
        else
            return 0;
    }
}
