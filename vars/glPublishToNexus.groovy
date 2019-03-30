#!/usr/bin/env groovy

def call(config = [:]) {
    def credentialsId = config.credentialsId
    def settingsXml = config.settingsXml
    def skipTest = config.skipTest ?: false

    def pom = readMavenPom file: 'pom.xml'

    withCredentials([usernamePassword(credentialsId: "${credentialsId}", usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASS')]) {
        return sh(script: "mvn -B -DskipTests=${skipTest} -s ${settingsXml} deploy", returnStdout: true)
    }

}