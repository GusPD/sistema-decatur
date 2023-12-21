package com.gl05.bad.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notificacion")
    @SendTo("/topic/notificaciones")
    public String notificacion( String message) {
        return message;
    }
}
