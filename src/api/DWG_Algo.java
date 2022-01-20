package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.ObjectInputStream;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import java.io.FileInputStream;
import java.io.IOException;


public class DWG_Algo implements DirectedWeightedGraphAlgorithms { // this class  represents a directed weighted graph with a basic/ complex methods
    //these proprites that we usued in our implementation for Diksatra ,fire tree and BFS
    private DirectedWeightedGraph graph; // our graph
    private Queue<NodeData> q ;
    private List<NodeData> DikstraQ;
    private int nill;
    private String color[] ;
    private NodeData p[] ;
    private double d[] ;
    private int pred[];
    private double dist[];
    private boolean visited[];


    //consrtructors
    public DWG_Algo() {
        graph = new directed_weighted_graph();
    }

    public DWG_Algo(DirectedWeightedGraph graph) {
        init(graph);
    }
    // initiallizaton for our fields
    @Override
    public void init(DirectedWeightedGraph g) {
        this.nill=-1;
        this.graph = g;
        this.q = new LinkedList<>();//the queue to manage the set of gray vertices
        this.DikstraQ = new LinkedList<>();
        this.color = new String[graph.nodeSize()];
        this.p = new NodeData[graph.nodeSize()];
        this.d = new double[graph.nodeSize()];
        this.pred = new int[graph.nodeSize()];
        this.dist = new double[graph.nodeSize()];
        this.visited = new boolean[graph.nodeSize()];

    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }
    // deep copy for our graph
    @Override
    public DirectedWeightedGraph copy() {
        return new directed_weighted_graph(this.graph);
    }

    @Override
    public boolean isConnected() {// Check if the nodes of  the graph are strongly connected
        NodeData node = this.graph.getNode(0);
        if(node!=null) {
            BFS(node.getKey(), this.graph);
            for (int i = 0; i < d.length; i++) {
                if (d[i] == nill) {
                    return false;
                }
            }
            DirectedWeightedGraph g = reverse(this.graph);
            node = g.getNode(0);
            BFS(node.getKey(), g);
            for (int i = 0; i < d.length; i++) {
                if (d[i] == nill) {
                    return false;
                }
            }
        }

        return true;
    }

    public void BFS(int startNode, DirectedWeightedGraph g){
        Iterator<NodeData>itr = g.nodeIter();
        while (itr.hasNext()){
            NodeData i = itr.next();
            color[i.getKey()] = "white";
            d[i.getKey()] = nill;
            p[i.getKey()] = null;
        }
        color[startNode] = "gray";
        d[startNode]=0;
        p[startNode]=null;
        q= new LinkedList<>();
        NodeData n = g.getNode(startNode);
        q.add(n);
        NodeData node;
        while (!q.isEmpty()){
            node = q.remove();
            Iterator<EdgeData>it = g.edgeIter(node.getKey());
            while (it.hasNext()){
                NodeData i = this.graph.getNode(it.next().getDest());
                if(i!=null) {
                    if (color[i.getKey()].equals("white")) {
                        color[i.getKey()] = "gray";
                        d[i.getKey()] = d[node.getKey()] + 1;
                        p[i.getKey()] = node;
                        q.add(i);
                    }
                }
            }
            color[node.getKey()]="black";
        }

    }
  // help function that we used in order to transpose our graph
    public  DirectedWeightedGraph reverse (DirectedWeightedGraph g){
        // Reverse the direction of all edges in the directed graph

        DirectedWeightedGraph graph = new directed_weighted_graph();
        Iterator<NodeData>itrNodes = g.nodeIter();
        Iterator<EdgeData>itrEdges= g.edgeIter();
        while (itrNodes.hasNext()){
            NodeData i = itrNodes.next();
            graph.addNode(i);
        }
        while (itrEdges.hasNext()) {
            EdgeData j = itrEdges.next();
            graph.connect(j.getDest(),j.getSrc(),j.getWeight());
        }
        return graph;
    }

    @Override
    public double shortestPathDist(int src, int dest) {// the length of the shortest path from source to destination
        if (src== dest){
            return  -1;
        }
        if(this.graph.getNode(src)==null||this.graph.getNode(dest)==null){
            return  -1 ;
        }
        Dikstra(src,this.graph);
        return this.dist[dest];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) { // thae path from 2 nodes
        List<NodeData> path = new LinkedList<>();
        if (src== dest){
            return  path;
        }
        if(this.graph.getNode(src)==null||this.graph.getNode(dest)==null){
            return  path ;
        }
        Dikstra(src,graph);
        int t = dest;
        NodeData n =graph.getNode(t);
        path.add(n);
        while (t!= src){
            t = pred[t];
            path.add(this.graph.getNode(t));
        }
        List<NodeData> reversed = new LinkedList<>();
        for(int i=path.size()-1;i>=0;i--){
            reversed.add(path.get(i));
        }
        return reversed;
    }

