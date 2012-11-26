package epam;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class LoaderTest {

	@Test
	public void test() throws IOException, InterruptedException {
		Loader loader = new ConcurrentLoader(new File[]{ new File("examples/test.txt") });
		assertNotNull(loader.parseFiles());
	}

}
