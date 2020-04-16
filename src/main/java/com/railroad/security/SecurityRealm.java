package com.railroad.security;

import com.railroad.rest.user.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;

public class SecurityRealm extends AuthorizingRealm {

    private Logger logger;
    private UserService userService;

    public SecurityRealm() {
        super();
        this.logger = Logger.getLogger(SecurityRealm.class.getName());

        setAuthenticationCachingEnabled(Boolean.TRUE);

        try {
            InitialContext ctx = new InitialContext();
            String moduleName = (String) ctx.lookup("java:module/ModuleName");
            this.userService = (UserService) ctx.lookup(String.format("java:global/%s/UserService", moduleName));
        } catch (NamingException ex) {
            logger.warning("Cannot do the JNDI Lookup to instantiate the User service : " + ex);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {


        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // Identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        String email = userPassToken.getUsername();
        if(email == null){
            logger.warning("Username is null");
        }

        // Read the user from DB
//        User user = this.userService.findUserByEmail(email);

        return null;
    }
}
