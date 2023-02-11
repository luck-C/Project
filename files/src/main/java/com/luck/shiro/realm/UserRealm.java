package com.luck.shiro.realm;

import com.luck.entity.Role;
import com.luck.entity.User;
import com.luck.salt.MyByteSource;
import com.luck.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findRolesByUserName(primaryPrincipal);
        List<Role> roles = user.getRoles();
        if(!ObjectUtils.isEmpty(roles)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            roles.forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getName());
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        User user = userService.findByUserName((String) authenticationToken.getPrincipal());
        if(!ObjectUtils.isEmpty(user)){
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user",user);
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),new  MyByteSource(user.getSalt()),this.getName());
        }
        return null;
    }

}
