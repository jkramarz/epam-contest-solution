package epam;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class CounterTest {

	@Test
	public void test() throws Exception {
		
		Counter counter = new Counter(new ConcurrentLoader(new File[]{new File("examples/example-big-single-file/people-0.txt")}).parseFiles());
		assertNotNull(counter.computeMax());
	}

}
