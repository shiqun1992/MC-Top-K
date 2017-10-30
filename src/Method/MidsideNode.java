package Method;

import Adlist.Data.DataVNode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shiqun on 2017/5/13 0013.
 */
public class MidsideNode
{
    //用来保存可能匹配的模式点;
    public int MatchedPatternNodeid;

    //指向的数据节点;
    public DataVNode dataVNode;

    //指向其父类;
    public MidsideNode parent;

    //用来表明这个节点是否是个匹配;
    public boolean isMatching = false;

    ////////////////////计算上界////////////////////////////
    //后代节点的匹配;
    public Set<DataVNode> UpperMatchingNode = new HashSet<>();
    //后代节点的匹配;
    public HashMap<MidsideNode,Double> TrustWithchild = new HashMap<>();

    //信任值上界总和的上界;
    public double MaxTrust = 0;

    //匹配的模式边个数上界;
    public int UpperNumOfMatchingPath = 0;

    //得分上界;
    public double Upper;

    ////////////////////计算上界////////////////////////////

    //信任值上界总和的上界;
    public double sumOfTrust_low = 0;

    //后代节点的匹配;
    public Set<DataVNode> matches_low = new HashSet<>();

    //得分下界;
    public double low;
    //得分下界;
    public double low2;

    //匹配的模式边个数上界;
    public int matchingPaths_low = 0;
}
