package epam;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map.Entry;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test() throws Exception {
		ConcurrentLoader loader = new ConcurrentLoader(new File[]{ new File("examples/test.txt") });
		Counter counter = new Counter(loader.parseFiles());
		Node max = counter.computeMax();
		for(Entry<String, Node> entry : loader.map.entrySet() ){
			if(entry.getValue().equals(max)){
				assertEquals(entry.getKey(), "d");
				return;
			}
		}
	}
	@Test
	public void test2() throws Exception {
		Loader loader = new ConcurrentLoader(new File[]{ new File("examples/example-small/people-0.txt") } ) ;
		Counter counter = new Counter(loader.parseFiles());
		Node max = counter.computeMax();
		for(Entry<String, Node> entry : loader.map.entrySet() ){
			if(entry.getValue().equals(max)){
				assertEquals(entry.getKey(), "Stanislaw0 Ostrega0");
				return;
			}
		}
	}
	
	
	@Test
	public void test3() throws Exception {
		Loader loader = new BigFileLoader(new File("examples/example-big-single-file/people-0.txt"));
		Counter counter = new Counter(loader.parseFiles());
		Node max = counter.computeMax();
		for(Entry<String, Node> entry : loader.map.entrySet() ){
			if(entry.getValue().equals(max)){
				assertEquals(entry.getKey(), "Krzysztof0 Dec0");
				return;
			}
		}
	}
	
	@Test
	public void test4() throws Exception {
		File[] files = Solution.listDirectory(new File("examples/example-big-multiple-files"));
		ConcurrentLoader loader = new ConcurrentLoader(files);
		Counter counter = new Counter(loader.parseFiles());
		Node max = counter.computeMax();
		for(Entry<String, Node> entry : loader.map.entrySet() ){
			if(entry.getValue().equals(max)){
				assertEquals(entry.getKey(), "Kamil0 Pietras0");
				return;
			}
		}
	}

}
