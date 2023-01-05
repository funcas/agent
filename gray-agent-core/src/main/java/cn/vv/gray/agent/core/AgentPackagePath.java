package cn.vv.gray.agent.core;

import cn.vv.gray.agent.core.exception.AgentPackageNotFoundException;
import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class AgentPackagePath {
    private static final ILog logger = LogManager.getLogger(AgentPackagePath.class);

    private static File AGENT_PACKAGE_PATH;

    public static File getPath() throws AgentPackageNotFoundException {
        if (AGENT_PACKAGE_PATH == null) {
            AGENT_PACKAGE_PATH = findPath();
        }
        return AGENT_PACKAGE_PATH;
    }

    public static boolean isPathFound() {
        return AGENT_PACKAGE_PATH != null;
    }

    private static File findPath() throws AgentPackageNotFoundException {
        String classResourcePath = AgentPackagePath.class.getName().replaceAll("\\.", "/") + ".class";

        URL resource = AgentPackagePath.class.getClassLoader().getSystemClassLoader().getResource(classResourcePath);
        if (resource != null) {
            String urlString = resource.toString();

            logger.debug("The beacon class location is {}.", urlString);

            int insidePathIndex = urlString.indexOf('!');
            boolean isInJar = insidePathIndex > -1;

            if (isInJar) {
                urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
                File agentJarFile = null;
                try {
                    agentJarFile = new File(new URL(urlString).getFile());
                } catch (MalformedURLException e) {
                    logger.error(e, "Can not locate agent jar file by url:" + urlString);
                }
                if (agentJarFile.exists()) {
                    return agentJarFile.getParentFile();
                }
            } else {
                String classLocation = urlString.substring(urlString.indexOf("file:"), urlString.length() - classResourcePath.length());
                return new File(classLocation);
            }
        }

        logger.error("Can not locate agent jar file.");
        throw new AgentPackageNotFoundException("Can not locate agent jar file.");
    }

}
