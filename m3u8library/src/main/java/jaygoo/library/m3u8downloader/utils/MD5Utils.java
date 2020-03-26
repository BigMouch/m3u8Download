package jaygoo.library.m3u8downloader.utils;

import android.os.Build;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import javax.crypto.NoSuchPaddingException;

import androidx.annotation.RequiresApi;


/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/27
 * 描    述: MD5加密工具
 * ================================================
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class MD5Utils {

    private final static String password = "654321";
    private final static Base64.Encoder encoder = Base64.getEncoder();//jia
    private final static Base64.Decoder decoder = Base64.getDecoder();//jie

    public static String encode(String str) {
        try {
  /*          // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);*/
            final byte[] textByte = str.getBytes("UTF-8");
            final String encodedText = encoder.encodeToString(textByte);
            return encodedText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 解密
     *
     * @param content   密文
     * @return String
     * @throws Exception 异常
     */
    public static String decode(String content) throws Exception {

        return  new String(decoder.decode(content), "UTF-8");
    }


}
