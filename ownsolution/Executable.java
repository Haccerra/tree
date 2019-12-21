package ownsolution;

import ownsolution.recurse.Tree;
import ownsolution.exceptions.InvalidPathException;
import ownsolution.exceptions.PathToFileException;

public class Executable
{
  public static void main(String[] args)
  {
    Tree tree = null;
    try
    {
      tree = new Tree(args[0]);
      tree.createTree();
    }
    catch (InvalidPathException ip_ex)
    {
      System.out.println (ip_ex.getMessage());
    }
    catch (PathToFileException p2f_ex)
    {
      System.out.println (p2f_ex.getMessage());
    }
  }
}
