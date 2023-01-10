package cn.vv.gray.plugin.resttemplate.async;

import cn.vv.gray.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import cn.vv.gray.agent.core.plugin.match.ClassMatch;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static cn.vv.gray.agent.core.plugin.match.NameMatch.byName;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class RestTemplateInstrumentation extends ClassInstanceMethodsEnhancePluginDefine {
    private static final String ENHANCE_CLASS = "org.springframework.web.client.AsyncRestTemplate";
    private static final String CREATE_REQUEST_METHOD_NAME = "createAsyncRequest";
    private static final String CREATE_REQUEST_INTERCEPTOR = "cn.vv.gray.plugin.resttemplate.async.RestRequestInterceptor";

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
                        return named(CREATE_REQUEST_METHOD_NAME);
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return CREATE_REQUEST_INTERCEPTOR;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }
}
