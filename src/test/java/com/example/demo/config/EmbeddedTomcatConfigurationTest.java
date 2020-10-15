package com.example.demo.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmbeddedTomcatConfigurationTest {


    @Test
    void addsAnAdditionalPort(){

        EmbeddedTomcatConfiguration oneNewPort = new EmbeddedTomcatConfiguration("8080", "8081");
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(0).getPort()).isEqualTo(8081);

    }
    @Test
    void addsTwoAdditionalPort(){

        EmbeddedTomcatConfiguration oneNewPort = new EmbeddedTomcatConfiguration("8080", "8081,8082");
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().size()).isEqualTo(2);
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(0).getPort()).isEqualTo(8081);
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(1).getPort()).isEqualTo(8082);

    }
    @Test
    void addsOnlyDistinctAdditionalPort(){

        EmbeddedTomcatConfiguration oneNewPort = new EmbeddedTomcatConfiguration("8080", "8081,8082,8081,8082,8081,8082,8081,8082,8081,8082,8081,8082,8081,8082,8081,8082,8081,8082");
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().size()).isEqualTo(2);
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(0).getPort()).isEqualTo(8081);
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(1).getPort()).isEqualTo(8082);

    }
    @Test
    void addsOnlyAdditionalPortThatAreDifferentFromServer(){

        EmbeddedTomcatConfiguration oneNewPort = new EmbeddedTomcatConfiguration("8080", "8081,8082,8080");
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().size()).isEqualTo(2);
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(0).getPort()).isEqualTo(8081);
        assertThat(oneNewPort.createTomcatWithConnectorsForPorts().getAdditionalTomcatConnectors().get(1).getPort()).isEqualTo(8082);

    }

}