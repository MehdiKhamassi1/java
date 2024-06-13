package toolkit;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    public static String encrypt(String plaintextPassword) {
        return BCrypt.hashpw(plaintextPassword, BCrypt.gensalt(13));
    }

    public static boolean match(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }
}
