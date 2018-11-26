package root;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
class JarServiceTest {
	
	final String PATH = System.getProperty("user.dir") + "\\files";
	File root = new File(PATH + "\\archive-1.jar");

	@Test
	void getFilesByName() throws IOException {
		List<File> res = ZipService.getFilesByName(root.getPath(), "text-file-1.txt");
		Assert.assertEquals(1, res.size());
	}

	@Test
	void getFilesByStringContent() throws IOException {
		List<File> res = ZipService.getFilesByContent(root.getPath(), "text-file-1");	
//		System.out.println(res);
		Assert.assertEquals(2, res.size());
	}
	
	
	@Test
	void getFoldersByFileContent() throws IOException {
		File f = new File(System.getProperty("user.dir") + "\\files\\archive-1.jar\\folder-1\\text-file-1.txt");
		List<File> res = ZipService.getFilesByContent(root.getPath(), f);
		Assert.assertEquals(Paths.get(f.getPath()).getParent().toString(), res.get(0).getPath());
	}
	
}
