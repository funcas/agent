package cn.vv.gray.plugin.nacos;

import cn.vv.gray.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import cn.vv.gray.agent.core.plugin.match.ClassMatch;
import cn.vv.gray.agent.core.plugin.match.NameMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class NacosRegistryInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    public static final String ENHANCE_CLASS = "com.alibaba.cloud.nacos.registry.NacosServiceRegistry";
    public static final String INTERCEPT_CLASS = "cn.vv.gray.plugin.nacos.NacosRegistryInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return NameMatch.byName(ENHANCE_CLASS);
    }

    @Override
    protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named("register");
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return true;
                    }
                }
        };
    }
}
