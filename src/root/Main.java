package root;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		
		final String PATH = System.getProperty("user.dir") + "\\files";
		
		File f = new File(PATH);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		
		System.out.println(files);

	}
	

}
