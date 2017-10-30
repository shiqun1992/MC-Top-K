package Test.efficiency;

import Adlist.Data.Data;
import Adlist.Data.DataVNode;
import Adlist.Pattern.Pattern;
import Bplus.Index;
import Method.Initialization;
import Method.MidsideNode;
import Method.Parameter;
import Method.Transmission;
import Tools.Compare;
import Tools.HeapMin;
import shortestPaths.graph.Graph;
import shortestPaths.graph.VariableGraph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shiqun on 2017/6/19 0019.
 */

public class Test
{
    public static void main(String args[])
    {
        //******************************数据图********************************************
        //30000
        String edgeUrl_data = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\CitHepTh\\25\\CitHepTh.txt";
        String nodeUrl_data = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\CitHepTh\\25\\CitHepThnode.txt";

        //******************************top-k最短路径要用的数据图********************************************
        //创建top-k最短路径要用的图;
        Graph graph = new VariableGraph(edgeUrl_data);
        Parameter.graph = new VariableGraph((Graph)graph);;

        //创建数据图;
        Data data = new Data(edgeUrl_data, nodeUrl_data);
        Parameter.Data = data;

        //******************************创建索引********************************************
        Index index = new Index(data.getList());

        //******************************模式图********************************************
        //模式图所在地址;
        String edgeUrl_pattern = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\25\\dblp.txt";
        String nodeUrl_pattern = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\25\\dblpnode.txt";

        //创建模式图;
        Parameter.NumOfPatternNode = 25;
        Pattern pattern = new Pattern(edgeUrl_pattern, nodeUrl_pattern);
        //跑10次计算平均时间;
        double AverageTime = 0;
        int TimeOfRun = 10;
        for(int Num = 0; Num < TimeOfRun; Num++)
        {
            //******************************开始查找********************************************
            double current = System.currentTimeMillis();
            int NumOfMinHeap = Parameter.K;

            //得到初始节点;
            Initialization Init = new Initialization(data, pattern);
            ArrayList<ArrayList<DataVNode>> InitialVNode = Init.getInitVNode(index);

            //用来传播的集合;
            ArrayList<MidsideNode> RootList = new ArrayList<MidsideNode>();

            //System.out.print("开始！！！！");
            //开始计算每一个点的上界;
            ArrayList<DataVNode> tmp;
            int num = 0;
            for (int a = 0; a < InitialVNode.size(); a++)
            {
                tmp = InitialVNode.get(a);
                for (int b = 0; b < tmp.size(); b++)
                {
                    tmp.get(b).patternVNode = Init.focusVNode;

                    //创建临时节点;
                    MidsideNode Root = new MidsideNode();
                    Root.MatchedPatternNodeid = 0;
                    Root.dataVNode = tmp.get(b);

                    //获得用于计算上界的中间变量;
                    Init.getUpBound(Root, Root);
                    Root.Upper = (1 - Parameter.Beta) * Math.atan(Root.UpperMatchingNode.size())
                            * 2 / Math.PI + Parameter.Beta * Root.MaxTrust;
                    System.out.println(num+"\t"+Root.Upper);
                    num++;

                    if (Root.Upper != 0)
                    {
                        RootList.add(Root);
                    }
                }
            }

            //对根节点排序，根据上界的大小，以便于找到最大上界;
            Collections.sort(RootList, new Compare());
            int MaxIndex = 1;

            //最小堆，用来检测提前终止条件是否达到;
            HeapMin heapMin = new HeapMin(Parameter.K - 2);
            Transmission transmission = new Transmission(data, pattern);

            //开始计算每一个点的下界;
            MidsideNode temp;
            for (int e = 0; e < RootList.size(); e++)
            {
                //取出一个根节点开始传播;
                temp = RootList.get(e);
                transmission.startWithTermination(temp, temp);

                //计算社交影响力和属性的综合评分;
                if (temp.matchingPaths_low == 0)
                    temp.low = 0;
                else
                {
                    temp.low = (1 - Parameter.Beta) * Math.atan(temp.matches_low.size()) * 2 / Math.PI
                            + Parameter.Beta * temp.sumOfTrust_low / temp.matchingPaths_low;
                }

                //更新最小堆，判断是否达到提前终止;
                if (temp.low != 0)
                {
                    //个数超过5个;
                    //将最小的拿去剔除加入最大堆中
                    if (heapMin.size >= Parameter.K - 1)
                    {
                        //替换到最小;
                        if (temp.low > heapMin.Heap.get(0).low)
                        {
                            heapMin.Heap.set(0, temp);
                            heapMin.Update(0);
                        }
                    } else
                    {
                        //插入最小堆需要从最大堆中删除;
                        heapMin.Insert(temp);
                    }

                    //提前终止条件达到了;
                    if (Termination(heapMin, RootList, MaxIndex) == true && NumOfMinHeap <= 1)
                    {
                        //输出所要找到的结果;
                        for(int T = 0;T<heapMin.size;T++)
                        {
                            System.out.println(heapMin.Heap.get(T).dataVNode.nodeID + " "
                                    + heapMin.Heap.get(T).low + " " + heapMin.Heap.get(T).low2 + " " + heapMin.Heap.get(T).Upper);
                        }

                        break;
                    }

                    //最小堆的剩余容量;
                    NumOfMinHeap--;
                }

                //当前，最大下届的下标;
                MaxIndex++;
            }

            //输出查找时间;
            double duration = System.currentTimeMillis() - current;
            double time = duration / 1000;
            AverageTime+=time;
        }
        System.out.print("平均查找时间："+AverageTime/TimeOfRun);
    }

    public static boolean Termination(HeapMin heapMin, ArrayList<MidsideNode> RootList, int MaxIndex)
    {
        if (heapMin.Heap.get(0).low > RootList.get(MaxIndex).Upper)
            return true;
        else
            return false;
    }
}
