package co.tton.mlibrary.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ContentHandler;

/**
 * Created by 46812 on 2015/8/17.
 * <p/>
 * a util for compressing images
 */
public class ImageUtil {

    //    private static String imageUri = "/soft2t/images/";
    private static final String TAG = ImageUtil.class.getSimpleName();

    /**
     * 质量压缩方法
     *
     * @param image the image you want to compress
     * @return byte[]
     */
    public static byte[] compressImage(Bitmap image, int fileSize) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100; //此处可尝试用90%开始压缩，跳过100%压缩
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        int length;
        while ((length = baos.toByteArray().length) / 1024 > fileSize) {
            // 每次都减少10
            options -= 10;

            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            if(options <=  0){
                options = 0;
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);

            if(options == 0 ){
                break;
            }

        }
        return baos.toByteArray();
        // 把压缩后的数据baos存放到ByteArrayInputStream中
//		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
//		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
//		return bitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath 文件路径
     * @return byte[]
     */
    public static byte[] getImage(String srcPath) {
        //图片需要旋转的角度
        int picSpinDegree = readPictureDegree(srcPath);

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
//        另外一种算压缩比的方法：
//        图片比例压缩倍数 就是 （宽度压缩倍数+高度压缩倍数）/2
//        be = (int) ((w / STANDARD_WIDTH + h/ STANDARD_HEIGHT) / 2);
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        //设置图片旋转角度
        Matrix matrix = new Matrix();
        matrix.postRotate(picSpinDegree);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return compressImage(resizedBitmap, 100);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 旋转图片角度
     *
     * @param path 文件路径
     * @return 旋转角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 图片按比例大小压缩方法（根据Bitmap图片压缩）
     */
    public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        ByteArrayInputStream isBm ;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        Bitmap bitmap ;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        byte[] b = compressImage(bitmap, 100);//压缩好比例大小后再进行质量压缩
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        return BitmapFactory.decodeStream(in, null, null);
    }

    public static String getCompressedByQualityPath(Context context, String filePath, int fileSize){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        byte[] bytes = compressImage(bitmap, fileSize);
        return compressedPath(context,bytes);

    }
    public static String getCompressedPath(Context context, String filePath) {
//        String destPath = getImageCachePath();
        byte[] bytes = getImage(filePath);
        return compressedPath(context,bytes);
    }


    private static String compressedPath(Context context , byte[] bytes){

        String imageCachePath = CachePathUtil.getImageCachePath(context);
        String destPath = imageCachePath + System.currentTimeMillis() + ".jpg";

        File f = new File(imageCachePath);
        if (!f.exists()) {
            if (!f.mkdirs()) {

                return null;
            }
        }


        InputStream in = new ByteArrayInputStream(bytes);
        try {
            OutputStream out = new FileOutputStream(destPath);
            int length;
            byte[] b = new byte[1024];
            while ((length = in.read(b)) > 0) {
                out.write(b, 0, length);
            }
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "the path after compress is " + destPath);

        return destPath;
    }

}
