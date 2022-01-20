package api;

public class geo_location implements GeoLocation // this class represnts the location of each node in the graph
{
    // the proprites of the locatio x,y,z
    private double x;
    private double y;
    private double z;
    public geo_location(double x,double y,double z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    // getters and setters for the x,y,z
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) // compute the distance between two loactions
    {
        return Math.sqrt(Math.pow(g.x()-this.x,2)+Math.pow(g.y()-this.y,2)+Math.pow(g.z(),2));
    }
}