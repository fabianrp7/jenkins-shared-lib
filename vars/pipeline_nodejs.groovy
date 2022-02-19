def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    pipelineParams.each { println(it) }

    pipeline {
        agent { label "nodejs-agent" }    
        stages {
                stage('Checkout') {
                    steps {
                        script{
                            if (binding.hasVariable('post_0_message')) {
                                if (post_0_message.contains('ci-skip')){
                                    println post_0_message
                                    currentBuild.result = 'ABORTED'
                                    error("ci-skip in last commit")                                 
                                }
                            }                            

                        }
                    }
                }

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
                            sh("npm publish") 
                            }
                        }
                }
        }
    }
}        

