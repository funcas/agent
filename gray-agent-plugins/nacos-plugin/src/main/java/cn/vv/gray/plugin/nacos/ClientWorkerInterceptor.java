package cn.vv.gray.plugin.nacos;

import cn.vv.gray.agent.core.common.GrayConfig;
import cn.vv.gray.agent.core.common.GrayInfoContextHolder;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import cn.vv.gray.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import cn.vv.gray.agent.core.util.StringUtil;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class ClientWorkerInterceptor implements InstanceMethodsAroundInterceptor {

    private static final ILog logger = LogManager.getLogger(ClientWorkerInterceptor.class);
    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, MethodInterceptResult result) throws Throwable {

    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        String dataId = ((String) allArguments[0]);
        if(logger.isDebugEnable()) {
            logger.debug("got nacos config changed: {}", dataId);
        }
        if(dataId.contains("gray")) {
            String cfg = "";
            if (ret instanceof String[]) {
                String[] retObj = (String[]) ret;
                cfg = retObj[0];
            }else if (ret instanceof String) {
                cfg = (String)ret;
            }
            if(!StringUtil.isEmpty(cfg)) {
                Yaml yaml = new Yaml();
                GrayConfig grayConfig = yaml.loadAs(cfg, GrayConfig.class);
//                System.out.println(grayObj);
//                Map<String,Object> grayConfig = yaml.load(cfg);
//
//                logger.info("parse yaml {}", grayConfig);
//                Map<String,Object> gray = ((Map<String,Object>) grayConfig.get("gray"));
//                List<String> services = ((List<String>) gray.get("services"));
                GrayInfoContextHolder.getInstance().setTag(grayConfig.getGray().getTag());
                GrayInfoContextHolder.getInstance().setMode(grayConfig.getGray().getMode());
                GrayInfoContextHolder.getInstance().setServices(grayConfig.getGray().getServices());
            }

        }
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {

    }

}
