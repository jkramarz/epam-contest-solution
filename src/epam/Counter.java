package epam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
public class Counter {
	List<Node> roots;
	Counter(List<Node> roots){
		this.roots = roots;
	}
	
	Node computeMax() throws InterruptedException{
		List<Integer> result = new ArrayList<>(roots.size());
		for(int i = 0; i < roots.size(); i++){
			result.add(i, computeNode(roots.get(i)));
		}
		return roots.get(result.indexOf(Collections.max(result)));
	}

	static class VisitorThread extends Thread{
		int result;
		CountDownLatch latch;
		Node root;
		VisitorThread(CountDownLatch latch, Node root){
			this.latch = latch;
			this.root = root;
		}
		
		@Override
		public void run() {
			try {
				result = computeNode(root);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.getLogger("Counter").info("Bubu ;-(");
			} finally {
				latch.countDown();
			}
		}
		
		private int computeNode(Node node) throws InterruptedException {
			int[] results = new int[node.neigh.size()];
			for(int i = 0; i < node.neigh.size(); i++){
				results[i] =  computeNode(node.neigh.get(i));
			}
			return node.neigh.size() + sumArray(results);
		}
		
		private int sumArray(int[] a){
			int result = 0;
			for(int i = 0; i < a.length ; ++i)
			{
				result += a[i];
			}
			return result;
		}
		
	}
	
	private int computeNode(Node node) throws InterruptedException {
		VisitorThread[] results = new VisitorThread[node.neigh.size()];
		CountDownLatch latch = new CountDownLatch(node.neigh.size());
		for(int i = 0; i < node.neigh.size(); i++){
			results[i] = new VisitorThread(latch, node.neigh.get(i));
			results[i].start();
		}
		latch.await();
		return sumArray(results);
	}
	
	private int sumArray(VisitorThread[] a){
		int result = 0;
		for(int i = 0; i < a.length ; ++i)
		{
			result += a[i].result;
		}
		return result;
	}
	
}
