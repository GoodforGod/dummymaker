package io.goodforgod.dummymaker.export.validators;

import io.goodforgod.dummymaker.cases.NamingCase;

/**
 * @author GoodforGod
 * @since 03.03.2018
 */
public interface ValidatorChecker {

    void isSingleDummyListValid(String[] dummy);

    void isSingleDummyValid(String[] dummy);

    void isSingleAutoDummyValid(String[] dummy);

    void isTwoDummiesValid(String[] dummies);

    void isTwoDummiesWithNumFieldValid(String[] dummies);

    void isTwoDummiesValidWithNamingStrategy(String[] dummies, NamingCase strategy);

    void isDummyTimeValid(String[] dummy);

    void isDummyUnixTimeValid(String[] dummy);

    void isDummyTimeFormatterValid(String[] dummy);
}
