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

package com.github.xingshuangs.iot.protocol.melsec.model;


import com.github.xingshuangs.iot.protocol.melsec.enums.EMcCommand;
import com.github.xingshuangs.iot.protocol.melsec.enums.EMcSeries;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 软元件访问批量读请求数据，位单位
 *
 * @author xingshuang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class McReadDeviceBatchInBitReqData extends McReadDeviceBatchReqData {

    public McReadDeviceBatchInBitReqData() {
        this(EMcSeries.Q_L, new McDeviceAddress());
    }

    public McReadDeviceBatchInBitReqData(EMcSeries series) {
        this(series, new McDeviceAddress());
    }

    public McReadDeviceBatchInBitReqData(EMcSeries series, McDeviceAddress deviceAddress) {
        this.series = series;
        this.command = EMcCommand.DEVICE_ACCESS_BATCH_READ_IN_UNITS;
        this.subcommand = series != EMcSeries.IQ_R ? 0x0001 : 0x0003;
        this.deviceAddress = deviceAddress;
    }
}
