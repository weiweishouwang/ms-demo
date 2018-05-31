package com.zhw.ms.commons.utils;

import com.zhw.ms.commons.consts.JccConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IO处理共通
 * <p>
 * Created by ZHW on 2015/5/11.
 */
public class IOUtil {

    private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    /**
     * 获取文件类型
     *
     * @param file 文件
     * @return 文件类型 file type
     */
    public static String getFileType(File file) {
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     *
     * @param fileName 文件名
     * @return 文件类型 file type
     */
    public static String getFileType(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return JccConst.EMPTY;
        } else {
            return fileName.substring(index + 1);
        }
    }

    /**
     * 关闭输出流
     *
     * @param stream 输出流
     */
    public static void closeOutputStream(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param stream 输入流
     */
    public static void closeInputStream(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param reader the reader
     */
    public static void closeReader(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param writer the writer
     */
    public static void closeWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 获取文件的二进制数据
     *
     * @param path the path
     * @return byte [ ]
     */
    public static byte[] getFile(String path) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            out = new ByteArrayOutputStream(1024 * 1024 * 10);

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }

            return out.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeInputStream(in);
            closeOutputStream(out);
        }
    }

    /**
     * 获取文件的文本内容
     *
     * @param path the path
     * @return string
     */
    public static String readFile(String path) {
        BufferedReader br = null;
        try {
            return getText(new FileInputStream(path));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeReader(br);
        }
    }

    /**
     * 保存文件
     *
     * @param path the path
     * @param data the data
     * @return boolean
     */
    public static Boolean saveFile(String path, byte[] data) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            bos.write(data);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            closeOutputStream(bos);
        }
    }

    /**
     * 保存文件
     *
     * @param dir  the dir
     * @param name the name
     * @param data the data
     * @return boolean
     */
    public static Boolean saveFile(String dir, String name, byte[] data) {
        BufferedOutputStream bos = null;
        try {
            if (!exist(dir)) {
                IOUtil.createDir(dir);
            }

            bos = new BufferedOutputStream(new FileOutputStream(dir + File.separator + name));
            bos.write(data);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            closeOutputStream(bos);
        }
    }

    /**
     * 删除文件
     *
     * @param path the path
     * @return boolean
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param sourPath the sour path
     * @param destPath the dest path
     */
    public static void copyFile(String sourPath, String destPath) {
        saveFile(destPath, getFile(sourPath));
    }

    /**
     * 获取临时文件名
     *
     * @param fileName the file name
     * @return tmp file name
     */
    public static String getTmpFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        StringBuilder sb = new StringBuilder();

        if (index < 0) {
            sb.append(fileName).append("_");
            sb.append(System.currentTimeMillis());
        } else {
            sb.append(fileName.substring(0, index)).append("_");
            sb.append(System.currentTimeMillis());
            sb.append(fileName.substring(index));
        }

        return sb.toString();
    }

    /**
     * 获取文件名
     *
     * @param path the path
     * @return file name
     */
    public static String getFileName(String path) {
        if (StringUtils.isBlank(path)) {
            return JccConst.EMPTY;
        } else {
            int index = path.lastIndexOf("\\");
            if (index > -1) {
                return path.substring(index + 1);
            } else {
                return path;
            }
        }
    }

    /**
     * 新建文件夹
     *
     * @param path the path
     * @return boolean
     */
    public static Boolean createDir(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        } else {
            File dir = new File(path);
            if (dir.exists()) {
                return true;
            } else {
                return dir.mkdirs();
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path the path
     * @return boolean
     */
    public static Boolean exist(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        } else {
            File file = new File(path);
            file.list();
            return file.exists();
        }
    }

    /**
     * 获取文件夹下的文件名
     *
     * @param dir           the dir
     * @param withOutPrefix the with out prefix
     * @return file name
     */
    public static List<String> getFileName(String dir, String withOutPrefix) {
        if (!StringUtils.isBlank(dir)) {
            File file = new File(dir);
            String[] fileList = file.list();

            if (fileList != null) {
                List<String> files = new ArrayList<String>();

                for (int i = 0; i < fileList.length; ++i) {
                    if (!fileList[i].startsWith(withOutPrefix)) {
                        files.add(fileList[i]);
                    }
                }

                return files;
            }
        }

        return new ArrayList<String>();
    }

    /**
     * 获取文件夹下的文件路径
     *
     * @param dir           the dir
     * @param withOutPrefix the with out prefix
     * @return file path
     */
    public static List<String> getFilePath(String dir, String withOutPrefix) {
        if (!StringUtils.isBlank(dir)) {
            File file = new File(dir);
            File[] fileList = file.listFiles();

            if (fileList != null) {
                List<String> files = new ArrayList<String>();

                for (int i = 0; i < fileList.length; ++i) {
                    if (!fileList[i].getName().startsWith(withOutPrefix)) {
                        files.add(fileList[i].getPath());
                    }
                }

                return files;
            }
        }

        return new ArrayList<String>();
    }

    /**
     * 获取文件所在的目录
     *
     * @param filePath the file path
     * @return dir
     */
    public static String getDir(String filePath) {
        if (!StringUtils.isBlank(filePath)) {
            File file = new File(filePath);
            return file.getParent();
        }

        return StringUtils.EMPTY;
    }

    /**
     * 获取文本
     *
     * @param is the is
     * @return text
     */
    public static String getText(InputStream is) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, JccConst.CHARSET_UTF8));
            StringBuilder sb = new StringBuilder();

            while (true) {
                char[] data = new char[1024];
                int count = br.read(data, 0, 1024);
                if (count <= 0) {
                    break;
                } else {
                    sb.append(data);
                }
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeReader(br);
        }
    }

}
