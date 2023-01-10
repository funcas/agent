package cn.vv.gray.agent.core.plugin.interceptor.enhance;

public interface EnhancedInstance {
    Object getDynamicField();

    void setDynamicField(Object value);
}
