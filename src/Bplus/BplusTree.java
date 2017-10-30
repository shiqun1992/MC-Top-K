package Bplus;

import Adlist.Data.DataVNode;

import java.util.ArrayList;

/**
 * Created by Administrator on 16/6/22.
 */
public class BplusTree implements BTree
{
    //根节点;
    protected Node root;

    //阶数M值;
    protected int M;

    //叶子节点的链表头;
    protected Node head;

    public void setHead(Node head) {
        this.head = head;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getM() {
        return M;
    }

    @Override
    public void get(ArrayList<ArrayList<DataVNode>> vNodes, Comparable key)
    {
        root.get(vNodes,key);
    }

    @Override
    public void addItem(Comparable key, Object obj) {
        root.addItem(key, obj, this);
    }

    public BplusTree(int M)
    {
        if (M < 3)
        {
            System.out.print("M must be greater than 2");
            System.exit(0);
        }

        this.M = M;
        root = new Node(true, true);
        head = root;
    }
}
