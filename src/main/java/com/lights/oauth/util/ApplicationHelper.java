package com.lights.oauth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationHelper {

    @Autowired
    private ApplicationContext applicationContext;

    public void shutDownGracefully() {
        int exitCode = SpringApplication.exit(applicationContext, (ExitCodeGenerator) () -> 0);
        System.exit(exitCode);
    }
}
