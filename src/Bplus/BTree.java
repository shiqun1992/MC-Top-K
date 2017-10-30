package Bplus;
import Adlist.Data.DataVNode;

import java.util.ArrayList;

/**
 * Created by Administrator on 16/6/22.
 */
public interface BTree
{
    //查询
    public void get(ArrayList<ArrayList<DataVNode>> VNodes, Comparable key);

    //插入或者更新，如果已经存在，就更新，否则插入
    public void addItem(Comparable key, Object obj);
}
