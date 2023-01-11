package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.common.Config;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogResolver;

public class PatternLogResolver implements LogResolver {

    @Override
    public ILog getLogger(Class<?> clazz) {
        return new PatternLogger(clazz, Config.Logging.PATTERN);
    }

    @Override
    public ILog getLogger(String clazz) {
        return new PatternLogger(clazz, Config.Logging.PATTERN);
    }
}
