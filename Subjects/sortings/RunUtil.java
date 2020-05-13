package benchmarks;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class RunUtil {

	public static String SM = "symbolic.method";
	public static String TA = "target.args";
	public static String DIR = System.getProperty("user.dir");
	public static String sep = System.getProperty("file.separator");
	public static String TEST_CLASS_PATH = DIR + sep + "build" + sep + "tests";
	public static String TEST_SOURCE_PATH = DIR + sep + "src" + sep + "tests";
	public static String EXAMPLE_CLASS_PATH = DIR + sep + "build" + sep
			+ "examples";

	public static Object createObject(String className) {
		Object object = null;
		try {
			Class<?> classDefinition = Class.forName(className);
			object = classDefinition.newInstance();
		} catch (InstantiationException e) {
			System.out.println(e);
		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return object;
	}

	public static Object createObject(String className, String dir) {
		File file = new File(dir);
		// convert the file to URL format
		URL url;
		Object object = null;
		try {
			url = file.toURI().toURL();
			URL[] urls = new URL[] { url };
			// load this folder into Class loader
			URLClassLoader cl = new URLClassLoader(urls);
			Class<?> cls = cl.loadClass(className);
			object = cls.newInstance();
			cl.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static void runMethod(Object o, String methodName)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		Class<?> c = o.getClass();
		Method[] allMethods = c.getDeclaredMethods();
		for (Method m : allMethods) {
			String mName = m.getName();
			// can not handle overloading yet
			if (mName.equals(methodName)) {
				Class<?>[] pType = m.getParameterTypes();
				Object[] paras = new Object[pType.length];
				int i = 0;
				for (Class<?> p : pType) {
					if (!p.isPrimitive()) {
						if (p.getName().equals("[D")) {
							paras[i] = new double[] { 1.0 };
						} else if (p.getName().equals("[B")) {
							paras[i] = new byte[] { 1 };
						} else if (p.getName().equals("[I")) {
							paras[i] = new int[] { 1 };
						} else {
							paras[i] = p.newInstance();
						}
					} else {
						if (p.getName().equals("int")) {
							paras[i] = 0;
						} else if (p.getName().equals("boolean")) {
							paras[i] = true;
						} else if (p.getName().equals("double")) {
							paras[i] = 1.0;
						}
					}
					i++;
				}
				m.invoke(o, paras);
			}
		}
	}

	public static Config jpfConfigSetup(Object driver, String className,
			String methodName, String classPath, String sourcePath, int depth) {
		Object o = createObject(className);
		if (o == null) {
			System.err.println("Cannot create the target object.");
			return null;
		}
		assert o != null;
		String[] args = {};
		Config conf = JPF.createConfig(args);
		conf.setProperty("symbolic.dp", "cvc3");
		conf.setProperty("symbolic.lazy", "true");
		conf.setProperty("target", driver.getClass().getName());
		conf.setProperty("classpath", classPath);
		conf.setProperty("sourcepath", sourcePath);
		Class<?> c = o.getClass();
		Method[] allMethods = c.getDeclaredMethods();
		String longName = c.getName() + ".";
		for (Method m : allMethods) {
			String mName = m.getName();
			if (mName.equals(methodName)) {
				Type[] pType = m.getGenericParameterTypes();
				StringBuilder s = new StringBuilder();
				s.append(mName + "(");
				for (int i = 0; i < pType.length; i++) {
					if (i == 0) {
						s.append("sym");
					} else {
						s.append("#sym");
					}
				}
				s.append(")");
				conf.setProperty(SM, longName + s.toString());
				conf.setProperty(TA, o.getClass().getName() + ", " + mName);
				break;
			}
		}
		if (depth != -1) {
			conf.setProperty("search.depth_limit", Integer.toString(depth));
		}
		return conf;
	}

	public static void runJPF(Config conf, ListenerAdapter... listeners) {
		JPF jpf = new JPF(conf);
		for (ListenerAdapter listener : listeners) {
			jpf.addListener(listener);
		}
		jpf.run();
	}

	public static void main(String[] args) {
		if (args.length != 3 && args.length != 2) {
			System.err
					.println("Wrong number of arguments. Expecting 2 or 3 arguments for class name and method name.");
			System.exit(1);
		}
		String className = args[0];
		String methodName = args[1];
		Object o = null;
		if (args.length == 3) {
			String classPath = args[2];
			o = createObject(className, classPath);
		}
		if (args.length == 2) {
			o = createObject(className);
		}
		assert o != null;
		try {
			runMethod(o, methodName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

}
