package com.shu.homework.util;

import java.io.*;

public class FileUtils {
    //文件名正则校验
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    public static void writeBytes(String filePath, OutputStream os) {
        FileInputStream fi = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fi = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fi.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fi != null) {
                try {
                    fi.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 是否成功
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名校验
     * @param fileName 文件名
     * @return true 正常， false 非法
     */
    public static boolean isValidName(String fileName) {
        return fileName.matches(FILENAME_PATTERN);
    }


}
