package com.matrix.proxy.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author luqiang
 */
public class GzipUtils {
    private static Logger logger = LoggerFactory.getLogger(GzipUtils.class);

    /**
     * 压缩字符串
     *
     * @param body 压缩的字符串
     * @return 压缩后的字符串
     */
    public static String compress(String body) {
        if (StringUtils.isEmpty(body)) {
            return body;
        }

        try {
            ByteArrayOutputStream outputStream = compressToStream(body);
            if (outputStream != null) {
                // 通过解码字节将缓冲区内容转换为字符串
                return new String(outputStream.toByteArray(), "ISO-8859-1");
            }

        } catch (Exception e) {
            logger.warn("GZIP compress 压缩失败，使用源文件", e);
        }

        return body;


    }

    /**
     * 压缩字符串
     *
     * @param body 压缩的字符串
     * @return 压缩后的字符串
     */
    public static ByteArrayOutputStream compressToStream(String body) {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                GZIPOutputStream os = new GZIPOutputStream(bos);
        ) {
            // 写入输出流
            os.write(body.getBytes());
            return bos;
        } catch (IOException e) {
            logger.warn("GZIP compressToStream 压缩失败，使用源文件", e);

        }
        return null;
    }


    /**
     * 解压缩字符串
     *
     * @param body 解压缩的字符串
     * @return 解压后的字符串
     */
    public static String decompress(String body) {

        if (StringUtils.isEmpty(body)) {
            return body;
        }


        byte[] buf = new byte[1024];
        int len = 0;
        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes("ISO-8859-1"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                GZIPInputStream is = new GZIPInputStream(bis);
        ) {
            // 将未压缩数据读入字节数组
            while ((len = is.read(buf)) != -1) {
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此byte数组输出流
                bos.write(buf, 0, len);
            }
            // 通过解码字节将缓冲区内容转换为字符串
            return new String(bos.toByteArray());
        } catch (Exception e) {
            logger.warn("GZIP 解压失败，使用源文件", e);
            return body;
        }
    }
}

