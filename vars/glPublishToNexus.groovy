#!/usr/bin/env groovy

def call(config[:]) {
    def credentialsId = config.credentialsId
    def settingsXml = config.settingsXml
    def skipTest = config.skipTest ?: false

    sh "mvn -B -DskipTests=true clean install"

}