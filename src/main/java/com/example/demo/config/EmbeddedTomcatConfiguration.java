package com.example.demo.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;



    @Configuration
    public class EmbeddedTomcatConfiguration {

        @Value("${server.port}")
        private String serverPort;

        @Value("${server.additionalPorts:null}")
        private String additionalPorts;

        public EmbeddedTomcatConfiguration() {
        }

        public EmbeddedTomcatConfiguration(String serverPort, String additionalPorts) {
            this.serverPort = serverPort;
            this.additionalPorts = additionalPorts;
        }

        @Bean
        public TomcatServletWebServerFactory createTomcatWithConnectorsForPorts() {
            final TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
            final Connector[] additionalConnectors = createDistinctConnectorArray();
            if (additionalConnectors != null && additionalConnectors.length > 0) {
                tomcat.addAdditionalTomcatConnectors(additionalConnectors);
            }
            return tomcat;
        }

        private Connector[] createDistinctConnectorArray() {
            if (StringUtils.isBlank(additionalPorts)) {
                return null;
            }
            final String[] ports = additionalPorts.split(",");

            return Arrays.stream(ports)
                    .distinct()
                    .filter(port -> !serverPort.equals(port))
                    .map(this::createConnectorForPort)
                    .toArray(Connector[]::new);
        }

        private Connector createConnectorForPort(final String port) {
            final Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.parseInt(port));
            return connector;
        }
    }

