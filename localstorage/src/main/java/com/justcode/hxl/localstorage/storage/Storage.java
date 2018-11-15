package com.justcode.hxl.localstorage.storage;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import android.util.Log;

import com.justcode.hxl.localstorage.storage.ExternalStorage.ExternalStorage;
import com.justcode.hxl.localstorage.storage.InternalStorage.InternalStorage;

import java.io.File;


public class Storage {

    private static final String TAG = "Storage";

    /**
     * 获取目录文件大小
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    public static long getCacheSize(Context context) {
        long internalCacheSize = getDirSize(InternalStorage.getCacheDir(context));
        long externalCacheSize = getDirSize(ExternalStorage.getCacheDir(context));
        return internalCacheSize + externalCacheSize;
    }


    /**
     * 清除缓存：
     * <p>
     * 1. 将内部数据下的cache包下的内容（/data/data/包名/cache/XXX）清除 。
     * <p>
     * 2. 将外部私有数据下的cache包（/storage/emulated/0/Android/data/包名/cache）清除，
     */
    public static void clearCache(Context context) {
        File mInternalCacheFile = InternalStorage.getCacheDir(context);
        deleteFolderFile(mInternalCacheFile, true);
        File mExternalCacheFile = ExternalStorage.getCacheDir(context);
        deleteFolderFile(mExternalCacheFile, true);
    }

    public static long getDataSize(Context context) {
        long internalDataSize = getDirSize(InternalStorage.getDataDir(context));
        long externalDataSize = getDirSize(ExternalStorage.getDataPkgDir(context));
        return internalDataSize + externalDataSize;
    }
    public static long getInternalDataSize(Context context){
        long internalDataSize = getDirSize(InternalStorage.getDataDir(context));
        return internalDataSize;
    }
    public static long getExternalDataSize(Context context){
        long externalDataSize = getDirSize(ExternalStorage.getDataPkgDir(context));
        return externalDataSize;
    }

    /**
     * 清除数据：
     * <p>
     * 1. 将内部数据下的所有内容（/data/data/包名/XXX）清除；
     * <p>
     * 2. 将外部私有数据包（/storage/emulated/0/Android/data/包名）清除，
     */
    public static void clearData(Context context) {
        File mInternalDataFile = InternalStorage.getDataDir(context);
        deleteFolderFile(mInternalDataFile, true);
        File mExternalDataFile = ExternalStorage.getDataPkgDir(context);
        deleteFolderFile(mExternalDataFile, true);
    }

    /**
     * 清除内部存储数据
     */
    public static void clearInternalData(Context context) {
        File mInternalDataFile = InternalStorage.getDataDir(context);
        deleteFolderFile(mInternalDataFile, true);
    }

    /**
     * 清除外部存储数据
     */
    public static void clearExternalData(Context context) {
        File mExternalDataFile = ExternalStorage.getDataPkgDir(context);
        deleteFolderFile(mExternalDataFile, true);
    }

    /**
     * 删除指定目录下文件及目录
     */
    public static void deleteFolderFile(File dir, boolean deleteThisPath) {
        try {
            if (dir.isDirectory()) {// 如果下面还有文件
                File files[] = dir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolderFile(files[i], true);
                }
            }
            if (deleteThisPath) {
                if (!dir.isDirectory()) {// 如果是文件，删除
                    dir.delete();
                } else {// 目录
                    if (dir.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        dir.delete();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "deleteFolderFile: ", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取手机内部剩余虚拟内部存储空间
     */
    public static long getRemainInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        long availableBlocks = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部剩余虚拟外部存储空间
     */
    public static long getRemainExternalStorageSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }
            long availableBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                availableBlocks = stat.getAvailableBlocks();
            }
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取手机内部总的存储空间
     */
    public static long getTotalInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
        } else {
            blockSize = stat.getBlockSize();
        }
        long totalBlocks = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBlocks = stat.getBlockCountLong();
        } else {
            totalBlocks = stat.getBlockCount();
        }
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机内部总的外部存储空间
     */
    public static long getTotalExternalStorageSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
            } else {
                blockSize = stat.getBlockSize();
            }
            long totalBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalBlocks = stat.getBlockCountLong();
            } else {
                totalBlocks = stat.getBlockCount();
            }
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    //=============其次介绍几个除了/data目录之外的目录===============
    // 1. /mnt :这个目录专门用来当作挂载点(MountPoint)。通俗点说,/mnt就是来挂载外部存储设备的(如sdcard),
    // 我们的sdcard将会被手机系统视作一个文件夹,这个文件夹将会被系统嵌入到收集系统的mnt目录
    // 2. /dev包：Linux系统的常规文件夹。
    // 3. /system包：系统配置的文件夹，比如Android系统框架（framework）、底层类库（lib）、字体（font）等。

}