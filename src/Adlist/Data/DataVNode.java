package Adlist.Data;
import Adlist.Pattern.PatternVNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by shiqun on 2016/4/25.
 */
//邻接表表头节点;
public class DataVNode
{
    //结点ID 分类号;
    public int nodeID;
    public int categoryID;

    //出度入度;
    public int indegree;
    public int outdegree;

    //影响力;
    public double Rou;

    //保存模式图中所有的邻接边;
    public ArrayList<DataNode> AdjEdge;

    //有些节点并不存在;
    public boolean isExist;

    //记录这个点所匹配的模式节点;
    public PatternVNode patternVNode;

    //按ID索引后代节点;
    public HashMap<Integer,DataNode> IndexAdjNodeByID = new HashMap<>();

    public DataVNode(int nodeID)
    {
        //id分类;
        this.nodeID = nodeID;
        this.categoryID = -1;

        //出度入度;
        this.indegree = 0;
        this.outdegree = 0;

        //有些点不存在;
        this.isExist = false;

        //某点周围所有的点或边;
        AdjEdge = new ArrayList<DataNode>();
    }

    //设置某点分类;
    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }
}

