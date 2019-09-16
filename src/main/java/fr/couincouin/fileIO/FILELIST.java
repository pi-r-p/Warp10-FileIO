package fr.couincouin.fileIO;

import io.warp10.WarpConfig;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

public class FILELIST extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  private File rootPath = null;

  public FILELIST(String name) {

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


    //
    // Check the class of the arguments retrieved
    //

    if (!(pathobj instanceof String)) {
      throw new WarpScriptException("expect a String for path on top of the stack.");
    }

    //
    // Apply function and push its outputs onto the stack or raise an exception
    //
    String filePath = (String) pathobj;
    FileExtensionHelper helper = new FileExtensionHelper();
    helper.ForbiddenPathDetection(filePath);

    File file = new File(rootPath, filePath);
    if (!file.isDirectory()) {
      throw new WarpScriptException("Not a directory, unable to list " + file.toString());
    }
    try {
      File[] filearray = file.listFiles(File::isFile);
      ArrayList l = new ArrayList();
      for (File value: Objects.requireNonNull(filearray)) {
        Path f = Paths.get(value.getCanonicalPath());
        Path base = Paths.get(file.getCanonicalPath());
        l.add(base.relativize(f).toString());
      }
      stack.push(l);
    } catch (IOException e) {
      throw new WarpScriptException("Cannot list canonical file names in  " + file.toString());
    }

    //
    // Return the new state of the stack
    //

    return stack;
  }
}
