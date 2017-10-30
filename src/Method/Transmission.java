package Method;
import Adlist.Data.Data;
import Adlist.Pattern.Pattern;
import java.util.*;

/**
 * Created by shiqun on 16/7/13.
 */
public class Transmission
{
    Pattern pattern;
    Data data;

    public Transmission(Data data, Pattern pattern)
    {
        this.data = data;
        this.pattern = pattern;
    }

    //只是找一个结果;
    public void startWithTermination(MidsideNode Parent, MidsideNode child)
    {
        //使用递归的方式;
        //找到一个统计分数然后加入到堆栈中去;/

        //用来判断一个点是否能够能成匹配;
        Set<Integer> Patternids = new HashSet<>();


        //递归遍历孩子节点;
        Iterator iter = child.TrustWithchild.entrySet().iterator();
        while (iter.hasNext())
        {
            //获取一个孩子;
            Map.Entry entry = (Map.Entry) iter.next();
            MidsideNode nextChild = (MidsideNode) entry.getKey();
            startWithTermination(Parent, nextChild);

            //
            if (nextChild.isMatching == true)
            {
                Patternids.add(nextChild.MatchedPatternNodeid);
            }
        }

        if (Patternids.size()==pattern.adlist
                .get(child.MatchedPatternNodeid).outdegree)
        {
            child.isMatching =true;
            iter = child.TrustWithchild.entrySet().iterator();
            while (iter.hasNext())
            {
                //获取一个孩子;
                Map.Entry entry = (Map.Entry) iter.next();

                //
                double trust = (double) entry.getValue();
                Parent.sumOfTrust_low  += trust;
                Parent.matchingPaths_low++;

                //
                MidsideNode nextChild = (MidsideNode) entry.getKey();
                Parent.matches_low.add(nextChild.dataVNode);
            }
        }
    }
}


