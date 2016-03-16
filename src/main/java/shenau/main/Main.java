package shenau.main;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		String url = System.getProperty("user.dir") + "\\Box";
		url = url.replace("\\", "/");
		
		File folder = new File(url);
		File[] listOfFiles = folder.listFiles();

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		for (File file : listOfFiles) {
			executor.submit(new Task(file, url));
		}
		
		executor.shutdown();
		while(!executor.isTerminated());
		logger.info("Mission completed!");	
	}

}
