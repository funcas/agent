package cn.vv.gray.plugin.scl;

import cn.vv.gray.agent.core.common.Constants;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import cn.vv.gray.agent.core.util.ReflectionUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class SpringCloudLoadBalanceInterceptor implements InstanceMethodsAroundInterceptor {
    private static final ILog logger = LogManager.getLogger(SpringCloudLoadBalanceInterceptor.class);
    final AtomicInteger position = new AtomicInteger(new Random().nextInt(1000));
    @Override
    public void beforeMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        Request request = (Request) allArguments[0];
        DefaultRequestContext requestContext = (DefaultRequestContext) request.getContext();
        RequestData clientRequest = (RequestData) requestContext.getClientRequest();
        HttpHeaders headers = clientRequest.getHeaders();
        RoundRobinLoadBalancer roundRobinLoadBalancer = (RoundRobinLoadBalancer) objInst;
        // TODO: 2023/1/9 : 这里如果性能低下可以适当加上缓存。
        ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider =
                ReflectionUtils.getFieldValue(roundRobinLoadBalancer, "serviceInstanceListSupplierProvider");
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        result.defineReturnValue(supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(serviceInstances, headers)));

    }

    @Override
    public Object afterMethod(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        return ret;
    }

    @Override
    public void handleMethodException(Object objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }

    private Response<ServiceInstance> processInstanceResponse(List<ServiceInstance> instances, HttpHeaders headers) {
        if (instances.isEmpty()) {
            return new EmptyResponse();
        } else {
            String reqVersion = headers.getFirst(Constants.VERSION);
            if (logger.isDebugEnable()) {
                logger.debug("[GW] - header x-version := {}", reqVersion);
            }
            if (StringUtils.isEmpty(reqVersion)) {
                return processRibbonInstanceResponse(instances);
            }

            List<ServiceInstance> serviceInstances = instances.stream()
                    .filter(instance -> reqVersion.equals(instance.getMetadata().get(Constants.META_VERSION)))
                    .collect(Collectors.toList());

            if (serviceInstances.size() > 0) {
                // 将筛选出来的服务实例负载均衡
                if(logger.isDebugEnable()) {
                    logger.debug("[GW] - got service instances: {}", serviceInstances.size());
                }
                return processRibbonInstanceResponse(serviceInstances);
            }

            // 如果一个都没找到，走默认负载均衡，这种情况一般是配置不对。
            return processRibbonInstanceResponse(instances);

        }
    }

    /**
     * 负载均衡器
     */
    private Response<ServiceInstance> processRibbonInstanceResponse(List<ServiceInstance> instances) {
        int pos = Math.abs(this.position.incrementAndGet());
        ServiceInstance instance = instances.get(pos % instances.size());
        return new DefaultResponse(instance);
    }
}


