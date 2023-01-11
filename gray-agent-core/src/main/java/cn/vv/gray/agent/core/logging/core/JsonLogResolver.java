package cn.vv.gray.agent.core.logging.core;

import cn.vv.gray.agent.core.logging.api.ILog;
import cn.vv.gray.agent.core.logging.api.LogResolver;
import com.google.gson.Gson;

public class JsonLogResolver implements LogResolver {
    private static final Gson GSON = new Gson();

    @Override
    public ILog getLogger(Class<?> aClass) {
        return new JsonLogger(aClass, GSON);
    }

    @Override
    public ILog getLogger(String s) {
        return new JsonLogger(s, GSON);
    }
}
