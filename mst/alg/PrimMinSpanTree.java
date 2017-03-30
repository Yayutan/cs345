package alg;

import impl.BasicHashSet;
import impl.HeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * PrimMinSpanTree
 * 
 * Implementation of Prim's algorithm for computing
 * the minimum spanning tree of a graph.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 24, 2015
 */
public class PrimMinSpanTree implements MinSpanTree {

    /**
     * Compute the minimum spanning tree of a given graph.
     * @param g The given graph
     * @return A set of the edges in the minimum spanning tree
     */
	public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
		
		VertexRecord[] records = new VertexRecord[g.numVertices()];
		for (int i = 0; i < g.numVertices(); i++)
			records[i] = new VertexRecord(i, Double.POSITIVE_INFINITY);
		PriorityQueue<VertexRecord> pq = 
				new HeapPriorityQueue<VertexRecord>(records, new VertexRecord.VRComparator());
        Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
		int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
        	parents[i] = -1;
		
        while(!pq.isEmpty()){
        	VertexRecord addFrom = pq.extractMax();
        	if(parents[addFrom.id] != -1){
        		mstEdges.add(new WeightedEdge(parents[addFrom.id], addFrom.id, g.weight(parents[addFrom.id], addFrom.id)));
        	}

        	for(int k : g.adjacents(addFrom.id)){
        		if(pq.contains(records[k])){
        			if(g.weight(addFrom.id, records[k].id) < records[k].getDistance()){
        				records[k].setDistance(g.weight(addFrom.id, records[k].id));
        				parents[records[k].id] = addFrom.id;
        				pq.increaseKey(records[k]);
        			}
        		}   		   
        	}
        }
        return mstEdges;
	}

}
