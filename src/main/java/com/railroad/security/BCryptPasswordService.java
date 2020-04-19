package com.railroad.security;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.credential.PasswordService;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordService implements PasswordService {

    public static final int DEFAULT_BCRYPT_ROUND = 10;

    @Setter @Getter
    private int logRounds;

    public BCryptPasswordService() {
        this.logRounds = DEFAULT_BCRYPT_ROUND;
    }

    public BCryptPasswordService(int logRounds) {
        this.logRounds = logRounds;
    }

    @Override
    public String encryptPassword(Object plainTextPassword) throws IllegalArgumentException {
        if(plainTextPassword instanceof String){
            String password = (String) plainTextPassword;
            return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
        }
        throw new IllegalArgumentException("BCryptPasswordService encryptPassword only support java.lang.String credential.");
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        if(submittedPlaintext instanceof  char[]){
            String password = String.valueOf((char[]) submittedPlaintext);
            return BCrypt.checkpw(password, encrypted);
        }
        throw new IllegalArgumentException("BCryptPasswordService passwordsMatch only support char[] credential.");
    }
}
