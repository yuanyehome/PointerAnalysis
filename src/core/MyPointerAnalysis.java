package core;

import java.io.File;
import java.util.Arrays;

import soot.Main;
import soot.PackManager;
import soot.Transform;

public class MyPointerAnalysis {
    /**
     * example args:
     *   args[0] = "/root/workspace/code"
     *   args[1] = "test.Hello"
     */
	public static String mainClass;
	public static void main(String[] args) {
		//String jdkLibPath = System.getProperty("java.home")+"/lib/"; // "/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/";

		mainClass = args[1];
		PackManager.v().getPack("wjtp").add(new Transform("wjtp.mypta", new WholeProgramTransformer()));
		String newArgv[] = new String[] { "-w", "-pp", "-f", "J", "-cp", args[0], args[1]};
		System.out.println(Arrays.toString(newArgv));
		System.out.println("Hello!");
		Main.main(newArgv);
	}
}
