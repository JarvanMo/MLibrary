package co.tton.mlibrary.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * 设备信息
 *
 * @author imknown
 */
public class DeviceUtil {

    private final static String TAG = "Device";

    /**
     * 设备名 MOTO ME632
     */
    private String deviceFullName;

    /**
     * 系统型号, 如 2.3.6
     */
    private String deviceReleaseVersion;
    /**
     * SDK版本, 如 10
     */
    private String sdk;
    /**
     * API版本, 如 10
     */
    private int apiVersion;

    private String IMEI;
    private String IMSI;
    private String SIM;

    private DeviceUtil(String deviceFullName, String deviceReleaseVersion, String sdk, int apiVersion, String IMEI, String IMSI, String SIM) {
        this.deviceFullName = deviceFullName;
        this.deviceReleaseVersion = deviceReleaseVersion;

        this.sdk = sdk;
        this.apiVersion = apiVersion;
        this.IMEI = IMEI;
        this.IMSI = IMSI;
        this.SIM = SIM;
    }

    public String getDeviceName() {
        return deviceFullName;
    }

    public String getSdk() {
        return sdk;
    }

    public String getDeviceNameReleaseVersion() {
        return deviceReleaseVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public String getIMEI() {
        return IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public String getSIM() {
        return SIM;
    }

    private static DeviceUtil device;

    /**
     * 获取手机设备型号
     */
    public static DeviceUtil getOSParameters(Context context) {
        if (device == null) {
            String deviceBrand = Build.BRAND;// 制造商 MOTO
            String deviceName = Build.MODEL;// 具体型号 ME632
            String deviceReleaseVersion = Build.VERSION.RELEASE;// 安卓版本 2.3.6

            @SuppressWarnings("deprecation")
            String sdk = Build.VERSION.SDK;
            int apiVersion = Build.VERSION.SDK_INT;

            String IMEI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
            String SIM = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();

            device = new DeviceUtil(deviceBrand + " " + deviceName, deviceReleaseVersion, sdk, apiVersion, IMEI, IMSI, SIM);
        }

        return device;
    }

    /**
     * 仅供参考
     */
    @SuppressLint("NewApi")
    @SuppressWarnings({"unused", "deprecation"})
    private static final void allOS_Build() {
        String SDK = Build.VERSION.SDK;// 22 ■ 19
        int SDK_INT = Build.VERSION.SDK_INT;// 22 ■ 19

        String BOARD = Build.BOARD;// msm8916 ■ mx4pro
        String BOOTLOADER = Build.BOOTLOADER;// unknown ■ unknown
        String BRAND = Build.BRAND;// TCL ■ Meizu
        String CPU_ABI = Build.CPU_ABI;// armeabi-v7a ■ armeabi-v7a
        String CPU_ABI2 = Build.CPU_ABI2;// armeab ■ armeabi
        String DEVICE = Build.DEVICE;// TCL_M3G ■ mx4pro
        String DISPLAY = Build.DISPLAY;// yios_m3g-userdebug 5.1.1 LMY48B 1eb006e8a6 test-keys ■ Flyme OS 4.2.8.2C
        String FINGERPRINT = Build.FINGERPRINT;// TCL/cm_m3g/m3g:5.1.1/LMY48B/1eb006e8a6:userdebug/test-keys ■ unknown
        String RADIOVERSION = Build.getRadioVersion();// .c12-00018-M8936FAAAANUZM-1 V1.1 ■ LTG_3.109.001
        String HARDWARE = Build.HARDWARE;// qcom ■ m76
        String HOST = Build.HOST;// yios01 ■ Mz-Builder-7
        String ID = Build.ID;// LMY48B ■ KTU84P
        String MANUFACTURER = Build.MANUFACTURER;// TCL ■ Meizu
        String MODEL = Build.MODEL;// TCL_M3G ■ MX4 Pro
        String PRODUCT = Build.PRODUCT;// TCL_M3G ■ meizu_mx4pro
        String RADIO = Build.RADIO;// unknown ■ unknown
        String SERIAL = Build.SERIAL;// 4f893801 ■ 760BBLB23BDX
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] SUPPORTED_32_BIT_ABIS = Build.SUPPORTED_32_BIT_ABIS;// [armeabi-v7a, armeabi]
            String[] SUPPORTED_64_BIT_ABIS = Build.SUPPORTED_64_BIT_ABIS;// [arm64-v8a]
            String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;// [arm64-v8a, armeabi-v7a, armeabi]
        }
        String TAGS = Build.TAGS;// test-keys ■ release-keys
        long TIME = Build.TIME;// 1434091789000 ■ 1429731383000
        String TYPE = Build.TYPE;// userdebug ■ user
        String UNKNOWN = Build.UNKNOWN;// unknown ■ unknown
        String USER = Build.USER;// official ■ flyme
        String VERSION_CODENAME = Build.VERSION.CODENAME;// REL ■ REL
        String VERSION_INCREMENTAL = Build.VERSION.INCREMENTAL;// 1eb006e8a6 ■ m76.Flyme_OS_4.2.8.2.20150423030715
        String VERSION_RELEASE = Build.VERSION.RELEASE;// 5.1.1 ■ 4.4.4

        //String batteryLevelRate = BaseApplication.MyBattery.levelRate + "%";// 94% ■ 100%
    }

