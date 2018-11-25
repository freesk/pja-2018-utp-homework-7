package root;

public interface IFile <TContent> {
	boolean contains(TContent content);
	String getName();
}
