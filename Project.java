import java.io.*;
import java.util.*;
public class Project {
	private LinkedList<Integer> graph[];
	Project(int[][] adj) {
		LinkedList<Integer> graph[] = initializegraph(adj);
	}
	public static void main(String args[]) {
		BufferedReader r = new BufferedReader(new FileReader(args[0]));
        String s = r.readLine();
        int V = Integer.parseInt(s);
        int adj[][] = new int[V][V];
        for(int j = 0; j < V; j++) {
            s = r.readLine();
            String[] s2 = s.split(" ");
            for(int k = 0; k < V; k++){
                adj[j][k] = Integer.parseInt(s2[k]);
            }
        }
        Project p = new Project(adj);
        HashMap<Integer, Integer> weight = initializeweight(adj);
	}

	private static LinkedList<Integer>[] initializegraph(int[][] input) {
		LinkedList<Integer> graph[] = new LinkedList[input.length];
		for (int i = 0; i < input.length; i++)
			graph[i] = new LinkedList();
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				if (input[i][j] == 1) {
					graph[i].add(j);
				}
			}
		}
	}
	private static HashMap<Integer, Integer> initializeweight(int[][] input) {
		HashMap<Integer, Integer> weight = new HashMap<>();
		for (int i = 0; i < input.length; i++) {
			weight.put(i, input[i][i]);
		}
	}

	private static void SCC(int length) {
		Stack stack = new Stack();
		boolean visited[] = new boolean[length];
		for (int i = 0; i < length; i++) {
			visited[i] = false;
		}

		for (int i = 0; i < length; i++) {
			if (visited[i] == false) {
				fillOrder(i, visited, stack);
			}
		}

		LinkedList<Integer> gr[] = getTranspose(length);
		for (int i = 0; i < length; i++)
            visited[i] = false;

        while (stack.empty() == false)
        {
            int v = (int)stack.pop();
            if (visited[v] == false)
            {
                DFSUtil(v, visited, gr);
                System.out.println();
            }
        }


	}

	private static void fillOrder(int v, boolean visited[], Stack stack)
    {
        visited[v] = true;
        Iterator<Integer> i = this.graph[v].iterator();
        while (i.hasNext())
        {
            int n = i.next();
            if(!visited[n])
                fillOrder(n, visited, stack);
        }
 
        stack.push(new Integer(v));
    }

    private static LinkedList<Integer>[] getTranspose(int length)
    {
        LinkedList<Integer> g[] = new LinkedList[length];
		for (int i = 0; i < length; i++)
			g[i] = new LinkedList();
        for (int v = 0; v < length; v++)
        {

            Iterator<Integer> i = this.graph[v].listIterator();
            while(i.hasNext())
                g[i.next()].add(v);
        }
        return g;
    }

    private static void DFSUtil(int v, boolean visited[], LinkedList<Integer>[] gr)
    {
        visited[v] = true;
        System.out.print(v + " ");
 
        int n;
 
        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i =gr[v].iterator();
        while (i.hasNext())
        {
            n = i.next();
            if (!visited[n])
                DFSUtil(n,visited,gr);
        }
    }


}