package fr.couincouin.fileIO;

import fr.couincouin.FileExtensionHelper;
import io.warp10.WarpConfig;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class FILEWRITE extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  private File rootPath = null;
  private boolean appendMode;

  public FILEWRITE(String name, boolean appendMode) {
    super(name);
    this.appendMode = appendMode;
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

    Object fileContentObj = stack.pop();

    if (!(fileContentObj instanceof byte[])) {
      throw new WarpScriptException("expects BYTES as file content");
    }

    FileExtensionHelper helper = new FileExtensionHelper();
    helper.ForbiddenPathDetection(filePath);
    File file = new File(rootPath, filePath);
    try {
      if (appendMode) {
        Files.write(file.toPath(), (byte[]) fileContentObj, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      } else {
        Files.write(file.toPath(), (byte[]) fileContentObj, StandardOpenOption.CREATE);
      }

    } catch (IOException e) {
      throw new WarpScriptException("Cannot write file " + file.toString());
    }


    //
    // Return the new state of the stack
    //

    return stack;
  }
}
