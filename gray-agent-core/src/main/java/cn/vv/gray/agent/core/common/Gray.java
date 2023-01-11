package cn.vv.gray.agent.core.common;

import java.util.Set;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class Gray {

    private ModeEnum mode;

    private String tag;

    private Set<String> services;

    public ModeEnum getMode() {
        return mode;
    }

    public void setMode(ModeEnum mode) {
        this.mode = mode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<String> getServices() {
        return services;
    }

    public void setServices(Set<String> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Gray{" +
                "mode=" + mode +
                ", tag='" + tag + '\'' +
                ", services=" + services +
                '}';
    }
}