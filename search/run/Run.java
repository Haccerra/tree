package search.run;

import search.src.code.Node;
import search.src.code.Tree;
import search.src.ex.InvalidPathException;
import search.src.ex.NoFolderException;

public final class Run
{
  public static void main (String[] arg)
  {
    try
    {
      String rootpath   = arg[0];       /**< Create a tree starting with this folder. */
      String searchtype = arg[1];       /**< Specify whether max file or folder is being searched in a  tree. */
      String searchfrom = arg[2];       /**< Specify folder in a tree from which a search should be ran. */
      String wholetree  = arg[3];       /**< Specify whether the whole tree should be printed out or not. */
      String markmax    = arg[4];       /**< Specify whether only the max file/folder should be marked. */

      try
      {
        Tree tree = new Tree(rootpath);
        tree.establish();
        try
        {
          tree.cutfrom(searchtype, searchfrom);
          tree.treeprint(Boolean.parseBoolean (wholetree), Boolean.parseBoolean (markmax));
        }
        catch (NoFolderException nf_ex)
        {
          System.out.println (nf_ex.getMessage());
        }
        catch (IllegalArgumentException ia_ex)
        {
          System.out.println (ia_ex.getMessage());
        }
        catch (NullPointerException np_ex)
        {
          System.out.println ("You managed to create a NullPointerException. \033[0;1m!!!You are a wizard!!!\033[0;0m");
        }
        /* tree.print_relationship(Integer.parseInt (arg[3])); */
      }
      catch (InvalidPathException ip_ex)
      {
        System.out.println (ip_ex.getMessage());
      }
      finally
      {
        /* Do nothing. */
      }
    }
    catch (ArrayIndexOutOfBoundsException aioob_ex)
    {
      System.out.println ("Number of specified arguments: " + aioob_ex.getMessage());
      System.out.println ("Number of arguments that must be specified: 5");
    }
    finally
    {
      /* Do nothing. */
    }

  }
}
