package cn.vv.gray.plugin.rabbitmq;

import cn.vv.gray.agent.core.common.Config;
import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.common.GrayInfoContextHolder;
import cn.vv.gray.agent.core.context.AgentContextManager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.ChannelN;

import java.io.IOException;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class ConsumerProxy implements Consumer {
    private Consumer delegate;
    private ChannelN channel;

    public ConsumerProxy(final Consumer delegate, final ChannelN channel) {
        this.delegate = delegate;
        this.channel = channel;
    }

    @Override
    public void handleConsumeOk(final String consumerTag) {
        this.delegate.handleConsumeOk(consumerTag);
    }

    @Override
    public void handleCancelOk(final String consumerTag) {
        this.delegate.handleRecoverOk(consumerTag);
    }

    @Override
    public void handleCancel(final String consumerTag) throws IOException {
        this.delegate.handleCancel(consumerTag);
    }

    @Override
    public void handleShutdownSignal(final String consumerTag, final ShutdownSignalException sig) {
        this.delegate.handleShutdownSignal(consumerTag, sig);
    }

    @Override
    public void handleRecoverOk(final String consumerTag) {
        this.delegate.handleRecoverOk(consumerTag);
    }

    @Override
    public void handleDelivery(final String consumerTag,
                               final Envelope envelope,
                               final AMQP.BasicProperties properties,
                               final byte[] body) throws IOException {
        // AB模式下使用
        if (properties.getHeaders() != null && properties.getHeaders().get(Constants.VERSION) != null){
            AgentContextManager.getCurrentContext()
                    .setVersion(properties.getHeaders().get(Constants.VERSION).toString());
        }


        try {
            if (properties.getHeaders() != null && properties.getHeaders().get(Constants.TAG) != null) {
                // Gray模式下使用
                String tag = properties.getHeaders().get(Constants.TAG).toString();
                // 如果本服务是灰度部署的服务之一且生产者的tag和消费者的tag不一致，重回队列
                if (GrayInfoContextHolder.getInstance().needNackMqMessages() && !Config.Agent.TAG.equals(tag)) {
                    long deliveryTag = envelope.getDeliveryTag();
                    channel.basicReject(deliveryTag, true);
                    return;
                }
            }
            this.delegate.handleDelivery(consumerTag, envelope, properties, body);
        } finally {
            if (properties.getHeaders() != null && properties.getHeaders().get(Constants.VERSION) != null) {
                AgentContextManager.clearContext();
            }
        }
    }
}
