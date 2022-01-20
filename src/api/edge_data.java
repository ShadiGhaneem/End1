package api;

public class edge_data implements EdgeData{// this class represents the edge between two nodes
    private int src ; //the source point of the edge
    private int dest ; //the destination of point the edge
    private double weight;// the weight of the edge
    private String info;// the info of the edge
    private int tag ;// the tag of the edge

    public edge_data(int src,int dest,double weight){ //a default  cunstractur
        this.src=src;
        this.dest=dest;
        this.weight=weight;
    }

    public edge_data (int src,int dest,double weight, String info, int tag){//a default  cunstractur
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        this.info=info;
        this.tag=tag;
    }

 // getters and setters for the edge feildes mentioned above
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;

    }
}