    public void Dikstra (int start ,DirectedWeightedGraph g){
        for (int i=0 ;i<pred.length;i++){
            pred[i]=nill;
            dist[i]=Double.POSITIVE_INFINITY;
            visited[i]=false;
        }
        dist[start]=0;
        Iterator<NodeData>itr = g.nodeIter();
        while (itr.hasNext()) {
            NodeData i = itr.next();
            DikstraQ.add(i);
        }
        while (!DikstraQ.isEmpty()){
            NodeData u = this.graph.getNode(ExtractMin(DikstraQ));
            DikstraQ.remove(this.graph.getNode(ExtractMin(DikstraQ)));
            Iterator<EdgeData> ItrE = g.edgeIter(u.getKey());
            while (ItrE.hasNext()){
                EdgeData e = ItrE.next();
                int v= e.getDest();
                if(!visited[v]){
                    double t= dist[u.getKey()]+this.graph.getEdge(u.getKey(),v).getWeight();
                    if(dist[v]>t){
                        dist[v]=t;
                        pred[v]=u.getKey();
                    }
                }

            }

            visited[u.getKey()]=true;
        }
    }

    public int ExtractMin(List<NodeData> q){
        int index = -1 ;
        double min = Double.POSITIVE_INFINITY;
        for (int i=0 ;i< q.size();i++){
            if(this.dist[q.get(i).getKey()]<= min){
                min =this.dist[q.get(i).getKey()];
                index=q.get(i).getKey();
            }
        }
        return  index;
    }

    @Override
    public NodeData center() { // return the center node in the grapg (we used tree fire algorithm )
        if(this.isConnected()){
        int n = this.graph.nodeSize();
        int  nVert =n ;
        List<Integer> leavs = new LinkedList<>();
        int degrees[]= new int [n];
        int levels[] = new int [n];
        for(int i=0;i<n;i++){
            int crt =0;
            Iterator<EdgeData> itr=graph.edgeIter(i);
            while ((itr.hasNext())){
                crt++;
                EdgeData e = itr.next();
            }
            degrees[i]= crt;
            if (degrees[i] == 1){
                leavs.add(i);
            }
        }
        int maxLevel = 0 ;
        while (nVert>2){
            int leaf =leavs.remove(leavs.size()-1);
            degrees[leaf]=0;
            Iterator<EdgeData>itr = graph.edgeIter(leaf);
            int v =itr.next().getDest();
            degrees[v]--;
            graph.removeEdge(v,leaf);
            nVert--;
            if(degrees[v] == 1){
                leavs.add(v);
                levels[v] = levels[leaf]+1;
                maxLevel = Math.max(maxLevel ,levels[v]);
            }
        }
        ArrayList<Integer> centers=new ArrayList<>();
        for (int i=0 ;i<n;i++){
            if(levels[i]==maxLevel){
                centers.add(i);
            }
        }
        int numCenters =centers.size();
        int radius;
        int diameter;
        if(numCenters ==2){
            radius =maxLevel+1;
            diameter = 2 * radius -1;
        }else{
            radius = maxLevel;
            diameter = 2* radius;


        }
        return this.graph.getNode(centers.get(0));
        } else {
            return null;
        }

    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        HashSet<Integer> ToGo = new HashSet<Integer>();
        List<NodeData> path = new LinkedList<NodeData>();
        path.add(cities.get(0));
        for(int i=1;i<cities.size();i++){
            ToGo.add(cities.get(i).getKey());
        }
        while(ToGo.size()>0)
        {
            List<NodeData> srcToDstPath = shortestPath(path.get(path.size()-1).getKey(), ToGo.iterator().next());
            Iterator<NodeData> gon = srcToDstPath.iterator();
            gon.next();
            while (gon.hasNext())
            {
                NodeData n = gon.next();
                ToGo.remove(n.getKey());
                path.add(n);
            }

        }
        return path;
    }

    @Override
    public boolean save(String file) {// saving the graph
        Gson g=new GsonBuilder().setPrettyPrinting().create();
        String str=g.toJson(this.graph);
        System.out.println(str);
        try {
            PrintWriter p=new PrintWriter(new File(file));
            p.write(str);
            p.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    @Override
    public boolean load(String file) {// loading the graph
        try {
            FileInputStream MygraphhFile = new FileInputStream(file);
            ObjectInputStream OtherGraphFile = new ObjectInputStream(MygraphhFile);
            this.graph = (directed_weighted_graph)
                    OtherGraphFile.readObject();
            OtherGraphFile.close();
            MygraphhFile.close();
            return true;
        }
        catch (IOException ex) {
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        }

    }


}