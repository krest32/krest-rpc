package com.krest.rpc.demo.day4.serialize;

import com.esotericsoftware.kryo.Kryo;

public class KryoHolder {
    private static ThreadLocal<Kryo> threadLocalKryo = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new KryoReflectionFactory();

            return kryo;
        }

        ;
    };

    public static Kryo get() {
        return threadLocalKryo.get();
    }

}
