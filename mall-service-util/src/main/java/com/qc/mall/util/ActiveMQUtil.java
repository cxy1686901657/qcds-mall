package com.qc.mall.util;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.*;
import java.util.HashMap;

/**
 * @author qc
 * @date 2019/10/10
 * @description
 * @project mall
 */

public class ActiveMQUtil {
    PooledConnectionFactory pooledConnectionFactory = null;

    public ConnectionFactory init(String brokerUrl) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        //加入连接池
        pooledConnectionFactory = new PooledConnectionFactory(factory);
        //出现异常时重新连接
        pooledConnectionFactory.setReconnectOnException(true);
        //
        pooledConnectionFactory.setMaxConnections(5);
        pooledConnectionFactory.setExpiryTimeout(10000);
        return pooledConnectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return pooledConnectionFactory;
    }


    public void sendTransactedMapMessage(String queue, HashMap<String, String> param) {


        Connection connection = null;
        Session session = null;
        try {
            connection = getConnectionFactory().createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 支付成功后，引起的系统服务-》订单服务的更新-》库存服务-》物流服务
            // 调用mq发送支付成功的消息
            Queue queue1 = session.createQueue(queue);
            MessageProducer producer = session.createProducer(queue1);
            //TextMessage textMessage=new ActiveMQTextMessage();//字符串文本
            MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
            param.keySet().forEach(key -> {
                try {
                    mapMessage.setString(key, param.get(key));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            producer.send(mapMessage);

            session.commit();
        } catch (Exception ex) {
            // 消息回滚
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void sendDelayTransactedMapMessage(String queue, HashMap<String, String> param) {


        Connection connection = null;
        Session session = null;
        try {
            connection = getConnectionFactory().createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 支付成功后，引起的系统服务-》订单服务的更新-》库存服务-》物流服务
            // 调用mq发送支付成功的消息
            Queue queue1 = session.createQueue(queue);
            MessageProducer producer = session.createProducer(queue1);
            //TextMessage textMessage=new ActiveMQTextMessage();//字符串文本
            MapMessage mapMessage = new ActiveMQMapMessage();// hash结构
            param.keySet().forEach(key -> {
                try {
                    mapMessage.setString(key, param.get(key));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 20 * 1000);
            producer.send(mapMessage);

            session.commit();
        } catch (Exception ex) {
            // 消息回滚
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }
    }

}
