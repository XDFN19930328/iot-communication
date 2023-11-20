package com.github.xingshuangs.iot.protocol.melsec.model;


import com.github.xingshuangs.iot.protocol.common.buff.ByteWriteBuff;
import com.github.xingshuangs.iot.protocol.melsec.enums.EMcCommand;
import lombok.Data;

/**
 * 软元件访问批量读请求数据
 *
 * @author xingshuang
 */
@Data
public class McReadDeviceBatchReqData extends McReqData {

    /**
     * 软元件设备地址
     */
    private McDeviceAddress deviceAddress;

    public McReadDeviceBatchReqData() {
    }

    public McReadDeviceBatchReqData(EMcCommand command, int subcommand, McDeviceAddress deviceAddress) {
        this.command = command;
        this.subcommand = subcommand;
        this.deviceAddress = deviceAddress;
    }

    @Override
    public int byteArrayLength() {
        return 4 + this.deviceAddress.byteArrayLengthWithPointsCount();
    }

    @Override
    public byte[] toByteArray() {
        return ByteWriteBuff.newInstance(this.byteArrayLength(), true)
                .putShort(this.command.getCode())
                .putShort(this.subcommand)
                .putBytes(this.deviceAddress.toByteArrayWithPointsCount())
                .getData();
    }
}
