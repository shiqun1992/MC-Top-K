package Adlist.Data;
/**
 * Created by shiqun on 2016/4/25.
 */
//邻接表表节点;
public class DataNode
{
    //一条边的起始节点;
    public DataVNode startVNode;
    public DataVNode endVNode;

    //边上的多约束;
    public double T;
    public double R;

    public DataNode(DataVNode startVNode, DataVNode endVNode, double T, double R)
    {
        //一条边起始节点;
        this.startVNode =startVNode;
        this.endVNode = endVNode;

        //约束;
        this.T = T;
        this.R = R;
    }
}