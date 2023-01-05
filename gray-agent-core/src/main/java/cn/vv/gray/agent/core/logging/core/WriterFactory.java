package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.AgentPackagePath;
import cn.vv.gray.agent.core.common.Config;
import cn.vv.gray.agent.core.exception.AgentPackageNotFoundException;
import cn.vv.gray.agent.core.util.StringUtil;

public class WriterFactory {
    public static IWriter getLogWriter() {
        if (AgentPackagePath.isPathFound()) {
            if (StringUtil.isEmpty(Config.Logging.DIR)) {
                try {
                    Config.Logging.DIR = AgentPackagePath.getPath() + "/logs";
                } catch (AgentPackageNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return FileWriter.get();
        } else {
            return SystemOutWriter.INSTANCE;
        }
    }
}
