package me.zhouzhuo810.stringkiller.utils;

import java.io.*;

/**
 * Created by zz on 2017/9/20.
 */
public class FileUtils {

    /***
     * 方法：
     * @Title: replaceContentToFile
     * @Description: TODO
     * @param @param path 文件
     * @param @param con  追加的文本
     * @return void    返回类型
     * @throws
     */
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

}
