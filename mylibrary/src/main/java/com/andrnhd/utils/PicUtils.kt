package com.andrnhd.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import java.io.File
import android.media.ExifInterface
import java.io.FileOutputStream
import java.io.IOException


/**
 * 图片管理工具类
 * Created by nhd on 2017/4/15.
 */
fun Context.readBitmap(resId: Int): Bitmap {
    val opt = BitmapFactory.Options()
    opt.inPreferredConfig = Bitmap.Config.RGB_565
    opt.inPurgeable = true
    opt.inInputShareable = true
    val stream = resources.openRawResource(resId)
    return BitmapFactory.decodeStream(stream, null, opt)
}

private val DISPLAY_WIDTH = 800f
private val DISPLAY_HEIGHT = 800f

/**
 * 从path中获取图片信息

 * @param path 路径
 * *
 * @return 图片
 */
fun decodeBitmap(path: String): Bitmap {
    val op = BitmapFactory.Options()
    op.inJustDecodeBounds = true
    var bmp = BitmapFactory.decodeFile(path, op) //获取尺寸信息
    //获取比例大小
    val wRatio = Math.ceil((op.outWidth / DISPLAY_WIDTH).toDouble()).toInt()
    val hRatio = Math.ceil((op.outHeight / DISPLAY_HEIGHT).toDouble()).toInt()
    //如果超出指定大小，则缩小相应的比例
    if (wRatio > 1 && hRatio > 1) {
        if (wRatio > hRatio) {
            op.inSampleSize = wRatio
        } else {
            op.inSampleSize = hRatio
        }
    }
    op.inJustDecodeBounds = false
    bmp = BitmapFactory.decodeFile(path, op)
    return bmp
}

fun thumbBitmap(filePath: String): File {
    val path = Environment.getExternalStorageDirectory().absolutePath + "/photo/compressPic.jpg"
    val compressImage = compressImage(filePath, path, 30)
    return File(compressImage)
}

fun compressImage(filePath: String, targetPath: String, quality: Int): String {
    var bm = getSmallBitmap(filePath)//获取一定尺寸的图片
    val degree = readPictureDegree(filePath)//获取相片拍摄角度
    if (degree != 0) {//旋转照片角度，防止头像横着显示
        bm = rotateBitmap(bm, degree)
    }
    val outputFile = File(targetPath)
    try {
        if (!outputFile.exists()) {
            outputFile.parentFile.mkdirs()
            //outputFile.createNewFile();
        } else {
            outputFile.delete()
        }
        val out = FileOutputStream(outputFile)
        bm.compress(Bitmap.CompressFormat.JPEG, quality, out)
    } catch (e: Exception) {
    }

    return outputFile.path
}

/**
 * 根据路径获得图片信息并按比例压缩，返回bitmap
 */
private fun getSmallBitmap(filePath: String): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true//只解析图片边沿，获取宽高
    BitmapFactory.decodeFile(filePath, options)
    // 计算缩放比
    options.inSampleSize = calculateInSampleSize(options, 480, 800)
    // 完整解析图片返回bitmap
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(filePath, options)
}

/**
 * 获取照片角度

 * @param path
 * *
 * @return
 */
private fun readPictureDegree(path: String): Int {
    var degree = 0
    try {
        val exifInterface = ExifInterface(path)
        val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL)
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return degree
}

/**
 * 旋转照片

 * @param bitmap
 * *
 * @param degress
 * *
 * @return
 */
private fun rotateBitmap(bitmap: Bitmap, degress: Int): Bitmap {
    val m = Matrix()
    m.postRotate(degress.toFloat())
    val bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
    return bmp
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
        val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    return inSampleSize
}