package fr.couincouin.fileIO;

import io.warp10.script.WarpScriptException;

public class FileExtensionHelper {
  public static final String FILE_ROOTPATH = "fr.couincouin.fileIOextension.rootPath";

  public void ForbiddenPathDetection(String path) throws WarpScriptException {
    if (path.matches(".*\\.(\\.+)[\\\\/].*")) {
      throw new WarpScriptException("Path contains forbidden reference to parent directory (../)");
    }
  }

}
