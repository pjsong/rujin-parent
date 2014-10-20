package ruking.utils;

import java.io.File;
import java.io.FileFilter;

public class FileFilterT implements FileFilter
{
  private final String[] okFileExtensions = 
    new String[] {"html"};

  public boolean accept(File file)
  {
    for (String extension : okFileExtensions)
    {
      if (file.getName().toLowerCase().endsWith(extension))
      {
        return true;
      }
    }
    return false;
  }
}
