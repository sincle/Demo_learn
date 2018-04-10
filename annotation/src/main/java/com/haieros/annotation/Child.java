package com.haieros.annotation;

/**
 * Created by Kang on 2018/4/10.
 */
@Description(desc = "123",author = "win",age = 12)
public class Child {

    @Description(desc = "1")
    public void say(){
        System.out.println("say");
    }
}
