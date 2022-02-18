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
                stage('Publish') {
                    steps {
                        script {
                            sh("npm config ls -l | grep config")
                            sh("cat /root/.npmrc")
                        }
                    }
                }     
        }
    }
}