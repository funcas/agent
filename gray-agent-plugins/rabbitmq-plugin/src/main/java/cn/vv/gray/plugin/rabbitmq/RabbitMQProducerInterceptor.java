package cn.vv.gray.plugin.rabbitmq;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.common.GrayInfoContextHolder;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import cn.vv.gray.agent.core.util.StringUtil;
import com.rabbitmq.client.AMQP;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class RabbitMQProducerInterceptor implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        AMQP.BasicProperties properties = (AMQP.BasicProperties) allArguments[4];
        AMQP.BasicProperties.Builder propertiesBuilder;

        Map<String, Object> headers = new HashMap<String, Object>();
        if (properties != null) {
            propertiesBuilder = properties.builder()
                    .appId(properties.getAppId())
                    .clusterId(properties.getClusterId())
                    .contentEncoding(properties.getContentEncoding())
                    .contentType(properties.getContentType())
                    .correlationId(properties.getCorrelationId())
                    .deliveryMode(properties.getDeliveryMode())
                    .expiration(properties.getExpiration())
                    .messageId(properties.getMessageId())
                    .priority(properties.getPriority())
                    .replyTo(properties.getReplyTo())
                    .timestamp(properties.getTimestamp())
                    .type(properties.getType())
                    .userId(properties.getUserId());

            // copy origin headers
            if (properties.getHeaders() != null) {
                headers.putAll(properties.getHeaders());
            }
        } else {
            propertiesBuilder = new AMQP.BasicProperties.Builder();
        }
        String tag = GrayInfoContextHolder.getInstance().getTag();
        String version = GrayInfoContextHolder.getInstance().getVersion();
        if(!StringUtil.isEmpty(tag)){
            headers.put(Constants.TAG, tag);
        }
        if(!StringUtil.isEmpty(version)) {
            headers.put(Constants.VERSION, version);
        }

        allArguments[4] = propertiesBuilder.headers(headers).build();
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
