import java.util.List;

import org.json.JSONException;

public class main {

	public static void main(String[] args) {
		try {
			DataRead.dataInterpretation();
			String[] reponse = findLongestShortestPath(DataRead.AdjMatrix,DataRead.stationsId); //-- faut le bfs avant de lancer cette methose
			//System.out.println("Le diametre du graphe est " + reponse[2] + "stations : de la station " + reponse[0] + " à la station " + reponse[1]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static String[] findLongestShortestPath(int[][] matrix,List<String> stationsId) {
		String[] pathSizes = {"0","0","0"};
		int LongestPath = 0;
		for (int station1 = 0; station1 < 502; station1++) {
			for (int station2 = 0; station2 < 502; station2++) {
				int BFS = DataRead.rechercheItineraire(matrix,station1 ,station2);
				System.out.println(BFS);
				if (BFS> LongestPath) {
					pathSizes[0] = stationsId.get(station1);
					pathSizes[1] = stationsId.get(station2);
					pathSizes[2] = Integer.toString(BFS);
				};

			}
		}
		

		return pathSizes;
	}

}
