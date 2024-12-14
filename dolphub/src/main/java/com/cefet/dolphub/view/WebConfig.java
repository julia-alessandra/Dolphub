package com.cefet.dolphub.view;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TagsConverter tagsConverter;

    public WebConfig(TagsConverter tagsConverter) {
        this.tagsConverter = tagsConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(tagsConverter);
    }
}
