package com.gl05.bad.domain;

import java.io.StringWriter;
import java.util.List;

import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class ReportePDF {

    public static String estadoCuentaMantenimiento(List<CuotaMantenimiento> cuotas) {
        JspFactory jspFactory = JspFactory.getDefaultFactory();
        PageContext pageContext = jspFactory.getPageContext(null, null, null, null, false, 0, false);
        pageContext.setAttribute("cuotas", cuotas);
        try {
            StringWriter stringWriter = new StringWriter();
            pageContext.include("/Reportes/EstadoCuentaMantenimiento.jsp", true);
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
