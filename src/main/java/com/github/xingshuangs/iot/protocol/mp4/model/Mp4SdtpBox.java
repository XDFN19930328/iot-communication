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

package com.github.xingshuangs.iot.protocol.mp4.model;


import com.github.xingshuangs.iot.common.buff.ByteWriteBuff;
import com.github.xingshuangs.iot.protocol.mp4.enums.EMp4Type;

import java.util.List;

/**
 * Independent and Disposable Samples Box(sdtp), it is mainly used to describe whether a specific sample is I-frame,
 * leading frame and other related attribute values, which is mainly used as synchronous reference information
 * when performing on-demand playback. Here, a simple assignment can be done.
 * 主要是用来描述具体某个 sample 是否是 I 帧，是否是 leading frame 等相关属性值，
 * 主要用来作为当进行点播回放时的同步参考信息,这里简单赋值即可
 *
 * @author xingshuang
 */
public class Mp4SdtpBox extends Mp4Box {

    /**
     * 4-bytes.
     */
    private final byte[] reserved;

    /**
     * 3-bytes, flags.
     */
    private final byte[] flags;

    public Mp4SdtpBox(List<Mp4SampleData> samples) {
        this.mp4Type = EMp4Type.SDTP;
        this.reserved = new byte[4];
        this.flags = new byte[samples.size()];
        for (int i = 0; i < samples.size(); i++) {
            Mp4SampleFlag sampleFlag = samples.get(i).getFlags();
            this.flags[i] = (byte) ((sampleFlag.getDependedOn() << 4)
                    | (sampleFlag.getIsDependedOn() << 2)
                    | (sampleFlag.getHasRedundancy()));
        }
    }

    @Override
    public int byteArrayLength() {
        return 12 + this.flags.length;
    }

    @Override
    public byte[] toByteArray() {
        int size = this.byteArrayLength();
        return ByteWriteBuff.newInstance(size)
                .putInteger(size)
                .putBytes(this.mp4Type.getByteArray())
                .putBytes(this.reserved)
                .putBytes(this.flags)
                .getData();
    }
}
