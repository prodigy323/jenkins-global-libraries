#!/usr/bin/env groovy

def call(config[:]) {
    def skipTest = config.skipTest ?: false

    sh "mvn -B -DskipTests=${skipTest} clean verify"

}