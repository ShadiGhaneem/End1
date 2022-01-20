package api;
public class node_data implements NodeData  //this class reprsesnts a vertices in the graph
{
    private int key; // this flied is the key of the node
    private GeoLocation location;// this flied is the location of the node
    private  double weight; // this flied is the weight of the node
    private String info;// this flied is the info of the node
    private int tag; // this flied is the tag of the node
    public  node_data(int key) // The cunstactor with a key value
    {
        this.key =key;
        this.location=new geo_location(0,0,0);
    }
    public  node_data(int node,GeoLocation location)  // this is the cunstactor with a key value and location
    {
        this.key =node;
        this.location =location ;
        this.weight =0 ;

    }
    // getters and setters for key , location , info ,tag ,weight
    @Override
    public int getKey()
    {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
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