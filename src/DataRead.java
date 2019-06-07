import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataRead {
	
	public static InputStream readJsonFile() throws JSONException {

		String path = "/data.json";
		InputStream is = DataRead.class.getResourceAsStream(path);
	    if (is == null) {
	        throw new NullPointerException("Cannot find resource file " + path);
	    	}
	    
	    return is;
	}
	
	public static void dataInterpretation() throws JSONException {
		InputStream is = DataRead.readJsonFile();
		
		JSONTokener tokener = new JSONTokener(is);
	    JSONObject object = new JSONObject(tokener);
	    
	    JSONArray corresp = object.getJSONArray("corresp");			//On récupère toutes les données du Json
	    JSONObject stations = object.getJSONObject("stations");
	    JSONArray routes = object.getJSONArray("routes");
	    JSONObject lignes = object.getJSONObject("lignes");
	    
	    
	    ArrayList<ArrayList<String>> lignesStops = new ArrayList<>();
	    Iterator<String> lignesKeys = lignes.keys();
	    while (lignesKeys.hasNext()) {
	    	String key = lignesKeys.next();
	    	if (!key.equals("0")) {
	    		System.out.println("key : "+key );
	    		JSONObject objTemp = lignes.getJSONObject(key);
	    		
	    		//Ligne ligneTemp = (Ligne) objTemp;
	    		try {
	    			System.out.println(objTemp.getJSONArray("arrets"));
	    		} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    	
//	    		lignesStops.set(key, lignes.getJSONArray(key).getJSONArray("arrets"));
	    	}
	    }
	    
	    
	    List<String> stationsId = new ArrayList<>();		//On associe les id des stations avec des valeurs allant de 0 à 501 (le nombre de stations)
	    Iterator<String> stationsKeys = stations.keys();
	    while (stationsKeys.hasNext()) {
	    	String key = stationsKeys.next();
	    	stationsId.add(key);
	    }
	    
	    
	    
	    	
	}
}
