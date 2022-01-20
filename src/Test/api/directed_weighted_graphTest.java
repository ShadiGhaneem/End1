package Test.api;

import static org.junit.Assert.*;
import api.directed_weighted_graph;
import api.NodeData;
import api.*;

import java.awt.font.GraphicAttribute;


public class directed_weighted_graphTest {

    public DirectedWeightedGraph InitGraph(){
        DirectedWeightedGraph graph = new directed_weighted_graph();
        for (int i=0 ; i<10000 ;i++){
            graph.addNode(new node_data(i));
        }
        for (int j=0 ;j<50000 ;j++){
            int src = (int)(Math.random()*10000);
            int dest =(int)(Math.random()*10000);
            double w = Math.random()*20;
            graph.connect(src,dest,w);
        }
        return graph;
    }

    @org.junit.Test
    public void getNode() {
        DirectedWeightedGraph graph = InitGraph();
        for (int i=0 ;i<10000;i++){
            assertEquals(i,graph.getNode(i).getKey());
            assertNull(graph.getNode(i+10000));
        }
    }

    @org.junit.Test
    public void addNode() {
        DirectedWeightedGraph graph = InitGraph();
        int x = graph.nodeSize();
        assertEquals(x,10000);
        for (int i=0 ; i<10000 ;i++){
            graph.addNode(new node_data(i));
        }
        assertEquals(x,10000);
    }

    @org.junit.Test
    public void connect() {
        DirectedWeightedGraph graph = new directed_weighted_graph();
        for (int i=0 ; i<10 ;i++){
            graph.addNode(new node_data(i));
        }
        graph.connect(1,2,10);
        graph.connect(2,3,13);
        graph.connect(4,2,22);
        graph.connect(2,6,1);
        graph.connect(1,3,7);
        assertEquals(graph.edgeSize(),5);
        graph.removeNode(1);
        assertEquals(graph.edgeSize(),3);
    }

    @org.junit.Test
    public void removeEdge() {
        DirectedWeightedGraph graph = new directed_weighted_graph();
        for (int i=0 ; i<10 ;i++){
            graph.addNode(new node_data(i));
        }
        graph.connect(1,2,10);
        graph.connect(2,3,13);
        graph.connect(4,2,22);
        graph.connect(2,6,1);
        graph.connect(1,3,7);
        assertEquals(graph.edgeSize(),5);
        graph.removeEdge(1,2);
        assertEquals(graph.edgeSize(),4);
        graph.removeEdge(1,1);
        graph.removeEdge(5,1000);
        assertEquals(graph.edgeSize(),4);
    }



}