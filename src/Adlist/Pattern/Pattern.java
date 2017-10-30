package Adlist.Pattern;
import Method.Parameter;
import Tools.fileTool;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by shiqun on 2016/4/25.
 */
public class Pattern
{
    //保存所有邻接节点;
    public ArrayList<PatternVNode> adlist;
    public int MaxVNode = Parameter.NumOfPatternNode;

    //边数点数;
    public int edgeNum;
    public int nodeNum;

    //按分类存储结点;
    public HashMap<Integer,PatternVNode> ClassVNode
            = new HashMap<Integer,PatternVNode>();

    //根据url初始化图;
    public Pattern(String fileUrl_Edge,String fileUrl_Node)
    {
        //边节点个数;
        edgeNum = 0;
        nodeNum = 0;

        //将包含边点的文件以字符串形式读内存;
        fileTool tool = new fileTool();
        String content_Edge = tool.getContent(fileUrl_Edge);
        String content_Node = tool.getContent(fileUrl_Node);

        //初始化表头节点;
        adlist = new ArrayList<PatternVNode>();
        for(int i = 0;i < Parameter.NumOfPatternNode;i++)
        {
            PatternVNode vNode = new PatternVNode(i);
            adlist.add(vNode);
        }

        //读取点分类号;
        String lines[] = content_Node.split("\n");
        int length = lines.length;
        String columns[];

        int NodeID;
        int categoryID;

        for(int i = 1;i < length;i++)
        {
            columns = lines[i].split("\t");
            NodeID = Integer
                    .parseInt(columns[0]);
            categoryID = Integer
                    .parseInt(columns[1]);

            adlist.get(NodeID).isExist = true;
            adlist.get(NodeID).setCategoryID(categoryID);

            //按分类存储结点;
            ClassVNode.put(adlist.get(NodeID).categoryID,adlist.get(NodeID));

            //表示节点数;
            nodeNum++;
        }

        //插入表节点;
        lines = content_Edge.split("\n");
        length = lines.length;

        //起始节点ID;
        int fromNodeID;
        int toNodeID;

        //约束;
        double T;
        double R;
        double Rou;

        //有界模拟;
        int k;

        for(int i = 1;i < length;i++)
        {
            columns = lines[i].split("\t");
            //读取起始节点;
            fromNodeID = Integer
                    .parseInt(columns[0]);
            toNodeID = Integer
                    .parseInt(columns[1]);

            //读取约束;
            T = Parameter.LambadaT;
            R = Parameter.LambadaR;
            Rou = Parameter.LambadaRho;

            //Boundsimulation;
            //k值不可能超过1000的;
            if(columns[5].equals("*"))
                k = 500;
            else
                k = Integer.parseInt(columns[5]);

            //保存这个点可能最远的孩子结点的距离;
            adlist.get(fromNodeID).L = Parameter.L;

            //根据约束初始化邻接边;
            PatternNode node = new PatternNode(adlist.get(fromNodeID), adlist.get(toNodeID), T,R,Rou,Parameter.L);

            //表示边数;
            edgeNum++;

            //计算出度入度;
            adlist.get(fromNodeID).outdegree++;
            adlist.get(toNodeID).indegree++;

            //加入到孩子节点;
            adlist.get(fromNodeID).Children.add(node);
        }
    }
}
