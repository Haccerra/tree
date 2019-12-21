package ownsolution.exceptions;

/* Custom defined exception to handle a path which searches for a file rather than a directory. */
public class InvalidPathException extends NullPointerException
{
  public InvalidPathException(String error_msg)
  {
    super (error_msg);   /* Let the exception class handle the information. */
  }
}
