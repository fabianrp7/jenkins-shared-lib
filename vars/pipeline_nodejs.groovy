def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    pipelineParams.each { println(it) }

    pipeline {
        agent { label "nodejs-agent" }
    
        stages {
                stage('Build') {
                    steps {
                        script {
                            //sh("npm install")
                            sh("npm-publish-nexus --domain=http://34.122.90.105:30707/ --repo=npm-gorilla-int")
                        }
                    }
                }

                   

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