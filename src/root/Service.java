package root;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Service<TFile extends IFile<TContent>, TContent> {
	
	public List<TFile> getByName(String name, List<TFile> files) {
		return files.stream()
				.filter(f -> f.getName().equals(name))
				.collect(Collectors.toList());
	}
	
	public List<TFile> getByContent(TContent content, List<TFile> files) {
		return files.stream()
				.filter(f -> f.contains(content))
				.collect(Collectors.toList());
	}	
	
}
