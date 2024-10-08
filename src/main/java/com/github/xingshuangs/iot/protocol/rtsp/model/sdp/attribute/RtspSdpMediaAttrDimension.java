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

package com.github.xingshuangs.iot.protocol.rtsp.model.sdp.attribute;


import com.github.xingshuangs.iot.exceptions.RtspCommException;
import lombok.Data;

import static com.github.xingshuangs.iot.protocol.rtsp.constant.RtspCommonKey.COMMA;

/**
 * SDP media dimension.
 * 附加信息：维度
 * x-dimensions:2048,1536
 *
 * @author xingshuang
 */
@Data
public class RtspSdpMediaAttrDimension {

    /**
     * Width.
     * (宽度)
     */
    private Integer width;

    /**
     * Height
     * (高度)
     */
    private Integer height;

    public static RtspSdpMediaAttrDimension fromString(String src) {
        if (src == null || src.equals("")) {
            throw new IllegalArgumentException("MediaAttrDimension of SDP data error");
        }
        RtspSdpMediaAttrDimension dimension = new RtspSdpMediaAttrDimension();
        String[] split = src.split(COMMA);
        if (split.length != 2) {
            throw new RtspCommException("RtspSdpMediaAttrDimension data error");
        }
        dimension.width = Integer.parseInt(split[0]);
        dimension.height = Integer.parseInt(split[1]);
        return dimension;
    }
}
