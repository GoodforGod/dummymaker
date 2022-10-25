package io.dummymaker.scan.impl;

import io.dummymaker.scan.IScanner;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.PackageUtils;
import io.dummymaker.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

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
     * @param packageOrPath package or path to start scan from
     * @return list of resources under target package or path
     */
    @Override
    public @NotNull Collection<String> scan(String packageOrPath) {
        if (StringUtils.isBlank(packageOrPath))
            return Collections.emptyList();

        final String path = PackageUtils.toRelativePath(packageOrPath);
        final List<URL> systemResources = getSystemResources(packageOrPath);
        return systemResources.stream()
                .map(r -> r.toString().startsWith("jar")
                        ? loadFromJar(r)
                        : loadFromDirectory(new File(r.getPath()), path))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Given a package name and a directory returns all classes within that directory
     *
     * @param directory   to process
     * @param packageName to process
     * @return Classes within Directory with package name
     */
    private static Collection<String> loadFromDirectory(File directory, String packageName) {
        final String[] files = directory.list();
        if (CollectionUtils.isEmpty(files))
            return Collections.emptySet();

        final Collection<String> classes = new ArrayList<>();
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
    private static Collection<String> loadFromJar(URL resource) {
        final String jarPath = resource.getPath()
                .replaceFirst("[.]jar[!].*", ".jar")
                .replaceFirst("file:", "");

        try {
            final String path = URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name());
            try (final JarFile jar = new JarFile(path)) {
                final List<String> classes = new ArrayList<>();
                final Enumeration<JarEntry> files = jar.entries();
                while (files.hasMoreElements()) {
                    final JarEntry file = files.nextElement();
                    classes.add(file.getName());
                }

                return classes;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Can not open JAR '" + resource + "', failed with: " + e.getMessage());
        }
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
            final Enumeration<URL> resourceUrls = getClass().getClassLoader().getResources(path);
            if (!resourceUrls.hasMoreElements())
                return Collections.emptyList();

            final List<URL> resources = new ArrayList<>();
            while (resourceUrls.hasMoreElements())
                resources.add(resourceUrls.nextElement());

            return resources;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
