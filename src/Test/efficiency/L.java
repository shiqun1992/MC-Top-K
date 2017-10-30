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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shiqun on 16/6/20.
 */
public class L {
    public void run() {
        //设置参数,beiErTa,调和影响力和;
        double beiErTa = 0.5;

        //**************************************************************************
        double current;

        //模式图所在地址;
        String edgeUrl_data[] = new String[5];
        String nodeUrl_data[] = new String[5];

        //******************************数据图********************************************
        //30000
        edgeUrl_data[0] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\CitHepTh\\25\\CitHepTh.txt";
        nodeUrl_data[0] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\CitHepTh\\25\\CitHepThnode.txt";

        //80000
        edgeUrl_data[1] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\slashdot\\77\\slashdot.txt";
        nodeUrl_data[1] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\slashdot\\77\\slashdotnode.txt";

        //500000
        edgeUrl_data[2] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\dblp\\dblp.txt";
        nodeUrl_data[2] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\dblp\\dblpnode.txt";

        //750000
        edgeUrl_data[3] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\twitter\\twitter.txt";
        nodeUrl_data[3] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\twitter\\twitternode.txt";

        //1730000
        edgeUrl_data[4] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\youtube\\youtube.txt";
        nodeUrl_data[4] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\data\\youtube\\youtubenode.txt";

        //******************************模式图********************************************
        //模式图所在地址;
        String edgeUrl_pattern[] = new String[5];
        String nodeUrl_pattern[] = new String[5];

        edgeUrl_pattern[0] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\5\\dblp.txt";
        nodeUrl_pattern[0] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\5\\dblpnode.txt";

        edgeUrl_pattern[1] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\10\\dblp.txt";
        nodeUrl_pattern[1] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\10\\dblpnode.txt";

        edgeUrl_pattern[2] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\15\\dblp.txt";
        nodeUrl_pattern[2] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\15\\dblpnode.txt";

        edgeUrl_pattern[3] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\20\\dblp.txt";
        nodeUrl_pattern[3] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\20\\dblpnode.txt";

        edgeUrl_pattern[4] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\25\\dblp.txt";
        nodeUrl_pattern[4] = "D:\\研究生\\模式识别\\旧实验\\测试数据\\Pattern\\5个模式图\\25\\dblpnode.txt";

        //参数L,bound length;
        int L[] = {5, 10, 15, 20, 25};

        //参数beiTa;
        double beiTa[] = {0.5, 0.6, 0.7, 0.8, 0.9, 1};

        //参数k
        int k[] = {10, 20, 30, 40, 50};

        //参数omiga;
        double omiga[][] =
                {
                        {0.25, 0.25, 0.5},
                        {0.5, 0.25, 0.25},
                        {0.25, 0.5, 0.25},
                        {0.33, 0.33, 0.33}
                };

        //保存三个维度的数据;
        int q_x;
        double L_y;
        int k_y;
        double beiTa_y;
        double time_z = Double.MAX_VALUE;
        double xyx[][] = new double[3][25];

        //五个模式图;
        for (int d = 4; d < 5; d++) {
            String edgeUrl = edgeUrl_data[d];
            String nodeUrl = nodeUrl_data[d];

            //创建数据图;
            Data data = new Data(edgeUrl, nodeUrl);
            int c = 0;

            //创建索引;
            Index index = new Index(data.getList());

            //
            for (int q = 0; q < 5; q++) {
                edgeUrl = edgeUrl_pattern[q];
                nodeUrl = nodeUrl_pattern[q];

                //创建模式图;
                Parameter.NumOfPatternNode = (q + 1) * 5;
                q_x = (q+1)*5;
                Pattern pattern = new Pattern(edgeUrl, nodeUrl);

                for (int i = 0; i < 5; i++)
                {
                    L_y = (i+1)*5;
                    Parameter.L = L[i];

                    double time;
                    time_z = Double.MAX_VALUE;
                    for (int number = 0; number < 30; number++)
                    {

                        current = System.currentTimeMillis();
                        //开始查找;
                        int Tmp = Parameter.K;

                        //得到初始节点;
                        Initialization Init = new Initialization(data, pattern);
                        ArrayList<ArrayList<DataVNode>> InitialVNode = Init.getInitVNode(index);

                        //用来传播的集合;
                        ArrayList<MidsideNode> CanTrans = new ArrayList<MidsideNode>();

                        //
                        //System.out.print("开始！！！！");
                        //开始计算每一个点的上界;
                        ArrayList<DataVNode> tmp;
                        for (int a = 0; a < InitialVNode.size(); a++) {
                            tmp = InitialVNode.get(a);
                            for (int b = 0; b < tmp.size(); b++) {
                                tmp.get(b).patternVNode = Init.focusVNode;

                                //创建临时节点;
                                MidsideNode Root = new MidsideNode();
                                Root.MatchedPatternNodeid = 0;
                                Root.dataVNode = tmp.get(b);

                                //获得用于计算上界的中间变量;
                                Init.getUpBound(Root, Root);
                                Root.Upper = (1 - beiErTa) * Math.atan(Root.UpperMatchingNode.size())
                                        * 2 / Math.PI + beiErTa * Root.MaxTrust;

                                if (Root.Upper != 0) {
                                    CanTrans.add(Root);
                                }
                            }
                        }

                        //开始计算每一个点的下界;
                        Collections.sort(CanTrans, new Compare());
                        int MaxIndex = 1;

                        //
                        HeapMin heapMin = new HeapMin(Parameter.K - 2);
                        Transmission transmission = new Transmission(data, pattern);

                        //
                        MidsideNode temp;
                        for (int e = 0; e < CanTrans.size(); e++) {
                            temp = CanTrans.get(e);
                            transmission.startWithTermination(temp, temp);

                            //计算社交影响力和属性的综合评分;
                            //temp.low = beiErTa * Math.atan(temp.matches_low.size()) * 2 / Math.PI
                            //+ (1 - beiErTa) * temp.sumOfTrust_low / temp.matchingPaths_low;

                            if (temp.matchingPaths_low == 0) {
                                temp.low = 0;
                            } else {
                                temp.low = (1 - beiErTa) * Math.atan(temp.matches_low.size()) * 2 / Math.PI
                                        + beiErTa * temp.sumOfTrust_low / temp.matchingPaths_low;
                            }

                            //
                            if (temp.low != 0) {
                                //个数超过5个;
                                //将最小的拿去剔除加入最大堆中
                                if (heapMin.size >= Parameter.K- 1) {
                                    //替换到最小;
                                    //更新;
                                    if (temp.low > heapMin.Heap.get(0).low) {
                                        heapMin.Heap.set(0, temp);
                                        heapMin.Update(0);
                                    }
                                } else {
                                    //插入最小堆需要从最大堆中删除;
                                    heapMin.Insert(temp);
                                }

                                //
                                if (Termination(heapMin, CanTrans, MaxIndex) == true && Tmp <= 1) {
                                    //System.out.print(i+" ");
                                    //System.out.println(CanTrans.size()+"");
                                    break;
                                }

                                //
                                Tmp--;
                            }

                            //
                            MaxIndex++;
                        }

                        //
                        double duration = System.currentTimeMillis() - current;
                        time = duration / 1000;

                        if (time_z > time)
                            time_z = time;
                    }

                    //保存数据;
                    xyx[0][c] = q_x;
                    xyx[1][c] = L_y;
                    xyx[2][c] = time_z;
                    c++;


                }
            }

            try {
                //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                FileWriter writer = new FileWriter("D:\\Users\\shiqun\\Desktop\\效率\\" + d + "\\L" + "_MTK" + ".txt");

                //保存结果数据到文件;
                for (int b = 0; b < 3; b++) {
                    for (int a = 0; a < 25; a++) {
                        writer.write(xyx[b][a] + " ");
                    }
                    writer.write("\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean Termination(HeapMin heapMin, ArrayList<MidsideNode> CanTrans, int MaxIndex) {
        if (heapMin.Heap.get(0).low > CanTrans.get(MaxIndex).Upper)
            return true;
        else
            return false;
    }
}
