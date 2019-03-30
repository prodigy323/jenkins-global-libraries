#!/usr/bin/env groovy

def call(config = [:]) {
    def skipTest = config.skipTest ?: false

    return sh(script: "mvn -B -DskipTests=${skipTest} clean verify", returnStdout: true)

}