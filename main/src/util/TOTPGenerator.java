package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * สร้าง Secret Key (16 ตัวอักษร) และ TOTP (6 หลัก)
 */
public class TOTPGenerator {

    public static String generateSecretKey() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, 16).toUpperCase();
    }

    public static String generateTOTP(String secretKey) {
        long currentTimeStep = System.currentTimeMillis() / 30000;
        String data = secretKey + currentTimeStep;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            String totp = sb.toString();
            if (totp.length() > 6) {
                totp = totp.substring(totp.length() - 6);
            }
            return totp.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "000000";
    }
}
