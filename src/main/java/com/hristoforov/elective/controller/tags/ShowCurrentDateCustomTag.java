package com.hristoforov.elective.controller.tags;


import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;

public class ShowCurrentDateCustomTag extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        JspWriter writer = getJspContext().getOut();
        writer.print(LocalDate.now());
    }
}
