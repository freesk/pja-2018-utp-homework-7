package root;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
	// get whatever by its name
	public static List<File> getFilesByName(File file, String name) {
		
		List<File> files = getAllFiles(file);
		
		return files.stream()
				.filter(f -> f.getName().equals(name))
				.collect(Collectors.toList());
	}
	
	// get files by their string content
	public static List<File> getFilesByContent(File file, String content) {
		
		List<File> files = getAllFiles(file);
		
		return files
				.stream()
				.parallel()
				.filter(f -> {
					if (!f.isFile()) return false;
					try {
						String res = new String(Files.readAllBytes(f.toPath()));
						return content.equals(res);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					return false;
				})
				.collect(Collectors.toList());
	}
	
	public static List<File> getFilesByContentNonParallel(File file, String content) {
		
		List<File> files = getAllFiles(file);
		
		return files
				.stream()
				.filter(f -> {
					if (!f.isFile()) return false;
					try {
						String res = new String(Files.readAllBytes(f.toPath()));
						return content.equals(res);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					return false;
				})
				.collect(Collectors.toList());
	}
	
	// get folders by their file content
	public static List<File> getFilesByContent(File file, File content) {
		
		List<File> files = getAllFiles(file);
		
		return files
				.stream()
				.filter(f -> {
					if (!f.isDirectory()) return false;
					List<File> res = new ArrayList<File>(Arrays.asList(f.listFiles()));
					return res.contains(content);
				})
				.collect(Collectors.toList());
	}
	
	public static List<File> getAllFiles(File file) {
		return new ArrayList<File>(Arrays.asList(file.listFiles()));
	}
}
