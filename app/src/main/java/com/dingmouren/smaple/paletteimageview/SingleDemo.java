package com.dingmouren.smaple.paletteimageview;


//单例模式（双重检测）
public class SingleDemo {

    private static volatile SingleDemo singleDemo;

    private SingleDemo() {

    }

    public static SingleDemo getSingleDemo() {
        if (singleDemo == null) {
            synchronized (SingleDemo.class) {
                if (singleDemo == null) {
                    singleDemo = new SingleDemo();
                }
            }
        }
        return singleDemo;
    }
}
