package Adlist.Pattern;

/**
 * Created by shiqun on 2016/4/25.
 */
//邻接表表节点;
public class PatternNode
{
    //一条边的起始节点;
    public PatternVNode startVNode;
    public PatternVNode endVNode;

    //边上的多约束;
    public double T;
    public double R;
    public double Rou;

    //Boundsimulation;
    public int k;

    public PatternNode(PatternVNode startVNode, PatternVNode endVNode,
                       double T, double R, double Rou, int k)
    {
        //起始节点;
        this.startVNode =startVNode;
        this.endVNode = endVNode;

        //约束;
        this.T = T;
        this.R = R;
        this.Rou =Rou;

        //有界模拟;
        this.k = k;
    }
}


