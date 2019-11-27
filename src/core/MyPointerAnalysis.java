package core;

import java.io.File;
import java.util.Arrays;

import soot.Main;
import soot.PackManager;
import soot.Transform;

public class MyPointerAnalysis {
	
	// args[0] = "/root/workspace/code"
	// args[1] = "test.Hello"
	public static String mainClass;
	public static void main(String[] args) {		
		//String jdkLibPath = System.getProperty("java.home")+"/lib/"; // "/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/";

		mainClass = args[1];
		PackManager.v().getPack("wjtp").add(new Transform("wjtp.mypta", new WholeProgramTransformer()));
		String newArgv[] = new String[] { "-w", "-pp", "-f", "J", "-cp", args[0], args[1]};
		System.out.println(Arrays.toString(newArgv));
		System.out.println("Hello!");
		Main.main(newArgv);

		/*String classpath = args[0]
				+ File.pathSeparator + args[0] + File.separator + "rt.jar"
				+ File.pathSeparator + args[0] + File.separator + "jce.jar";	
		System.out.println(classpath);
		System.out.println("Hello!");
		PackManager.v().getPack("wjtp").add(new Transform("wjtp.mypta", new WholeProgramTransformer()));
		soot.Main.main(new String[] {
			"-w",
			"-p", "cg.spark", "enabled:true",
			"-p", "wjtp.mypta", "enabled:true",
			"-soot-class-path", classpath,
			args[1]
		});*/
	}

}
