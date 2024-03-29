package io.goodforgod.dummymaker.bundle;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class CurrencyBundle extends AbstractBundle {

    private static final List<String> BUNDLE_ENGLISH = Arrays.asList(
            "ALL", "AFN", "ARS", "AWG", "AUD", "AZN", "BSD", "BBD", "BYN", "BZD", "BMD", "BOB", "BAM", "BWP", "BGN", "BRL", "BND",
            "KHR", "CAD", "KYD", "CLP", "CNY", "COP", "CRC", "HRK", "CUP", "CZK", "DKK", "DOP", "XCD", "EGP", "SVC", "EUR", "FKP",
            "FJD", "GHS", "GIP", "GTQ", "GGP", "GYD", "HNL", "HKD", "HUF", "ISK", "INR", "IDR", "IRR", "IMP", "ILS", "JMD", "JPY",
            "JEP", "KZT", "KPW", "KRW", "KGS", "LAK", "LBP", "LRD", "MKD", "MYR", "MUR", "MXN", "MNT", "MZN", "NAD", "NPR", "ANG",
            "NZD", "NIO", "NGN", "NOK", "OMR", "PKR", "PAB", "PYG", "PEN", "PHP", "PLN", "QAR", "RON", "RUB", "SHP", "SAR", "RSD",
            "SCR", "SGD", "SBD", "SOS", "ZAR", "LKR", "SEK", "CHF", "SRD", "SYP", "TWD", "THB", "TTD", "TRY", "TVD", "UAH", "GBP",
            "USD", "UYU", "UZS", "VEF", "VND", "YER", "ZWD");

    @Override
    List<String> getEnglish() {
        return BUNDLE_ENGLISH;
    }

    @Override
    List<String> getRussian() {
        return BUNDLE_ENGLISH;
    }
}
