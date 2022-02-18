def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    pipelineParams.each { println(it) }

    pipeline {
        agent { label "nodejs-agent" }
        environment {
            NEXUS_VERSION = "nexus3"
            NEXUS_PROTOCOL = "http"
            NEXUS_URL = "http://34.122.90.105:30707"
            NEXUS_REPOSITORY = "npm-gorilla-int"
            NEXUS_CREDENTIAL_ID = "nexuscredentials"

    }
    
        stages {
                stage('Build') {
                    steps {
                        script {
                            sh("npm install")
                            withCredentials([usernamePassword(credentialsId: 'nexuscredentials', passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]) {
                            sh("npm publish")
                            }
                            //sh("npm-publish-nexus --domain=http://34.122.90.105:30707/ --repo=npm-gorilla-int")
                        }
                    }
                }


        // stage('NPM: Config') {
        // withCredentials([usernamePassword(credentialsId: nexusCredentialsId, passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]) {
        //     def token = sh(returnStdout: true, script: "set +x && curl -s -k -H \"Accept: application/json\" -H \"Content-Type:application/json\" -X PUT --data '{\"name\": \"$NEXUS_USERNAME\", \"password\": \"$NEXUS_PASSWORD\"}' https://nexus-repository.net:8088/repository/my-npm/-/user/org.couchdb.user:$NEXUS_USERNAME 2>&1 | grep -Po '(?<=\"token\":\")[^\"]*'")
        //     sh "set +x && echo \"//nexus-repository.net:8088/repository/my-npm/:_authToken=$token\" >> .npmrc"
        // }
        // }

                   

                // stage('Docker build') {
                //     steps {
                //         script {
                //             def dockerImage = docker.build("my-image:${env.BUILD_ID}")
                        
                //             /* Push the container to the docker Hub */
                //             dockerImage.push()

                //             /* Remove docker image*/
                //             sh 'docker rmi -f my-image:${env.BUILD_ID}'
                //         }
                //     }
                // } 
                
        }
    }
}