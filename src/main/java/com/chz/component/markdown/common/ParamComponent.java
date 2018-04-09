package com.chz.component.markdown.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParamComponent {

    @Value("${docDir}")
    private String docDir;

    public String getDocDir() {
        return docDir;
    }
}
