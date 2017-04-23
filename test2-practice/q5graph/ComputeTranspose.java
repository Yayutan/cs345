package q5graph;

/**
 * ComputeTranspose
 * 
 * Placeholder class for the static method computeTranspose() which
 * compute the transpose of a given directed graph.
 * 
 * CSCI 345
 * Test 2 Practice Problem 5.
 */
public class ComputeTranspose {

    /**
     * Compute the transpose graph of the given directed graph g.
     * That is, build a new graph with the same number of
     * vertices as g but only those edges that do not exist
     * in g.
     * @param g The directed graph to compute the transpose of
     * @return The transpose of the given graph
     */
	public static AdjListGraph computeTranspose(AdjListGraph g) {
		// make the builder
		AdjListGraph.ALGBuilder builder = new AdjListGraph.ALGBuilder(g.numVertices());
		
		// ... add edges using builder.connect(u, v) ...
		for(int i = 0; i < g.numVertices(); i++){
			for(int v : g.adjacents(i)){
				builder.connect(v, i);
			}
		}
		// now that we're done, get the graph
		return builder.getGraph();                
	}
}
