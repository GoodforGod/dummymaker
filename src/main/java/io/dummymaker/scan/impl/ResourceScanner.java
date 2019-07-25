package io.dummymaker.scan.impl;

import io.dummymaker.scan.IScanner;
import io.dummymaker.util.PackageUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;

/**
 * Scan for resources at runtime by package
 *
 * @author GoodforGod
 * @since 25.07.2019
 */
public class ResourceScanner implements IScanner<String, String> {

    /**
     * Scans for all resources under specified package and its subdirectories
     *
     * @param packageName to scan from
     * @return list of resources under target package
     */
    @Override
    public Collection<String> scan(String packageName) {
        try {
            final String path = PackageUtils.toRelativePath(packageName);
            final Enumeration<URL> resourceUrls = ClassLoader.getSystemClassLoader().getResources(path);
            if (!resourceUrls.hasMoreElements())
                return Collections.emptySet();

            final Set<String> resources = new HashSet<>();
            while (resourceUrls.hasMoreElements()) {
                final URL resource = resourceUrls.nextElement();
                final Set<String> classes = (resource.toString().startsWith("jar:"))
                        ? loadFromJar(resource)
                        : loadFromDirectory(new File(resource.getPath()), packageName);

                resources.addAll(classes);
            }

            return resources;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Given a package name and a directory returns all classes within that directory
     *
     * @param directory   to process
     * @param packageName to process
     * @return Classes within Directory with package name
     */
    private static Set<String> loadFromDirectory(File directory, String packageName) {
        final String[] files = directory.list();
        if (files == null)
            return Collections.emptySet();

        final Set<String> classes = new HashSet<>();
        for (String file : files) {
            classes.add(packageName + "." + file);

            // If is directory recursively load all classes
            final File subdir = new File(directory, file);
            if (subdir.isDirectory())
                classes.addAll(loadFromDirectory(subdir, packageName + "." + file));
        }

        return classes;
    }

    /**
     * Given a jar file's URL and a package name returns all classes within jar file.
     *
     * @param resource as jar to process
     */
    private static Set<String> loadFromJar(URL resource) {
        final Set<String> classes = new HashSet<>();
        final String jarPath = resource.getPath()
                .replaceFirst("[.]jar[!].*", ".jar")
                .replaceFirst("file:", "")
                .replace(" ", "\\ ");

        final Enumeration<JarEntry> files = PackageUtils.getJarFiles(jarPath);
        while (files.hasMoreElements()) {
            final JarEntry file = files.nextElement();
            classes.add(file.getName());
        }

        return classes;
    }
}
