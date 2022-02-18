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
                            sh("npm adduser --registry='http://34.66.223.61:30707/repository/npm-gorilla/'")
                            sh("npm publish")
                        }
                    }
                }     
        }
    }
}