package epam;

import java.util.List;

public abstract class Node {

	protected List<Node> neigh;

	public Node() {
		super();
	}

	public final void addNeigh(Node neigh) {
			this.neigh.add(neigh);
	}

}