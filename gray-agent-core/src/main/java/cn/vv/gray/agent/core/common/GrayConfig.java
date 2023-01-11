package cn.vv.gray.agent.core.common;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class GrayConfig {

    private Gray gray;

    public Gray getGray() {
        return gray;
    }

    public void setGray(Gray gray) {
        this.gray = gray;
    }

    @Override
    public String toString() {
        return "GrayConfig{" +
                "gray=" + gray +
                '}';
    }
}
