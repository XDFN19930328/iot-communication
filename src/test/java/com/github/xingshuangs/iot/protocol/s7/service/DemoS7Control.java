package com.github.xingshuangs.iot.protocol.s7.service;


import com.github.xingshuangs.iot.protocol.s7.enums.EDestinationFileSystem;
import com.github.xingshuangs.iot.protocol.s7.enums.EFileBlockType;
import com.github.xingshuangs.iot.protocol.s7.enums.EPlcType;

/**
 * @author xingshuang
 */
public class DemoS7Control {

    public static void main(String[] args) {
        S7PLC s7PLC = new S7PLC(EPlcType.S1200, "127.0.0.1");
        // upload file data, PLC -> PC, success in 200Smart
        byte[] bytes = s7PLC.uploadFile(EFileBlockType.OB, 1, EDestinationFileSystem.A);

        // FIXME: download file, but not test success
        // byte[] downloadFile = s7PLC.downloadFile(EFileBlockType.OB, 1, EDestinationFileSystem.P, 100, 100);

        // hot restart
        s7PLC.hotRestart();

        // cold restart
        s7PLC.coldRestart();

        // plc stop
        s7PLC.plcStop();

        // copy ram to rom
        s7PLC.copyRamToRom();

        // compress
        s7PLC.compress();

        s7PLC.close();
    }
}
