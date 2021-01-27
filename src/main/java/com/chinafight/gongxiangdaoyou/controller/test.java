package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class test {
    @GetMapping("websocket")
    public Object test(){
        return "websocket";
    }

    @ResponseBody
    @GetMapping("sent")
    public Object sentMessage() throws IOException {
        return "success";
    }
}
