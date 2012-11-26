package epam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BigFileLoader extends Loader {
	File file;
	
	BigFileLoader(File file){
		map = new HashMap<>();
		roots = new ArrayList<Node>();
		this.file = file;
	}
	@Override
	public List<Node> parseFiles() throws IOException, InterruptedException {
		BufferedReader r = null;			
		r = new BufferedReader(new FileReader(file));
		String line = null;
		while((line = r.readLine()) != null){
			String[] vars = line.split(":");
			for(int j = 0; j < vars.length; j++){
				vars[j] = vars[j].trim();
				if(map.get(vars[j]) == null){
					map.put(vars[j], new BigFileNode());
				}
			}
			switch(vars.length){
				case 2:
					map.get(vars[1]).addNeigh(map.get(vars[0]));
					break;
				case 1:
					roots.add(map.get(vars[0]));
					break;
				default:
					throw new RuntimeException("Should never happens!");
			}
		}
		r.close();
		return roots;
	}

}
