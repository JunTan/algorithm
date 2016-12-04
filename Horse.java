import java.io.*;
import java.util.*;
public class Horse {
	private LinkedList<Integer> graph[];
    private int weight[];
    private HashSet<Integer> team;
	Horse(int[][] adj) {
		this.graph = initializegraph(adj);
        weight = initializeweight(adj);
        team = new HashSet<Integer>();
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
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> curlongest = p.extractlongestpath(visited);
            p.team.addAll(curlongest);
            result.add(curlongest);
            while (curlongest.size() > 1) {
                for (int i = 0; i < V; i++) {
                    visited[i] = false;
                }
                curlongest = p.extractlongestpath(visited);
                p.team.addAll(curlongest);
                result.add(curlongest);
            }
            for (int i = 0; i < V; i++) {
                if (!p.team.contains(i)) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(i);
                    result.add(temp);
                }
            }

            String str = ""

            for (ArrayList<Integer> l : result) {
                for (int i : l) {
                    str = str + i + " ";
                }
                str = str.trim() + ";"
            }
            str = str.substring(0, str.length()-1) + "\n";



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
            if (visited[i] == true || team.contains(i)) {
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
            if ((visited[child] && path.contains(child)) || team.contains(child)) {
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