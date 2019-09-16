package fr.couincouin;

import io.warp10.WarpConfig;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

public class FILEDF extends NamedWarpScriptFunction implements WarpScriptStackFunction {

  private File rootPath = null;

  public FILEDF(String name) {

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


    try {
      long df = rootPath.getUsableSpace();
      stack.push(df);
    } catch (WarpScriptException e) {
      e.printStackTrace();
      throw new WarpScriptException("Stack exception" + e.getMessage() + Arrays.toString(e.getStackTrace()));
    }

    //
    // Return the new state of the stack
    //

    return stack;
  }
}
