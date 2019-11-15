package com.appleiapservices.demo.natives;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface INativeXXX extends Library {

    INativeXXX INSTANCE = Native.load("libTenonTest", INativeXXX.class);

    /**
     * 这里定义 类库接口方法就可以调用了
     */
    int getRandom();
}
