package cn.vv.gray.plugin.rabbitmq;

import cn.vv.gray.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.DeclaredInstanceMethodsInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import cn.vv.gray.agent.core.plugin.match.ClassMatch;
import cn.vv.gray.agent.core.plugin.match.NameMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static cn.vv.gray.agent.core.plugin.bytebuddy.ArgumentTypeNameMatch.takesArgumentWithType;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class ChannelNInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    public static final String INTERCEPTOR_CLASS = "cn.vv.gray.plugin.rabbitmq.RabbitMQProducerInterceptor";
    public static final String ENHANCE_CLASS_PRODUCER = "com.rabbitmq.client.impl.ChannelN";
    public static final String PUBLISH_ENHANCE_METHOD = "basicPublish";
    public static final String CONSUME_ENHANCE_METHOD = "basicConsume";
    public static final String CONSUME_INTERCEPTOR_CONSTRUCTOR = "cn.vv.gray.plugin.rabbitmq.RabbitMQConsumerInterceptor";

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named(PUBLISH_ENHANCE_METHOD).and(takesArgumentWithType(4, "com.rabbitmq.client.AMQP$BasicProperties"));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return INTERCEPTOR_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return true;
                    }
                },
                new DeclaredInstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named(CONSUME_ENHANCE_METHOD).and(takesArguments(7));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return CONSUME_INTERCEPTOR_CONSTRUCTOR;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return true;
                    }
                }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(ENHANCE_CLASS_PRODUCER);
    }
}
