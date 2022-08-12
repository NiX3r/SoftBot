package Utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        /* MessageDigest instance for hashing using SHA256 */
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        /* digest() method called to calculate message digest of an input and return array of byte */
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String getHashedPassword(String password)
    {
        /* Convert byte array of hash into digest */
        BigInteger number = null;
        try {
            number = new BigInteger(1, getSHA(password));
            /* Convert the digest into hex value */
            StringBuilder hexString = new StringBuilder(number.toString(16));

            /* Pad with leading zeros */
            while (hexString.length() < 32)
            {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

}
