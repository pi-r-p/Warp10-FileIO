package fr.couincouin;

import io.warp10.WarpConfig;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class FILEDELETE extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  private File rootPath = null;

  public FILEDELETE(String name) {
    super(name);
    Properties props = WarpConfig.getProperties();

    if (props.containsKey(FileExtensionHelper.FILE_ROOTPATH)) {
      this.rootPath = new File(props.getProperty(FileExtensionHelper.FILE_ROOTPATH));
    }

  }

  public Object apply(WarpScriptStack stack) throws WarpScriptException {

    if (null == rootPath) {
      throw new WarpScriptException("The root path configuration is missing, set it with " + FileExtensionHelper.FILE_ROOTPATH);
    }

    Object pathobj = stack.pop();

    if (!(pathobj instanceof String)) {
      throw new WarpScriptException("expect a String for file path on top of the stack.");
    }
    String filePath = (String) pathobj;
    FileExtensionHelper helper = new FileExtensionHelper();
    helper.ForbiddenPathDetection(filePath);
    File file = new File(rootPath, filePath);
    try {

      Files.delete(file.toPath());

    } catch (IOException e) {
      throw new WarpScriptException("Cannot delete file " + file.toString());
    }

    return stack;
  }
}
