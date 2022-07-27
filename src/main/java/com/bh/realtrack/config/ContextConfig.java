package com.bh.realtrack.config;

import com.bh.realtrack.model.CallerContext;
import com.bh.realtrack.service.ICallerContextManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


/**
 * @author Ganesh Mali
 */

@Configuration
public class ContextConfig {


    @Configuration
    @Profile("secure")
    static class CloudConfig {
        private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfig.class);

        private ICallerContextManager callerContextManager;

        @Autowired
        private HttpServletRequest request;

        @Autowired
        public CloudConfig(ICallerContextManager callerContextManager) {
            this.callerContextManager = callerContextManager;
        }

        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public CallerContext getCallerContext() {
            return this.callerContextManager.getCallerContext(request);
        }

    }

    @Configuration
    @Profile("!secure")
    static class LocalConfig {

    	private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfig.class);

        private ICallerContextManager callerContextManager;

        @Autowired
        private HttpServletRequest request;

        @Autowired
        public LocalConfig(ICallerContextManager callerContextManager) {
            this.callerContextManager = callerContextManager;
        }
        
        @Bean
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
        public CallerContext getCallerContext() {
            //we do not have an oauth token to get data from so we have to make it up
            //return new CallerContext("unsecured", null, Collections.EMPTY_LIST);
        	return this.callerContextManager.getCallerContext(request);
        }
    }

}
