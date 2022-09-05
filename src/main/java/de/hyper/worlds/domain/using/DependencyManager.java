package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.obj.Dependency;
import lombok.Getter;

@Getter
public class DependencyManager {

    private Dependency[] dependencies;

    public DependencyManager(Dependency... dependencies) {
        this.dependencies = dependencies;
    }

    public Dependency getDependency(String mainClassName) {
        for (Dependency dependency : dependencies) {
            if (dependency.getMainClass().endsWith(mainClassName)) {
                return dependency;
            }
        }
        return null;
    }
}