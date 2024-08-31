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

package com.github.xingshuangs.iot.protocol.rtsp.model.base;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 范围
 *
 * @author xingshuang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RtspRangeNpt extends RtspRange {

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public RtspRangeNpt() {
        this(null, null);
    }

    public RtspRangeNpt(String startTime) {
        this(startTime, null);
    }

    public RtspRangeNpt(String startTime, String endTime) {
        this.type = "npt";
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        if ((this.startTime == null || this.startTime.equals("")) && (this.endTime == null || this.endTime.equals(""))) {
            return "";
        }
        if ((this.startTime != null && !this.startTime.equals("")) && (this.endTime == null || this.endTime.equals(""))) {
            return String.format("%s=%s-", this.type, this.startTime);
        }
        if ((this.startTime != null && !this.startTime.equals("")) && (this.endTime != null && !this.endTime.equals(""))) {
            return String.format("%s=&s-%s", this.type, this.startTime, this.endTime);
        }
        return "";
    }
}
