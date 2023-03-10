package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRA_JSPFiles.LOGIN_PAGE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SignInActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserDao userDao = mock(UserDao.class);


    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(LOGIN_PAGE)).thenReturn(rd);
        new SignInAction(appContext).executeDoGet(request, response);
    }

   /* @Test
    void testExecuteDoPost() throws ServletException, IOException, NoSuchAlgorithmException {
//        MessageDigest messageDigest = mock(MessageDigest.class);
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(userDao.findByLogin(LOGIN_VALUE)).thenReturn(getTestUser());
//        when(MessageDigest.getInstance(SHA_256_ALGORITHM)).thenReturn(messageDigest);
//        when(PasswordHashingUtil.encode(PASSWORD_VALUE,SHA_256_ALGORITHM)).thenReturn("b067e4ad385a099d5825c86e1e777e47dbce5120ce0951ea689fe3088a86a284");
        when(PasswordHashingUtil.verify(getTestUser().getPassword(), PASSWORD_VALUE)).thenReturn(true);
        when(UserAuthenticator.authenticate(userDao,LOGIN_VALUE,PASSWORD_VALUE)).thenReturn(getTestUser());
        when(request.getServletPath()).thenReturn(USER_INFO_PAGE_SERVLET);
        new SignInAction(appContext).executeDoPost(request, response);
        assertEquals(USER_INFO_PAGE_SERVLET, request.getServletPath());
    }*/

    @Test
    void testExecuteBadDoPostLoginException() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        new SignInAction(appContext).executeDoPost(request, response);
    }

   /* @Test
    void testExecuteBadDoPostPasswordException() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(userDao.findByLogin(LOGIN_VALUE)).thenReturn(getTestUser());
        assertThrows(AuthenticationException.class, () -> UserAuthenticator.authenticate(userDao, LOGIN_VALUE, PASSWORD_VALUE));
        when(UserAuthenticator.authenticate(userDao, LOGIN_VALUE, PASSWORD_VALUE)).thenThrow(
                new AuthenticationException(AuthenticationException.Type.PASS));
        new SignInAction(appContext).executeDoPost(request, response);
    }*/
}
