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

package com.github.xingshuangs.iot.protocol.rtsp.model;


import com.github.xingshuangs.iot.protocol.rtsp.authentication.AbstractAuthenticator;
import com.github.xingshuangs.iot.protocol.rtsp.enums.ERtspAcceptContent;
import com.github.xingshuangs.iot.protocol.rtsp.enums.ERtspMethod;
import lombok.Getter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.xingshuangs.iot.protocol.rtsp.constant.RtspCommonKey.*;
import static com.github.xingshuangs.iot.protocol.rtsp.constant.RtspRequestHeaderFields.ACCEPT;

/**
 * Describe request
 *
 * @author xingshuang
 */
@Getter
public final class RtspDescribeRequest extends RtspMessageRequest {

    private List<ERtspAcceptContent> acceptContents = new ArrayList<>();

    public RtspDescribeRequest(URI uri) {
        super(ERtspMethod.DESCRIBE, uri);
    }

    public RtspDescribeRequest(URI uri, List<ERtspAcceptContent> acceptContents) {
        super(ERtspMethod.DESCRIBE, uri);
        this.acceptContents = acceptContents;
    }

    public RtspDescribeRequest(URI uri, List<ERtspAcceptContent> acceptContents, AbstractAuthenticator authenticator) {
        super(ERtspMethod.DESCRIBE, uri, authenticator);
        this.acceptContents = acceptContents;
    }

    @Override
    protected void addRequestHeader(StringBuilder sb) {
        if (!this.acceptContents.isEmpty()) {
            sb.append(ACCEPT)
                    .append(COLON + SP)
                    .append(this.acceptContents.stream().map(ERtspAcceptContent::getCode).collect(Collectors.joining(COMMA)))
                    .append(CRLF);
        }
    }
}
