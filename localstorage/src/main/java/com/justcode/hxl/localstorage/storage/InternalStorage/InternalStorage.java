package com.justcode.hxl.localstorage.storage.InternalStorage;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 内部存储
 * <p>
 * <p>当我们在打开DDMS下的File Explorer面板的时候，/data目录就是所谓的内部存储 (ROM )。 但是注意，当手机没有root的时候不能打开此文件夹。</p>
 * <p>
 * <li>'/data/app' :--> app文件夹里存放着我们所有安装的app的apk文件</li> <br/>
 * <p>
 * <li>'/data/data':-->第二个文件夹是data,也就是我们常说的/data/data目录(存储包私有数据)</li>
 * <p>
 * <li>内部数据：/data/data/包名/XXX</li>
 * <p>
 * //此目录下将每一个APP的存储内容按照包名分类存放好。 比如:
 * <p>
 * <li>1.data/data/包名/shared_prefs 存放该APP内的SP信息
 * <p>
 * <li>2.data/data/包名/databases 存放该APP的数据库信息
 * <p>
 * <li>3.data/data/包名/files 将APP的文件信息存放在files文件夹
 * <p>
 * <li>/4.data/data/包名/cache 存放的是APP的缓存信息
 */
public class InternalStorage {

    /**
     * 获取在其中存储内部文件的文件系统目录的绝对路径。
     *
     * @return /data/data/包名/files
     */
    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * @return 返回您的应用当前保存的一系列文件
     */
    public static String[] getFileList(Context context) {
        return context.fileList();
    }

    /**
     * @param name 文件名
     * @return 删除保存在内部存储的文件。
     */
    public static boolean deleteFile(Context context, String name) {
        return context.deleteFile(name);
    }

    /**
     * 向指定的文件中写入指定的数据(没有该文件会自动创建)
     *
     * @param name    文件名
     * @param content 文件内容
     * @param mode    MODE_PRIVATE | MODE_APPEND 自 API 级别 17 以来，常量 MODE_WORLD_READABLE 和
     *                MODE_WORLD_WRITEABLE 已被弃用
     *                <p>
     *                Context.MODE_APPEND    私有（只有创建此文件的程序能够使用，其他应用程序不能访问），在原有内容基础上增加数据
     *                Context.MODE_PRIVATE   私有，每次打开文件都会覆盖原来的内容
     *                Context.MODE_WORLD_READABLE 可以被其他应用程序读取
     *                Context.MODE_WORLD_WRITEABLE 可以被其他应用程序写入
     *                默认可以使用Context.MODE_PRIVATE
     *
     *                注意，这里的name就是文件名称，不要含有路径，因为，context.openFileOutput，是打开系统默认的一个路径下的文件，
     *                如果手动添加路径，会抛出异常 java.lang.IllegalArgumentException  contains a path separator
     */
    public static void writeFileData(Context context, String name, String content, int mode) {
        FileOutputStream fos = null;
        if (TextUtils.isEmpty(content)) {
            content = "您输入的内容为null，默认给您显示了这句话";
        }
        if (TextUtils.isEmpty(name)) {
            name = "InternalStorage.txt";
        }
        try {
            fos = context.openFileOutput(name, mode);
            fos.write(content.getBytes());
        } catch (IOException e) {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开指定文件，读取其数据，返回字符串对象
     */
    public static String readFileData(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = "InternalStorage.txt";
        }

        String result = "";
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            //获取文件长度
            int length = fis.available();
            byte[] buffer = new byte[length];
            if (fis.read(buffer) != -1) {
                //将byte数组转换成指定格式的字符串
                result = getString(buffer, "UTF-8");
            }
        } catch (Exception e) {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String getString(final byte[] data, final String charset) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }
        return getString(data, 0, data.length, charset);
    }

    private static String getString(final byte[] data, int offset, int length, String charset) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }
        if (charset == null || charset.length() == 0) {
            throw new IllegalArgumentException("charset may not be null or empty");
        }
        try {
            return new String(data, offset, length, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(data, offset, length);
        }
    }

    /**
     * @param name 文件名
     * @param mode MODE_PRIVATE | MODE_APPEND 自 API 级别 17 以来，常量 MODE_WORLD_READABLE 和
     *             MODE_WORLD_WRITEABLE 已被弃用
     * @return 在您的内部存储空间内创建（或打开现有的）目录。
     * @see #writeFileData(Context, String, String, int)
     */
    public static File getDir(Context context, String name, int mode) {
        return context.getDir(name, mode);
    }

    /**
     * 将临时缓存文件保存到的内部目录。 使其占用的空间保持在合理的限制范围内（例如 1 MB）
     *
     * @return /data/data/包名/cache
     */
    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * dir: data|user/0
     *
     * @return /data/{dir}/包名
     */
    public static File getDataDir(Context context) {
        return ContextCompat.getDataDir(context);
    }
}
