#!/usr/bin/env groovy

def call(config[:]) {
    def credentialsId = config.credentialsId
    def settingsXml = config.settingsXml
    def skipTest = config.skipTest ?: false

    def pom = readMavenPom file: 'pom.xml'

    withCredentials([string(credentialsId: "${githubToken}", variable: 'TOKEN')]) {
        return sh(script: """
            mvn -B -DskipTests=${skipTest} -s ${settingsXml} deploy
            git config user.email "jhng323@gmail.com"
            git config user.name "prodigy323"
            git tag -a ${pom.version} -m 'create tag ${pom.version}'
            git push --tags https://\${TOKEN}@github.com/prodigy323/basic-springboot-project.git
        """, returnStdout: true)
    }

}