package com.railroad.security;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;

import javax.ws.rs.Produces;

public class ShiroConfiguration {

    @Produces
    public WebSecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager = null;
        if(securityManager == null){
            AuthorizingRealm realm = new SecurityRealm();

            securityManager = new DefaultWebSecurityManager(realm);
        }
        return null;
    }

    @Produces
    public FilterChainResolver getFilterChainResolver(){
        return null;
    }
}
