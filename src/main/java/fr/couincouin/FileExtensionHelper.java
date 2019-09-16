package fr.couincouin;

import io.warp10.script.WarpScriptException;

public class FileExtensionHelper {
  public static final String FILE_ROOTPATH = "ext.fileIO.rootPath";

  public void ForbiddenPathDetection(String path) throws WarpScriptException {
    if (path.matches(".*\\.(\\.+)[\\\\/].*")) {
      throw new WarpScriptException("Path contains forbidden reference to parent directory (../)");
    }
  }

}
