package io.dummymaker.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utility methods to help scan for classes at runtime
 *
 * @author GoodforGod
 * @since 24.07.2019
 */
public class PackageUtils {

    private PackageUtils() { }

    /**
     * Loads class by class name
     *
     * @param className to load
     * @return loaded class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unexpected ClassNotFoundException while loading class '" + className + "'");
        }
    }

    /**
     * Get name of package and turn it to a relative path
     *
     * @param packageName name of package
     * @return package as relative path
     */
    public static String toRelativePath(String packageName) {
        return packageName.replace('.', '/');
    }

    /**
     * Get contents of jar file
     *
     * @param path to jar
     * @return files inside jar
     */
    public static Enumeration<JarEntry> getJarFiles(String path) {
        try {
            final JarFile jar = new JarFile(path);
            return jar.entries();
        } catch (IOException e) {
            throw new IllegalArgumentException("Unexpected IOException reading JAR File '" + path + "'", e);
        }
    }
}
