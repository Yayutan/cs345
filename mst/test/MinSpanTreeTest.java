package test;

import static org.junit.Assert.*;
import impl.GraphFactory;

import org.junit.Test;

import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;
import alg.KruskalMinSpanTree;
import alg.MinSpanTree;

public abstract class MinSpanTreeTest {

	protected MinSpanTree mstAlg;
	
	protected abstract void reset();
	
	@Test
	public void tinyGraph() {
		reset();
		WeightedGraph g = GraphFactory.weightedUndirectedALGraphFromFile("tinyEWG.txt");
		for(WeightedEdge e:g.edges()){
			System.out.println("From: " + e.first + "  To: " + e.second + "  Weight: " + e.weight);
		}
        double totalWeight = 0.0;
        for (WeightedEdge e : mstAlg.minSpanTree(g)) {
        	totalWeight += e.weight;
        }
        assertEquals(1.81, totalWeight, 0.01);
        
	}
	
}
