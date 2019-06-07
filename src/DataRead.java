import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	    
	    JSONArray correspJson = object.getJSONArray("corresp");			//On récupère toutes les données du Json
	    JSONObject stationsJson = object.getJSONObject("stations");
	    JSONArray routesJson = object.getJSONArray("routes");
	    JSONObject lignesJson = object.getJSONObject("lignes");
	    
	    Map<String, ArrayList<ArrayList<String>>> lignes = new HashMap<String, ArrayList<ArrayList<String>>>();		//IMPORTANT Map qui associe un objets contenant les différentes listes d'arrets à la ligne de metro correspondante
	    Set<String> keyList = new HashSet<String>();		//IMPORTANT Liste des différentes key correspondant a une ligne de métro, tram ou rer
	    Iterator<String> lignesKeys = lignesJson.keys();
	    	    
	    while (lignesKeys.hasNext()) {
	    	String key = lignesKeys.next();
	    	if (!key.equals("0")) {				//On ne veut pas récupérer la ligne "0" qui n'est pas une vraie ligne
	    		keyList.add(key);			
	    		
	    		JSONObject objTempJson = lignesJson.getJSONObject(key);			//Objet temporaire qui nous permet de récupérer une ligne sous forme d'objet JSON
	    		JSONArray arrayTempJson = objTempJson.getJSONArray("arrets");	//Array temp qui récupère juste la partie "arrets" de l'objet précédent
	    																		//Les arrayJSON se comportent comme des ObjetsJava
	    		ArrayList<ArrayList<String>> listTemp = new ArrayList<ArrayList<String>>();    //liste temp d'objets équivalents à l'array json temporaire au dessus
	    	    JSONArray jsonArray = (JSONArray)arrayTempJson; 
	    	    if (jsonArray != null) { 
	    	       for (int i=0;i<jsonArray.length();i++){ 
	    	    	   ArrayList<String> listTemp2 = new ArrayList<String>();
	    	    	   JSONArray jsonArray2 = (JSONArray)jsonArray.get(i);			//Gros micmac pour se retrouver avec un
	    	    	   if (jsonArray2 != null) {									//listemp en array d'array de string, sans jsons :D
	    	    		   for (int j=0;j<jsonArray2.length();j++){
	    	    			   listTemp2.add(jsonArray2.get(j).toString());
	    	    		   }
	    	    	   }
	    	    	   listTemp.add(listTemp2);
	    	       } 
	    		lignes.put(key, listTemp);
	    	    }
	    	}
	    } 
	    System.out.println(lignes);
	    
	    
	    List<String> stationsId = new ArrayList<>();			//Les stations ont normalement des ID cheloues
	    Iterator<String> stationsKeys = stationsJson.keys();	//IMPORTANT On crée une liste dans laquelle on stock tous ces ID
	    while (stationsKeys.hasNext()) {						//Ainsi, on peut se rapporter à cette table pour avoir des id allant de 0 à 501
	    	String key = stationsKeys.next();					//pour faciliter la prog, tout en conservant un lien avec l'ID réel d'une station
	    	stationsId.add(key);
	    }
	    
	    
	    	
	}
}
