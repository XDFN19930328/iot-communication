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

package com.github.xingshuangs.iot.common.algorithm;


import lombok.Data;

/**
 * Loop group item, include basic parameter.
 * (基础数据参数)
 *
 * @author xingshuang
 */
@Data
public class LoopGroupItem {

    /**
     * Actual length.
     * (实际长度)
     */
    private int actualLength = 0;

    /**
     * Current offset.
     * (当前偏移量)
     */
    private int off = 0;

    /**
     * Current length.
     * (当前长度)
     */
    private int len = 0;

    public LoopGroupItem() {
    }

    public LoopGroupItem(int actualLength) {
        this.actualLength = actualLength;
    }

    /**
     * If current data is in valid range.
     * (判定是否在有效范围内)
     *
     * @return true：in range，false：out range.
     */
    public boolean inRange() {
        return this.off + this.len < this.actualLength;
    }
}
