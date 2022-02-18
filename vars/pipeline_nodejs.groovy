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
                            sh('echo "34.66.223.61:30707/repository/npm-gorilla/:_authToken=NpmToken.fa1bc4c7-08ac-3f0d-b9a8-fbc9d2f7f867" >> ~/.npmrc')
                            sh("npm publish")
                        }
                    }
                }     
        }
    }
}