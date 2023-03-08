package io.dummymaker.testdata;

import java.util.List;

/**
 * @author GoodforGod
 * @since 15.09.2019
 */
public class DummyAutoNamed {

    private String id;
    private String code;
    private String name;
    private String surname;

    private List<String> surnames;
    private List<String> btcHexes;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<String> getSurnames() {
        return surnames;
    }

    public List<String> getBtcHexes() {
        return btcHexes;
    }
}
