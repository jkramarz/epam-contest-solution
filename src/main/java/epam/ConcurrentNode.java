package epam;

import java.util.ArrayList;
import java.util.Collections;


public class ConcurrentNode extends Node {
	public ConcurrentNode() {
		neigh = Collections.synchronizedList(new ArrayList<Node>());
	}
}
