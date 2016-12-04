import java.io.*;
import java.util.*;
public class Horse {
	private LinkedList<Integer> graph[];
    private int weight[];
	Horse(int[][] adj) {
		this.graph = initializegraph(adj);
        weight = initializeweight(adj);
	}
	public static void main(String args[]) {
        try {
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
            Horse p = new Horse(adj);
            Boolean visited[] = new Boolean[V];
            for (int i = 0; i < V; i++) {
                visited[i] = false;
            }

            Iterator<Integer> i = p.extractlongestpath(visited).iterator();
            while (i.hasNext()) {
                int n = i.next();
                System.out.print(n);
            }

        }
        catch (IOException e) {
            return;
        }
    }
    private LinkedList<Integer>[] initializegraph(int[][] input) {
        LinkedList<Integer> graph[] = new LinkedList[input.length];
        for (int i = 0; i < input.length; i++)
            graph[i] = new LinkedList();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (i != j) {
                    if (input[i][j] == 1) {
                       graph[i].add(j);
                    }
                }
            }
        }
        return graph;
    }
    private int[] initializeweight(int[][] input) {
        int[] weight = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            weight[i] = input[i][i];
        }
        return weight;
    }

    private ArrayList<Integer> extractlongestpath(Boolean visited[]) {
        int maxweight = 0;
        ArrayList<Integer> longestpath = new ArrayList<Integer>();
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == true) {
                continue;
            }
            else {
                Tuple temp = longestchildpath(visited, graph[i]);
                if ((temp.max+weight[i])*(temp.path.size()+1) > maxweight) {
                    maxweight = temp.max+weight[i];
                    longestpath = temp.path;
                    longestpath.add(0, i);
                }
                //dont forget to add itself
            }

        }
        return longestpath;
    }
    private Tuple longestchildpath(Boolean[] visited, LinkedList<Integer> children) {
        int max = 0;
        ArrayList<Integer> path = new ArrayList<>();
        Tuple result = new Tuple(max, path);
        Iterator<Integer> i = children.iterator();
        while (i.hasNext()) {
            int child = i.next();
            if (visited[child]) {
                continue;
            }
            else {
                visited[child] = true;
                Tuple temp = longestchildpath(visited, graph[child]);
                if ((weight[child] + temp.max)*(temp.path.size()+1) > result.max) {
                    result.max = temp.max + weight[child];
                    result.path = temp.path;
                    result.path.add(0, child);
                }
            }
        }
        return result;

    }

    public class Tuple {
        int max;
        ArrayList<Integer> path;
        Tuple(int max, ArrayList<Integer> path) {
            this.max = max;
            this.path = path;
        }
    }
}