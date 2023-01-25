package com.hristoforov.elective.controller.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {

    void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
