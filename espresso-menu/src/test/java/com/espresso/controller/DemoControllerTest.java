package com.espresso.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoControllerTest {

    @Autowired
    DemoController demoController;

    @Test
    void helloWorld() {
        assertEquals("Hello world!", demoController.helloWorld());
    }
}