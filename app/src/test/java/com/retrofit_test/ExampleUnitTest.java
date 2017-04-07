package com.retrofit_test;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() throws Exception {

    }

    @Test
    public void test() {
        Par1 childa = new Childa() {
        };
        Childb childb = new Childb() {
        };
        System.out.println(childa.getClass());
        System.out.println(childb.getClass());
        System.out.println(childa instanceof Par1);
        System.out.println(childb instanceof Par1);
    }

    interface Par {

    }

    interface Par1 extends Par {

    }

    abstract class Childa implements Par1 {
    }

    abstract class Childb implements Par {
    }

}