package com.railroad.security;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;

import javax.enterprise.inject.Produces;

;

public class ShiroConfiguration {

    @Produces
    public WebSecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager = null;
        if(securityManager == null){
            AuthorizingRealm realm = new SecurityRealm();
            CredentialsMatcher credentialsMatcher = new PasswordMatcher();
            ((PasswordMatcher)credentialsMatcher).setPasswordService(new BCryptPasswordService());
            realm.setCredentialsMatcher(credentialsMatcher);

            securityManager = new DefaultWebSecurityManager(realm);

            byte[] cypherKey = String.format("0x%s", Hex.encodeToString(new AesCipherService().generateNewKey().getEncoded())).getBytes();
            RememberMeManager rememberMeManager = new CookieRememberMeManager();
            ((CookieRememberMeManager) rememberMeManager).setCipherKey(cypherKey);
            securityManager.setRememberMeManager(rememberMeManager);

            CacheManager cacheManager = new EhCacheManager();
            ((EhCacheManager) cacheManager).setCacheManagerConfigFile("classpath:ehcache.xml");
            securityManager.setCacheManager(cacheManager);

        }
        return securityManager;
    }

    @Produces
    public FilterChainResolver getFilterChainResolver(){
        FilterChainResolver filterChainResolver = null;
        if(filterChainResolver == null){
            FormAuthenticationFilter authc = new FormAuthenticationFilter();
            AnonymousFilter anon = new AnonymousFilter();
            UserFilter user = new UserFilter();

            authc.setLoginUrl("/");
            user.setLoginUrl("/");

            FilterChainManager fcMan = new DefaultFilterChainManager();
            fcMan.addFilter("authc", authc);
            fcMan.addFilter("anon", anon);
            fcMan.addFilter("user", user);

            fcMan.createChain("/api/v1/auth/*", "anon");
            fcMan.createChain("/api/v1/users/*", "anon");
            fcMan.createChain("/api/v1/*/*", "user");
            fcMan.createChain("/api/v1/*/*", "authc");

            PathMatchingFilterChainResolver resolver = new PathMatchingFilterChainResolver();
            resolver.setFilterChainManager(fcMan);
            filterChainResolver = resolver;
        }
        return filterChainResolver;
    }
}
