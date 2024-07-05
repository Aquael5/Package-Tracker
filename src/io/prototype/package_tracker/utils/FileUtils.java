package io.prototype.package_tracker.utils;

import java.io.*;
import java.util.Map;

public class FileUtils {
    public static void writeText(File path, String content) throws Exception {
        path.getParentFile().mkdirs();
        if (path.exists()) {
            path.delete();
        }
        FileOutputStream o = new FileOutputStream(path);
        o.write(content.getBytes("UTF-8"));
        o.flush();
        o.close();
    }

    public static String readText(File configFile) {
        try {
            FileInputStream in = new FileInputStream(configFile);
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 128];
            int len;
            while ((len = in.read(buffer)) != -1) {
                o.write(buffer, 0, len);
            }
            o.flush();
            o.close();
            in.close();
            return o.toString("UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeData(File path, InputStream stream) throws Exception {
        if (path.exists()) {
            path.delete();
        }
        path.getParentFile().mkdirs();
        FileOutputStream o = new FileOutputStream(path);
        byte[] buffer = new byte[1024*64];
        int len;
        while ((len = stream.read(buffer)) != -1) {
            o.write(buffer,0,len);
        }
        o.flush();
        o.close();
        stream.close();
    }
}
