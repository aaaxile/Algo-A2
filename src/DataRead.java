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

	static int[][] AdjMatrix;
	static List<String> stationsId; 

	public static InputStream readJsonFile() throws JSONException {

		String path = "/data.json";
		InputStream is = DataRead.class.getResourceAsStream(path);
		if (is == null) {
			throw new NullPointerException("Cannot find resource file " + path);
		}

		return is;
	}

	public static void dataInterpretation() throws JSONException {
		InputStream is = DataRead.readJsonFile(); // On récupère toutes les données du Json
		JSONTokener tokener = new JSONTokener(is);
		JSONObject toutJson = new JSONObject(tokener);

		List<String> stationsId = DataRead.stationsInterpretation(toutJson); // Stations de 1 à 501
		Map<String, ArrayList<ArrayList<String>>> lignes = DataRead.lignesInterpretation(toutJson); // Arrets par ligne
		Set<String> keyList = DataRead.listeDesKeys(lignes); // Liste des clés (numéros de ligne)

		JSONArray correspJson = toutJson.getJSONArray("corresp");
		ArrayList<ArrayList<String>> correspondances = correspInterpretation(correspJson); // liste des correspondances

		JSONArray routesJson = toutJson.getJSONArray("routes");

		DataRead.AdjMatrix = adjMatrix(stationsId, lignes, correspondances);

		/*
		 * Coucou celui ou celle qui reprendra ce code, je sais que c'est chiant de
		 * reprendre le code d'un autre donc j'ai essayé d'expliquer un maximum, et je
		 * vais essayer d'approfondir un peu ici. Déjà, tu es un amour. Ensuite, t'as
		 * stationsId, lignes et keyList à ta disposition, normalement c'est suffisant
		 * pour faire la matrice, mais elle ne sera pas complète tant que "correspJson"
		 * n'aura pas été traité aussi. Mais tout d'abord va falloir créer une grosse
		 * matrice de 502 zeros de long et de large Ensuite, en gros, de ce que j'ai
		 * compris, il va falloir faire la boucle suivante : -Prendre l'arret d'une
		 * ligne (en commencant par le premier) et regarder l'arret suivant. (Par
		 * exemple 1865 et 2156 sont les deux Id d'arrets) -Prendre la valeur comprise
		 * entre 0 et 501 de ces deux arrets grâce à "stationsId" (par exemple imaginons
		 * que 12 et 85 sont les équivalents de 1865 et 2156) -Mettre un 1 à la place du
		 * 0 aux deux endroits correspondant dans la matrice (Donc à matrice[12][85] et
		 * matrice[85][12]) -Et voala Ensuite il faudra aussi s'occuper de lier les
		 * correspondances de la même facon mais ca devrait pas être trop long, surtout
		 * que vous êtes trop forts. Je suis désolé si ca sonne condescendant ou quoi,
		 * c'est pas du tout mon intention, je veux juste aider si tu es perdu 0:) Merci
		 * beaucoup, je suis désolé de pas avoir pu faire plus, j'ai vraiment begayé sur
		 * des trucs tout cons, j'essayerai d'aider pour le word et le ppt comme je peux
		 * :3 Love <3
		 */
	}

	public static List<String> stationsInterpretation(JSONObject toutJson) throws JSONException {
		JSONObject stationsJson = toutJson.getJSONObject("stations");

		List<String> stationsId = new ArrayList<>(); // Les stations ont normalement des ID cheloues
		Iterator<String> stationsKeys = stationsJson.keys(); // On crée une liste dans laquelle on stock tous ces ID
		while (stationsKeys.hasNext()) { // Ainsi, on peut se rapporter à cette table pour avoir des id allant de 0 à
											// 501
			String key = stationsKeys.next(); // pour faciliter la prog, tout en conservant un lien avec l'ID réel d'une
												// station
			stationsId.add(key);
		}
		return stationsId;
	}

	public static Map<String, ArrayList<ArrayList<String>>> lignesInterpretation(JSONObject toutJson)
			throws JSONException {
		JSONObject lignesJson = toutJson.getJSONObject("lignes");

		Map<String, ArrayList<ArrayList<String>>> lignes = new HashMap<String, ArrayList<ArrayList<String>>>(); // Map
																												// qui
																												// associe
																												// un
																												// objets
																												// contenant
																												// les
																												// différentes
																												// listes
																												// d'arrets
																												// à la
																												// ligne
																												// de
																												// metro
																												// correspondante
		Iterator<String> lignesKeys = lignesJson.keys();

		while (lignesKeys.hasNext()) {
			String key = lignesKeys.next();
			if (!key.equals("0")) { // On ne veut pas récupérer la ligne "0" qui n'est pas une vraie ligne
				JSONObject objTempJson = lignesJson.getJSONObject(key); // Objet temporaire qui nous permet de récupérer
																		// une ligne sous forme d'objet JSON
				JSONArray arrayTempJson = objTempJson.getJSONArray("arrets"); // Array temp qui récupère juste la partie
																				// "arrets" de l'objet précédent
																				// Les arrayJSON se comportent comme des
																				// ObjetsJava
				ArrayList<ArrayList<String>> listTemp = new ArrayList<ArrayList<String>>(); // liste temp d'objets
																							// équivalents à l'array
																							// json temporaire au dessus
				JSONArray jsonArray = (JSONArray) arrayTempJson;
				if (jsonArray != null) {
					for (int i = 0; i < jsonArray.length(); i++) {
						ArrayList<String> listTemp2 = new ArrayList<String>();
						JSONArray jsonArray2 = (JSONArray) jsonArray.get(i); // Gros micmac pour se retrouver avec un
						if (jsonArray2 != null) { // listTemp en array d'array de string, sans jsons :D
							for (int j = 0; j < jsonArray2.length(); j++) {
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

	public static Set<String> listeDesKeys(Map<String, ArrayList<ArrayList<String>>> lignes) { // Ressort la liste des
																								// clés (numéros de
																								// lignes)
		Set<String> keyList = new HashSet<String>();
		for (String key : lignes.keySet()) {
			keyList.add(key);
		}
		return keyList;
	}

	public static ArrayList<ArrayList<String>> correspInterpretation(JSONArray json) throws JSONException {
		ArrayList<ArrayList<String>> correspList = new ArrayList<ArrayList<String>>();
		if (json != null) {
			for (int i = 0; i < json.length(); i++) {
				ArrayList<String> correspSubList = new ArrayList<String>();
				for (int j = 0; j < json.getJSONArray(i).length(); j++) {
					correspSubList.add(json.getJSONArray(i).getString(j));
				}
				correspList.add(correspSubList);
			}
		}
		return correspList;
	}

	public static int[][] adjMatrix(List<String> stations, Map<String, ArrayList<ArrayList<String>>> lignes,
			ArrayList<ArrayList<String>> correspondances) {
		int[][] matrix = new int[502][502];
		lignes.values().stream().forEach(l -> {
			ArrayList<ArrayList<String>> ligne = ((ArrayList<ArrayList<String>>) l);
			for (int k = 0; k < ligne.size(); k++) {
				for (int stationIndex = 0; stationIndex < ligne.get(k).size() - 1; stationIndex++) {
					int stationNumberIndex = stations.indexOf(ligne.get(k).get(stationIndex));
					int stationSuivanteIndex = stations.indexOf(ligne.get(k).get(stationIndex + 1));
					matrix[stationNumberIndex][stationSuivanteIndex] = 1;
					matrix[stationSuivanteIndex][stationNumberIndex] = 1;
				}
			}
		});
		for (int i = 0; i < correspondances.size(); i++) {
			for (int j = 0; j < correspondances.get(i).size() - 1; j++) {
				for (int k = j + 1; k < correspondances.get(i).size(); k++) {
					int stationIndex1 = stations.indexOf(correspondances.get(i).get(j));
					int stationIndex2 = stations.indexOf(correspondances.get(i).get(k));
					matrix[stationIndex1][stationIndex2] = 1;
					matrix[stationIndex2][stationIndex1] = 1;
				}
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		return matrix;

	}
	
	
	public static int rechercheItineraire(int[][] matrix, int start, int finish){

		Map<Integer, Integer> prev = new HashMap<Integer, Integer>();
        boolean visited[] = new boolean[matrix.length];
        visited[start-1] = true;
  
        Queue<Integer> queue = new LinkedList<>(); 
        queue.add(start);
        // Mark the current node as visited and enqueue it
        int current = start;
        //visited[current]=true; 
        //queue.add(current); 
  
        while (queue.size() != 0) 
        { 
        	int x = queue.poll();	//retire x de la queue
        	int i;
            
        	if (current==finish){
        		return tracebackPath(current, prev);
                //break;
            }else{
            	for(i=0; i<matrix.length;i++){
                    if(matrix[x-1][i] == 1 && visited[i] == false){
                        queue.add(i+1);
                        visited[i] = true;
                        System.out.println(visited[i]);
                        prev.put((i+1), i); // si ne fonctionne pas remplacer i+1 par i-1
                    }
                }
            }
  
            
        }
        
        
        
        
		return 0;
	}
	
	public static int tracebackPath(int target, Map<Integer, Integer> prev1) {	//Recupere le path a partir du finish
        List<Integer> s = new LinkedList<Integer>();
        Integer u = target;
        while(prev1.get(u) != null){
            s.add(u);
            u = prev1.get(u);
        }
        return s.size();
    }

}
