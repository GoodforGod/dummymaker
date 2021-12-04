package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Generates photo as base64
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.12.2021
 */
public class PhotoGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("pic(ture)?|photo|avatar|base64", CASE_INSENSITIVE);

    private static final String PHOTO_JPG_AS_BASE64 = "/9j/4AAQSkZJRgABAQEAYABgAAD/4QAiRXhpZgAATU0AKgAAAAgAAQESAAMAAAABAAEAAAAAAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAAeAB4DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9pAMmvlz9oL/gqz4J+DHjW88P6PpOoeM9Q02VoL2a2uUtbKCVTho1lYOZGUghtqbQRgMSCB9F/EUaofh34h/sPzP7b/su6/s7y/v/AGnyX8nHv5m3HvX4b2m37LHt+7tGPyoA/Xb9lL9u3wd+1jLcafpsd9oniOzi8+bSb/aXkiBAMkMinbKikgHhWGclcYY+1V+Pv7Bv9pf8Nl/Dn+yfM+1f2uvmbOv2by3+059vs/m59s1+wVAHnn7Sv7Tvhn9lbwEuu+IpZ5JLqQwadp9qA11qMoGSqAkAKoILuxCqCOrMit8J+LLH9m79qjVpvE3/AAlusfBvxLqTmfVNLudNa+sJJm5eWJkUKNzEknemc58pSTnmP+ConxKvfH37YGuafO7iw8JQwaVYxE/KgMSTSvjpuaWVuepVUB+6MfPNAH2h8Fv2g/2e/wBh3xPE/hdvFXxG1rUv9G1PxH9kFuunWx5ZLaKURk5YKWVQSwB/ekAIfvvwh4v0vx/4V0/XNEvrfU9I1aBbm0uoSSk8bdCM4IPUFSAykEEAggfhpX6Df8EXviTfaz4G8aeELh2ks9BurbUbHcc+SLoTCWMei74A4A43SSHqeQD/2Q==";

    @Override
    public @Nullable String generate() {
        return PHOTO_JPG_AS_BASE64;
    }

    @Override
    public @Nullable Pattern pattern() {
        return pattern;
    }
}
