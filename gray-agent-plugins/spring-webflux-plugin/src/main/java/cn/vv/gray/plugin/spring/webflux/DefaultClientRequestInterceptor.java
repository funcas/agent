package cn.vv.gray.plugin.spring.webflux;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.common.GrayInfoContextHolder;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import cn.vv.gray.agent.core.util.StringUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class DefaultClientRequestInterceptor implements InstanceMethodsAroundInterceptor {
    private static final ILog logger = LogManager.getLogger(DefaultClientRequestInterceptor.class);

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        ServerWebExchange serverWebExchange = (ServerWebExchange) allArguments[0];
        String active = GrayInfoContextHolder.getInstance().getTag();
        if (!StringUtil.isEmpty(active)) {
            logger.info("setting header version {}", active);
            ServerHttpRequest request = serverWebExchange.getRequest().mutate().header(Constants.VERSION, active).build();
            ServerWebExchange newServerWebExchange = serverWebExchange.mutate().request(request).build();
            allArguments[0] = newServerWebExchange;
        }
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
