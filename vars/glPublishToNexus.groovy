#!/usr/bin/env groovy

def call(config = [:]) {
    def credentialsId = config.credentialsId
    def settingsXml = config.settingsXml
    def skipTest = config.skipTest ?: false

    def pom = readMavenPom file: 'pom.xml'

    withCredentials([string(credentialsId: "${credentialsId}", variable: 'TOKEN')]) {
        return sh(script: "mvn -B -DskipTests=${skipTest} -s ${settingsXml} deploy", returnStdout: true)
    }

}