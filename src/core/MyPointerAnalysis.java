package core;

import soot.Main;
import soot.PackManager;
import soot.Transform;

import java.util.Arrays;

/**
 * @author yangchenyang
 * @brief entrance of program
 * <p>
 * example args:
 * args[0] = "/root/workspace/code"
 * args[1] = "test.Hello"
 */
public class MyPointerAnalysis {

    private static String mainClass;

    public static void main(String[] args) {
        mainClass = args[1];
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.mypta", new WholeProgramTransformer()));
        String[] newArgv = new String[]{"-w", "-pp", "-f", "J", "-cp", args[0], args[1]};
        System.out.println(Arrays.toString(newArgv));
        System.out.println("Begin to analysis pointer!");
        Main.main(newArgv);
    }

    public static String get_mainClass() {
        return mainClass;
    }

}
