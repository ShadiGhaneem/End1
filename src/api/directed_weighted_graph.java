package api;
import java.util.*;
import java.util.stream.Collectors;

public class directed_weighted_graph implements DirectedWeightedGraph{ // this class represents a directed weighted graph with a basic methods
    private HashMap<Integer,NodeData> nodes; // hashmap contains our graph nodes
    private HashMap<String,EdgeData> edges;// hashmap contains our graph edges
    private int MC;
    public directed_weighted_graph(){// default  cunstractur
        this.nodes  = new HashMap<>();
        this.edges = new HashMap<>();
        MC = 0;

    }
    public directed_weighted_graph(DirectedWeightedGraph g){// default  cunstractur
       this.nodes  = new HashMap<>();
       this.edges = new HashMap<>();
       this.MC = g.getMC();
       Iterator<NodeData>itrNodes = g.nodeIter();
       Iterator<EdgeData>itrEdges= g.edgeIter();
       while (itrNodes.hasNext()){
           NodeData i = itrNodes.next();
           this.nodes.put(i.getKey(),i);
       }
       while (itrEdges.hasNext()){
           EdgeData j = itrEdges.next();
           String edge = j.getSrc() +","+ j.getDest();
           this.edges.put(edge,j);
       }

    }
    @Override
    public NodeData getNode(int key) {//The node with the key value
        if (nodes.containsKey(key)){
            return nodes.get(key);
        }else {
            return null;
        }
    }


    @Override
    public EdgeData getEdge(int src, int dest) {// returns an edge between  src and  dest
        String s=src+","+dest;
        if(edges.containsKey(s))
            return edges.get(s);
        return null;
    }

    @Override
    public void addNode(NodeData n) {// this method adds a new node to the graph
        if (!nodes.containsValue(n)) {
            nodes.put(n.getKey(), n);
            MC++;// modify the mc
        }
    }

    @Override
    public void connect(int src, int dest, double w) {// this method takes two nodes and connect them (make them neighbors)
        if(src==dest)// connecting a node to himself does not do anything so this if check if nodes are the same
            return;
        NodeData  source =this.nodes.get(src);
        NodeData des=this.nodes.get(dest);
        if(source==null || des==null )// if one of the nodes does not exist we  do nothing
            return;
        edge_data e = new edge_data(src, dest, w);
        String str = src + "," + dest;
        if (getEdge(src, dest) == null)// if the nodes are not  neighbors modify edgeSize by adding one
        {
            edges.put(str, e);
            MC++;
        } else {
            if (getEdge(src, dest).getWeight() == w) {
                return;
            }
            edges.remove(str);
            edges.put(str,e);
            MC++;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() // returns iterator on the graph nodes
    {
        Iterator<NodeData>itr =nodes.values().iterator();
        return itr;
    }

    @Override
    public Iterator<EdgeData> edgeIter() // returns iterator on the graph edges
    {
        Iterator<EdgeData>itr = edges.values().iterator();
        return itr;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id)// returns iterator on a node neighbors
    {
        List<EdgeData> edg = new LinkedList<>();
        for (EdgeData e: edges.values()){
            if(e.getSrc()==node_id){
                edg.add(e);
            }
        }
        Iterator<EdgeData>itr = edg.iterator();
        return itr;
    }

    @Override
    public NodeData removeNode(int key) {// this method removes the node with key key
        NodeData n;
        if(nodes.containsKey(key)==true){// if the node has edges
            n=nodes.get(key);
        }else {
            n=null;
        }
        System.out.println(edges.toString());
        if(nodes.containsKey(key)) {
            nodes.remove(key);
            MC++;
            List<EdgeData> list = edges.values().stream().collect(Collectors.toList());
            for (EdgeData e : list) {// removes nodes from neighbors
                if (e.getSrc()==key||e.getDest()==key){
                    String st=e.getSrc()+","+e.getDest();
                    this.edges.remove(st);
                    MC++;
                }
            }
        }
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {// this method removes an edge between two nodes
        EdgeData e;
        String st=src+","+dest;
        if(edges.containsKey(st)==true){
            e=edges.get(st);
        }else {
            e=null;
        }
        if (edges.containsKey(st)) {
            edges.remove(st);
            MC++;
        }
        return e;
    }
    @Override
    public int nodeSize() {//  returns the number of the nodes in the graph
        return nodes.size();
    }

    @Override
    public int edgeSize() {// return the number of the edges in the graph
        return edges.size();
    }

    @Override
    public int getMC() {// return  mc
        return this.MC;
    }
}