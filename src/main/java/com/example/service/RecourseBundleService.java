package com.example.service;

import com.example.enums.AppLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class RecourseBundleService {

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    public String getMessage(String code, AppLanguage language) {
        return resourceBundleMessageSource.getMessage(code, null, new Locale(language.name()));
    }

}
