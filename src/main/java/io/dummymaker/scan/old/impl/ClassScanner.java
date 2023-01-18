package io.dummymaker.scan.old.impl;

import io.dummymaker.scan.old.ListScanner;
import io.dummymaker.util.PackageUtils;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Scan for classes at runtime by packages
 *
 * @author Anton Kurako (GoodforGod)
 * @since 24.07.2019
 */
public class ClassScanner implements ListScanner<Class<?>, String> {

    private final ResourceScanner resourceScanner = new ResourceScanner();

    @Override
    public @NotNull List<Class<?>> scan(String packageName) {
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
