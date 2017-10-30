package Method;

import Adlist.Data.Data;
import shortestPaths.graph.Graph;
import shortestPaths.graph.VariableGraph;

/**
 * Created by shiqun on 2017/3/16 0016.
 */
public class Parameter
{
    //找top-k最短路径要用的图;
    public static VariableGraph graph;

    //数据图;
    public static Data Data;

    //求一条路劲信任值;
    public static double w1 = 0.25;
    public static double w2 = 0.25;
    public static double w3 = 0.5;

    //模式节点个数;
    public static int NumOfPatternNode;

    //数据节点个数;
    public static int NumOfDataNode = 1730000;

    //boundlength
    public static int L = 5;

    //K
    public static int K = 5;

    //调和参数;
    public static double Beta = 0.5;

    //T,R,Rou的约束;
    public static double LambadaT = 0.2;
    public static double LambadaR = 0.2;
    public static double LambadaRho = 0.2;
}
