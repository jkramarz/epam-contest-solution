package epam;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map.Entry;

public class Solution {
	public static File[] listDirectory(File inputDirectory){
		File[] inputFiles = inputDirectory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				if(arg1.equals("readme.txt")){
					return false;
				}else{
					return true;
				}
			}
		});
		return inputFiles;
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length != 1){
			throw new IllegalArgumentException("Wymagany argument: katalog z testami");
		}
		
		File inputDirectory = new File(args[0]);
		File[] inputFiles = listDirectory(inputDirectory);
		
		Loader loader = null;
		if(inputFiles.length == 1){
			loader = new BigFileLoader(inputFiles[0]);
		}else{
			loader = new ConcurrentLoader(inputFiles);
		}
		Counter counter = new Counter(loader.parseFiles());
		Node max = counter.computeMax();
		for(Entry<String, Node> entry : loader.map.entrySet() ){
			if(entry.getValue().equals(max)){
				System.out.println(entry.getKey());
				return;
			}
		}
	}

}
