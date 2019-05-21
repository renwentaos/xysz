package com.xws.xysz.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZipUtil {
    /**
     * <p>Description:使用gzip进行解压缩</p>
     *
     * @param compressedStr
     * @return
     */
    public static String gunzip(String compressedStr) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;

        // 对返回数据BASE64解码
        compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
        in = new ByteArrayInputStream(compressed);
        ginzip = new GZIPInputStream(in);

        // 解码后对数据gzip解压缩
        byte[] buffer = new byte[1024];
        int offset = -1;
        while ((offset = ginzip.read(buffer)) != -1) {
            out.write(buffer, 0, offset);
        }

        // 最后对数据进行utf-8转码
        decompressed = out.toString("utf-8");

        return decompressed;
    }
}
