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

package com.github.xingshuangs.iot.protocol.rtp.model.frame;


import com.github.xingshuangs.iot.common.IObjectByteArray;
import com.github.xingshuangs.iot.protocol.rtp.enums.EFrameType;
import lombok.Getter;
import lombok.Setter;

/**
 * 帧的基础类
 *
 * @author xingshuang
 */
@Getter
@Setter
public class RawFrame implements IObjectByteArray {

    /**
     * 帧类别
     */
    protected EFrameType frameType;

    /**
     * 时间戳
     */
    protected long timestamp;

    /**
     * 帧内容
     */
    protected byte[] frameSegment = new byte[0];

    /**
     * Presentation Time Stamp
     * (显示时间戳)
     */
    protected long pts;

    /**
     * Decoding Time Stamp
     * (解码时间戳)
     */
    protected long dts;

    /**
     * sample duration, Sample Duration=DTSn+1 - DTSn
     * (每个样本（例如一帧）从解码开始到下一个样本解码开始的时间间隔)
     */
    protected int duration = 3600;

    @Override
    public int byteArrayLength() {
        return 0;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
