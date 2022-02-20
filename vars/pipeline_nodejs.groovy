def call(body) {    
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

                stage('Docker build & push image') {
                    steps {
                        script {
                            def packageJSON = readJSON file: 'package.json'
                            def packageJSONVersion = packageJSON.version
                            println packageJSONVersion
                            sleep 9000
                            sh("mv Dockerfile mv Dockerfile-${packageJSON.version}")
                            sh("kubectl cp Dockerfile-${packageJSON.version} dood:/home -n gorilla-logic") 
                            sh("kubectl exec dood -- docker image build -f /home/Dockerfile-${packageJSON.version} /home/ --tag fabianrp7/timeoff:${packageJSON.version}") 
                            sh("kubectl exec dood -- docker login --username=fabianrp7 --password=fabrodpe2077*") 
                            sh("kubectl exec dood -- docker push fabianrp7/timeoff:${packageJSON.version}")
                            sh("kubectl exec dood -- docker rmi fabianrp7/timeoff:${packageJSON.version}")
                            }
                        }
                }
        }
    }
}