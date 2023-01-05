package cn.vv.gray.agent.core.plugin.exception;


public class IllegalPluginDefineException extends Exception {
    public IllegalPluginDefineException(String define) {
        super("Illegal plugin define : " + define);
    }
}
