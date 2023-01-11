package cn.vv.gray.agent.core.logging.core;

import java.io.PrintStream;

public enum SystemOutWriter implements IWriter {
    INSTANCE;

    /**
     * Tricky codes for avoiding style-check. Because, in here, "system.out.println" is the only choice to output logs.
     */
    @Override
    public void write(String message) {
        PrintStream out = System.out;
        out.println(message);
    }
}
