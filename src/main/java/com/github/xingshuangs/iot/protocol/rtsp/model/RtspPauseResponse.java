package com.github.xingshuangs.iot.protocol.rtsp.model;


import com.github.xingshuangs.iot.exceptions.RtspCommException;
import com.github.xingshuangs.iot.protocol.rtsp.model.base.RtspSessionInfo;
import lombok.Getter;

import java.util.Map;

import static com.github.xingshuangs.iot.protocol.rtsp.constant.RtspCommonKey.SESSION;

/**
 * Setup响应
 *
 * @author xingshuang
 */
@Getter
public class RtspPauseResponse extends RtspMessageResponse {

    /**
     * 特殊的会话信息
     */
    private RtspSessionInfo sessionInfo;

    public static RtspPauseResponse fromHeaderString(String src) {
        if (src == null || src.equals("")) {
            throw new RtspCommException("解析RtspPlayResponse时字符串为空");
        }
        RtspPauseResponse response = new RtspPauseResponse();
        Map<String, String> map = response.parseHeaderAndReturnMap(src);

        // 会话ID
        if (map.containsKey(SESSION)) {
            response.sessionInfo = RtspSessionInfo.fromString(map.get(SESSION).trim());
            response.session = response.sessionInfo.getSessionId();
        }
        return response;
    }

}
