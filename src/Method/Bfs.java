package Method;

import Adlist.Data.DataNode;
import Adlist.Data.DataVNode;
import Adlist.Pattern.PatternNode;
import Adlist.Pattern.PatternVNode;
import shortestPaths.abstraction.BaseVertex;
import shortestPaths.graph.Path;
import shortestPaths.shortestpaths.YenTopK;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by shiqun on 16/7/4.
 */

//获取孩子匹配并，计算与孩子匹配之间的信任值;
public class Bfs
{
    //判断是否, 这个点已经匹配过这个模式点;
    //value: 数据点id, 模式点id
    HashSet<String> IsMatched = new HashSet<>();

    //判断一个点是否已经访问过;
    HashSet<Integer> IsVisited = new HashSet<>();

    //从某点开始，广度有限查找;
    public void Propagate(MidsideNode startVNode, DataVNode child,
                          PatternVNode patternVNode, int level, int L)

    {
        //子孙节点;
        DataVNode offspring;

        //如果这个点，是在boundlength之内的话，是可能成为匹配的;
        if (level <= L)
        {

            //遍历这个点的所有孩子;
            for (int i = 0; i < child.AdjEdge.size(); i++)
            {
                //
                if(child.AdjEdge.get(i).endVNode.categoryID == 0)
                {
                    continue;
                }

                //寻找匹配;
                offspring = child.AdjEdge.get(i).endVNode;

                //这个节点没有访问过;
                if (!IsVisited.contains(offspring.nodeID))
                {
                    // 找到这个点可能匹配的模式点;
                    // 遍历模式节点的孩子;
                    // 判断这个点和模式点的孩子是否具有相同的标签;
                    PatternNode temp = null;
                    for (int j = 0; j < patternVNode.Children.size(); j++)
                    {
                        //如果这数据点能够匹配模式点的孩子;
                        temp = patternVNode.Children.get(j);
                        if (temp.endVNode.categoryID == offspring.categoryID)
                        {
                            String tmp = offspring.nodeID + temp.endVNode.nodeID + "";
                            if (!IsMatched.contains(tmp))
                            {
                                //创建一个临时节点，用于保存与父类节点之间的联系;
                                MidsideNode MN = new MidsideNode();
                                MN.dataVNode = offspring;
                                MN.parent = startVNode;
                                MN.MatchedPatternNodeid = temp.endVNode.nodeID;

                                //使用top-k最短路径算法，计算与这孩子之间的信任值;
                                YenTopK yenAlg = new YenTopK(Parameter.graph,
                                        Parameter.graph.getVertex(startVNode.dataVNode.nodeID),
                                        Parameter.graph.getVertex(offspring.nodeID));

                                //起始节点;
                                //System.out.println(startVNode.dataVNode.nodeID+"\t"+offspring.nodeID);

                                //查找K条路径;
                                int MaxTrust = 0;
                                while(yenAlg.hasNext())
                                {
                                    //获取这条路径上的点;
                                    Path path = yenAlg.next();
                                    List<BaseVertex> vertexList = path.getVertexList();
                                    System.out.println("Path : "+path);

                                    //保存路径的TRRHO;
                                    double tmpT = 1;
                                    double tmpR = 1;
                                    double tmpRho = 0;

                                    //计算这条路径的属性;
                                    double lastRho = 0;
                                    int length = 0;
                                    for(int v=0;v<vertexList.size()-1;v++)
                                    {
                                        //获取路径中的某条边起点;
                                        BaseVertex start = vertexList.get(v);

                                        //获取路径中的某条边终点;
                                        BaseVertex end = vertexList.get(v+1);

                                        //获取这条边;
                                        HashMap<Integer,DataNode> AdjNode
                                                = Parameter.Data.adlist.get(start.getId()).IndexAdjNodeByID;
                                        DataNode  edge = AdjNode.get(end.getId());

                                        //计算路径的TRRHO;
                                        tmpT *= edge.T;
                                        tmpR *= edge.R;

                                        if(v==0)
                                            tmpRho = edge.endVNode.Rou;

                                        if(v==1)
                                        {
                                            lastRho = edge.endVNode.Rou;
                                            tmpRho = tmpRho;
                                        }

                                        if(v>1)
                                        {
                                            tmpRho += lastRho+edge.endVNode.Rou;
                                            lastRho = edge.endVNode.Rou;
                                        }

                                        //计算路径长度;
                                        length++;
                                    }

                                    //路径长度满足;
                                    if(length < Parameter.L)
                                    {
                                        //满足就计算这条路径的信任值;
                                        if(tmpT >= Parameter.LambadaT && tmpR >= Parameter.LambadaR
                                                && tmpRho >= Parameter.LambadaRho)
                                        {
                                            //保存最大的信任值;
                                            double Trust = tmpT*Parameter.w1 + tmpR*Parameter.w2+tmpRho*Parameter.w3;
                                            if (Trust > MaxTrust)
                                                startVNode.TrustWithchild.put(MN, Trust);
                                        }
                                    }
                                    else
                                        break;
                                }

                                //添加到已匹配记录中去;
                                IsMatched.add(tmp);
                            }

                            break;
                        }
                    }

                    //标记这个节点已经访问过;
                    IsVisited.add(offspring.nodeID);
                }

                //继续查找;
                if (!(level == patternVNode.L))
                {
                    //递归调用;
                    this.Propagate(startVNode, offspring, patternVNode, level + 1, L);
                }

            }
        }
    }
}
