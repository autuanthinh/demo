package shenau.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class Task implements Runnable {
	private File file;
	private String url;
	final static Logger logger = Logger.getLogger(Task.class);
	
	public Task(File file, String url) {
		super();
		this.file = file;
		this.url = url;
	}
	
	@Override
	public void run() {
		if (file.isFile()) {
	    	FileInputStream fis;
			try {
				fis = new FileInputStream(file.getAbsolutePath());
				XWPFDocument document = new XWPFDocument(fis);
				List<XWPFParagraph> paragraphs = document.getParagraphs();
				String newName = "";
				for (XWPFParagraph para : paragraphs) {
					//subtract with spaces & special letters not be allowed file name
					String name = para.getText().trim().replaceAll("\\b\\s{2,}\\b", " ").replaceAll("[\\,/,:,*,?,\",<,>,|]", "");
					System.out.println(name);
					String[] sliceName = name.split(" ");
					int subLenght = 0;
					if(sliceName != null) {
						try {
							Integer.parseInt(sliceName[0]); 
						} catch(Exception e) {
							for(int i = 0;i < sliceName.length;i++) {
								try {
									Integer.parseInt(sliceName[i]);
									break;
								} catch (Exception ex) {
									subLenght += sliceName[i].length() + 1; //add 1 white space
									continue;
								}
							}
						}
					}
					
					newName += name.substring(subLenght);
					break;
				}
				fis.close();
		        File newfile = new File(url.replace("/", "\\") + "\\" + newName + ".docx");
	        	
		        if(file.renameTo(newfile)){
	        		logger.info("Rename succesful file - " + newfile.getName());
	    		}else{
	    			logger.info("Rename failed");
	    		}
		        
			} catch (FileNotFoundException e) {
				logger.error("file not found " + e);
			} catch (IOException e) {
				logger.error("Io exception " +e);
			}
	    }
	}
}
