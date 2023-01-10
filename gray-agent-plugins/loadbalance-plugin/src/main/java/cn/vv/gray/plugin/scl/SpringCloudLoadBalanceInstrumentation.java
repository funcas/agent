package cn.vv.gray.plugin.scl;

import cn.vv.gray.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import cn.vv.gray.agent.core.plugin.match.ClassMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static cn.vv.gray.agent.core.plugin.bytebuddy.ArgumentTypeNameMatch.takesArgumentWithType;
import static cn.vv.gray.agent.core.plugin.match.NameMatch.byName;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class SpringCloudLoadBalanceInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    public static final String ENHANCE_CLASS = "org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer";
    private static final String INTERCEPT_CLASS = "cn.vv.gray.plugin.scl.SpringCloudLoadBalanceInterceptor";
    @Override
    protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
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
                        return named("choose")
                                .and(takesArgumentWithType(0, "org.springframework.cloud.client.loadbalancer.Request"));
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return INTERCEPT_CLASS;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }
}
