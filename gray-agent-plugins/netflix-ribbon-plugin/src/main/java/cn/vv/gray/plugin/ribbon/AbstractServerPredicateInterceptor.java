package cn.vv.gray.plugin.ribbon;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.context.AgentContextManager;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.Server;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link AbstractServerPredicateInterceptor} intercept the implementation of http calls by the Feign LoadBalancer.
 */
public class AbstractServerPredicateInterceptor implements InstanceMethodsAroundInterceptor {
    private static final ILog logger = LogManager.getLogger(AbstractServerPredicateInterceptor.class);


    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                             MethodInterceptResult result) throws Throwable {
//        PredicateBasedRule inst = (PredicateBasedRule) objInst;
        List<Server> servers = (List<Server>) allArguments[0];
        String version = AgentContextManager.getCurrentContext().getVersion();
        if(logger.isDebugEnable()) {
            logger.debug("[RIBBON] - got header version {}", version);
        }
        if(version == null || "".equals(version)) {
            return;
        }
        List<Server> newServers = servers.stream().filter(s -> {
            NacosServer server = (NacosServer) s;

            return server.getMetadata().containsKey(Constants.META_VERSION)
                    && server.getMetadata().get(Constants.META_VERSION).equals(version);
        }).collect(Collectors.toList());
        if (newServers.size() == 0) {
            return;
        }

        if(logger.isDebugEnable()) {
            logger.debug("[RIBBON] - got service instances {}", newServers.size());
        }
        allArguments[0] = newServers;
    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                              Object ret) {
        return ret;
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments,
                                      Class<?>[] argumentsTypes, Throwable t) {

    }


}
