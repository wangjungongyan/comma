package com.vali.comma;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vali on 15-9-28.
 */
public class CommaTest {

    private static ApplicationContext applicationContext;

    public static void init() {
        String[] path = new String[] {
                "classpath*:/config/spring/test/appcontext-test.xml" };

        applicationContext = new ClassPathXmlApplicationContext(path);
    }

    public static void main(String[] args) {
        init();
        PrintHelloWorld printHelloWorld = (PrintHelloWorld)applicationContext.getBean("printHelloWorldFromCommaServiceRegistry");
        printHelloWorld.PrintHw();
    }
}
