package project.study.collection;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.study.domain.Basic;

public class PasswordManager {

    private final Basic basic;
    private final BCryptPasswordEncoder encoder;

    public PasswordManager(Basic basic, BCryptPasswordEncoder encoder) {
        this.basic = basic;
        this.encoder = encoder;
    }

    public boolean isValidPassword(String password) {
        String pw = basic.getPassword();
        String loginPw = combineSalt(password, basic.getSalt());
        return encoder.matches(loginPw, pw);
    }

    private String combineSalt(String encodeBeforePassword, String salt) {
        return encodeBeforePassword + salt;
    }

}
