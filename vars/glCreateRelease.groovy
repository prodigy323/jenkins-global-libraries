#!/usr/bin/env groovy

def call(config = [:]) {
    def credentialsId = config.credentialsId
    def settingsXml = config.settingsXml
    def skipTest = config.skipTest ?: false

    def pom = readMavenPom file: 'pom.xml'

    withCredentials([string(credentialsId: "${credentialsId}", variable: 'TOKEN')]) {
        return sh(script: "mvn -B -DskipTests=${skipTest} release:prepare release:perform", returnStdout: true)
    }

}


/*
        return sh(script: "mvn build-helper:parse-version versions:set -DnewVersion='\${parsedVersions.majorVersion}.\${parsedVersion.MinorVersion}.\${parsedVersion.NextIncrementalVersion}-SNAPSHOT'", returnStdout: true)

        return sh(script: "mvn -B -DskipTests=true versions:set -DnextSnapshot", returnStdout: true)
*/