package org.hengsir.websocket.coder;

import io.netty.util.AsciiString;

/**
 * @author hengsir
 * @date 2018/6/20 下午5:08
 */
public class MessageLengthUtil {
    public MessageLengthUtil() {
    }

    public static byte[] toAsicii(int length) {
        return AsciiString.of(String.format("%04d", length)).array();
    }

    public static int toInt(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException("ascii bytes length is not 4.");
        } else {
            AsciiString asciiString = new AsciiString(bytes);
            return asciiString.parseInt();
        }
    }
}

