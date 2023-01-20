package io.dummymaker.export.validators;

import io.dummymaker.cases.Case;

/**
 * @author GoodforGod
 * @since 03.03.2018
 */
public interface ValidatorChecker {

    void isSingleDummyListValid(String[] dummy);

    void isSingleDummyValid(String[] dummy);

    void isSingleAutoDummyValid(String[] dummy);

    void isTwoDummiesValid(String[] dummies);

    void isTwoDummiesValidWithNamingStrategy(String[] dummies, Case strategy);

    void isDummyTimeValid(String[] dummy);

    void isDummyUnixTimeValid(String[] dummy);

    void isDummyTimeFormatterValid(String[] dummy);
}