    private static PackageInfo getAppVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;

        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return packInfo;
    }

    public static int getAppVersionCode(Context context) {
        return getAppVersion(context).versionCode;
    }

    public static String getAppVersionName(Context context) {
        return getAppVersion(context).versionName;
    }

    /**
     * 收集设备参数信息
     */
    public static void collectDeviceInfo(Context context, Map<String, String> infos) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, "收集程序包信息时出错.", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                // LogUtil.i(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(TAG, "手机程序崩溃信息时出错", e);
            }
        }
    }

    public static class UuidHelper {
        /**
         * UUID = 设备号 + SIM序列号 + 唯一识别码 (不可变)
         */
        public static String getMyUUID(Context context) {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, /* tmPhone, */androidId;

            androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            tmDevice = tm.getDeviceId();
            tmSerial = tm.getSimSerialNumber();

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

            String uniqueId = deviceUuid.toString();

            LogUtil.i("UUID = " + uniqueId);

            return uniqueId;
        }

        /**
         * 随机获取可变 UUID
         */
        public static String getMyUUID() {
            UUID uuid = UUID.randomUUID();

            String uniqueId = uuid.toString();

            LogUtil.i("UUID = " + uuid);

            return uniqueId;
        }
    }

    public static class RamUtil {

        /**
         * 应用程序最大可用内存, 单位 MB
         */
        public static long getMaxMemory() {
            return Runtime.getRuntime().maxMemory() / 1024 / 1024;
        }

        /**
         * 应用程序已获得内存, 单位 MB
         */
        public static long getTotalMemory() {
            return Runtime.getRuntime().totalMemory() / 1024 / 1024;
        }

        /**
         * 应用程序已获得内存中未使用内存, 单位 MB
         */
        public static long getFreeMemory() {
            return Runtime.getRuntime().freeMemory() / 1024 / 1024;
        }

        /**
         * 堆内存大小, 单位 MB
         */
        public static long getNativeHeapSize() {
            return Debug.getNativeHeapSize() / 1024 / 1024;
        }

        /**
         * 已获得 堆内存, 单位 MB
         */
        public static long getNativeHeapAllocatedSize() {
            return Debug.getNativeHeapAllocatedSize() / 1024 / 1024;
        }

        /**
         * 未使用的 堆内存, 单位 MB
         */
        public static long getNativeHeapFreeSize() {
            return Debug.getNativeHeapFreeSize() / 1024 / 1024;
        }

        public static void getMemoryInfo(Debug.MemoryInfo mi) {
            Debug.getMemoryInfo(mi);
        }

        /**
         * 获取可用运存大小
         */
        public static long getAvailMemory(Context context) {
            // 获取android当前可用内存大小
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            MemoryInfo mi = new MemoryInfo();
            am.getMemoryInfo(mi);

            // mi.availMem; 当前系统的可用内存
            // return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化

            return mi.availMem / (1024 * 1024);
        }

        /**
         * 获取总运存大小 <br>
         * http://www.cnblogs.com/helloandroid/articles/2210334.html
         */
        public static long getTotalMemory(Context context) {
            final String str1 = "/proc/meminfo";// 系统内存信息文件
            String str2;
            String[] arrayOfString;
            long initial_memory = 0;

            try {
                FileReader localFileReader = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
                str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
                arrayOfString = str2.split("\\s+");

                for (String num : arrayOfString) {
                    Log.i(str2, num + "\t");
                }

                // 获得系统总内存，单位是KB，乘以1024转换为Byte
                initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;

                localBufferedReader.close();
                localFileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
            return initial_memory / (1024 * 1024);
        }

        /**
         * 获取 单个应用 使用内存的上限
         */
        public static int getMemoryLimit(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            return activityManager.getMemoryClass();
        }
    }

    public static class CpuUtil {
        /**
         * 获取CPU 信息
         *
         * @return [0]CPU型号, [1]CPU频率
         */
        public static String[] getCpuInfo() {
            String sourcePath = "/proc/cpuinfo";
            String tempString = "";

            String[] cpuInfo = {"", ""};
            String[] arrayOfString;

            try {
                FileReader fr = new FileReader(sourcePath);

                BufferedReader localBufferedReader = new BufferedReader(fr, 8192);

                tempString = localBufferedReader.readLine();

                arrayOfString = tempString.split("\\s+");

                int size = arrayOfString.length;

                for (int i = 2; i < size; i++) {
                    cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
                }

                tempString = localBufferedReader.readLine();

                arrayOfString = tempString.split("\\s+");

                cpuInfo[1] += arrayOfString[2];

                localBufferedReader.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return cpuInfo;
        }
    }
}
