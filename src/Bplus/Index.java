package Bplus;

import Adlist.Data.DataVNode;

import java.util.ArrayList;

/**
 * Created by shiqun on 16/6/21.
 */
public class Index
{

    //1.任意非叶子结点最多有M个子节点；且M>2；
    //2.除根结点以外的非叶子结点至少有 M/2个子节点；
    //3.根结点至少有2个子节点；
    //4.除根节点外每个结点存放至少M/2和至多M个关键字；（至少2个关键字）
    //5.非叶子结点的子树指针与关键字个数相同；
    //6.所有结点的关键字：K[1], K[2], …, K[M]；且K[i] < K[i+1]；
    //7.非叶子结点的子树指针P[i]，指向关键字值属于[K[i], K[i+1])的子树；
    //8.所有叶子结点位于同一层；
    //5.为所有叶子结点增加一个链指针；
    //6.所有关键字都在叶子结点出现；

    //B+树;
    BplusTree tree;

    //根据图创建索引;
    public Index(ArrayList<DataVNode> adlist)
    {
        tree = new BplusTree(6);
        for(int i=0;i<adlist.size();i++)
        {
            Trait trait = new Trait(adlist.get(i).categoryID,
                    adlist.get(i).indegree,
                    adlist.get(i).outdegree);

            if(adlist.get(i).isExist == true)
            {
                //System.out.println(i);
                ArrayList<DataVNode> vNodes = new ArrayList<DataVNode>();
                vNodes.add(adlist.get(i));
                this.addItem(trait,vNodes);
            }

        }
    }

    public void addItem(Trait T, ArrayList<DataVNode> vNode) {
        tree.addItem(T, vNode);
    }

    //查找候选节点;
    public ArrayList<ArrayList<DataVNode>> findItem(Trait T)
    {
        ArrayList<ArrayList<DataVNode>> VNodes = new ArrayList<ArrayList<DataVNode>>();
        tree.get(VNodes,T);
        return VNodes;
    }
}
