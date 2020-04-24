package com.main.security;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.main.models.Role;
import com.main.models.UserPrincipal;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulCustomFilter extends ZuulFilter {

    private static final String ZULL_HEADER_USER_ID = "X-Zuul-UserId";
    private static final String ZULL_HEADER_USER_ROLES = "X-Zuul-UserRoles";
    private String authoritiesString = "";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() != null;
    }

    @Override
    public Object run() throws ZuulException  {
    	
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
        	&& !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
        	
        	String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            List<SimpleGrantedAuthority> authorities =  (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
            		                        .getAuthentication()
            		                        .getAuthorities().stream().collect(Collectors.toList());
            
            authorities.forEach(x->authoritiesString += x + " ");
            RequestContext ctx = RequestContext.getCurrentContext();

            ctx.addZuulRequestHeader(ZULL_HEADER_USER_ID, principal);
            ctx.addZuulRequestHeader(ZULL_HEADER_USER_ROLES, authoritiesString);

        }
        return null;
    }
}
