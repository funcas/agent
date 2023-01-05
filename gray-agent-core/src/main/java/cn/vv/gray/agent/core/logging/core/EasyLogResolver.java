package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogResolver;

/**
 * Created by wusheng on 2016/11/26.
 */
public class EasyLogResolver implements LogResolver {
    @Override
    public ILog getLogger(Class<?> clazz) {
        return new EasyLogger(clazz);
    }
}
