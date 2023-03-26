package io.dummymaker.testdata;

import io.dummymaker.annotation.simple.number.GenInt;
import java.util.List;

public class DummyRulesClone {

    private Long number;
    @GenInt
    private String code;
    @GenInt
    private String name;
    private List<String> emails;

    public Long getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<String> getEmails() {
        return emails;
    }
}
