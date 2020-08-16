package com.railroad.security;

import com.railroad.entity.User;
import com.railroad.rest.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // Identify account to log to
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        String email = userPassToken.getUsername();
        if(email == null){
            logger.warning("Username is null");
        }

        User user = null;
        try{
            user = this.userService.findUserByEmail(email);
            if(user == null){
                logger.warning(String.format("No account for for user [%s]", email));
            }
        } catch (NoSuchFieldException ex){
            logger.warning(ex.getMessage());
            throw new AuthenticationException();
        }


        return new SimpleAuthenticationInfo(email, user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if(principals == null)
            throw new AuthorizationException("PrincipalCollection method argument cannot be null");

        String username = (String) getAvailablePrincipal(principals);
        Set<String> roleNames = new HashSet<>();


//        try{
//            this.userService.findUserByEmail(username)
//                    .getUserRoles()
//                    .stream()
//                    .forEach(x -> roleNames.add(x.getRole().getName()));
//        } catch (NoSuchFieldException ex){
//            throw new AuthorizationException("Cannot find user roles");
//        }

        AuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);

        return info;
    }
}
