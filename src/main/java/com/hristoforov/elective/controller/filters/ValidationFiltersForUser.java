package com.hristoforov.elective.controller.filters;

import com.hristoforov.elective.constants.CaptchaParams;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.enums.Status;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.IncorrectDataFormatException;
import com.hristoforov.elective.services.validation.ValidationService;
import com.hristoforov.elective.utils.PasswordHashingUtil;
import com.hristoforov.elective.utils.VerifyRecaptcha;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.REGISTRATION_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.TEACHER_REGISTRATION_SERVLET;
import static com.hristoforov.elective.constants.CommonConstants.SHA_256_ALGORITHM;
import static com.hristoforov.elective.constants.ErrorMessage.*;
import static com.hristoforov.elective.constants.HttpAttributes.*;

@WebFilter(filterName = "ValidationFilterForUser", urlPatterns = {REGISTRATION_SERVLET, TEACHER_REGISTRATION_SERVLET})
public class ValidationFiltersForUser implements Filter {

    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = AppContext.getAppContext().getUserDao();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession httpSession = req.getSession();
        String login = "";
        String password = "";
        String email = "";
        String firstName = "";
        String lastName = "";
        int errorCounter = 0;
        if (req.getMethod().equalsIgnoreCase(POST)) {
            try {
                login = ValidationService.loginCheck(req.getParameter(LOGIN));
                httpSession.removeAttribute(LOGIN_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(LOGIN_SPELLING_ERROR, ERROR_SPELLING);
            }

            try {
                password = ValidationService.passwordCheck(req.getParameter(PSW));
                httpSession.removeAttribute(PASSWORD_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(PASSWORD_SPELLING_ERROR, ERROR_SPELLING);
            }

            try {
                ValidationService.passwordCheck(req.getParameter(PSW_REPEAT));
                httpSession.removeAttribute(PASSWORD_AGAIN_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(PASSWORD_AGAIN_SPELLING_ERROR, ERROR_SPELLING);
            }

            try {
                ValidationService.passwordEqualityCheck(req.getParameter(PSW), req.getParameter(PSW_REPEAT));
                httpSession.removeAttribute(PASSWORDS_ARE_NOT_EQUALS_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(PASSWORDS_ARE_NOT_EQUALS_ERROR, ERROR_PASSWORDS);
            }

            try {
                email = ValidationService.emailCheck(req.getParameter(EMAIL));
                httpSession.removeAttribute(EMAIL_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(EMAIL_SPELLING_ERROR, ERROR_SPELLING);
            }

            try {
                lastName = ValidationService.lastNameCheck(req.getParameter(LAST_NAME));
                httpSession.removeAttribute(LAST_NAME_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(LAST_NAME_SPELLING_ERROR, ERROR_SPELLING);
            }

            try {
                firstName = ValidationService.firstNameCheck(req.getParameter(FIRST_NAME));
                httpSession.removeAttribute(FIRST_NAME_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCounter++;
                httpSession.setAttribute(FIRST_NAME_SPELLING_ERROR, ERROR_SPELLING);
            }

            if (userDao.findByEmail(req.getParameter(EMAIL)) != null) {
                errorCounter++;
                httpSession.setAttribute(EMAIL_EXISTS_ERROR, ERROR_EMAIL_EXISTS);
            } else {
                httpSession.removeAttribute(EMAIL_EXISTS_ERROR);
            }

            if (userDao.findByLogin(req.getParameter(LOGIN)) != null) {
                errorCounter++;
                httpSession.setAttribute(LOGIN_EXISTS_ERROR, ERROR_LOGIN_EXISTS);
            } else {
                httpSession.removeAttribute(LOGIN_EXISTS_ERROR);
            }

            if (req.getRequestURI().equals(REGISTRATION_SERVLET)) {
                boolean verify = new VerifyRecaptcha(CaptchaParams.URL,
                        CaptchaParams.SECRET_KEY,
                        CaptchaParams.METHOD,
                        CaptchaParams.ACCEPT_LANGUAGE)
                        .verify(req.getParameter("g-recaptcha-response"));
                if (!verify) {
                    errorCounter++;
                    httpSession.setAttribute(CAPTCHA_ERROR, ERROR_CAPTCHA);
                } else {
                    httpSession.removeAttribute(CAPTCHA_ERROR);
                }
            }

            if (errorCounter == 0) {
                User user = new User.Builder()
                        .login(login)
                        .password(PasswordHashingUtil.encode(password, SHA_256_ALGORITHM))
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .role(Role.checkRole(req.getParameter(ROLE)))
                        .status(Status.UNBLOCKED)
                        .build();
                httpSession.setAttribute(CORRECT_USER, user);

                httpSession.removeAttribute(INCORRECT_USER_LOGIN);
                httpSession.removeAttribute(INCORRECT_USER_PASSWORD);
                httpSession.removeAttribute(INCORRECT_USER_AGAIN_PASSWORD);
                httpSession.removeAttribute(INCORRECT_USER_EMAIL);
                httpSession.removeAttribute(INCORRECT_USER_FIRST_NAME);
                httpSession.removeAttribute(INCORRECT_USER_LAST_NAME);
            } else {
                httpSession.setAttribute(CORRECT_USER, null);
                httpSession.setAttribute(INCORRECT_USER_LOGIN, req.getParameter(LOGIN));
                httpSession.setAttribute(INCORRECT_USER_PASSWORD, req.getParameter(PSW));
                httpSession.setAttribute(INCORRECT_USER_AGAIN_PASSWORD, req.getParameter(PSW_REPEAT));
                httpSession.setAttribute(INCORRECT_USER_EMAIL, req.getParameter(EMAIL));
                httpSession.setAttribute(INCORRECT_USER_FIRST_NAME, req.getParameter(FIRST_NAME));
                httpSession.setAttribute(INCORRECT_USER_LAST_NAME, req.getParameter(LAST_NAME));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
