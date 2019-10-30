package io.dummymaker.scan.impl;

import io.dummymaker.model.error.GenException;
import io.dummymaker.scan.IScanner;
import io.dummymaker.util.PackageUtils;
import io.dummymaker.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

/**
 * Scan for resources at runtime by package
 *
 * @author GoodforGod
 * @since 25.07.2019
 */
public class ResourceScanner implements IScanner<String, String> {

    /**
     * Given a package name and a directory returns all classes within that directory
     *
     * @param directory   to process
     * @param packageName to process
     * @return Classes within Directory with package name
     */
    private static Set<String> loadFromDirectory(File directory, String packageName) {
        final String[] files = directory.list();
        if (files == null || files.length == 0)
            return Collections.emptySet();

        final Set<String> classes = new HashSet<>();
        for (String file : files) {
            classes.add(packageName + "/" + file);

            // If is directory recursively load all classes
            final File subdir = new File(directory, file);
            if (subdir.isDirectory())
                classes.addAll(loadFromDirectory(subdir, packageName + "/" + file));
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

    /**
     * Scans for all resources under specified package and its subdirectories
     *
     * @param packageOrPath package or path to start scan from
     * @return list of resources under target package or path
     */
    @Override
    public Collection<String> scan(String packageOrPath) {
        if (StringUtils.isBlank(packageOrPath))
            return Collections.emptyList();

        final String path = PackageUtils.toRelativePath(packageOrPath);
        return getSystemResources(packageOrPath).stream()
                .map(r -> r.toString().contains("jar:")
                        ? loadFromJar(r)
                        : loadFromDirectory(new File(r.getPath()), path))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Scan resource with absolute path added
     *
     * @param packageOrPath package or path to start scan from
     * @return list of resources under target package or path
     */
    public Collection<String> scanAbsolute(String packageOrPath) {
        final Collection<String> scanned = scan(packageOrPath);
        if (scanned.isEmpty())
            return Collections.emptySet();

        final String path = PackageUtils.toRelativePath(packageOrPath);
        return getSystemResources(packageOrPath).stream()
                .findFirst()
                .map(absolute -> scanned.stream()
                        .map(p -> p.replace(path, absolute.getFile()))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    /**
     * Loads system resources as URLs
     *
     * @param packageOrPath to load resources from
     * @return resources urls
     */
    private List<URL> getSystemResources(String packageOrPath) {
        try {
            final String path = PackageUtils.toRelativePath(packageOrPath);
            final Enumeration<URL> resourceUrls = ClassLoader.getSystemClassLoader().getResources(path);
            if (!resourceUrls.hasMoreElements())
                return Collections.emptyList();

            final List<URL> resources = new ArrayList<>();
            while (resourceUrls.hasMoreElements())
                resources.add(resourceUrls.nextElement());

            return resources;
        } catch (IOException e) {
            throw new GenException(e);
        }
    }
}
