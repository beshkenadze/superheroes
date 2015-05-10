package net.beshkenadze.mysuperheroes.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 25.03.14.
 */
public class CryptUtils {
    private static String convertToHex (byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1 (String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(content.getBytes("iso-8859-1"), 0, content.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static String MD5 (String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(content.getBytes("iso-8859-1"), 0, content.length());
        byte[] md5hash = md.digest();
        return convertToHex(md5hash);
    }
}
