package com.vali.comma;

/**
 * Created by vali on 15-9-28.
 */
public class PrintHelloWorldImpl implements PrintHelloWorld {

    private SayHello sayHello;

    @Override public void PrintHw() {
        System.out.print("start PrintHw....");
        sayHello.saySth();
        System.out.print("end PrintHw....");
    }

    public void setSayHello(SayHello sayHello) {
        this.sayHello = sayHello;
    }
}
