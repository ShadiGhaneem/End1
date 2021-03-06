import api.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
        Ex2.runGUI("C:\\Users\\shadi\\IdeaProjects\\untitled5\\data\\G1.json");
    }
    public static DirectedWeightedGraph getGrapg(String json_file) throws IOException, ParseException, org.json.simple.parser.ParseException {
        DirectedWeightedGraph ans = new directed_weighted_graph();
        JSONParser parser=new JSONParser();
        JSONObject ob= (JSONObject) parser.parse(new FileReader(json_file));
        JSONArray edge= (JSONArray) ob.get("Edges");
        JSONArray node=(JSONArray) ob.get("Nodes");
        for(Object i:node){
            JSONObject o=(JSONObject) i;
            String str=o.get("pos").toString();
            String [] Xyz=str.split(",");
            GeoLocation geo=new geo_location(Double.parseDouble(Xyz[0]),Double.parseDouble(Xyz[1]),Double.parseDouble(Xyz[2]));
            NodeData n=new node_data(Integer.parseInt(o.get("id").toString()),geo);
            ans.addNode(n);
        }
        for(Object j:edge){
            JSONObject o=(JSONObject) j;
            int src=Integer.parseInt(o.get("src").toString());
            double weight= Double.parseDouble(o.get("w").toString());
            int dest=Integer.parseInt(o.get("dest").toString());
            ans.connect(src,dest, weight);
        }

        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) throws IOException, ParseException, org.json.simple.parser.ParseException {
        DirectedWeightedGraph g=getGrapg(json_file);
        DirectedWeightedGraphAlgorithms ans = new DWG_Algo();
        ans.init(g);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) throws IOException, ParseException, org.json.simple.parser.ParseException {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        new GUI(alg);
    }


}