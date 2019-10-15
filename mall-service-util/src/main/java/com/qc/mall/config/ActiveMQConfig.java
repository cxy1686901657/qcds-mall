package com.qc.mall.config;


import com.qc.mall.util.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @author qc
 * @date 2019/10/9
 * @description
 * @project mall
 */
@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.broker-url:disabled}")
    String brokerURL ;

    @Value("${spring.activemq.listener.enable:disabled}")
    String listenerEnable;

    @Bean
    public ActiveMQUtil getActiveMQUtil() throws JMSException {
        if(brokerURL.equals("disabled")){
            return null;
        }
        ActiveMQUtil activeMQUtil=new ActiveMQUtil();
        activeMQUtil.init(brokerURL);
        return  activeMQUtil;
    }
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        if(brokerURL.equals("disabled")){
            return null;
        }
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(brokerURL);
        return activeMQConnectionFactory;
    }

    //定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        if(!listenerEnable.equals("true")){
            return null;
        }

        factory.setConnectionFactory(activeMQConnectionFactory);
        //设置并发数
        factory.setConcurrency("5");

        //重连间隔时间
        factory.setRecoveryInterval(5000L);
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }


}
