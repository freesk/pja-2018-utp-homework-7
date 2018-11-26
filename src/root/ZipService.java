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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipService {
	
	public static List<File> getFilesByName(String path, String name) throws IOException {
		
		ZipFile zipFile = new ZipFile(path);
		
		// filter entries by name and create File instances 
		// (maybe it's better to make them String in this context?)
		return zipFile
			.stream()
			// parallel() is supposed to make ZipEntry::getName (which is partial unzipping)
			// more efficient I guess?
			.parallel()
			.map(ZipEntry::getName)
			.filter(n -> n.equals(name))
			.map(s -> new File(s))
			.collect(Collectors.toList());
	}
	
	public static List<File> getFilesByNameNonParallel(String path, String name) throws IOException {
		
		ZipFile zipFile = new ZipFile(path);
		
		// filter entries by name and create File instances 
		// (maybe it's better to make them String in this context?)
		return zipFile
			.stream()
			.map(ZipEntry::getName)
			.filter(n -> n.equals(name))
			.map(s -> new File(s))
			.collect(Collectors.toList());
	}
	
	private static boolean contains(ZipFile zipFile, ZipEntry zipEntry, String needle) {
	    try {
	    	InputStream inputStream = zipFile.getInputStream(zipEntry);
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
		ZipFile zipFile = new ZipFile(path);

	    return zipFile.stream()
	    		.parallel()
	    		.filter(ze -> !ze.isDirectory())
	            // not sure what happens here
	            .filter(ze -> contains(zipFile, ze, content))
				.map(s -> new File(path + "\\" + s))
	            .collect(Collectors.toList());
	}
	
	public static List<File> getFilesByContentNonParallel(String path, String content) throws IOException {
		ZipFile zipFile = new ZipFile(path);

	    return zipFile.stream()
	    		.filter(ze -> !ze.isDirectory())
	            // not sure what happens here
	            .filter(ze -> contains(zipFile, ze, content))
				.map(s -> new File(path + "\\" + s))
	            .collect(Collectors.toList());
	}
	
	// get folders by their file content
	public static List<File> getFilesByContent(String path, File file) throws IOException {
		ZipFile zipFile = new ZipFile(path);

	    return zipFile.stream()
	    		.parallel()
	    		.filter(ze -> {
	    			Path p = Paths.get(ze.getName());
	    			return file.toPath().endsWith(p);
	    		})
	    		.map(ze -> new File(path + "\\" + Paths.get(ze.getName()).getParent()))
	            .collect(Collectors.toList());
	}
	
	public static List<File> getFilesByContentNonParallel(String path, File file) throws IOException {
		ZipFile zipFile = new ZipFile(path);

	    return zipFile.stream()
	    		.filter(ze -> {
	    			Path p = Paths.get(ze.getName());
	    			return file.toPath().endsWith(p);
	    		})
	    		.map(ze -> new File(path + "\\" + Paths.get(ze.getName()).getParent()))
	            .collect(Collectors.toList());
	}
	
}
