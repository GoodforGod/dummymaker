package io.dummymaker.util;

/**
 * Utility methods to help scan for classes at runtime
 *
 * @author Anton Kurako (GoodforGod)
 * @since 24.07.2019
 */
public final class PackageUtils {

    private PackageUtils() {}

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
}
