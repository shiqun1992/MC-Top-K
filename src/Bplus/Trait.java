package Bplus;
/**
 * Created by Administrator on 16/6/23.
 */
public class Trait implements Comparable<Trait>
{
    int label;
    int indegree;
    int outdegree;

    public Trait(int label,int indegree,int outdegree)
    {
        this.label = label;
        this.indegree = indegree;
        this.outdegree = outdegree;
    }

    @Override
    public int compareTo(Trait o)
    {
        if(this.label > o.label) {
            return 1;
        }
        else if(this.label < o.label) {
            return -1;
        }
        else
        {

            if(this.indegree > o.indegree)
            {
                return 1;
            }
            else if (this.indegree < o.indegree){
                return -1;
            }
            else
            {
                if(this.outdegree > o.outdegree)
                    return 1;
                else if(this.outdegree < o.outdegree)
                    return -1;
                else
                    return 0;
            }
        }
    }

    //label要相等;
    //degree要大于;
    public int Meet(Trait o)
    {
        if(this.label == o.label
                && this.indegree >= o.indegree
                && this.outdegree >= o.outdegree)
            return 1;
        else
            return 0;
    }

}
