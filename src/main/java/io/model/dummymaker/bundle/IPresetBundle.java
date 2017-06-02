package io.model.dummymaker.bundle;

import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public abstract class IPresetBundle<T> implements IBundle<T> {

    protected List<T> preset;

    private IPresetBundle() { }

    public IPresetBundle(List<T> preset) {
        this.preset = preset;
    }

    @Override
    public T get(int index) {
        return (index > 0 && index < preset.size() - 1)
                ? preset.get(index)
                : preset.get(0);
    }

    @Override
    public T getRandom() {
        return preset.get(current().nextInt(0, preset.size() - 1));
    }
}
