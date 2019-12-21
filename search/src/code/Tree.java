package search.src.code;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import search.src.code.Node;
import search.src.code.attribute.Att;
import search.src.ex.InvalidPathException;
import search.src.ex.NoFolderException;

public final class Tree extends Att
{
  private String rootfilepath;
  private ArrayList<Node> node;

  public Tree(String filepath) throws InvalidPathException
  {
    rootfilepath = filepath;
    node = new ArrayList<Node>();
    node.add (new Node(filepath));

    this.set_maxsize(0);
  }

  public void print_relationship_on_specified_lvl(int lvl)
  {
    System.out.println (node.get(lvl).root.getName());
    for (File f : node.get(lvl).children)
    {
      System.out.println ("\t" + f.getName());
    }
  }

  private void treeprint_recursively(File[] atlvlfiles, int lvl, int sublvl, boolean markmax)
  {
    if (atlvlfiles.length == lvl)
    {
      return;
    }
    else
    {
      /* Continue operation. */
    }

    for (int i = 0; i < sublvl; i++)
    {
      System.out.print ("\t");
    }

    if (atlvlfiles[lvl].isFile())
    {
      if (markmax)
      {
        if (this.get_absdest().equals(atlvlfiles[lvl].getAbsolutePath()))
        {
          System.out.print   ("\033[;1m\u001B[33m\u001B[43m[" + atlvlfiles[lvl].getName() + "]\033[0;0m");
          System.out.print   ("\033[0;1m\u001B[33m\u001B[43m[" + atlvlfiles[lvl].length() + "]\033[0;0m");
          System.out.println ("");
        }
        else
        {
          /* File is not the one with maximum size. */
          System.out.print   ("[" + atlvlfiles[lvl].getName() + "]");
          System.out.println ("[" + atlvlfiles[lvl].length() + "]");
        }
      }
      else
      {
        System.out.print   ("\033[0;1m\u001B[33m\u001B[43m[" + atlvlfiles[lvl].getName() + "]\033[0;0m");
        System.out.print   ("\033[0;1m\u001B[33m\u001B[43m[" + atlvlfiles[lvl].length() + "]\033[0;0m");
        System.out.println ("");
      }
    }
    else if (atlvlfiles[lvl].isDirectory())
    {
      if (markmax)
      {
        if (this.get_absdest().equals(atlvlfiles[lvl].getAbsolutePath()))
        {
          System.out.print   ("\033[;1m\u001B[33m\u001B[41m[" + atlvlfiles[lvl].getName() + "]\033[0;0m");
          System.out.print   ("\033[0;1m\u001B[33m\u001B[41m["  + foldersize(atlvlfiles[lvl]) + "]\033[0;0m");
          System.out.println ("");
        }
        else
        {
          /* Folder is not the one with maximum size. */
          System.out.print   ("[" + atlvlfiles[lvl].getName() + "]");
          System.out.println ("["  + foldersize(atlvlfiles[lvl]) + "]");
        }
      }
      else
      {
        System.out.print   ("\033[0;1m\u001B[33m\u001B[41m[" + atlvlfiles[lvl].getName() + "]\033[0;0m");
        System.out.print   ("\033[0;1m\u001B[33m\u001B[41m["  + foldersize(atlvlfiles[lvl]) + "]\033[0;0m");
        System.out.println ("");
      }

      treeprint_recursively(atlvlfiles[lvl].listFiles(), 0, sublvl+1, markmax);
    }
    else
    {
      /* Impossible entry. */
    }

    treeprint_recursively(atlvlfiles, ++lvl, sublvl, markmax);
  }
  public void treeprint(boolean wholetree, boolean markmax)
  {
    if (wholetree)
    {
      treeprint_recursively(node.get(0).children, 0, 0, markmax);
    }
    else
    {
      treeprint_recursively(this.get_rootinroot().listFiles(), 0, 0, markmax);
    }
  }

  private long foldersize(File folder)
  {
    long length = 0;
    File[] allfiles = folder.listFiles();

    for (int i = 0; i < allfiles.length; i++)
    {
        if (allfiles[i].isFile())
        {
            length += allfiles[i].length();
        }
        else
        {
            length += foldersize(allfiles[i]);
        }
    }
    return length;
  }

