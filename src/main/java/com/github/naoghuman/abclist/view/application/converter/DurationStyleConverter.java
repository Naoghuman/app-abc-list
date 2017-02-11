package com.github.naoghuman.abclist.view.application.converter;

import java.util.Optional;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;
import javafx.util.Duration;

public final class DurationStyleConverter extends StyleConverter<String, Duration> {

    private static final Optional<DurationStyleConverter> instance = Optional.of(new DurationStyleConverter());

    public static final DurationStyleConverter getDefault() {
        return instance.get();
    }
    
    private DurationStyleConverter() {
        
    }

    @Override
    public Duration convert(ParsedValue<String, Duration> value, Font font) {
        final String cssProperty = value.getValue();
        Duration duration = Duration.millis(Long.parseLong(cssProperty));
        
        if(cssProperty.endsWith("ms")) { // NOI18N
            duration = Duration.millis(Long.parseLong(cssProperty.substring(0, cssProperty.length() - 2)));
        }
        
        if(cssProperty.endsWith("s")) { // NOI18N
            duration = Duration.seconds(Long.parseLong(cssProperty.substring(0, cssProperty.length() - 1)));
        }
        
        return duration;
    }
}
