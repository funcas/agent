package cn.vv.gray.agent.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Shane Fang
 * @since 1.0
 */
public class AgentContextManager {

    private static final TransmittableThreadLocal<AgentContext> holder = TransmittableThreadLocal.withInitial(AgentContext::new);

    public static AgentContext getCurrentContext() {
        return holder.get();
    }

    public static void setCurrentContext(AgentContext context) {
        holder.set(context);
    }

    public static void clearContext() {
        holder.remove();
    }
}
