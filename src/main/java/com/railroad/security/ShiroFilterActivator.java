package com.railroad.security;

import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class ShiroFilterActivator extends ShiroFilter {
}
