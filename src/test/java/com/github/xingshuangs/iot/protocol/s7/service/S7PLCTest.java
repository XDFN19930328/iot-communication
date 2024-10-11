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

package com.github.xingshuangs.iot.protocol.s7.service;

import com.github.xingshuangs.iot.protocol.s7.enums.*;
import com.github.xingshuangs.iot.protocol.s7.model.Mc7File;
import com.github.xingshuangs.iot.utils.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@Ignore
public class S7PLCTest {
//        private S7PLC s7PLC = new S7PLC(EPlcType.S1200, "192.168.3.98");
//    private S7PLC s7PLC = new S7PLC(EPlcType.S1500, "192.168.3.103");
//    private S7PLC s7PLC = new S7PLC(EPlcType.S200_SMART, "192.168.3.102");
    private final S7PLC s7PLC = new S7PLC(EPlcType.S1200);

    @Before
    public void before() {
        this.s7PLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));
    }

    @Test
    public void readByRaw() {
        byte[] expect = new byte[]{(byte) 0x00};
        this.s7PLC.writeRaw(EParamVariableType.BIT, 1, EArea.DATA_BLOCKS, 1, 0, 3,
                EDataVariableType.BIT, expect);
        byte[] actual = this.s7PLC.readRaw(EParamVariableType.BIT, 1, EArea.DATA_BLOCKS, 1, 0, 3);
        assertArrayEquals(expect, actual);

        expect = new byte[]{(byte) 0x02, (byte) 0x03};
        this.s7PLC.writeRaw(EParamVariableType.BYTE, 2, EArea.DATA_BLOCKS, 1, 1, 0,
                EDataVariableType.BYTE_WORD_DWORD, expect);
        actual = this.s7PLC.readRaw(EParamVariableType.BYTE, 2, EArea.DATA_BLOCKS, 1, 1, 0);
        assertArrayEquals(expect, actual);
    }

    @Test
    public void readByRaw1() {
        byte[] actual = this.s7PLC.readRaw(EParamVariableType.BYTE, 4, EArea.DATA_BLOCKS, 4, 298, 0);
        System.out.println(actual);
    }

    @Test
    public void readByte() {
//        byte data = s7PLC.readByte("DB14.0.0");
        byte[] actual = s7PLC.readByte("DB14.0.1", 2);
        assertEquals(2, actual.length);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            try {
                s7PLC.readBoolean("DB14.2.0");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void readBoolean() {
        List<Boolean> actual = s7PLC.readBoolean("DB1.2.0", "DB1.2.1", "DB1.2.7");
        assertEquals(3, actual.size());
        s7PLC.readBoolean("DB1.2.0");
    }


    @Test
    public void writeBoolean() {
        s7PLC.writeBoolean("DB2.1.7", true);
        boolean res = s7PLC.readBoolean("DB2.1.7");
        assertTrue(res);

        List<Boolean> actual = s7PLC.readBoolean("DB2.2.0", "DB2.2.1", "DB2.2.7");
        assertEquals(3, actual.size());
    }

    @Test
    public void writeByte() {
        s7PLC.writeByte("DB2.0", (byte) 0x11);
        byte actual = s7PLC.readByte("DB2.0");
        assertEquals((byte) 0x11, actual);
    }

    @Test
    public void writeUInt16() {
        s7PLC.writeUInt16("DB2.0", 0x2222);
        int actual = s7PLC.readUInt16("DB2.0");
        assertEquals(0x2222, actual);
    }

    @Test
    public void writeInt16() {
        s7PLC.writeInt16("DB2.0", (short) 16);
        int i = s7PLC.readUInt16("DB2.0");
        assertEquals(16, i);

        List<Integer> actual = s7PLC.readUInt16("DB1.0", "DB1.2");
        assertEquals(2, actual.size());
    }

    @Test
    public void writeUInt32() {
        s7PLC.writeUInt32("DB2.0", 0x11111122);
        long actual = s7PLC.readUInt32("DB2.0");
        assertEquals(0x11111122L, actual);

        List<Long> actual1 = s7PLC.readUInt32("DB1.0", "DB1.4");
        assertEquals(2, actual1.size());
    }

    @Test
    public void writeInt32() {
        s7PLC.writeInt32("DB2.4", 0x11113322);
        int i = s7PLC.readInt32("DB2.4");
        assertEquals(0x11113322, i);
    }

    @Test
    public void writeInt64Test() {
        this.s7PLC.writeInt64("DB1.0", 1313513515314534100L);
        long b = this.s7PLC.readInt64("DB1.0");
        assertEquals(1313513515314534100L, b);
    }

    @Test
    public void writeFloat32() {
        s7PLC.writeFloat32("DB1.2", 12);
        float actual = s7PLC.readFloat32("DB1.2");
        assertEquals(12, actual, 0.00001);
    }

    @Test
    public void writeFloat64() {
        s7PLC.writeFloat64("DB2.10", 12.02);
        double actual = s7PLC.readFloat64("DB2.10");
        assertEquals(12.02, actual, 0.00001);
    }

    @Test
    public void writeString() {
//        s7PLC.writeString("DB1.80", "1234567");
        s7PLC.writeString("DB4.304", "百搭利器");
        String str = s7PLC.readString("DB4.304");
        assertEquals("百搭利器", str);
    }

    @Test
    public void writeStringS200Smart() {
        s7PLC.writeString("V10", "手动反馈和搜狐");
        String str = s7PLC.readString("V10");
        assertEquals("手动反馈和搜狐", str);
    }

    @Test
    public void writeTime() {
        s7PLC.writeTime("DB4.292", 1000);
        long actual = s7PLC.readTime("DB4.292");
        assertEquals(1000, actual);
    }

    @Test
    public void writeDate() {
        LocalDate expect = LocalDate.of(2023, 4, 1);
        s7PLC.writeDate("DB4.296", expect);
        LocalDate actual = s7PLC.readDate("DB4.296");
        assertEquals(expect, actual);
    }

    @Test
    public void writeTimeOfDay() {
        LocalTime expect = LocalTime.of(20, 15, 11);
        s7PLC.writeTimeOfDay("DB4.298", expect);
        LocalTime actual = s7PLC.readTimeOfDay("DB4.298");
        assertEquals(expect, actual);
    }

    @Test
    public void writeDTL() {
        LocalDateTime expect = LocalDateTime.of(2023, 5, 27, 12, 11, 22, 333225555);
        s7PLC.writeDTL("DB1.0", expect);

        LocalDateTime actual = s7PLC.readDTL("DB1.0");
        assertEquals(expect, actual);
    }

    @Test
    public void writeMultiData() {
        MultiAddressWrite addressWrite = new MultiAddressWrite();
        addressWrite.addUInt16("DB2.0", (byte) 0x11)
                .addUInt16("DB2.4", 88)
                .addBoolean("DB2.2.0", true);
        s7PLC.writeMultiData(addressWrite);
        boolean actual = s7PLC.readBoolean("DB2.2.0");
        assertTrue(actual);

        MultiAddressRead addressRead = new MultiAddressRead();
        addressRead.addData("DB2.0", 1)
                .addData("DB2.2", 3)
                .addData("DB2.3", 5);
        List<byte[]> list = s7PLC.readMultiByte(addressRead);
        assertEquals(1, list.get(0).length);
        assertEquals(3, list.get(1).length);
        assertEquals(5, list.get(2).length);
    }

    @Test
    public void writeMultiData2() {
        S7PLC s7PLC1 = new S7PLC(EPlcType.S1200);
        MultiAddressWrite addressWrite = new MultiAddressWrite();
        addressWrite.addString("DB1.0", "123456")
                .addString("DB1.10", "abcdefg");
        s7PLC1.writeMultiData(addressWrite);
        String actual = s7PLC1.readString("DB1.0");
        assertEquals("123456", actual);
        actual = s7PLC1.readString("DB1.10");
        assertEquals("abcdefg", actual);
        s7PLC1.close();

        s7PLC1 = new S7PLC(EPlcType.S200_SMART);
        MultiAddressRead addressRead = new MultiAddressRead();
        addressWrite.addStringIn200Smart("DB2.0", "123456")
                .addStringIn200Smart("DB2.10", "abcdefg");
        s7PLC1.writeMultiData(addressWrite);
        actual = s7PLC1.readString("DB2.0");
        assertEquals("123456", actual);
        actual = s7PLC1.readString("DB2.10");
        assertEquals("abcdefg", actual);
        s7PLC1.close();
    }

    @Test
    public void writeMultiData1() {
//        s7PLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));
        MultiAddressRead addressRead = new MultiAddressRead();
        addressRead.addData("DB2.0", 1)
                .addData("DB2.1", 1)
                .addData("DB2.2", 1)
                .addData("DB2.3", 1)
                .addData("DB2.4", 1);
        List<byte[]> list = s7PLC.readMultiByte(addressRead);

        MultiAddressWrite addressWrite = new MultiAddressWrite();
        addressWrite.addByte("DB2.0", (byte) 0x01)
                .addByte("DB2.1", (byte) 0x02)
                .addByte("DB2.2", (byte) 0x03)
                .addByte("DB2.3", (byte) 0x04)
                .addByte("DB2.4", (byte) 0x05);
        s7PLC.writeMultiData(addressWrite);

//        MultiAddressRead addressRead = new MultiAddressRead();
//        addressRead.addData("V0", 1)
//                .addData("V1", 1)
//                .addData("V2", 1)
//                .addData("V3", 1)
//                .addData("V4", 1);
//        List<byte[]> list = s7PLC.readMultiByte(addressRead);
//
//        MultiAddressWrite addressWrite = new MultiAddressWrite();
//        addressWrite.addByte("V0", (byte) 0x01)
//                .addByte("V1", (byte) 0x02)
//                .addByte("V2", (byte) 0x03)
//                .addByte("V3", (byte) 0x04)
//                .addByte("V4", (byte) 0x05);
//        s7PLC.writeMultiData(addressWrite);

//        MultiAddressWrite addressWrite = new MultiAddressWrite();
////        for (int i = 0; i < 53; i++) {
////            addressWrite.addBoolean(String.format("DB1.%d.0", i), true);
////        }
////        s7PLC.writeMultiData(addressWrite);
//
//        addressWrite = new MultiAddressWrite();
//        for (int i = 0; i < 100; i++) {
//            addressWrite.addByte(String.format("DB1.%d", i), (byte) i);
//        }
//        s7PLC.writeMultiData(addressWrite);

//        addressWrite = new MultiAddressWrite();
//        for (int i = 0; i < 100; i++) {
//            addressWrite.addUInt16(String.format("DB1.%d", i * 2), i);
//        }
//        s7PLC.writeMultiData(addressWrite);

//        addressWrite = new MultiAddressWrite();
//        for (int i = 0; i < 53; i++) {
//            addressWrite.addUInt32(String.format("DB1.%d", i * 4), i);
//        }
//        s7PLC.writeMultiData(addressWrite);

//        addressWrite = new MultiAddressWrite();
//        for (int i = 0; i < 100; i++) {
//            addressWrite.addFloat32(String.format("DB1.%d", i * 4), i);
//        }
//        s7PLC.writeMultiData(addressWrite);

//        addressWrite = new MultiAddressWrite();
//        for (int i = 0; i < 100; i++) {
//            addressWrite.addFloat64(String.format("DB1.%d", i * 8), i);
//        }
//        s7PLC.writeMultiData(addressWrite);
    }

    @Test
    public void readMultiData() {

        MultiAddressRead addressRead = new MultiAddressRead();
//        addressRead.addData("DB2.0", 1)
//                .addData("DB2.2", 3)
//                .addData("DB2.1", 208);
//        List<byte[]> list = s7PLC.readMultiByte(addressRead);

//        addressRead = new MultiAddressRead();
//        for (int i = 0; i < 40; i++) {
//            addressRead.addData(String.format("DB1.%d", i), 1);
//        }
//        s7PLC.readMultiByte(addressRead);
        addressRead = new MultiAddressRead();
        for (int i = 0; i < 100; i++) {
            addressRead.addData(String.format("DB1.%d", i), 1);
        }
        s7PLC.readMultiByte(addressRead);
//        MultiAddressRead addressRead = new MultiAddressRead();
//        addressRead.addData("DB2.1", 222);
//        List<byte[]> list = s7PLC.readMultiByte(addressRead);

//        byte[] bytes = s7PLC.readByte("DB2.1", 240);
    }

    @Test
    public void readByteData() {
        s7PLC.setComCallback((tag, bytes) -> System.out.printf("%s[%d] %s%n", tag, bytes.length, HexUtil.toHexString(bytes)));
        byte[] bytes = s7PLC.readByte("DB2.12", 1000);
        long start = System.currentTimeMillis();
        s7PLC.writeByte("DB2.12", bytes);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void writeI() {
        s7PLC.writeBoolean("I0.5", true);

        List<Boolean> booleans = s7PLC.readBoolean("I0.0", "I0.1", "I0.2", "I0.3", "I0.4", "I0.5");
        assertEquals(6, booleans.size());
        System.out.println(booleans);

    }

    @Test
    public void writeQ() {
        s7PLC.writeBoolean("Q0.7", true);
        boolean b = s7PLC.readBoolean("Q0.7");
        assertTrue(b);

        List<Boolean> booleans = s7PLC.readBoolean("Q0.0", "Q0.1", "Q0.2", "Q0.3", "Q0.4", "Q0.5", "Q0.6", "Q0.7");
        System.out.println(booleans);
    }

    @Test
    public void writeM() {
        s7PLC.writeBoolean("M0.4", true);
        boolean b = s7PLC.readBoolean("M0.4");
        assertTrue(b);

        s7PLC.writeByte("M0", (byte) 0x33);
        byte m1 = s7PLC.readByte("M0");
        assertEquals((byte) 0x33, m1);

        List<Boolean> booleans = s7PLC.readBoolean("M1.0", "M1.1", "M1.2", "M1.3", "M1.4", "M1.5", "M1.6", "M1.7");
        System.out.println(booleans);
    }

    @Test
    public void writeV() {
        s7PLC.writeBoolean("V1.4", true);
        boolean b = s7PLC.readBoolean("V1.4");
        assertTrue(b);

        s7PLC.writeByte("V2", (byte) 0x34);
        byte v2 = s7PLC.readByte("V2");
        assertEquals((byte) 0x34, v2);
    }

    @Test
    public void persistence() {
        s7PLC.setPersistence(false);
        s7PLC.writeByte("DB2.1", (byte) 0x11);
        byte actual = s7PLC.readByte("DB2.1");
        assertEquals((byte) 0x11, actual);
        s7PLC.writeByte("DB2.1", (byte) 0x11);
        actual = s7PLC.readByte("DB2.1");
        assertEquals((byte) 0x11, actual);
    }

    //region 200smart上传下载
    @Test
    public void downloadFileDB1() {
        byte[] bytes = this.s7PLC.uploadFile(EFileBlockType.DB, 1);
        System.out.println(bytes.length);
        this.s7PLC.plcStop();
        Mc7File mc7File = Mc7File.fromBytes(bytes);
        this.s7PLC.downloadFile(mc7File);
        this.s7PLC.insert(mc7File.getBlockType(), mc7File.getBlockNumber());
        this.s7PLC.hotRestart();
    }

    @Test
    public void downloadFileSDB0() {
        byte[] bytes = this.s7PLC.uploadFile(EFileBlockType.SDB, 0);
        System.out.println(bytes.length);
        this.s7PLC.plcStop();
        Mc7File mc7File = Mc7File.fromBytes(bytes);
        this.s7PLC.downloadFile(mc7File);
        this.s7PLC.insert(mc7File.getBlockType(), mc7File.getBlockNumber());
        this.s7PLC.hotRestart();
    }

    @Test
    public void downloadFileOB1() {
        byte[] bytes = this.s7PLC.uploadFile(EFileBlockType.OB, 1);
        System.out.println(bytes.length);
        this.s7PLC.plcStop();
        Mc7File mc7File = Mc7File.fromBytes(bytes);
        this.s7PLC.downloadFile(mc7File);
        this.s7PLC.insert(mc7File.getBlockType(), mc7File.getBlockNumber());
        this.s7PLC.hotRestart();
    }

    @Test
    public void uploadFileOB1() throws IOException {
        byte[] bytes = this.s7PLC.uploadFile(EFileBlockType.OB, 1);
        System.out.println(bytes.length);
        // 长度：145，mc7长度：16
        String fileName = "G:\\Study\\Protocol\\S7\\mc7File\\OB1.mc7";
        this.writeDataToFile(fileName, bytes, false);
    }

    @Test
    public void uploadFileSDB0() throws IOException {
        byte[] bytes = this.s7PLC.uploadFile(EFileBlockType.SDB, 0);
        System.out.println(bytes.length);
        // 长度：530，MC7长度：494
        String fileName = "G:\\Study\\Protocol\\S7\\mc7File\\SDB0.mc7";
        this.writeDataToFile(fileName, bytes, false);
    }

    @Test
    public void uploadFileDB1() throws IOException {
        byte[] bytes = this.s7PLC.uploadFile(EFileBlockType.DB, 1);
        System.out.println(bytes.length);
        // 长度：71，MC7长度：0
        String fileName = "G:\\Study\\Protocol\\S7\\mc7File\\DB1.mc7";
        this.writeDataToFile(fileName, bytes, false);
    }

    public void writeDataToFile(String fileName, byte[] bytes, boolean append) throws IOException {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName, append))) {
            out.write(bytes);
        }
    }
    //endregion
}