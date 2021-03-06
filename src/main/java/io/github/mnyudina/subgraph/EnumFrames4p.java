package io.github.mnyudina.subgraph;

import edu.uci.ics.jung.graph.Graph;
import io.github.mnyudina.util.MySparseGraph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * This is parallel version of 4-size directed subgraphs counter which uses
 * random frame sampling algorithm.
 *
 * @author Gleepa
 */

public class EnumFrames4p<V, E> extends AbstractExecutor {
	private MySparseGraph<V, E> graph;

	public final double[] motifsScobka = new double[218];
	public final double[] motifsLapka = new double[218];

	/**
	 * 
	 * @param v1
	 */
	public void searchLapka(V v1) {

		List<V> nei = new LinkedList<V>(graph.getNeighbors(v1));
		// if (nei.size() < 3)
		// return;
		for (int a = 0; a < nei.size() - 2; a++)
			for (int b = a + 1; b < nei.size() - 1; b++)
				for (int c = b + 1; c < nei.size(); c++) {
					V[] vert = (V[]) new Object[4];
					vert[0] = v1;
					vert[1] = nei.get(a);
					vert[2] = nei.get(b);
					vert[3] = nei.get(c);
					int code = 0;
					for (int i = 0; i < vert.length - 1; i++) {
						for (int j = i + 1; j < vert.length; j++) {
							E o1 = graph.findEdge(vert[i], vert[j]);
							if (o1 != null)
								code |= arr_idx4[4 * i + j];
							E o2 = graph.findEdge(vert[j], vert[i]);
							if (o2 != null)
								code |= arr_idx4[4 * j + i];
						}
					}
					int x = arrcode4[code];
					synchronized (this) { // object to synchronize motifsLapka
						motifsLapka[x] = motifsLapka[x] + 1;
					}

				}
		synchronized (this.getClass()) { // another object to synchronize iter
			iter = iter + 2;
		}

	}

	/**
	 * 
	 * @param edge
	 */
	public void searchScobka(E edge) {
		V v1 = graph.getEndpoints(edge).getFirst();
		V v2 = graph.getEndpoints(edge).getSecond();
		List<V> successors1 = new LinkedList<V>(graph.getNeighbors(v1));
		List<V> successors2 = new LinkedList<V>(graph.getNeighbors(v2));
		successors1.remove(v2);
		successors2.remove(v1);

		for (V v3 : successors1) {
			for (V v4 : successors2) {
				if (v3 != v4) {
					V[] vert = (V[]) new Object[4];
					vert[0] = v1;
					vert[1] = v2;
					vert[2] = v3;
					vert[3] = v4;
					int code = 0;
					for (int i = 0; i < vert.length - 1; i++) {
						for (int j = i + 1; j < vert.length; j++) {
							E o1 = graph.findEdge(vert[i], vert[j]);
							if (o1 != null)
								code |= arr_idx4[4 * i + j];
							E o2 = graph.findEdge(vert[j], vert[i]);
							if (o2 != null)
								code |= arr_idx4[4 * j + i];
						}
					}
					if (code > 0) {
						int x = arrcode4[code];
						synchronized (graph) { // object to synchronize motifsScobka
							motifsScobka[x] = motifsScobka[x] + 1;
						}
					}
				}
			}
		}
		synchronized (this.getClass()) { // another object to synchronize iter
			iter = iter + 2;
		}

	}

	/**
	 * Constructs and initializes the class.
	 *
	 * @param g the graph
	 * @author Gleepa
	 */
	public EnumFrames4p(Graph<V, E> g) {
		graph = (MySparseGraph) g;
		numberOfRuns = (graph.getVertexCount() + graph.getEdgeCount());
		iter = 0;
		motifs = new Double[218];
		sigmas = new Double[218];
		counts = new Double[218];
	}

	/**
	 *
	 * @author Gleepa
	 */
	public void execute() {
//////////////////// Prepare Branch frames  ////////////////////////////////////
		Collection<V> vertices = graph.getVertices();
//////////////////// Prepare Path Frames  ////////////////////////////////////
		Collection<E> edges = graph.getEdges();

////////////////////  Experiments Making ////////////////////////////////////
		vertices.parallelStream().forEach(this::searchLapka);
		edges.parallelStream().forEach(this::searchScobka);

//////////////////// Results Processing ////////////////////////////////////
		for (int i = 0; i < motifs.length; i++) {
			if (motifsScobka[i] > 0)
				motifs[i] = motifsScobka[i] / massKoefPath[i];
			if (motifsLapka[i] > 0)
				motifs[i] = motifsLapka[i] / massKoefBranch[i];
			double dif = Math.abs(motifsLapka[i] / massKoefPath[i] - motifsScobka[i] / massKoefBranch[i]);
			sigmas[i] = 0.;
			if (motifs[i] != null)
				counts[i] = motifs[i];
			else
				counts[i] = 0.;
		}

//////////////////////////////           END       /////////////////////////////////////////////
	}

}