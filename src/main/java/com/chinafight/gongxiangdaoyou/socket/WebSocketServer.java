package com.chinafight.gongxiangdaoyou.socket;

import com.alibaba.fastjson.JSONArray;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.service.order.OrderManger;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/websocket/{sid}")
@Component
@EqualsAndHashCode
public class WebSocketServer {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private Session session;//与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String sid = "";//接收sid
    private OrderDTO curOrder;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public Object onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        addOnlineCount();
        Utils.getLogger().info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        webSocketSet.add(this);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        Utils.getLogger().info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        Utils.getLogger().info("收到来自窗口" + sid + "的信息:" + message);
        for (WebSocketServer server : webSocketSet) {
            server.sendMessage(message);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        Utils.getLogger().info("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private void sentObj(Object obj) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(obj);
    }


    /**
     * 指定用户或者群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        Utils.getLogger().info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                    Utils.getLogger().info("向全体用户推送消息推送成功");
                    return;
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                    Utils.getLogger().info("指定用户推送消息成功");
                    return;
                }
            } catch (IOException ignored) {
                Utils.getLogger().info("消息推送失败");
            }
        }
        Utils.getLogger().info("消息推送失败");
    }

    public static Integer privateChat(String message,@PathParam("sid") String sid){
        for (WebSocketServer item : webSocketSet) {
            if (item.sid.equals(sid)){
                try {
                    item.sendMessage(message);
                    Utils.getLogger().info("向"+sid+"用户私聊信息"+message);
                    return 0;
                } catch (IOException e) {
                    Utils.getLogger().info("私聊失败");
                }
            }
        }
        Utils.getLogger().info("用户"+sid+"未上线");
        return -1;
    }

    public static void groupChat(String message){
        for (WebSocketServer item: webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                Utils.getLogger().info("全体消息发送失败");
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public void setCurOrder(OrderDTO curOrder) {
        this.curOrder = curOrder;
    }
}