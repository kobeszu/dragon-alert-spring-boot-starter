package com.kobeszu.alert.configuration;

import com.kobeszu.alert.chain.RobotAlarmMsgHandler;
import com.kobeszu.alert.channel.robot.lark.LarkRobotAlarm;
import com.kobeszu.alert.current.RobotAlarmThreadPool;
import com.kobeszu.alert.chain.EnableAlarmMsgHandler;
import com.kobeszu.alert.chain.LevelAlarmMsgHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置说明:
 * dragon.alert.enable = true
 * dragon.alert.lark.webhook = xxxx
 * dragon.alert.lark.secretKey = xxxx
 *
 * @author kobeszu@163.com
 * @date 2021-04-21 15:12
 */
@Configuration
@ConditionalOnProperty(
        prefix = "dragon.alert",
        name = {"enable"},
        havingValue = "true",
        matchIfMissing = false
)
public class AlertAutoConfiguration {

    public AlertAutoConfiguration() {
        RobotAlarmThreadPool.getPool();
    }

    @Bean(initMethod = "register")
    public EnableAlarmMsgHandler enableAlarmMsgHandler() {
        return new EnableAlarmMsgHandler();
    }

    @Bean(initMethod = "register")
    public LevelAlarmMsgHandler levelAlarmMsgHandler() {
        return new LevelAlarmMsgHandler();
    }

    @Bean(initMethod = "register")
    public RobotAlarmMsgHandler robotAlarmMsgHandler() {
        return new RobotAlarmMsgHandler();
    }


    @Configuration
    @ConfigurationProperties(
            prefix = "dragon.alert.lark"
    )
    public class LarkRobotConfiguration {


        private String webhook;

        private String secretKey;


        public void setWebhook(String webhook) {
            this.webhook = webhook;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }


        @Bean
        public LarkRobotAlarm larkRobotAlarm() {
            return new LarkRobotAlarm(webhook, secretKey);
        }
    }


    //todo
    //后续增加其他渠道



}
