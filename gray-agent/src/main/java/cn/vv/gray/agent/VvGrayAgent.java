package cn.vv.gray.agent;

import cn.vv.gray.agent.core.config.SnifferConfigInitializer;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;
import cn.vv.gray.agent.core.plugin.AbstractClassEnhancePluginDefine;
import cn.vv.gray.agent.core.plugin.EnhanceContext;
import cn.vv.gray.agent.core.plugin.PluginBootstrap;
import cn.vv.gray.agent.core.plugin.PluginFinder;
import cn.vv.gray.agent.core.plugin.exception.PluginException;
import cn.vv.gray.agent.core.util.StringUtil;
import com.alibaba.ttl.threadpool.agent.TtlAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Properties;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class VvGrayAgent {

    private static final ILog logger = LogManager.getLogger(VvGrayAgent.class);
    public static void premain(String agentArgs, Instrumentation instrumentation) throws PluginException {
        TtlAgent.premain(agentArgs, instrumentation);

        final PluginFinder pluginFinder;

        try {
            SnifferConfigInitializer.initialize(agentArgs);

            pluginFinder = new PluginFinder(new PluginBootstrap().loadPlugins());

        } catch (Exception e) {
            logger.error(e, "agent initialized failure. Shutting down.");
            return;
        }


        new AgentBuilder.Default().type(pluginFinder.buildMatch()).transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
            List<AbstractClassEnhancePluginDefine> pluginDefines = pluginFinder.find(typeDescription, classLoader);
            if (pluginDefines.size() > 0) {
                DynamicType.Builder<?> newBuilder = builder;
                EnhanceContext context = new EnhanceContext();
                for (AbstractClassEnhancePluginDefine define : pluginDefines) {
                    DynamicType.Builder<?> possibleNewBuilder = define.define(typeDescription, newBuilder, classLoader, context);
                    if (possibleNewBuilder != null) {
                        newBuilder = possibleNewBuilder;
                    }
                }
                if (context.isEnhanced()) {
                    logger.debug("Finish the prepare stage for {}.", typeDescription.getName());
                }

                return newBuilder;
            }

            logger.debug("Matched class {}, but ignore by finding mechanism.", typeDescription.getTypeName());
            return builder;
        }).with(new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                                         boolean loaded, DynamicType dynamicType) {
                if (logger.isDebugEnable()) {
                    logger.debug("On Transformation class {}.", typeDescription.getName());
                }

//                InstrumentDebuggingClass.INSTANCE.log(typeDescription, dynamicType);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                                  boolean loaded) {

            }

            @Override public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
                                          Throwable throwable) {
                logger.error("Enhance class " + typeName + " error.", throwable);
            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
        }).installOn(instrumentation);
    }
}