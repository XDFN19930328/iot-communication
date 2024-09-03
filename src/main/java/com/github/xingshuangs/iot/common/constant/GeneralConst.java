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

package com.github.xingshuangs.iot.common.constant;


/**
 * General const data.
 * (通用常量)
 *
 * @author xingshuang
 */
public class GeneralConst {

    private GeneralConst() {
        // NOOP
    }

    /**
     * Localhost ip
     * (本地ip，127.0.0.1)
     */
    public static final String LOCALHOST = "127.0.0.1";

    /**
     * S7 protocol default port.
     * (S7的端口号)
     */
    public static final int S7_PORT = 102;

    /**
     * Modbus protocol default port.
     * (Modbus的端口号)
     */
    public static final int MODBUS_PORT = 502;

    /**
     * MELSEC protocol port, not default.
     * (三菱的端口号)
     */
    public static final int MELSEC_PORT = 6000;

    /**
     * The bit type.
     * (bit类型)
     */
    public static final int TYPE_BIT = 0;

    /**
     * The word type.
     * (word类型)
     */
    public static final int TYPE_WORD = 1;

    /**
     * The dword type.
     * (dword类型)
     */
    public static final int TYPE_DWORD = 2;

    /**
     * Request package tag name.
     * (请求)
     */
    public static final String PACKAGE_REQ = "REQ";

    /**
     * Response package tag name.
     * (响应)
     */
    public static final String PACKAGE_ACK = "ACK";

}
