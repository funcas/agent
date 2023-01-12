package cn.vv.gray.agent.core.common;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class GrayInfoContextHolder {

    private ModeEnum mode;
    private String tag;

    private String version;

    private Set<String> services = new HashSet<>();

    private static class Holder {
        private static final GrayInfoContextHolder holder = new GrayInfoContextHolder();
    }

    public static GrayInfoContextHolder getInstance() {
        return Holder.holder;
    }

    public boolean needReRouteServices(String serviceId) {
        return ModeEnum.Prod != this.mode && this.services.contains(serviceId);
    }

    public boolean needNackMqMessages() {
        return ModeEnum.Prod != this.mode && this.services.contains(Config.Agent.SERVICE_NAME);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ModeEnum getMode() {
        return mode;
    }

    public void setMode(ModeEnum mode) {
        this.mode = mode;
    }

    public Set<String> getServices() {
        return services;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
