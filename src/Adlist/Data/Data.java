package Adlist.Data;

import Method.Parameter;
import Tools.fileTool;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shiqun on 2016/4/25.
 */
public class Data
{
    public ArrayList<DataVNode> adlist;
    //dplp 500000
    //twitter 750000
    public int NumOfDataNode = Parameter.NumOfDataNode;

    public Data(String fileUrl_Edge, String fileUrl_Node)
    {
        //将包含边点的文件以字符串形式读内存;
        fileTool tool = new fileTool();
        String content_Edge = tool.getContent(fileUrl_Edge);
        String content_Node = tool.getContent(fileUrl_Node);

        //初始化表头节点;
        adlist = new ArrayList<DataVNode>();
        for(int i = 0;i < NumOfDataNode;i++)
        {
            DataVNode vNode = new DataVNode(i);
            adlist.add(vNode);
        }

        //读取点分类号;
        String lines[] = content_Node.split("\n");
        int length = lines.length;
        String columns[];

        int NodeID;
        int categoryID;
        double Rou;

        for(int i = 1;i < length;i++)
        {
            columns = lines[i].split("\t");
            NodeID = Integer
                    .parseInt(columns[0]);
            categoryID = Integer
                    .parseInt(columns[1]);
            Rou = Double.parseDouble(columns[2]);

            //
            adlist.get(NodeID).isExist = true;
            adlist.get(NodeID).setCategoryID(categoryID);
            adlist.get(NodeID).Rou = Rou;

        }

        //插入表节点;
        lines = content_Edge.split("\n");
        length = lines.length;

        int fromNodeID;
        int toNodeID;

        double T;
        double R;

        for(int i = 1;i < length;i++)
        {
            columns = lines[i].split("\t");
            //读取起始节点;
            fromNodeID = Integer
                    .parseInt(columns[0]);
            toNodeID = Integer
                    .parseInt(columns[1]);

            //读取约束;
            T = Double.parseDouble(columns[2]);
            R = Double.parseDouble(columns[3]);

            //插入表节点;
            DataNode edge = new DataNode(adlist.get(fromNodeID),adlist.get(toNodeID), T,R);

            //保存所有相邻边
            //保存所有相邻节点;
            adlist.get(fromNodeID).AdjEdge.add(edge);

            //计算出度入度;
            adlist.get(fromNodeID).outdegree++;
            adlist.get(toNodeID).indegree++;

            //按ID索引孩子节点;
            HashMap<Integer,DataNode> temp;
            temp = adlist.get(fromNodeID).IndexAdjNodeByID;
            temp.put(toNodeID,edge);
        }
    }

    public ArrayList<DataVNode> getList() {
        return adlist;
    }

}
