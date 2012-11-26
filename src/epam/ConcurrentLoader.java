package epam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentLoader extends Loader {
	
	File[] files;
	CountDownLatch latch;
	
	ConcurrentLoader(File[] pliki){
		map = new ConcurrentHashMap<>();
		roots = Collections.synchronizedList(new ArrayList<Node>());
		this.files = pliki;
		latch = new CountDownLatch(files.length);
	}	
	
	class FilereaderThread extends Thread{
		File file;
		
		FilereaderThread(File file){
			this.file = file;
		}
		
		@Override
		public void run() {
			BufferedReader r = null;			
			try {
			r = new BufferedReader(new FileReader(file));
			String line = null;
			//Integer i = 0;
			while((line = r.readLine()) != null){
				//System.err.println(i++);
				String[] vars = line.split(":");
				for(int j = 0; j < vars.length; j++){
					vars[j] = vars[j].trim();
					if(map.get(vars[j]) == null){
						map.put(vars[j], new ConcurrentNode());
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					r.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					latch.countDown();
				}
			}
			
			

		}
		
	}
	
	/* (non-Javadoc)
	 * @see epam.Loader#parseFiles()
	 */
	@Override
	public List<Node> parseFiles() throws IOException, InterruptedException{
		for(File file: files){
			new FilereaderThread(file).start();
		}
		latch.await();
		return roots;
	}
	
}
