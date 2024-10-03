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

package com.github.xingshuangs.iot.protocol.rtcp.model;


import com.github.xingshuangs.iot.common.IObjectByteArray;
import com.github.xingshuangs.iot.common.buff.ByteReadBuff;
import com.github.xingshuangs.iot.common.buff.ByteWriteBuff;
import com.github.xingshuangs.iot.protocol.rtcp.enums.ERtcpSdesItemType;
import lombok.Data;

/**
 * @author xingshuang
 */
@Data
public class RtcpSdesItem implements IObjectByteArray {

    /**
     * RTCP sdes item type.
     */
    private ERtcpSdesItemType type;

    /**
     * Text length.
     */
    private int length;

    /**
     * Text content.
     */
    private String text = "";

    @Override
    public int byteArrayLength() {
        return 2 + this.text.length();
    }

    @Override
    public byte[] toByteArray() {
        return ByteWriteBuff.newInstance(2 + this.text.length())
                .putByte(this.type.getCode())
                .putByte(this.length)
                .putString(this.text)
                .getData();
    }

    /**
     * Parses byte array and converts it to object.
     *
     * @param data byte array
     * @return RtcpHeader
     */
    public static RtcpSdesItem fromBytes(final byte[] data) {
        return fromBytes(data, 0);
    }

    /**
     * Parses byte array and converts it to object.
     *
     * @param data   byte array
     * @param offset index offset
     * @return RtcpHeader
     */
    public static RtcpSdesItem fromBytes(final byte[] data, final int offset) {
        if (data.length < 2) {
            throw new IndexOutOfBoundsException("RtcpSdesItem, data length < 2");
        }
        ByteReadBuff buff = new ByteReadBuff(data, offset);
        RtcpSdesItem res = new RtcpSdesItem();
        res.type = ERtcpSdesItemType.from(buff.getByte());
        res.length = buff.getByteToInt();
        res.text = buff.getString(res.length);
        return res;
    }
}
