#!/usr/bin/env groovy

def call(config = [:]) {
    def credentialsId = config.credentialsId
    def settingsXml = config.settingsXml
    def skipTest = config.skipTest ?: false
    def gitUrl = config.gitUrl

    println "git url: " + gitUrl
    assert gitUrl
    assert credentialsId

    pipeline {
        agent { label 'docker-maven-slave' }
        stages {
            stage('Clean Workspace') {
                steps {
                    cleanWs()
                }
            }
            stage('Checkout SCM') {
                steps {
                    checkout([$class: 'GitSCM',
                              branches: [[name: '*/master']],
                              doGenerateSubmoduleConfigurations: false,
                              extensions: [[$class: 'LocalBranch', localBranch: '**']],
                              submoduleCfg: [],
                              userRemoteConfigs: [[credentialsId: "${credentialsId}", url: "${gitUrl}"]]
                    ])
                }
            }
            stage('Create Release') {
                steps {
                    script {
                        withCredentials([string(credentialsId: "${credentialsId}", variable: 'TOKEN')]) {
                            return sh(script: "mvn -B -s ${settingsXml} -DskipTests=${skipTest} -DscmCommentPrefix="[ci skip] " release:prepare release:perform", returnStdout: true)
                        }
                    }
                }
            }
        }
    }

}


/*
        return sh(script: "mvn build-helper:parse-version versions:set -DnewVersion='\${parsedVersions.majorVersion}.\${parsedVersion.MinorVersion}.\${parsedVersion.NextIncrementalVersion}-SNAPSHOT'", returnStdout: true)

        return sh(script: "mvn -B -DskipTests=true versions:set -DnextSnapshot", returnStdout: true)
*/