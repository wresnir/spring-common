package dev.wresni.common.utilities;

import com.eatthepath.otp.HmacOneTimePasswordGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OtpUtil {
    public static String generateOtp(int length) throws NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator generator = KeyGenerator.getInstance("HmacSHA1");
        generator.init(128);
        SecretKey secretKey = generator.generateKey();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        HmacOneTimePasswordGenerator otpGenerator = new HmacOneTimePasswordGenerator();
        String format = "%0" + length + "d";
        return String.format(format, otpGenerator.generateOneTimePassword(secretKey, random.nextInt()));
    }

    public static String generateOtp() throws NoSuchAlgorithmException, InvalidKeyException {
        return generateOtp(6);
    }

    public static String hashOtp(String otp) {
        return DigestUtils.sha1Hex(otp);
    }

    public static boolean validateOtp(String otp, String encrypted) {
        return DigestUtils.sha1Hex(otp).equals(encrypted);
    }
}
