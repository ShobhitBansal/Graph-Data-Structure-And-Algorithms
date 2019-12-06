public class GraphClient {

	public static void main(String[] args) {
		Graph graph = new Graph();

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addVertex("G");

		graph.addEdge("A", "B", 2);
		graph.addEdge("B", "C", 1);
		graph.addEdge("C", "D", 3);
		graph.addEdge("D", "A", 10);
		graph.addEdge("D", "E", 4);
		graph.addEdge("E", "F", 3);
		graph.addEdge("F", "G", 6);
		graph.addEdge("G", "E", 2);

		graph.display();
		System.out.println(graph);

//		System.out.println(graph.containsEdge("A", "B"));
//		System.out.println(graph.containsVertex("C"));
//		System.out.println(graph.numEdges());
//		System.out.println(graph.numVertex());
//		graph.removeEdge("D", "E");
//		graph.removeVertex("C");
//
//		System.out.println(graph);
//		System.out.println(graph.isPathPossible("A", "F"));
//		graph.possiblePath("A", "F");
//		graph.allpossiblePaths("A", "F");
//		graph.bft();
//		graph.dft();
//		System.out.println(graph.isConnected());
//		System.out.println(graph.isCycle());
//		System.out.println(graph.isTree());
//		System.out.println(graph.connectedComp());
		System.out.println(graph.minSpanningTree());
	}

}
