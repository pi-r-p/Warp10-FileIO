package fr.couincouin.fileIO;

import io.warp10.WarpConfig;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FILESIZE extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  private File rootPath = null;

  public FILESIZE(String name) {

    super(name);
    Properties props = WarpConfig.getProperties();

    if (props.containsKey(FileExtensionHelper.FILE_ROOTPATH)) {
      this.rootPath = new File(props.getProperty(FileExtensionHelper.FILE_ROOTPATH));
    }

  }

  private Long readFileSize(Object f) throws WarpScriptException {
    if (!(f instanceof String)) {
      throw new WarpScriptException("expect a String for file path, or a list thereof, on top of the string");
    }
    String filePath = (String) f;
    FileExtensionHelper helper = new FileExtensionHelper();
    helper.ForbiddenPathDetection(filePath);
    File file = new File(rootPath, filePath);
    if (file.isFile()) {
      return file.length();
    } else {
      throw new WarpScriptException(filePath + " is not a regular file, cannot read size");
    }
  }


  public Object apply(WarpScriptStack stack) throws WarpScriptException {

    if (null == rootPath) {
      throw new WarpScriptException("The root path configuration is missing, set it with " + FileExtensionHelper.FILE_ROOTPATH);
    }

    Object o = stack.pop();

    if (o instanceof List) {
      List elements = (List) o;
      ArrayList<Object> resultList = new ArrayList<>(elements.size());
      for (Object element: elements) {
        resultList.add(readFileSize(element));
      }
      stack.push(resultList);
    } else {
      stack.push(readFileSize(o));
    }

    return stack;
  }
}
