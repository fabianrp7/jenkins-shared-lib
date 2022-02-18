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
                            sh("npm install")
                        }
                    }
                } 
                // stage('Publish') {
                //     steps {
                //         script {
                //             sh("printenv")
                //             sh("npm publish")
                //         }
                //     }
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