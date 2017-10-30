package Tools;

import Method.MidsideNode;
import java.util.ArrayList;
/**
 * Created by shiqun on 16/6/23.
 */
public class HeapMin
{
    public ArrayList<MidsideNode> Heap;
    public int maxsize;
    public int size;

    public HeapMin(int max)
    {
        maxsize = max;
        Heap = new ArrayList<MidsideNode>();
        size = 0;

        //初始放入第一个节点;
        MidsideNode can = new MidsideNode();
        can.low = Integer.MIN_VALUE;

        //添加到堆中;
        Heap.add(can);
    }

    private int leftchild(int pos)
    {
        return 2 * pos;
    }

    private int parent(int pos)
    {
        return pos / 2;
    }

    private boolean isleaf(int pos)
    {
        return ((pos > size / 2) && (pos <= size));
    }

    private void swap(int pos1, int pos2)
    {
        MidsideNode tmp;
        tmp = Heap.get(pos1);
        Heap.set(pos1,Heap.get(pos2));
        Heap.set(pos2,tmp);
    }


    public void Insert(MidsideNode elem)
    {
        size++;
        Heap.add(elem);
        int position = size;
        while (getLowBound(position)
                < getLowBound(parent(position)))
        {
            swap(position, parent(position));
            position = parent(position);
        }
    }

    //候选值被修改了;
    //调整堆栈返回位置;
    public int Update(int position)
    {
        if(getLowBound(position) < getLowBound(parent(position)))
        {
            swap(position, parent(position));
            position = parent(position);
        }
        else
        {
            int smallestchild;
            while (!isleaf(position))
            {
                smallestchild = leftchild(position);
                if ((smallestchild < size)
                        && (getLowBound(smallestchild) > getLowBound(smallestchild+1)))
                    smallestchild = smallestchild + 1;

                if (getLowBound(position) <= getLowBound(smallestchild))
                    break;

                swap(position, smallestchild);
                position = smallestchild;
            }
        }
        return  position;
    }

    public double getLowBound(int position) {
        return Heap.get(position).low;
    }

    public MidsideNode removemin()
    {
        swap(1, size);
        size--;
        if (size != 0)
            pushdown(1);
        return Heap.get(size + 1);
    }

    private void pushdown(int position) {
        int smallestchild;
        while (!isleaf(position)) {
            smallestchild = leftchild(position);
            if ((smallestchild < size)
                    && (getLowBound(smallestchild) > getLowBound(smallestchild + 1)))
                smallestchild = smallestchild + 1;
            if (getLowBound(position) <= getLowBound(smallestchild))
                return;
            swap(position, smallestchild);
            position = smallestchild;
        }
    }
}