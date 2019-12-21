package ownsolution.recurse;

import java.io.File;
import ownsolution.exceptions.InvalidPathException;
import ownsolution.exceptions.PathToFileException;

public final class Tree
{
  private File   rootdir;
  private File[] filelist;

  public  String pathdir;

  public Tree(String pathdir) throws InvalidPathException, PathToFileException
  {
    /* Try to open a folder on a specified path. */
    this.pathdir = pathdir;
    try
    {
        this.rootdir = new File(this.pathdir);
    }
    catch (InvalidPathException np_ex)
    {
      throw new InvalidPathException("Err::The specified path " + this.pathdir + " does not exist.");
    }

    /* Error handling. */
    if (!this.rootdir.isDirectory())
    {
      throw new PathToFileException("Err::The specified path " + this.pathdir + " is a file, not a directory.");
    }
    else
    {
      /* The specified path exists and is a directory. */
    }

    /* Only if the path exists and the path is a directory, load all files from a directory. */
    this.filelist = this.rootdir.listFiles();
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

  private void recurse(File[] lvlspecfiles, int onlvl, int reclvl)
  {
    /* Terminate recursion on the current level. */
    if (onlvl == lvlspecfiles.length)
    {
      return;
    }

    /* Each level is idented with a level*tab spaces. */
    for (int i = 0; reclvl > i; i++)
    {
      System.out.print ("\t");
    }

    if (lvlspecfiles[onlvl].isFile())
    {
      System.out.print   ("[\033[0;1m\u001B[33m\u001B[43m" + lvlspecfiles[onlvl].getName() + "\033[0;0m]");
      System.out.print   ("[\033[0;1m\u001B[33m\u001B[43m" + lvlspecfiles[onlvl].length() + "\033[0;0m]");
      System.out.println ("");
    }
    else if (lvlspecfiles[onlvl].isDirectory())
    {
      System.out.print   ("[\033[0;1m\u001B[33m\u001B[41m" + lvlspecfiles[onlvl].getName() + "\033[0;0m]");
      System.out.print   ("[\033[0;1m\u001B[33m\u001B[41m"  + foldersize(lvlspecfiles[onlvl]) + "\033[0;0m]");
      System.out.println ("");

      File[] onlvlfiles = lvlspecfiles[onlvl].listFiles();
      recurse(onlvlfiles, 0, reclvl+1);
    }
    else
    {
      /* Unreachable condition. */
    }

    /* Root directory recursion. */
    recurse(lvlspecfiles, ++onlvl, reclvl);
  }

  public void createTree()
  {
    recurse(filelist, 0, 0);
  }

}
