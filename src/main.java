import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

public class main {

	public static void main(String[] args) {
		try {
			DataRead.dataInterpretation();
			String[] reponse = findLongestShortestPath(DataRead.AdjMatrix,DataRead.stationsId); //-- faut le bfs avant de lancer cette methose
			System.out.println("Le diametre du graphe est " + reponse[2] + "stations : de la station " + reponse[0] + " à la station " + reponse[1]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static String[] findLongestShortestPath(int[][] matrix,List<String> stationsId) {
		String[] pathSizes = {"0","0","0"};
		int LongestPath = 0;
		for (int station1 = 0; station1 < 502; station1++) {
			for (int station2 = 0; station2 < 502; station2++) {
				int BFS = bfs(matrix,station1 ,station2);
				System.out.println(BFS);
				if (BFS> LongestPath) {
					LongestPath = BFS;
					pathSizes[0] = stationsId.get(station1);
					pathSizes[1] = stationsId.get(station2);
					pathSizes[2] = Integer.toString(BFS);
				};

			}
		}
		

		return pathSizes;
	}
	
	public static LinkedList<Integer> getNeighbours(int[][] matrix, int station1){
		LinkedList<Integer> neighbours = new LinkedList<>();
		for(int j=0;j<matrix[station1].length;j++){
			if (station1!=j){ // self loops do not count
				if(matrix[station1][j]==1){
					neighbours.add(j);
				}
			}
		}
		return neighbours;

	}

	public static int bfs(int[][] matrix, int station1, int station2){
		// Mark all the vertices as not visited(By default
		// set as false)
		boolean visited[] = new boolean[502];
		int count=0;

		// Create a queue for BFS
		LinkedList<Integer> queue = new LinkedList<Integer>();

		// Mark the current node as visited and enqueue it
		visited[station1]=true;
		queue.add(station1);

		while (queue.size() != 0)
		{
			// Dequeue a vertex from queue and print it
			station1 = queue.poll();
			//System.out.print(station1+" ");

			// Get all adjacent vertices of the dequeued vertex s
			// If a adjacent has not been visited, then mark it
			// visited and enqueue it
			Iterator<Integer> i = getNeighbours(matrix, station1).listIterator();
			while (i.hasNext())
			{

				int n = i.next();
				if(n == station2){
					return count;
				}
				if (!visited[n])
				{
					visited[n] = true;
					queue.add(n);
					count ++;
				}
			}
		}
		return count;
	}

}
