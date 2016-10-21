package play;

import java.io.File;

public class GenImages {

	public static void main(String[] args) {
		File dir = new File(args[0]);
		
		File[] files = dir.listFiles();
		
		
		System.out.println("<div id=\"links\">");
		
		for(int i = 0; i < files.length; i++) {
			System.out.println("    <a href=\"images/" + files[i].getName() + "\" title=\"" + files[i].getName() + "\">");
			System.out.println("        <img src=\"images/thumbnails/" + files[i].getName() + "\" alt=\"" + files[i].getName() + "\">");
			System.out.println("    </a>");
			
		}
		
		System.out.println("<div>");

	}
}
