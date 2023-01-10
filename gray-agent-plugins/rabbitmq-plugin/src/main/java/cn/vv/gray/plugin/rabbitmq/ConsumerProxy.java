package cn.vv.gray.plugin.rabbitmq;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.context.AgentContextManager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class ConsumerProxy implements Consumer {
    private Consumer delegate;

    public ConsumerProxy(final Consumer delegate) {
        this.delegate = delegate;
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
        if (properties.getHeaders() != null && properties.getHeaders().get(Constants.VERSION) != null){
            AgentContextManager.getCurrentContext()
                    .setVersion(properties.getHeaders().get(Constants.VERSION).toString());

        }
        try {
            this.delegate.handleDelivery(consumerTag, envelope, properties, body);
        } finally {
            AgentContextManager.clearContext();
        }
    }
}
