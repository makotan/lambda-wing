package com.makotan.libs.lambda.wing.core.util;

/**
 * Created by makotan on 2015/09/20.
 */
public class Tuple2<T1,T2> {
    public T1 _1;
    public T2 _2;
    public static <T1,T2> Tuple2<T1,T2> tuple(T1 _1,T2 _2) {
        Tuple2<T1,T2> t = new Tuple2<>();
        t._1 = _1;
        t._2 = _2;
        return t;
    }
}
