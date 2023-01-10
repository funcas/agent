package cn.vv.gray.plugin.ribbon;

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
public class AbstractServerPredicateInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    public static final String ENHANCE_CLASS = "com.netflix.loadbalancer.AbstractServerPredicate";

    private static final String INTERCEPT_CLASS = "cn.vv.gray.plugin.ribbon.AbstractServerPredicateInterceptor";

    @Override
    protected ClassMatch enhanceClass() {
        return byName(ENHANCE_CLASS);
    }

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new InstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        return named("getEligibleServers")
                                .and(takesArgumentWithType(0, "java.util.List"))
                                .and(takesArgumentWithType(1, "java.lang.Object"));
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
