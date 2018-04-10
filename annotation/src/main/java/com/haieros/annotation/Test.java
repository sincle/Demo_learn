package com.haieros.annotation;

import java.lang.reflect.Method;

/**
 * Created by Kang on 2018/4/10.
 */

public class Test {

    public static void main(String[] agrs){

        try {
            Class<?> clazz = Class.forName("com.haieros.annotation.Child");
            boolean b = clazz.isAnnotationPresent(Description.class);
            if(b) {
                Description description = clazz.getAnnotation(Description.class);
                System.out.println("description:"+description.author()+"desc:"+description.desc()+"age:"+description.age());
            }
            Method say = clazz.getMethod("say");
            boolean annotationPresent = say.isAnnotationPresent(Description.class);
            if(annotationPresent) {
                Description annotation = say.getAnnotation(Description.class);
                System.out.println("annotation:"+annotation.author()+"desc:"+annotation.desc()+"age:"+annotation.age());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
