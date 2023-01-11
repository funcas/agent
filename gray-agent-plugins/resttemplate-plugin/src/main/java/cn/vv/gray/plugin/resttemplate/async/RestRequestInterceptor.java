package cn.vv.gray.plugin.resttemplate.async;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.context.AgentContextManager;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.springframework.http.client.AsyncClientHttpRequest;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class RestRequestInterceptor implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {

    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        AsyncClientHttpRequest clientHttpRequest = (AsyncClientHttpRequest) ret;
        if (ret != null) {
            String version = AgentContextManager.getCurrentContext().getVersion();
            clientHttpRequest.getHeaders().set(Constants.VERSION, version);
        }
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
