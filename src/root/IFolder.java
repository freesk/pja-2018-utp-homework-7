package root;

import java.io.File;

public class IFolder extends File implements IFile<File> {
	
	public IFolder(String path) {
		super(path);
	}

	@Override
	public boolean contains(File content) {
		return false;
	}

	@Override
	public String getName() {
		return this.getName();
	}

}
