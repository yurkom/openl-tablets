package org.openl.rules.webstudio.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openl.rules.security.Group;
import org.openl.rules.security.Privilege;
import org.openl.rules.security.SimpleUser;
import org.openl.rules.security.User;
import org.openl.rules.webstudio.service.GroupManagementService;
import org.openl.rules.webstudio.service.UserManagementService;
import org.openl.util.StringUtils;
import org.springframework.core.env.PropertyResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

public class CreateIfNotExistAuthenticationUserDetailsService implements AuthenticationUserDetailsService {
    private final UserManagementService userManagementService;
    private final GroupManagementService groupManagementService;
    private String defaultGroup;

    public CreateIfNotExistAuthenticationUserDetailsService(UserManagementService userManagementService,
            GroupManagementService groupManagementService,
            PropertyResolver propertyResolver,
            String prefix) {
        this.userManagementService = userManagementService;
        this.groupManagementService = groupManagementService;
        this.defaultGroup = propertyResolver.getProperty("security." + prefix + ".default-group");
    }

    @Override
    public UserDetails loadUserDetails(Authentication delegatedAuth) {
        UserDetails userDetails;
        User dbUser = userManagementService.loadUserByUsername(delegatedAuth.getName());
        if (dbUser != null) {
            userDetails = dbUser;

            // Check if First Name or Last Name were changed since last login
            User user = null;
            if (delegatedAuth.getPrincipal() instanceof User) {
                user = (User) delegatedAuth.getPrincipal();
            } else if (delegatedAuth.getDetails() instanceof User) {
                user = (User) delegatedAuth.getDetails();
            }
            if (user != null) {
                String firstName = StringUtils.trimToEmpty(user.getFirstName());
                String lastName = StringUtils.trimToEmpty(user.getLastName());

                if (!firstName.equals(StringUtils.trimToEmpty(dbUser.getFirstName())) || !lastName
                    .equals(StringUtils.trimToEmpty(dbUser.getLastName()))) {
                    // Convert authorities to groups. We don't want to loose them.
                    Collection<Privilege> privileges = new ArrayList<>();
                    for (GrantedAuthority authority : dbUser.getAuthorities()) {
                        Privilege group = groupManagementService.getGroupByName(authority.getAuthority());
                        privileges.add(group);
                    }

                    String username = user.getUsername();
                    SimpleUser userToUpdate = new SimpleUser(firstName, lastName, username, null, privileges);
                    userManagementService.updateUserData(username, firstName, lastName, null, false);
                    userDetails = userToUpdate;
                }
            }
        } else {
            // Create new user
            List<Privilege> groups;
            if (!StringUtils.isBlank(defaultGroup) && groupManagementService.isGroupExist(defaultGroup)) {
                Group group = groupManagementService.getGroupByName(defaultGroup);
                groups = Collections.singletonList((Privilege) group);
            } else {
                groups = Collections.emptyList();
            }
            String firstName = null;
            String lastName = null;
            User preAuthenticatedUser = null;
            if (delegatedAuth.getPrincipal() instanceof User) {
                preAuthenticatedUser = (User) delegatedAuth.getPrincipal();
            } else if (delegatedAuth.getDetails() instanceof User) {
                preAuthenticatedUser = (User) delegatedAuth.getDetails();
            }
            if (preAuthenticatedUser != null) {
                firstName = preAuthenticatedUser.getFirstName();
                lastName = preAuthenticatedUser.getLastName();
            }
            SimpleUser user = new SimpleUser(firstName, lastName, delegatedAuth.getName(), null, groups);
            userManagementService.addUser(delegatedAuth.getName(), firstName, lastName, null);
            userManagementService.updateAuthorities(delegatedAuth.getName(),
                groups.stream().map(Privilege::getName).collect(Collectors.toSet()));
            userDetails = user;
        }
        return userDetails;
    }
}
