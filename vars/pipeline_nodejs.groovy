def call(body) {    
        node{ 
                stage('Checkout') {
                    if (binding.hasVariable('post_0_message')) {
                        if (post_0_message.contains('ci-skip')){
                            println post_0_message
                            currentBuild.result = 'ABORTED'
                            error("ci-skip in last commit")                                 
                        }
                    }

                    cleanWs()
                    checkout scm
                }

                stage('Build') {

                    sh("npm install") 

                }

                stage('Publish') {

                    sh("npm publish") 

                }

                stage('Docker build & push image') {
                            def packageJSON = readJSON file: 'package.json'
                            sh("mv Dockerfile Dockerfile-${packageJSON.version}")
                            sh("kubectl cp Dockerfile-${packageJSON.version} dood:/home -n gorilla-logic") 
                            sh("kubectl exec dood -- docker image build -f /home/Dockerfile-${packageJSON.version} /home/ --tag fabianrp7/timeoff:${packageJSON.version}") 
                            sh("kubectl exec dood -- docker login --username=fabianrp7 --password=fabrodpe2077*") 
                            sh("kubectl exec dood -- docker push fabianrp7/timeoff:${packageJSON.version}")
                            sh("kubectl exec dood -- docker rmi fabianrp7/timeoff:${packageJSON.version}")
                            sh("kubectl exec dood -- rm /home/Dockerfile-${packageJSON.version}")                          
                }
    }
}