package io.dummymaker.scan.impl;

import io.dummymaker.scan.Scanner;
import io.dummymaker.util.PackageUtils;
import java.util.Collection;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Scan for classes at runtime by packages
 *
 * @author Anton Kurako (GoodforGod)
 * @since 24.07.2019
 */
public class ClassScanner implements Scanner<Class, String> {

    private final ResourceScanner resourceScanner = new ResourceScanner();

    @Override
    public @NotNull Collection<Class> scan(String packageName) {
        final Collection<String> resources = resourceScanner.scan(packageName);

        return resources.stream()
                .filter(f -> f.endsWith(".class"))
                .map(f -> f.replace('/', '.')
                        .replace('\\', '.')
                        .replace(".class", ""))
                .map(PackageUtils::loadClass)
                .collect(Collectors.toList());
    }
}
