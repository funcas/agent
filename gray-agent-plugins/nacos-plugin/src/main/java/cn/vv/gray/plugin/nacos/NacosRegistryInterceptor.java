package cn.vv.gray.plugin.nacos;

import cn.vv.gray.agent.core.common.Config;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosRegistration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class NacosRegistryInterceptor implements InstanceMethodsAroundInterceptor {
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {
        NacosRegistration registration = (NacosRegistration) allArguments[0];
        Field field = NacosRegistration.class.getDeclaredField("nacosDiscoveryProperties");
        field.setAccessible(true);
        NacosDiscoveryProperties properties = registration.getNacosDiscoveryProperties();
        properties.getMetadata().put("tag", Config.Agent.TAG);

        field.set(registration, properties);
//        Map<String, String> metadata = registration.getMetadata();
        allArguments[0] = registration;

    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        return null;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }
}
