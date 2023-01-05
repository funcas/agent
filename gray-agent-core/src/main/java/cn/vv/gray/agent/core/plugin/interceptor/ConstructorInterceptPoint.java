package cn.vv.gray.agent.core.plugin.interceptor;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * One of the three "Intercept Point".
 * "Intercept Point" is a definition about where and how intercept happens.
 * In this "Intercept Point", the definition targets class's constructors, and the interceptor.
 * <p>
 * ref to two others: {@link StaticMethodsInterceptPoint} and {@link InstanceMethodsInterceptPoint}
 * <p>
 * Created by wusheng on 2016/11/29.
 */
public interface ConstructorInterceptPoint {
    /**
     * Constructor matcher
     *
     * @return matcher instance.
     */
    ElementMatcher<MethodDescription> getConstructorMatcher();

    String getConstructorInterceptor();
}
