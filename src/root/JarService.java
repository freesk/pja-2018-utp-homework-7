package root;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class JarService {
	
	public static List<File> getFilesByName(String path, String name) throws IOException {
		
		JarFile jarFile = new JarFile(path);
		
		// filter entries by name and create File instances 
		// (maybe it's better to make them String in this context?)
		return jarFile
			.stream()
			// parallel() is supposed to make ZipEntry::getName (which is partial unzipping)
			// more efficient I guess?
			.parallel()
			.map(JarEntry::getName)
			.filter(n -> n.equals(name))
			.map(s -> new File(s))
			.collect(Collectors.toList());
	}
	
	public static List<File> getFilesByNameNonParalell(String path, String name) throws IOException {
		
		JarFile jarFile = new JarFile(path);
		
		// filter entries by name and create File instances 
		// (maybe it's better to make them String in this context?)
		return jarFile
			.stream()
			.map(JarEntry::getName)
			.filter(n -> n.equals(name))
			.map(s -> new File(s))
			.collect(Collectors.toList());
	}
	
	private static boolean contains(JarFile jarFile, JarEntry zipEntry, String needle) {
	    try {
	    	InputStream inputStream = jarFile.getInputStream(zipEntry);
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    	Optional<String> found = reader.lines()
	    			.filter(l -> l.contains(needle))
	    		    .findFirst();
	    	
	    	return found.isPresent();
	    } catch (IOException e) {
	        return false;
	    }
	}
	
	// get files by their string content
	public static List<File> getFilesByContent(String path, String content) throws IOException {
		JarFile jarFile = new JarFile(path);

	    return jarFile.stream()
	    		.parallel()
	    		.filter(ze -> !ze.isDirectory())
	            .filter(ze -> contains(jarFile, ze, content))
				.map(s -> new File(path + "\\" + s))
	            .collect(Collectors.toList());
	}
	
	public static List<File> getFilesByContentNonParalell(String path, String content) throws IOException {
		JarFile jarFile = new JarFile(path);

	    return jarFile.stream()
	    		.filter(ze -> !ze.isDirectory())
	            .filter(ze -> contains(jarFile, ze, content))
				.map(s -> new File(path + "\\" + s))
	            .collect(Collectors.toList());
	}
	
	// get folders by their file content
	public static List<File> getFilesByContent(String path, File file) throws IOException {
		JarFile jarFile = new JarFile(path);

	    return jarFile.stream()
	    		.parallel()
	    		.filter(ze -> {
	    			Path p = Paths.get(ze.getName());
	    			return file.toPath().endsWith(p);
	    		})
	    		.map(ze -> new File(path + "\\" + Paths.get(ze.getName()).getParent()))
	            .collect(Collectors.toList());
	}
	
	public static List<File> getFilesByContentNonParalell(String path, File file) throws IOException {
		JarFile jarFile = new JarFile(path);

	    return jarFile.stream()
	    		.filter(ze -> {
	    			Path p = Paths.get(ze.getName());
	    			return file.toPath().endsWith(p);
	    		})
	    		.map(ze -> new File(path + "\\" + Paths.get(ze.getName()).getParent()))
	            .collect(Collectors.toList());
	}
	
}
