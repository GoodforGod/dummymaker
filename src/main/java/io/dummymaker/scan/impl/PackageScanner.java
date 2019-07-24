package io.dummymaker.scan.impl;

import io.dummymaker.scan.IScanner;
import io.dummymaker.util.PackageUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * Scan for classes at runtime in packages
 *
 * Creds to ddopson for his code on SO it helped indeed
 * @author GoodforGod
 * @since 24.07.2019
 */
public class PackageScanner implements IScanner<Class<?>, String, String> {

    /**
     * Scans package and its subpackages for classes
     *
     * @param packageName to scan
     * @return map where KEY is class and VALUE is its package
     */
    @Override
    public Map<Class<?>, String> scan(String packageName) {
        final Map<Class<?>, String> classMap = new HashMap<>();
        final String path = PackageUtils.toRelativePath(packageName);

        try {
            // Get a File object for the package
            final Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(path);
            if (!resources.hasMoreElements())
                throw new IllegalArgumentException("No resource for '" + path + "'");

            while (resources.hasMoreElements()) {
                final URL resource = resources.nextElement();
                final Map<Class<?>, String> classes = (resource.toString().startsWith("jar:"))
                        ? loadFromJar(resource, packageName)
                        : loadFromDirectory(new File(resource.getPath()), packageName);

                classMap.putAll(classes);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return classMap;
    }

    /**
     * Given a package name and a directory returns all classes within that directory
     *
     * @param directory to process
     * @param packageName to process
     * @return Classes within Directory with package name
     */
    private static Map<Class<?>, String> loadFromDirectory(File directory, String packageName) {
        final String[] files = directory.list();
        if (files == null)
            return Collections.emptyMap();

        final Map<Class<?>, String> classes = new HashMap<>();
        for (String file : files) {
            if (file.endsWith(".class")) {
                final String className = packageName + '.' + file.substring(0, file.length() - 6);
                classes.put(PackageUtils.loadClass(className), packageName + "." + file);
            }

            // If is directory recursively load all classes
            final File subdir = new File(directory, file);
            if (subdir.isDirectory())
                classes.putAll(loadFromDirectory(subdir, packageName + "." + file));
        }

        return classes;
    }

    /**
     * Given a jar file's URL and a package name returns all classes within jar file.
     *
     * @param resource as jar to process
     * @param packageName to process
     */
    private static Map<Class<?>, String> loadFromJar(URL resource, String packageName) {
        final Map<Class<?>, String> classes = new HashMap<>();

        final String path = PackageUtils.toRelativePath(packageName);
        final String jarPath = resource.getPath()
                .replaceFirst("[.]jar[!].*", ".jar")
                .replaceFirst("file:", "")
                .replace(" ", "\\ ");

        final Enumeration<JarEntry> files = PackageUtils.getJarFiles(jarPath);
        while (files.hasMoreElements()) {
            final JarEntry file = files.nextElement();

            if (file.getName().endsWith(".class")
                    && file.getName().startsWith(path)
                    && file.getName().length() > path.length() + 1) {

                final String className = file.getName()
                        .replace('/', '.')
                        .replace('\\', '.')
                        .replace(".class", "");

                classes.put(PackageUtils.loadClass(className), file.getName());
            }
        }

        return classes;
    }
}
