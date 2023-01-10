package cn.vv.gray.plugin.resttemplate.sync;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.context.AgentContextManager;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpRequest;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class RestRequestInterceptor implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {

    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        ClientHttpRequest clientHttpRequest = (ClientHttpRequest) ret;
        if (clientHttpRequest instanceof AbstractClientHttpRequest) {
            AbstractClientHttpRequest httpRequest = (AbstractClientHttpRequest) clientHttpRequest;
            String version = AgentContextManager.getCurrentContext().getVersion();
            httpRequest.getHeaders().set(Constants.VERSION, version);
        }
        return ret;
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
