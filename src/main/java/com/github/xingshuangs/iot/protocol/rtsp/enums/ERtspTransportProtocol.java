/*
 * MIT License
 *
 * Copyright (c) 2021-2099 Oscura (xingshuang) <xingshuang_cool@163.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.xingshuangs.iot.protocol.rtsp.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * RTSP transport protocol.
 * 传输协议，TCP/UDP
 *
 * @author xingshuang
 */
public enum ERtspTransportProtocol {

    /**
     * TCP
     */
    TCP(0),

    /**
     * UDP
     */
    UDP(1),

    ;

    private static Map<Integer, ERtspTransportProtocol> map;

    public static ERtspTransportProtocol from(int data) {
        if (map == null) {
            map = new HashMap<>();
            for (ERtspTransportProtocol item : ERtspTransportProtocol.values()) {
                map.put(item.code, item);
            }
        }
        return map.get(data);
    }

    private final int code;

    ERtspTransportProtocol(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