  private void find_max_file_size(File[] lvlspecfiles, int onlvl)
  {
    if (onlvl == lvlspecfiles.length)
    {
      return;
    }
    else
    {
      /* Continue operation. */
    }

    if (lvlspecfiles[onlvl].isFile())
    {
      if (lvlspecfiles[onlvl].length() > this.get_maxsize())
      {
        this.set_maxsize(lvlspecfiles[onlvl].length());
        this.set_maxname(lvlspecfiles[onlvl].getName());
        this.set_absdest(lvlspecfiles[onlvl].getAbsolutePath());
      }
      else
      {
        /* Keep the saved value as it is currently the biggest one. */
      }
    }
    else if (lvlspecfiles[onlvl].isDirectory())
    {
      /* Recurse into depths to find the biggest file. */

      File[] onlvlfiles = lvlspecfiles[onlvl].listFiles();
      find_max_file_size(onlvlfiles, 0);
    }
    else
    {
      /* Unreachable condition. */
    }

    find_max_file_size(lvlspecfiles, ++onlvl);
  }
  private void find_max_folder_size(File[] lvlspecfiles, int onlvl)
  {
    if (onlvl == lvlspecfiles.length)
    {
      return;
    }
    else
    {
      /* Continue operation. */
    }

    if (lvlspecfiles[onlvl].isFile())
    {
      /* Does not matter in case of folder search. */
    }
    else if (lvlspecfiles[onlvl].isDirectory())
    {
      long size = foldersize(lvlspecfiles[onlvl]);

      if (size > this.get_maxsize())
      {
        this.set_maxsize(size);
        this.set_maxname(lvlspecfiles[onlvl].getName());
        this.set_absdest(lvlspecfiles[onlvl].getAbsolutePath());
      }
      else
      {
        /* Keep the old maximum size value. */
      }

      File[] onlvlfiles = lvlspecfiles[onlvl].listFiles();
      find_max_folder_size(onlvlfiles, 0);
    }
    else
    {
      /* Unreachable condition. */
    }

    find_max_folder_size(lvlspecfiles, ++onlvl);
  }

  private void populateTree(File[] rootchildren, int lvl, int sublvl)
  {
    if (rootchildren.length == lvl)
    {
      return;
    }

    if (rootchildren[lvl].isFile())
    {
      /* Do not do anything. */
    }
    else if (rootchildren[lvl].isDirectory())
    {
      File   newgenfolder   = rootchildren[lvl];
      File[] newgenchildren = rootchildren[lvl].listFiles();

      node.add (new Node(newgenfolder, newgenchildren));

      populateTree(newgenchildren, 0, sublvl+1);
    }

    populateTree(rootchildren, ++lvl, sublvl);
  }

  public void establish()
  {
    populateTree(node.get(0).children, 0, 0);
  }

  public void cutfrom(String maxtype, String dirname) throws NoFolderException, IllegalArgumentException, NullPointerException
  {
    if (!maxtype.equals("folder") && !maxtype.equals("directory") && !maxtype.equals("file"))
    {
      throw new IllegalArgumentException ("The specified argument " + maxtype + " is not allowed.");
    }
    else
    {
      /* Do nothing. Later in this function there is decision making depending on this argument. */
    }

    File newroot;

    if (!dirname.equals("."))
    {
      ArrayList<Integer> positions = new ArrayList<Integer>();
      File newfile = new File (dirname);
      for (int i = 0; i < node.size(); i++)
      {
        for (File file : node.get(i).children)
        {
          if (file.getName().equals(newfile.getName()) && file.isDirectory())
          {
            positions.add (new Integer(i));
          }
        }
      }

      if (0 == positions.size())
      {
        throw new NoFolderException ("The specified directory \033[1;m" + dirname + "\033[0;m was not found on the root path.");
      }
      else if (1 < positions.size())
      {
        /* Folder found in more than one root. */
        System.out.println ("The specified directory \033[1;m" + dirname + "\033[0;m was found in ");
        for (int i = 0; i < positions.size(); i++)
        {
          System.out.println ("\t(" + i + ") " + node.get(Integer.valueOf (positions.get (i))).root);
        }
        System.out.println ("Which one to use?");

        int user_choice;
        while (true)
        {
          System.out.print (">> ");
          Scanner input = new Scanner (System.in);

          try
          {
            user_choice = input.nextInt();

            if (user_choice < 0 || user_choice > positions.size())
            {
              System.out.println ("Err::Inappropriate choice.");
            }
            else
            {
              break;
            }
          }
          catch (InputMismatchException im_ex)
          {
            /* Do nothing. */
          }
        }
        if (dirname.contains(""+"/") && !dirname.contains("."))
        {
          String add2path = dirname.substring(dirname.indexOf('/'));
          newroot = new File(node.get(Integer.valueOf (positions.get (user_choice))).root.getAbsolutePath() + add2path);
        }
        else
        {
          newroot = new File(node.get(Integer.valueOf (positions.get (user_choice))).root.getAbsolutePath() + "/" + dirname);
        }
      }
      else
      {
        /* There is only one folder with unique name. */
        if (dirname.contains(""+"/") && !dirname.contains("."))
        {
          String add2path = dirname.substring(dirname.indexOf('/'));
          newroot = new File(node.get(Integer.valueOf (positions.get (0))).root.getAbsolutePath() + add2path);
        }
        else
        {
          newroot = new File(node.get(Integer.valueOf (positions.get(0))).root.getAbsolutePath() + "/" + dirname);
        }
      }
    }
    else
    {
      newroot = node.get(0).root;
    }

    /* Will only execute this code if there is one folder with unique name or chosen by the user. */
    this.set_rootinroot(newroot);
    if (maxtype.equals("folder") || maxtype.equals("directory"))
    {
      System.out.println (newroot.getAbsolutePath());
      find_max_folder_size(new File(newroot.getAbsolutePath()).listFiles(), 0);
    }
    else if (maxtype.equals("file"))
    {
      find_max_file_size(new File(newroot.getAbsolutePath()).listFiles(), 0);
    }
    else
    {
      /* Already checked at the beginning of the code, so it cannot enter here. */
    }
  }

}
