package search.src.code;

import java.io.File;
import search.src.ex.InvalidPathException;

public final class Node
{
  protected File   root;
  protected File[] children;

  public Node()
  {
    /* Empty constructor allowed. Information need to be filled out manually. */
  }
  public Node(String filepath2root) throws InvalidPathException
  {
    try
    {
      root     = new File(filepath2root);
      children = root.listFiles ();
    }
    catch (NullPointerException np_ex)
    {
      throw new InvalidPathException ("Err::The directory could not be found at \033[1;m" + filepath2root + "\033[0;m location.");
    }
  }
  public Node(File root, File[] children)
  {
    this.root     = root;
    this.children = children;
  }
}
