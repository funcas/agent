package cn.vv.gray.agent.core.context;

import java.io.Serializable;
import java.util.Map;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class AgentContext implements Serializable {

    private String version;

    private Map<String,Object> extData;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, Object> extData) {
        this.extData = extData;
    }
}
