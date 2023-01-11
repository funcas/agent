package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.AgentPackagePath;
import cn.vv.gray.agent.core.common.Config;
import cn.vv.gray.agent.core.config.SnifferConfigInitializer;
import cn.vv.gray.agent.core.exception.AgentPackageNotFoundException;
import cn.vv.gray.agent.core.plugin.PluginFinder;
import cn.vv.gray.agent.core.util.StringUtil;

public class WriterFactory {

    private static IWriter WRITER;

    public static IWriter getLogWriter() {

        switch (Config.Logging.OUTPUT) {
            case FILE:
                if (WRITER != null) {
                    return WRITER;
                }
                if (SnifferConfigInitializer.isInitCompleted()
                        && PluginFinder.isPluginInitCompleted()
                        && AgentPackagePath.isPathFound()) {
                    if (StringUtil.isEmpty(Config.Logging.DIR)) {
                        try {
                            Config.Logging.DIR = AgentPackagePath.getPath() + "/logs";
                        } catch (AgentPackageNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    WRITER = FileWriter.get();
                } else {
                    return SystemOutWriter.INSTANCE;
                }
                break;
            default:
                return SystemOutWriter.INSTANCE;

        }
        return WRITER;
    }
}
