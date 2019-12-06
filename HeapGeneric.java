import java.util.ArrayList;
import java.util.HashMap;

public class HeapGeneric<T extends Comparable<T>> {
	HashMap<T, Integer> prims = new HashMap<T, Integer>(); // prims
	ArrayList<T> data = new ArrayList<>();

	public void add(T val) {
		data.add(val);
		prims.put(val, data.size() - 1); // prims
		upheapify(data.size() - 1);
	}

	private void upheapify(int ci) {
		int pi = (ci - 1) / 2;

		if (isLarger(data.get(ci), data.get(pi)) > 0) {
			swap(ci, pi);
			upheapify(pi);
		}

	}

	private void swap(int ci, int pi) {
		T ith = data.get(ci);
		T jth = data.get(pi);

		data.set(pi, ith);
		data.set(ci, jth);

		prims.put(jth, ci); // prims
		prims.put(ith, pi); // prims

	}

	public T remove() {
		swap(0, data.size() - 1);
		T temp = data.remove(data.size() - 1);
		prims.remove(temp); // prims
		downheapify(0);
		return temp;
	}

	private void downheapify(int pi) {
		int lci = 2 * pi + 1;
		int rci = 2 * pi + 2;

		int mini = pi;

		if (lci < data.size() && isLarger(data.get(lci), data.get(mini)) > 0) {
			mini = lci;
		}

		if (rci < data.size() && isLarger(data.get(rci), data.get(mini)) > 0) {
			mini = rci;
		}

		if (mini != pi) {
			swap(mini, pi);
			downheapify(mini);
		}

	}

	public int size() {
		return data.size();
	}

	public boolean isEmpty() {
		return data.size() == 0;
	}

	public void display() {
		System.out.println(data);
	}

	public T getHP() {
		return data.get(0);
	}

	private int isLarger(T t, T o) {
		return t.compareTo(o);
	}

	public void updatePriority(T t) { // prims
		int idx = prims.get(t);
		upheapify(idx);
	}

}
