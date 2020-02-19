package com.chinafight.gongxiangdaoyou.socket;

import com.alibaba.fastjson.JSONArray;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public Object onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        this.sid=sid;
        addOnlineCount();           //在线数加1
        logger.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        webSocketSet.add(this);     //加入set中
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            logger.info("websocket IO异常");
        }
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("收到来自窗口"+sid+"的信息:"+message);
        this.sendMessage("服务端已成功接收到消息："+message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    private  void sentObj(Object obj) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(obj);
    }


    /**
     * 指定用户发送自定义消息
     * */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        logger.info("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid==null) {
                    item.sendMessage(message);
                    logger.info("消息推送成功");
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                    logger.info("消息推送成功");
                }
            } catch (IOException ignored) {
                logger.info("消息推送失败");
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

    public static String objectToString(Object object){
        Object obj = JSONArray.toJSON(object);
        return obj.toString();
    }
}