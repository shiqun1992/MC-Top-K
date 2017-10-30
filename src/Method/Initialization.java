package Method;

import Adlist.Data.Data;
import Adlist.Data.DataVNode;
import Adlist.Pattern.Pattern;
import Adlist.Pattern.PatternVNode;
import Bplus.Index;
import Bplus.Trait;

import java.util.*;

/**
 * Created by shiqun on 16/7/26.
 */
public class Initialization
{
    Pattern pattern;
    Data data;

    public Initialization(Data data, Pattern pattern)
    {
        //
        this.data = data;
        this.pattern = pattern;

        //默认第一个为输出节点;
        focusVNode = pattern.adlist.get(0);
    }

    //输出节点;
    public PatternVNode focusVNode;

    //找到初始节点;
    ArrayList<ArrayList<DataVNode>> InitialVNode;

    public ArrayList<ArrayList<DataVNode>> getInitVNode(Index index)
    {
        InitialVNode = new ArrayList<ArrayList<DataVNode>>();
        //查找第一个节点的索引;
        //分别从这几个节点开始查找;
        Trait trait = new Trait(focusVNode.categoryID, 0, 1);
        InitialVNode = index.findItem(trait);
        return InitialVNode;
    }

    //只是找一个结果;
    public void getUpBound(MidsideNode Root, MidsideNode child)
    {
        //使用递归的方式;
        //找到这个点可能匹配的模式节点;
        PatternVNode patternVNode = pattern.adlist.get(child.MatchedPatternNodeid);

        //查找能够匹配的孩子节点;
        Bfs bfs = new Bfs();
        bfs.Propagate(child, child.dataVNode, patternVNode, 1, patternVNode.L);

        //遍历孩子节点;
        //递归查找孩子的孩子的匹配;
        Iterator iter = child.TrustWithchild.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry) iter.next();
            MidsideNode offspring = (MidsideNode) entry.getKey();
            double trust = (double) entry.getValue();

            //根节点统计孩子节点个数;
            Root.UpperMatchingNode.add(offspring.dataVNode);
            Root.UpperNumOfMatchingPath++;

            //找到与孩子信任值最大的那个值;
            if(trust>Root.MaxTrust)
            {
                Root.MaxTrust = trust;
            }

            //如果这个节点可能匹配的模式节点出度为0，那么这个点是匹配的模式节点;
            if(pattern.adlist.get(offspring.MatchedPatternNodeid).outdegree == 0)
            {
                offspring.isMatching = true;
            }
            else
            {
                //递归查找孩子;
                getUpBound(Root, offspring);
            }
        }
    }
}

