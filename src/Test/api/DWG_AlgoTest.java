package Test.api;

import api.*;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class DWG_AlgoTest extends TestCase {

    public void testIsConnected() {
        DirectedWeightedGraphAlgorithms g=new DWG_Algo();
        DirectedWeightedGraph graph=new directed_weighted_graph();
        for(int i=0;i<1000;i++){
            graph.addNode(new node_data(i));
        }
        for(int i=0;i<9000;i++){
            int src=(int)(Math.random()*10000);
            int dest=(int)(Math.random()*10000);
            int w=(int)(Math.random()*10);
            graph.connect(src,dest,w);
            g.init(graph);
            assertFalse(g.isConnected());
        }
        graph=new directed_weighted_graph();
        for(int i=0;i<10;i++){
            graph.addNode(new node_data(i));
        }
        for(int i=0 ;i<9;i++){
            graph.connect(i,i+1,2);
            g.init(graph);
            assertFalse(g.isConnected());
        }
        graph.connect(9,0,2);
        g.init(graph);
        assertTrue(g.isConnected());
    }

    public void testShortestPathDist() {
        DirectedWeightedGraph graph =new directed_weighted_graph();
        for(int i=0;i<1000;i++){
            graph.addNode(new node_data(i));
        }
        graph.connect(0, 1, 10);
        graph.connect(1, 2, 100);
        graph.connect(2, 3, 1000);
        DirectedWeightedGraphAlgorithms g=new DWG_Algo();
        g.init(graph);
        assertEquals(g.shortestPathDist(0,2),110.0);
        graph=new directed_weighted_graph();
        for(int i=0;i<100;i++){
            graph.addNode(new node_data(i));
        }
        for(int i=0;i<90;i++) {
            int w = 1;
            graph.connect(i,i+1,w);
        }
        g= new DWG_Algo();
        g.init(graph);
        for(int i=1;i<80;i++){
            double d = i ;
            assertEquals(g.shortestPathDist(0,i),d);
        }
        double n= -1;
        assertEquals(g.shortestPathDist(0,0),n);


    }

    public void testShortestPath() {
        DirectedWeightedGraph graph =new directed_weighted_graph();
        for(int i=0;i<100;i++){
            graph.addNode(new node_data(i));
        }
        graph.connect(0, 1, 10);
        graph.connect(1, 2, 100);
        graph.connect(2, 3, 1000);
        DirectedWeightedGraphAlgorithms g=new DWG_Algo();
        g.init(graph);
        List<NodeData> l= g.shortestPath(0,2);
        assertEquals(l.get(0).getKey(),0);
        assertEquals(l.get(1).getKey(),1);
        assertEquals(l.get(2).getKey(),2);
        graph=new directed_weighted_graph();
        for(int i=0;i<100;i++){
            graph.addNode(new node_data(i));
        }
        for(int i=0;i<90;i++) {
            int src = i;
            int dest = i+1;
            int w = 1;
            graph.connect(src,dest,w);
        }
        g.init(graph);
        for(int i=1;i<80;i++){
            l=g.shortestPath(0,i);
            for(int j=0;j<=i;j++){
                assertEquals(j,l.get(j).getKey());
            }
        }
        l=g.shortestPath(0,0);
        assertEquals(l.size(),0);
    }

    public void testCenter() {
        DirectedWeightedGraph graph = new directed_weighted_graph();
        DirectedWeightedGraphAlgorithms gg=new DWG_Algo();
        for(int i=0 ;i<5;i++){
            graph.addNode(new node_data(i));
        }
        graph.connect(1,2,0.5);
        graph.connect(2,0,0.5);
        graph.connect(0,1,1.5);
        graph.connect(2,3,1.5);
        graph.connect(3,4,0.5);
        graph.connect(4,1,0.5);
        gg.init(graph);
        NodeData center = gg.center();
        assertEquals(center.getKey(),2);
    }


    public void testTsp() {
        DirectedWeightedGraph graph =new directed_weighted_graph();
        List<NodeData> l=new LinkedList<>();
        for(int i=0;i<=3;i++){
            graph.addNode(new node_data(i));
            l.add(new node_data(i));
        }
        graph.connect(0, 1, 10);
        graph.connect(1, 0, 10);
        graph.connect(0, 3, 20);
        graph.connect(3, 0, 20);
        graph.connect(0, 2, 15);
        graph.connect(2, 0, 15);
        graph.connect(1, 3, 25);
        graph.connect(3, 1, 25);
        graph.connect(3, 2, 30);
        graph.connect(2, 3, 30);
        graph.connect(1, 2, 35);
        graph.connect(2, 1, 35);
        DirectedWeightedGraphAlgorithms g=new DWG_Algo();
        g.init(graph);
        List<NodeData> ls =g.tsp(l);
        assertEquals(ls.get(0).getKey(),0);
        assertEquals(ls.get(1).getKey(),1);
        assertEquals(ls.get(2).getKey(),0);
        assertEquals(ls.get(3).getKey(),2);
        assertEquals(ls.get(4).getKey(),3);
    }

    public void testSave() {
        DirectedWeightedGraphAlgorithms g=new DWG_Algo();
        DirectedWeightedGraph graph=new directed_weighted_graph();
        for(int i=0;i<100;i++){
            graph.addNode(new node_data(i));
        }
        for(int i=0;i<90;i++){
            int src=(int)(Math.random()*100);
            int dest=(int)(Math.random()*100);
            int w=(int)(Math.random()*10);
            graph.connect(src,dest,w);
            g.init(graph);
        }
        double x=g.shortestPathDist(8,19);
        g.save("file1");
        g.load("file1");
        double x2=g.shortestPathDist(8,19);
        assertEquals(x2,x);
    }

}