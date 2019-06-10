import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public class main {

	public static void main(String[] args) {
		try {
			DataRead.dataInterpretation();
			// findLongestShortestPath(DataRead.AdjMatrix,DataRead.stationsId); -- faut le bfs avant de lancer cette methose
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static String[] findLongestShortestPath(int[][] matrix,List<String> stationsId) {
		String[] pathSizes = new String[3];
		int LongestPath = 0;
		for (int station1 = 0; station1 < 502; station1++) {
			for (int station2 = 0; station2 < 502; station2++) {
				if (bfs(matrix,station1,station2)> LongestPath) {
					pathSizes[0] = stationsId.get(station1);
					pathSizes[1] = stationsId.get(station2);
					pathSizes[2] = bfs(matrix,station1,station2).toString();
				};

			}
		}
		

		return pathSizes;
	}

}
