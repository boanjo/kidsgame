package play;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class GenImagesByDate {

	class MetaFile {
		long timestamp = 0;
		String date = null;
		File file = null;
	}
	Comparator<MetaFile> comparator = new Comparator<MetaFile>() {
	    @Override
	    public int compare(MetaFile c1, MetaFile c2) {
	    	if(c2.timestamp < c1.timestamp) return 1;
	    	else return -1;
	    }
	};
	
	public GenImagesByDate(String[] args) {
		File dir = new File(args[0]);

		File[] files = dir.listFiles();
		try {
		FileWriter out = new FileWriter(new File("c:\\users\\epkboan\\Downloads\\pics.html"));
		LinkedList<MetaFile> list = new LinkedList<MetaFile>();

		out.append("<div id=\"links\">\n");

		for(int i = 0; i < files.length; i++) {
			try {
				Metadata metadata = ImageMetadataReader.readMetadata(files[i]);
		  //  	System.out.println("NEWWWWWW");

				for (Directory directory : metadata.getDirectories()) {
				    for (Tag tag : directory.getTags()) {
				    	
//				        System.out.format("[%s] - %s = %s",
//			            directory.getName(), tag.getTagName(), tag.getDescription());
//				    	System.out.println();
				    	if(tag.getTagName().equals("Date/Time Original")) {
//				        System.out.format("[%s] - %s = %s",
//				            directory.getName(), tag.getTagName(), tag.getDescription());
				    		//System.out.println(tag.getDescription());
				    		
				    		MetaFile mf = new MetaFile();
				    		mf.date = tag.getDescription();
				    		
				    		try {
				    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss"); 
				    		Date d = formatter.parse(mf.date);
				    		long timestamp = d.getTime();
				    		mf.timestamp = timestamp;
				    		} catch (Exception e) {
				    			System.err.println(mf.date);
				    		}
				    		mf.file = files[i];
				    		list.add(mf);
				    	}
				    }
				    if (directory.hasErrors()) {
				        for (String error : directory.getErrors()) {
				            System.err.format("ERROR: %s", error);
				        }
				    }
				}
				
				
				} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		Collections.sort(list, comparator);
		
		String prevDate = "undef";
		for(int i = 0; i < list.size(); i++)  {
			
			MetaFile mf = list.get(i);
			
			if(!mf.date.startsWith(prevDate)) {
				prevDate = mf.date.substring(0, mf.date.indexOf(" "));
				out.append("    <h2>" + prevDate.replace(":", "-") + "</h2>\n");
			}
			out.append("    <a href=\"images/" + mf.file.getName() + "\" title=\"" + mf.file.getName() + "\">\n");
			out.append("        <img src=\"images/thumbnails/" + mf.file.getName() + "\" alt=\"" + mf.file.getName() + "\">\n");
			out.append("    </a>\n");

		}


		out.append("<div>\n");
		out.flush();
		out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new GenImagesByDate(args);

	}
}
