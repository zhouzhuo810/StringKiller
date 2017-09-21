package me.zhouzhuo810.stringkiller.utils;

import java.io.*;

/**
 * Created by zz on 2017/9/20.
 */
public class FileUtils {

    public static void replaceContentToFile(String path, String con) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(con);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void closeQuietly(Closeable c) {
        if(c != null) {
            try {
                c.close();
            } catch (IOException var2) {
                ;
            }
        }

    }

}
