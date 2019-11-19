package io.dummymaker.factory;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Factory that generates data objects Core that handles all top level logic
 *
 * @author GoodforGod
 * @see io.dummymaker.factory.impl.GenFactory
 * @since 21.07.2019
 */
public interface IGenFactory {

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> T build(Class<T> target);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> List<T> build(Class<T> target, int amount);

    /**
     * Instantiates class instance and populate its fields
     *
     * @param target class to build and fill with data
     * @param amount of objects to produce
     * @param <T>    object type
     * @return generates class filled with data
     */
    <T> Stream<T> stream(Class<T> target, int amount);

    /**
     * Instantiates class instance and populate its fields Use in case class have no
     * empty constructor of have complex initialization logic
     *
     * @param supplier of class to build
     * @param amount   of objects to produce
     * @param <T>      object type
     * @return generates class filled with data
     */
    <T> List<T> build(Supplier<T> supplier, int amount);

    /**
     * Instantiates class instance and populate its fields Use in case class have no
     * empty constructor of have complex initialization logic
     *
     * @param supplier of class to build
     * @param amount   of objects to produce
     * @param <T>      object type
     * @return generates class filled with data
     */
    <T> Stream<T> stream(Supplier<T> supplier, int amount);

    /**
     * Populates dummy object fields
     *
     * @param t   object to fill with data
     * @param <T> object type
     * @return Populated Object
     */
    <T> T fill(T t);

    /**
     * Populates dummy object fields
     *
     * @param stream of objects to fill with data
     * @param <T>    object type
     * @return Populated Object
     */
    <T> Stream<T> fill(Stream<T> stream);

    /**
     * Populates dummy object fields
     *
     * @param list of objects to fill with data
     * @param <T>  object type
     * @return Populated Object
     */
    <T> List<T> fill(List<T> list);
}
