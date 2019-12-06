import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph {

	private class Vertex {
		HashMap<String, Integer> ngbr = new HashMap<String, Integer>();
	}

	HashMap<String, Vertex> vtces = new HashMap<String, Graph.Vertex>();

	public int numVertex() {
		return vtces.size();
	}

	public boolean containsVertex(String vname) {
		return vtces.containsKey(vname);
	}

	public void addVertex(String vname) {

		if (this.containsVertex(vname)) {
			return;
		}
		vtces.put(vname, new Vertex());

	}

	public void removeVertex(String vname) {

		if (this.containsVertex(vname)) {
			Vertex vtx = vtces.get(vname);

			for (String key : vtx.ngbr.keySet()) {
				Vertex keyvtx = vtces.get(key);
				keyvtx.ngbr.remove(vname);

			}
			vtces.remove(vname);
		}
	}

	public int numEdges() {
		int edges = 0;
		for (String key : vtces.keySet()) {
			edges += vtces.get(key).ngbr.size();
		}
		return edges / 2;
	}

	public boolean containsEdge(String vname1, String vname2) {

		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);
		if (vtx1 == null || vtx2 == null || !vtx1.ngbr.containsKey(vname2)) {
			return false;
		}
		return true;
	}

	public void addEdge(String vname1, String vname2, int cost) {

		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);
		if (vtx1 == null || vtx2 == null || vtx1.ngbr.containsKey(vname2)) {
			return;
		}

		vtx1.ngbr.put(vname2, cost);
		vtx2.ngbr.put(vname1, cost);
	}

	public void removeEdge(String vname1, String vname2) {

		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);
		if (vtx1 == null || vtx2 == null || !vtx1.ngbr.containsKey(vname2)) {
			return;
		}
		vtx1.ngbr.remove(vname2);
		vtx2.ngbr.remove(vname1);
	}

	public void display() {
		System.out.println("----------" + "\n");
		for (String key : vtces.keySet())
			System.out.println(key + "->" + vtces.get(key).ngbr);
		System.out.println("----------");
	}

	@Override
	public String toString() {
		String str = "----------" + "\n";
		for (String key : vtces.keySet())
			str += key + "->" + vtces.get(key).ngbr + "\n";
		str += "----------";
		return str;
	}

	public boolean hasPath(String vname1, String vname2) {
		return hasPath(vname1, vname2, new HashMap<>());
	}

	private boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> map) {
		if (containsEdge(vname1, vname2)) {
			return true;
		}
		map.put(vname1, true);
		for (String key : vtces.get(vname1).ngbr.keySet()) {
			if (!map.containsKey(key) && hasPath(key, vname2, map)) {
				return true;
			}
		}
		return false;
	}

	public void PrintPath(String src, String dst) {
		PrintPath(src, dst, new HashMap<>(), "");
	}

	private void PrintPath(String src, String dst, HashMap<String, Boolean> map, String ans) {
		if (containsEdge(src, dst)) {
			System.out.println(ans + dst);
			return;
		}
		map.put(src, true);
		ans += src;
		for (String key : vtces.get(src).ngbr.keySet()) {
			if (!map.containsKey(key)) {
				PrintPath(key, dst, map, ans);
			}
		}
	}

	public void PrintAllPath(String src, String dst) {
		PrintAllPath(src, dst, new HashMap<>(), "");
	}

	private void PrintAllPath(String src, String dst, HashMap<String, Boolean> map, String ans) {
		if (src.equals(dst)) {
			System.out.println(ans + dst);
			return;
		}
		map.put(src, true);
		ans += src;
		for (String key : vtces.get(src).ngbr.keySet()) {
			if (!map.containsKey(key)) {
				PrintAllPath(key, dst, map, ans);
			}
		}
		map.remove(src);
	}

	private class Pair {
		String vname;
		String psf;
		String color;
	}

	public boolean bfs(String src, String dst) {

		LinkedList<Pair> queue = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();
		Pair np = new Pair();

		np.vname = src;
		np.psf = src;
		queue.addLast(np);

		while (!queue.isEmpty()) {
			Pair rp = queue.removeFirst();

			// ** Ignore the second arrival
			if (processed.containsKey(rp.vname))
				continue;

			// 1.process
			processed.put(rp.vname, true);

			// 2.contains Edge
			if (containsEdge(rp.vname, dst)) {
				System.out.println(rp.psf + dst);
				return true;
			}

			// 3.nbr explore
			for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
				if (!processed.containsKey(nbr)) {
					Pair nbp = new Pair();
					nbp.vname = nbr;
					nbp.psf = rp.psf + nbr;
					queue.addLast(nbp);
				}
			}
		}
		return false;
	}

	public void bft() {

		LinkedList<Pair> queue = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();

		for (String key : vtces.keySet()) {
			if (processed.containsKey(key))
				continue;

			Pair np = new Pair();
			np.vname = key;
			np.psf = key;
			queue.addLast(np);

			while (!queue.isEmpty()) {
				Pair rp = queue.removeFirst();

				// ** Ignore the second arrival
				if (processed.containsKey(rp.vname))
					continue;

				// 1.process
				processed.put(rp.vname, true);

				// 2.contains Edge
				System.out.println(rp.vname + " via " + rp.psf);

				// 3.nbr explore
				for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
					if (!processed.containsKey(nbr)) {
						Pair nbp = new Pair();
						nbp.vname = nbr;
						nbp.psf = rp.psf + nbr;
						queue.addLast(nbp);
					}
				}
			}

		}
	}

	public boolean dfs(String src, String dst) {

		LinkedList<Pair> stack = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();
		Pair np = new Pair();

		np.vname = src;
		np.psf = src;
		stack.addFirst(np);

		while (!stack.isEmpty()) {
			Pair rp = stack.removeFirst();

			// ** Ignore the second arrival
			if (processed.containsKey(rp.vname))
				continue;

			// 1.process
			processed.put(rp.vname, true);

			// 2.contains Edge
			if (containsEdge(rp.vname, dst)) {
				System.out.println(rp.psf + dst);
				return true;
			}

			// 3.nbr explore
			for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
				if (!processed.containsKey(nbr)) {
					Pair nbp = new Pair();
					nbp.vname = nbr;
					nbp.psf = rp.psf + nbr;
					stack.addFirst(nbp);
				}
			}
		}
		return false;
	}

	public void dft() {

		LinkedList<Pair> stack = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();

		for (String key : vtces.keySet()) {
			if (processed.containsKey(key))
				continue;

			Pair np = new Pair();
			np.vname = key;
			np.psf = key;
			stack.addFirst(np);

			while (!stack.isEmpty()) {
				Pair rp = stack.removeFirst();

				// ** Ignore the second arrival
				if (processed.containsKey(rp.vname))
					continue;

				// 1.process
				processed.put(rp.vname, true);

				// 2.contains Edge
				System.out.println(rp.vname + " via " + rp.psf);

				// 3.nbr explore
				for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
					if (!processed.containsKey(nbr)) {
						Pair nbp = new Pair();
						nbp.vname = nbr;
						nbp.psf = rp.psf + nbr;
						stack.addFirst(nbp);
					}
				}
			}

		}
	}

	public boolean isCycle() {
		LinkedList<Pair> queue = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();

		for (String key : vtces.keySet()) {
			if (processed.containsKey(key))
				continue;

			Pair np = new Pair();
			np.vname = key;
			np.psf = key;
			queue.addLast(np);

			while (!queue.isEmpty()) {
				Pair rp = queue.removeFirst();

				// ** Ignore the second arrival
				if (processed.containsKey(rp.vname))
					return true;

				// 1.process
				processed.put(rp.vname, true);

				// 3.nbr explore
				for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
					if (!processed.containsKey(nbr)) {
						Pair nbp = new Pair();
						nbp.vname = nbr;
						nbp.psf = rp.psf + nbr;
						queue.addLast(nbp);
					}
				}
			}

		}
		return false;
	}

	public boolean isConnected() {
		LinkedList<Pair> queue = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();
		int count = 0;

		for (String key : vtces.keySet()) {
			if (processed.containsKey(key))
				continue;

			count++;
			Pair np = new Pair();
			np.vname = key;
			np.psf = key;
			queue.addLast(np);

			while (!queue.isEmpty()) {
				Pair rp = queue.removeFirst();

				// ** Ignore the second arrival
				if (processed.containsKey(rp.vname))
					continue;

				// 1.process
				processed.put(rp.vname, true);

				// 3.nbr explore
				for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
					if (!processed.containsKey(nbr)) {
						Pair nbp = new Pair();
						nbp.vname = nbr;
						nbp.psf = rp.psf + nbr;
						queue.addLast(nbp);
					}
				}
			}

		}
		if (count > 1) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isTree() {
		if (isConnected() && !isCycle()) {
			return true;
		} else {
			return false;
		}

	}

	public ArrayList<ArrayList<String>> connectedComp() {
		LinkedList<Pair> queue = new LinkedList<Graph.Pair>();
		HashMap<String, Boolean> processed = new HashMap<String, Boolean>();
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();

		for (String key : vtces.keySet()) {
			if (processed.containsKey(key))
				continue;

			ArrayList<String> sarr = new ArrayList<String>();
			Pair np = new Pair();
			np.vname = key;
			np.psf = key;
			queue.addLast(np);

			while (!queue.isEmpty()) {
				Pair rp = queue.removeFirst();

				// ** Ignore the second arrival
				if (processed.containsKey(rp.vname))
					continue;

				// 1.process
				processed.put(rp.vname, true);

				// 2.contains Edge
				sarr.add(rp.vname);

				// 3.nbr explore
				for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
					if (!processed.containsKey(nbr)) {
						Pair nbp = new Pair();
						nbp.vname = nbr;
						nbp.psf = rp.psf + nbr;
						queue.addLast(nbp);
					}
				}
			}
			arr.add(sarr);
		}
		return arr;
	}

	public boolean isBipartite() {

		LinkedList<Pair> queue = new LinkedList<Graph.Pair>();
		HashMap<String, String> processed = new HashMap<String, String>();

		for (String key : vtces.keySet()) {
			if (processed.containsKey(key))
				continue;

			// 1. Put the first element in queue
			Pair np = new Pair();
			np.vname = key;
			np.psf = key;
			np.color = "r";
			queue.addLast(np);

			// 2. Work till queue is not empty
			while (!queue.isEmpty()) {
				Pair rp = queue.removeFirst();

				// 2.1 Ignore the second arrival
				if (processed.containsKey(rp.vname)) {
					String clr1 = processed.get(rp.vname);
					String clr2 = rp.color;

					if (!clr1.equals(clr2)) {
						return false;
					}
					continue;
				}

				// 2.2 Processed
				processed.put(rp.vname, rp.color);

				// 3.Process Remaining Nbrs
				for (String nbr : vtces.get(rp.vname).ngbr.keySet()) {
					if (!processed.containsKey(nbr)) {
						Pair nbp = new Pair();
						nbp.vname = nbr;
						nbp.psf = rp.psf + nbr;
						nbp.color = rp.color == "r" ? "g" : "r";
						queue.addLast(nbp);
					}
				}
			}

		}

		return true;
	}

	private class PrimsPair implements Comparable<PrimsPair> {
		String vname;
		String acqname;
		int cost;

		@Override
		public int compareTo(PrimsPair o) {
			return o.cost - this.cost;
		}
	}

	public Graph minSpanningTree() {

		Graph mst = new Graph();

		HeapGeneric<PrimsPair> heap = new HeapGeneric<Graph.PrimsPair>();
		HashMap<String, PrimsPair> map = new HashMap<String, Graph.PrimsPair>();

		for (String key : vtces.keySet()) {
			PrimsPair np = new PrimsPair();
			np.vname = key;
			np.acqname = null;
			np.cost = Integer.MAX_VALUE;

			heap.add(np);
			map.put(key, np);
		}

		while (!heap.isEmpty()) {
			PrimsPair rp = heap.remove();
			map.remove(rp.vname);

			if (rp.acqname == null) {
				mst.addVertex(rp.vname);
			} else {
				mst.addVertex(rp.vname);
				mst.addEdge(rp.acqname, rp.vname, rp.cost);
			}

			Vertex vtx = vtces.get(rp.vname);
			for (String nbr : vtx.ngbr.keySet()) {
				if (map.containsKey(nbr)) {
					PrimsPair nb = map.get(nbr);
					int oc = nb.cost;
					int nc = vtx.ngbr.get(nbr);

					if (nc < oc) {
						nb.cost = nc;
						nb.acqname = rp.vname;
						// IMP
						heap.updatePriority(nb);
					}

				}
			}

		}

		return mst;
	}
}
