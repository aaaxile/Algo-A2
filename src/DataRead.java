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
		InputStream is = DataRead.readJsonFile();	//On récupère toutes les données du Json
		JSONTokener tokener = new JSONTokener(is);
	    JSONObject toutJson = new JSONObject(tokener);
	    
	    List<String> stationsId = DataRead.stationsInterpretation(toutJson);							//Stations de à 501
	    Map<String, ArrayList<ArrayList<String>>> lignes = DataRead.lignesInterpretation(toutJson);		//Arrets par ligne 
	    Set<String> keyList = DataRead.listeDesKeys(lignes);											//Liste des clés (numéros de ligne)
	    
	    JSONArray correspJson = toutJson.getJSONArray("corresp");		//Encore a traiter	
	    JSONArray routesJson = toutJson.getJSONArray("routes");			//Same mais n'a pas l'air essentiel
	    
	    
	    /*Coucou celui ou celle qui reprendra ce code, je sais que c'est chiant de reprendre le code d'un autre donc j'ai essayé
	     * d'expliquer un maximum, et je vais essayer d'approfondir un peu ici.
	     * Déjà, tu es un amour.
	     * Ensuite, t'as stationsId, lignes et keyList à ta disposition, normalement c'est suffisant pour faire la matrice, mais elle
	     * ne sera pas complète tant que "correspJson" n'aura pas été traité aussi.
	     * Mais tout d'abord va falloir créer une grosse matrice de 502 zeros de long et de large
	     * Ensuite, en gros, de ce que j'ai compris, il va falloir faire la boucle suivante :
	     * 	-Prendre l'arret d'une ligne (en commencant par le premier) et regarder l'arret suivant.
	     * 		(Par exemple 1865 et 2156 sont les deux Id d'arrets)
	     * 	-Prendre la valeur comprise entre 0 et 501 de ces deux arrets grâce à "stationsId"
	     * 		(par exemple imaginons que 12 et 85 sont les équivalents de 1865 et 2156)
	     * 	-Mettre un 1 à la place du 0 aux deux endroits correspondant dans la matrice
	     * 		(Donc à matrice[12][85] et matrice[85][12])
	     * 	-Et voala
	     * Ensuite il faudra aussi s'occuper de lier les correspondances de la même facon mais ca devrait pas être trop long, surtout que vous
	     * êtes trop forts.
	     * Je suis désolé si ca sonne condescendant ou quoi, c'est pas du tout mon intention, je veux juste aider si tu es perdu 0:)
	     * Merci beaucoup, je suis désolé de pas avoir pu faire plus, j'ai vraiment begayé sur des trucs tout cons, j'essayerai d'aider 
	     * pour le word et le ppt comme je peux :3
	     * Love <3
	     */
	 

	}
	
	public static List<String> stationsInterpretation(JSONObject toutJson) throws JSONException {
		JSONObject stationsJson = toutJson.getJSONObject("stations");
		
		List<String> stationsId = new ArrayList<>();			//Les stations ont normalement des ID cheloues
		Iterator<String> stationsKeys = stationsJson.keys();	//On crée une liste dans laquelle on stock tous ces ID
		while (stationsKeys.hasNext()) {						//Ainsi, on peut se rapporter à cette table pour avoir des id allant de 0 à 501
			String key = stationsKeys.next();					//pour faciliter la prog, tout en conservant un lien avec l'ID réel d'une station
			stationsId.add(key);
		}
		return stationsId;
	}
	
	public static Map<String, ArrayList<ArrayList<String>>> lignesInterpretation(JSONObject toutJson) throws JSONException {
		JSONObject lignesJson = toutJson.getJSONObject("lignes");
		    
		Map<String, ArrayList<ArrayList<String>>> lignes = new HashMap<String, ArrayList<ArrayList<String>>>();		//Map qui associe un objets contenant les différentes listes d'arrets à la ligne de metro correspondante
		Iterator<String> lignesKeys = lignesJson.keys();
		    	    
		while (lignesKeys.hasNext()) {
			String key = lignesKeys.next();
			if (!key.equals("0")) {								//On ne veut pas récupérer la ligne "0" qui n'est pas une vraie ligne
				JSONObject objTempJson = lignesJson.getJSONObject(key);			//Objet temporaire qui nous permet de récupérer une ligne sous forme d'objet JSON
				JSONArray arrayTempJson = objTempJson.getJSONArray("arrets");	//Array temp qui récupère juste la partie "arrets" de l'objet précédent
																				//Les arrayJSON se comportent comme des ObjetsJava
				ArrayList<ArrayList<String>> listTemp = new ArrayList<ArrayList<String>>();    //liste temp d'objets équivalents à l'array json temporaire au dessus
				JSONArray jsonArray = (JSONArray)arrayTempJson; 
				if (jsonArray != null) { 
					for (int i=0;i<jsonArray.length();i++){ 
						ArrayList<String> listTemp2 = new ArrayList<String>();
						JSONArray jsonArray2 = (JSONArray)jsonArray.get(i);			//Gros micmac pour se retrouver avec un
						if (jsonArray2 != null) {									//listTemp en array d'array de string, sans jsons :D
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
		return lignes;
	}
	
	public static Set<String> listeDesKeys(Map<String, ArrayList<ArrayList<String>>> lignes) {	//Ressort la liste des clés (numéros de lignes)
		Set<String> keyList = new HashSet<String>();
		for (String key : lignes.keySet()) {
			keyList.add(key);
		}
		return keyList;
	}
	
}
