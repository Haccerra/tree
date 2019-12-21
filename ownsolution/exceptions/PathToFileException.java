package ownsolution.exceptions;

/* Custom defined exception to handle a path which searches for a file rather than a directory. */
public class PathToFileException extends NullPointerException
{
  public PathToFileException(String error_msg)
  {
    super (error_msg);   /* Let the exception class handle the information. */
  }
}
