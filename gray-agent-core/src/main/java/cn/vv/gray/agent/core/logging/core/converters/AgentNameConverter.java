package cn.vv.gray.agent.core.logging.core.converters;

import cn.vv.gray.agent.core.common.Config;
import cn.vv.gray.agent.core.logging.core.Converter;
import cn.vv.gray.agent.core.logging.core.LogEvent;

public class AgentNameConverter implements Converter {
    @Override
    public String convert(LogEvent logEvent) {
        return Config.Agent.SERVICE_NAME;
    }

    @Override
    public String getKey() {
        return "agent_name";
    }
}
