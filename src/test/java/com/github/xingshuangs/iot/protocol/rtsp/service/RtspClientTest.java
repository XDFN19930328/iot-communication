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

package com.github.xingshuangs.iot.protocol.rtsp.service;

import com.github.xingshuangs.iot.protocol.rtp.model.frame.H264VideoFrame;
import com.github.xingshuangs.iot.protocol.rtsp.authentication.DigestAuthenticator;
import com.github.xingshuangs.iot.protocol.rtsp.authentication.UsernamePasswordCredential;
import com.github.xingshuangs.iot.protocol.rtsp.enums.ERtspTransportProtocol;
import com.github.xingshuangs.iot.utils.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Ignore
@Slf4j
public class RtspClientTest {

    @Test
    public void connectUdp() {
        URI uri = URI.create("rtsp://192.168.3.142:554/h264/ch1/main/av_stream");
        UsernamePasswordCredential credential = new UsernamePasswordCredential("admin", "kilox1234");
        DigestAuthenticator authenticator = new DigestAuthenticator(credential);
        RtspClient client = new RtspClient(uri, authenticator, ERtspTransportProtocol.UDP);
        client.onCommCallback(log::info);
        client.onFrameHandle(x -> {
            H264VideoFrame f = (H264VideoFrame) x;
            log.debug(f.getFrameType() + ", " + f.getNaluType() + ", " + f.getTimestamp() + ", " + f.getFrameSegment().length);
        });
        client.onDestroyHandle(() -> log.debug("close"));
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{}", client.getTrackInfo());
            client.stop();
        });
        CompletableFuture<Void> future = client.start();
        while (!future.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void connectTcp() {
        URI uri = URI.create("rtsp://192.168.3.142:554/h264/ch1/main/av_stream");
        UsernamePasswordCredential credential = new UsernamePasswordCredential("admin", "kilox1234");
        DigestAuthenticator authenticator = new DigestAuthenticator(credential);
        RtspClient client = new RtspClient(uri, authenticator, ERtspTransportProtocol.TCP);
        client.onCommCallback(System.out::println);
        client.onFrameHandle(x -> {
            H264VideoFrame f = (H264VideoFrame) x;
            log.debug(f.getFrameType() + ", " + f.getNaluType() + ", " + f.getTimestamp() + ", " + f.getFrameSegment().length);
        });
        client.onDestroyHandle(() -> log.debug("close"));
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("{}", client.getTrackInfo());
            client.stop();
        });
        CompletableFuture<Void> future = client.start();
        while (!future.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void connectUdpWithoutAuthenticator() {
        List<H264VideoFrame> list = new ArrayList<>();
        URI uri = URI.create("rtsp://192.168.3.36:8554/back");
        RtspClient client = new RtspClient(uri, ERtspTransportProtocol.TCP);
        client.onCommCallback(log::info);
        client.onFrameHandle(x -> {
            H264VideoFrame f = (H264VideoFrame) x;
            list.add(f);
            H264VideoFrame tmp = null;
            if (list.size() >= 5) {
                list.sort((a, b) -> (int) (a.getTimestamp() - b.getTimestamp()));
                tmp = list.remove(0);
//                log.debug(f.getTimestamp() + ", " + HexUtil.toHexString(new byte[]{f.getFrameSegment()[0]}) + tmp.getTimestamp() + ", " + HexUtil.toHexString(new byte[]{tmp.getFrameSegment()[0]}));
            }
            if (tmp == null) {
                log.debug(f.getTimestamp() + ", " + HexUtil.toHexString(new byte[]{f.getFrameSegment()[0]}));
            } else {
                log.debug(f.getTimestamp() + ", " + HexUtil.toHexString(new byte[]{f.getFrameSegment()[0]}) + ", " + tmp.getTimestamp() + ", " + HexUtil.toHexString(new byte[]{tmp.getFrameSegment()[0]}));
            }
        });
        client.onDestroyHandle(() -> log.debug("close"));
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            client.stop();
        });
        CompletableFuture<Void> future = client.start();
        while (!future.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}