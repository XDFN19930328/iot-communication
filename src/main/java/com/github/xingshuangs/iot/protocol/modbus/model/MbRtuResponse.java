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

package com.github.xingshuangs.iot.protocol.modbus.model;


import com.github.xingshuangs.iot.exceptions.ModbusCommException;
import com.github.xingshuangs.iot.common.IObjectByteArray;
import com.github.xingshuangs.iot.common.buff.ByteReadBuff;
import com.github.xingshuangs.iot.common.buff.ByteWriteBuff;
import com.github.xingshuangs.iot.utils.CRCUtil;
import lombok.Data;

import java.util.Arrays;

/**
 * Modbus rtu response.
 * (RTU的modbus响应)
 *
 * @author xingshuang
 */
@Data
public class MbRtuResponse implements IObjectByteArray {

    /**
     * Unit id or slave id.
     * 单元标识符<br>
     * 字节大小：1个字节
     * 字节序数：0
     */
    private int unitId = 1;

    /**
     * PDU
     * (协议数据单元)
     */
    private MbPdu pdu;

    /**
     * CRC.
     * (循环冗余校验，最后两个字节)
     */
    private byte[] crc;

    @Override
    public int byteArrayLength() {
        return 3 + this.pdu.byteArrayLength();
    }

    @Override
    public byte[] toByteArray() {
        return ByteWriteBuff.newInstance(this.byteArrayLength())
                .putByte(this.unitId)
                .putBytes(this.pdu.toByteArray())
                .putBytes(this.crc)
                .getData();
    }

    /**
     * Check self.
     * (自我数据校验)
     */
    public void selfCheck() {
        if (this.pdu == null) {
            throw new ModbusCommException("pdu is null");
        }
        byte[] data = ByteWriteBuff.newInstance(1 + this.pdu.byteArrayLength())
                .putByte(this.unitId)
                .putBytes(this.pdu.toByteArray())
                .getData();
        this.crc = CRCUtil.crc16ToByteArray(data);
    }

    /**
     * Check crc.
     * (校验CRC)
     *
     * @return true：check success，false：check failed.
     */
    public boolean checkCrc() {
        if (this.pdu == null) {
            throw new ModbusCommException("pdu is null");
        }
        byte[] data = ByteWriteBuff.newInstance(1 + this.pdu.byteArrayLength())
                .putByte(this.unitId)
                .putBytes(this.pdu.toByteArray())
                .getData();
        return Arrays.equals(this.crc, CRCUtil.crc16ToByteArray(data));
    }


    /**
     * Parses byte array and converts it to object.
     * (解析字节数组数据)
     *
     * @param data byte array
     * @return MbRtuResponse
     */
    public static MbRtuResponse fromBytes(byte[] data) {
        return fromBytes(data, 0);
    }

    /**
     * Parses byte array and converts it to object.
     * (解析字节数组数据)
     *
     * @param data   byte array
     * @param offset index offset
     * @return MbRtuResponse
     */
    public static MbRtuResponse fromBytes(byte[] data, int offset) {
        MbRtuResponse response = new MbRtuResponse();
        ByteReadBuff buff = ByteReadBuff.newInstance(data, offset);
        response.unitId = buff.getByteToInt(0);
        response.pdu = MbPdu.fromBytes(data, 1);
        response.crc = buff.getBytes(1 + response.pdu.byteArrayLength(), 2);
        return response;
    }
}
