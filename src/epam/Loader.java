package epam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Loader {

	Map<String, Node> map;
	List<Node> roots;
	
	public abstract List<Node> parseFiles() throws IOException,
			InterruptedException;

}