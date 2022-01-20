package api;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

public class Panel extends JPanel {
    DirectedWeightedGraph graph;

    private double minX;
    private double minY;
    private final int ARR_SIZE = 7;
    private double maxX;
    private double maxY;


    private double scaleX;
    private double scaleY;


    public Panel(DirectedWeightedGraph graph) {
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.graph = graph;
        MaxMinForXY();
    }

    private void MaxMinForXY() {
        Iterator<NodeData> n = graph.nodeIter();
        NodeData node = n.next();
        minX = node.getLocation().x();
        minY = node.getLocation().y();

        maxX = node.getLocation().x();
        maxY = node.getLocation().y();
        while (n.hasNext()) {
            node = n.next();
            minX = Math.min(minX, node.getLocation().x());
            minY = Math.min(minY, node.getLocation().y());

            maxX = Math.max(maxX, node.getLocation().x());
            maxY = Math.max(maxY, node.getLocation().y());
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.scaleX = this.getWidth() / Math.abs(maxX - minX)*0.89999 ;
        this.scaleY = this.getHeight() / Math.abs(maxY - minY)*0.855;
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        Iterator<NodeData> NodesIter=this.graph.nodeIter();
        while(NodesIter.hasNext()){
            NodeData n=NodesIter.next();
            drawNode(g,n);
            Iterator<EdgeData> edgesIter=this.graph.edgeIter(n.getKey());
            while(edgesIter.hasNext()){
                EdgeData e=edgesIter.next();
                drawEdge(g,e);
            }
        }
    }

    public void drawNode(Graphics g,NodeData node) {
        int x = (int) ((node.getLocation().x() - this.minX) * this.scaleX);
        int y = (int) ((node.getLocation().y() - this.minY) * this.scaleY);
        g.setColor(Color.green);
        g.fillOval(x, y, 24, 24);
        g.setColor(Color.WHITE);
        g.setFont(new Font("OOP", Font.BOLD, 15));
        g.drawString(String.valueOf(node.getKey()), x+8 , y+14 );

    }


    public void drawEdge(Graphics g,EdgeData edge) {
        double x1 = graph.getNode(edge.getSrc()).getLocation().x();
        x1 = ((x1 - minX) * this.scaleX) + 16.5;
        double x2 = graph.getNode(edge.getSrc()).getLocation().y();
        x2 = ((x2 - minY) * this.scaleY) + 16.5;

        double y1 = this.graph.getNode(edge.getDest()).getLocation().x();
        y1 = ((y1 - this.minX) * this.scaleX) + 16.5;
        double y2 = this.graph.getNode(edge.getDest()).getLocation().y();
        y2 = ((y2 - this.minY) * this.scaleY) + 16.5;

        g.setColor(Color.BLACK);
        drawArrow(g,(int) x1, (int) x2, (int) y1, (int) y2);
        String weightString =String.valueOf(edge.getWeight()) ;
        weightString = weightString.substring(0,weightString.indexOf(".")+2);

        g.setColor(Color.RED);
        g.setFont(new Font("OOP", Font.BOLD, 15));
        g.drawString(weightString, (int)(x1*0.25 + y1*0.75),(int)(x2*0.25 + y2*0.75));

    }
    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        g.drawLine(0, 2, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }









}