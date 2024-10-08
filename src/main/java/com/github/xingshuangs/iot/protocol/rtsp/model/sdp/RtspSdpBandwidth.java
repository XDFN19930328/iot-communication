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

package com.github.xingshuangs.iot.protocol.rtsp.model.sdp;


import com.github.xingshuangs.iot.exceptions.RtspCommException;
import lombok.Data;

import static com.github.xingshuangs.iot.protocol.rtsp.constant.RtspCommonKey.COLON;

/**
 * SDP bandwidth
 * 带宽
 * AS:5050
 *
 * @author xingshuang
 */
@Data
public class RtspSdpBandwidth {

    /**
     * Type
     * 带宽类型
     * i）CT（Conference Total）表示多会话广播中会话或者媒体使用的最大带宽建议值，CT值相当于所有会话带宽值。
     * ii）AS(Application-Specific)是指具体某个应用程序所占用的总带宽建议值，相当于最大应用程序带宽值，它仅值单媒体在单点所占用的带宽。
     */
    private String type;

    /**
     * Value
     * 带宽值
     */
    private Integer value;

    public static RtspSdpBandwidth fromString(String src) {
        if (src == null || src.equals("")) {
            throw new IllegalArgumentException("The SDP failed to parse the Bandwidth part of the data source");
        }
        RtspSdpBandwidth bandwidth = new RtspSdpBandwidth();
        String[] split = src.split(COLON);
        if (split.length != 2) {
            throw new RtspCommException("RtspSdpBandwidth data is incorrect and cannot be resolved");
        }
        bandwidth.type = split[0];
        bandwidth.value = Integer.parseInt(split[1]);
        return bandwidth;
    }
}
