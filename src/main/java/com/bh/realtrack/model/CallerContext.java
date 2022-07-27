package com.bh.realtrack.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Ganesh Mali
 */
public class CallerContext {

    private String name;

    private String applicationKey;

    private Collection<GrantedAuthority> scopes;

    public CallerContext(String name, String applicationKey, Collection<GrantedAuthority> scopes) {
        this.name = name;
        this.applicationKey = applicationKey;
        this.scopes = scopes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public Collection<GrantedAuthority> getScopes() {
        return scopes;
    }

    public void setScopes(Collection<GrantedAuthority> scopes) {
        this.scopes = scopes;
    }

    @Override
    public String toString() {
        return "CallerContext{" +
                "name='" + name + '\'' +
                ", applicationKey='" + applicationKey + '\'' +
                ", scopes=" + scopes +
                '}';
    }
}
