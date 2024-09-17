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

package com.github.xingshuangs.iot.protocol.rtp.model.payload;


import com.github.xingshuangs.iot.common.IObjectByteArray;
import com.github.xingshuangs.iot.common.buff.ByteReadBuff;
import com.github.xingshuangs.iot.protocol.rtp.enums.EH264NaluType;
import com.github.xingshuangs.iot.utils.BooleanUtil;
import lombok.Data;

/**
 * Nalu的FU头
 * +---------------+
 * |0|1|2|3|4|5|6|7|
 * +-+-+-+-+-+-+-+-+
 * |S|E|R|  Type   |
 * +---------------+
 *
 * @author xingshuang
 */
@Data
public class H264NaluFuHeader implements IObjectByteArray {

    /**
     * 当设置成1,开始位指示分片NAL单元的开始。当跟随的FU荷载不是分片NAL单元荷载的开始，开始位设为0。
     */
    private boolean start;

    /**
     * 当设置成1, 结束位指示分片NAL单元的结束，即, 荷载的最后字节也是分片NAL单元的最后一个字节。
     * 当跟随的 FU荷载不是分片NAL单元的最后分片,结束位设置为0。
     */
    private boolean end;

    /**
     * 保留位必须设置为0，接收者必须忽略该位
     */
    private boolean reserve;

    /**
     * 表示 NALU 数据类型，该字段占 5 位，取值 0 ~ 31
     */
    private EH264NaluType type;

    @Override
    public int byteArrayLength() {
        return 1;
    }

    @Override
    public byte[] toByteArray() {
        byte res = (byte) (BooleanUtil.setBit(7, this.start)
                | BooleanUtil.setBit(6, this.end)
                | BooleanUtil.setBit(5, this.reserve)
                | (this.type.getCode() & 0x1F));
        return new byte[]{res};
    }

    /**
     * 字节数组数据解析
     *
     * @param data byte array
     * @return RtcpHeader
     */
    public static H264NaluFuHeader fromBytes(final byte[] data) {
        return fromBytes(data, 0);
    }

    /**
     * 字节数组数据解析
     *
     * @param data   byte array
     * @param offset index offset
     * @return RtcpHeader
     */
    public static H264NaluFuHeader fromBytes(final byte[] data, final int offset) {
        if (data.length < 1) {
            throw new IndexOutOfBoundsException("H264NaluFuHeader, data length < 1");
        }
        ByteReadBuff buff = new ByteReadBuff(data, offset);
        H264NaluFuHeader res = new H264NaluFuHeader();
        byte aByte = buff.getByte();
        res.start = BooleanUtil.getValue(aByte, 7);
        res.end = BooleanUtil.getValue(aByte, 6);
        res.reserve = BooleanUtil.getValue(aByte, 5);
        res.type = EH264NaluType.from(aByte & 0x1F);
        return res;
    }
}
