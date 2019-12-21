package search.src.code.attribute;

import java.io.File;

public abstract class Att
{
  private long   maxsize;
  private String maxname;
  private String absdest;
  private File   rootinroot;

  public void set_maxsize(long maxsize)
  {
    this.maxsize = maxsize;
  }
  public void set_maxname(String maxname)
  {
    this.maxname = maxname;
  }
  public void set_absdest(String absdest)
  {
    this.absdest = absdest;
  }
  public void set_rootinroot(File rootinroot)
  {
    this.rootinroot = rootinroot;
  }

  public long get_maxsize()
  {
    return maxsize;
  }
  public String get_maxname()
  {
    return maxname;
  }
  public String get_absdest()
  {
    return absdest;
  }
  public File get_rootinroot()
  {
    return rootinroot;
  }
}
