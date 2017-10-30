package Adlist.Pattern;
import Method.Parameter;

import java.util.ArrayList;


/**
 * Created by shiqun on 2016/4/25.
 */
//邻接表表头节点;
public class PatternVNode
{
    //结点ID 分类号;
    public int nodeID;
    public int categoryID;

    //出度入度;
    public int indegree;
    public int outdegree;

    //有些节点并不存在;
    public boolean isExist;

    //用来记录孩子节点最远的节点;
    public int  L = Parameter.L;
    public ArrayList<PatternNode> Children;

    public PatternVNode(int nodeID)
    {
        //ID.分类ID
        this.nodeID = nodeID;
        this.categoryID = -1;

        //出度入度;
        this.indegree = 0;
        this.outdegree = 0;

        //是否存在;
        //某点距离孩子最远的距离;
        this.isExist = false;
        //this.Maxk = 0;

        //孩子节点;
        Children = new ArrayList<PatternNode>();
    }

    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }
}

