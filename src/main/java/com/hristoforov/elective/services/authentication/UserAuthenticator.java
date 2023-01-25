package com.hristoforov.elective.services.authentication;

import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.enums.Status;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.AuthenticationException;
import com.hristoforov.elective.utils.PasswordHashingUtil;

public final class UserAuthenticator {
    public static User authenticate(UserDao userDao,String login, String password) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            if (PasswordHashingUtil.verify(user.getPassword(), password)) {
                if (user.getStatus().equals(Status.UNBLOCKED)) {
                    return user;
                } else {
                    throw new AuthenticationException(AuthenticationException.Type.BLOCKED);
                }
            } else {
                throw new AuthenticationException(AuthenticationException.Type.PASS);
            }
        } else {
            throw new AuthenticationException(AuthenticationException.Type.LOGIN);
        }
    }
}
