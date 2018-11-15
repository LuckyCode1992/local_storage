package com.justcode.hxl.localstorage.storage.ExternalStorage;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.os.Environment.getExternalStorageState;

public class ExternalStorage {

    private static final String TAG = "ExternalStorage";

    /**
     * 文件写入sd卡（最常使用）
     */
    public static void write(String content, String fileName) {
        FileOutputStream fileOutputStream = null;
        if(TextUtils.isEmpty(content)){
            content = "您没有写入数据，默认写入本条数据";
        }
        if(TextUtils.isEmpty(fileName)){
            fileName = "external.txt";
        }
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                File file = Environment.getExternalStorageDirectory();
                fileOutputStream = new FileOutputStream(file.getCanonicalPath() + "/" + fileName);
                fileOutputStream.write(content.getBytes());
            } else {
                Log.d(TAG, "sd卡，不可读写");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从sd卡读出文件（最常使用）
     */
    public static String read(String fileName) {

        if(TextUtils.isEmpty(fileName)){
            fileName = "external.txt";
        }
        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream fileInputStream = null;
        try {
            //SD卡是否可用，存在
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + fileName);
                //文件是否存在
                if (file.exists()) {
                    fileInputStream = new FileInputStream(file);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        stringBuffer.append(str);
                    }
                } else {
                    Log.d(TAG, "目录下，不存在该文件");
                }
            } else {
                Log.d(TAG, "sd卡不存在或不可用");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }


    /**
     * 判断外部设置是否有效
     */
    public static boolean isEmulated() {
        return Environment.isExternalStorageEmulated();
    }

    /**
     * 判断外部设置是否可以移除
     */
    public static boolean isRemovable() {
        return Environment.isExternalStorageEmulated();
    }

    public static String getStorageState() {
        return getExternalStorageState();
    }

    /* Checks if external storage is available for read and write */
    public static boolean isStorageWritable() {
        String state = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isStorageReadable() {
        String state = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * @return /storage/emulated/0/Android/data/包名/cache
     */
    public static File getCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static File createDirInCacheDir(Context context, String name) {
        File file = getCacheDir(context);
        if (file != null) {
            File newFile = new File(file, name);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e(TAG, "getCacheDirWithName:  Directory not created");
                    return null;
                }
            }
            return newFile;
        }
        return null;
    }

    public static String getCacheDirPath(Context context) {
        File file = getCacheDir(context);
        if (file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }

    public static File[] getCacheDirs(Context context) {
        return ContextCompat.getExternalCacheDirs(context);
    }

    /**
     * 保存应用私有文件
     *
     * @param type The type of files directory to return. May be {@code null} for the root of
     *             the files directory or one of the following constants for a subdirectory:
     *             {@link Environment#DIRECTORY_MUSIC}, {@link
     *             Environment#DIRECTORY_PODCASTS}, {@link Environment#DIRECTORY_RINGTONES},
     *             {@link Environment#DIRECTORY_ALARMS}, {@link
     *             Environment#DIRECTORY_NOTIFICATIONS}, {@link
     *             Environment#DIRECTORY_PICTURES}, or {@link
     *             Environment#DIRECTORY_MOVIES}.
     *             <p>
     *             如果您不需要特定的媒体目录，请传递 null 以接收应用私有目录的根目录。
     * @return /storage/emulated/0/Android/data/包名/files/{type}
     */
    public static File getFilesDir(Context context, String type) {
        return context.getExternalFilesDir(type);
    }

    public static File createDirInFilesDir(Context context, String type, String name) {
        File file = getFilesDir(context, type);
        if (file != null) {
            File newFile = new File(file, name);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e(TAG, "getFilesDirWithName:  Directory not created");
                    return null;
                }
            }
            return newFile;
        }
        return null;
    }

    public static String getFilesDirPath(Context context, String type) {
        File file = getFilesDir(context, type);
        if (file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }

    public static File[] getFilesDirs(Context context, String type) {
        return ContextCompat.getExternalFilesDirs(context, type);
    }

    /**
     * @return 保存可与其他应用共享的文件
     */
    @Deprecated
    public static File getStoragePublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }

    public static File getStoragePublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }

    /**
     * Writing to this path requires the
     * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} permission
     *
     * @param type 目录类型
     * @param name 目录名称
     * @return 在公共目录中创建了一个指定名称的目录：
     */
    public static File createDirInStoragePublicDir(String type, String name) {
        // Get the directory for the user's public directory.
        File file = new File(getStoragePublicDir(type), name);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG, "getStoragePublicDirWithName Directory not created");
                return null;
            }
        }
        return file;
    }

    public static String getStoragePublicDirPath(String type) {
        File file = getStoragePublicDir(type);
        if (file != null) {
            return file.getPath();
        }
        return null;
    }

    /**
     * @return /storage/emulated/0/Android/data/包名
     */
    public static File getDataPkgDir(Context context) {
        File file = getFilesDir(context, null);
        if (file != null) {
            String path = file.getPath();
            String newPath = path.substring(0, path.lastIndexOf("/"));
            file = new File(newPath);
        }
        return file;
    }

    /**
     * @param name 文件名
     * @return /storage/emulated/0/Android/data/io.github.changjiashuai.storage/{name}
     */
    public static File createDirInDataPkgDir(Context context, String name) {
        File file = getDataPkgDir(context);
        if (file != null) {
            File newFile = new File(file, name);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e(TAG, "createDirInDataPkgDir Directory not created");
                    return null;
                }
            }
            return newFile;
        }
        return null;
    }

    public static String getDataPkgDirPath(Context context) {
        File file = getDataPkgDir(context);
        if (file != null) {
            return file.getPath();
        }
        return null;
    }
}
