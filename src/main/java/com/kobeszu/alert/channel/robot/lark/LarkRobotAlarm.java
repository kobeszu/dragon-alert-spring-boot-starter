package com.kobeszu.alert.channel.robot.lark;

import com.alibaba.fastjson.JSONObject;
import com.kobeszu.alert.channel.robot.IRobotAlarm;
import com.kobeszu.alert.constant.RobotConstant;
import com.kobeszu.alert.utils.HttpUtil;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author kobeszu@163.com
 * @date 2021-04-22 11:28
 */
public class LarkRobotAlarm implements IRobotAlarm {

    private static final Logger logger = LoggerFactory.getLogger(LarkRobotAlarm.class);

    private String webhook;

    private String secretKey;


    public LarkRobotAlarm(String webhook, String secretKey) {
        this.webhook = webhook;
        this.secretKey = secretKey;
    }

    @Override
    public void alarm(String msg) {
        try {
            if(msg == null || msg.length() == 0) {
                return;
            }
            String json = toJson(msg);
            Response response = HttpUtil.post(webhook, json);
            if(!response.isSuccessful()) {
                logger.warn("[{}] msg:{} error response:{}", RobotConstant.ERROR_CONTEXT, json, response.toString());
            }
        } catch (Exception e) {
            logger.warn("[{}] error:{}", RobotConstant.ERROR_CONTEXT, e.getMessage(), e);
        }
    }

    private String toJson(String msg) {
        JSONObject contentJson = new JSONObject();
        contentJson.put("text", msg);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg_type", "text");
        jsonObject.put("content", contentJson);
        return jsonObject.toJSONString();
    }
}
