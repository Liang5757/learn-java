package cn.youyitech.anyview.system.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {
	public static void main(String[] args) {
        // 1. create log
        Logger log = Logger.getLogger(TestLog4j.class);
        // 2. get log config file
        PropertyConfigurator.configure("resource/log/log4j.properties");
        // 3. start log
        log.debug("Here is some DEBUG");
        log.info("Here is some INFO");
        log.warn("Here is some WARN");
        log.error("Here is some ERROR");
        log.fatal("Here is some FATAL");
    }
}
