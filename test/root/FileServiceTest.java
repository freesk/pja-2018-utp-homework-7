package root;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class FileServiceTest {
	
	final String PATH = System.getProperty("user.dir") + "\\files";
	File root = new File(PATH);
	
	@Test
	void getAllFiles() {
		List<File> res = FileService.getAllFiles(root);
		Assert.assertEquals(res.size(), 7);
	}
	
	@Test
	void getFilesByStringContent() {
		List<File> res = FileService.getFilesByContent(root, "text-file-1");
		Assert.assertEquals("text-file-1.txt", res.get(0).getName());
	}
	
	@Test
	void getFoldersByFileContent() {
		File f = new File(System.getProperty("user.dir") + "\\files\\folder-1\\text-file-1.txt");
		List<File> res = FileService.getFilesByContent(root, f);
		Assert.assertEquals("folder-1", res.get(0).getName());
	}

}
