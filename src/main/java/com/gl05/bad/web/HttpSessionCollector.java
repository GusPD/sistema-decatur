package com.gl05.bad.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@WebListener
public class HttpSessionCollector implements HttpSessionListener {

    private static final Map<String, HttpSession> sesionesActivas = new ConcurrentHashMap<>();

    public static void agregarSesion(HttpSession sesion) {
        sesionesActivas.put(sesion.getId(), sesion);
    }

    public static void eliminarSesion(HttpSession sesion) {
        sesionesActivas.remove(sesion.getId(), sesion);
    }

    public static void eliminarSesiones(HttpSession sesion) {
        sesionesActivas.clear();
        sesionesActivas.put(sesion.getId(), sesion);
    }

    public static List<HttpSession> getSesionesActivas() {
        return new ArrayList<>(sesionesActivas.values());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        agregarSesion(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        eliminarSesion(se.getSession());
    }
}