def call(body) {    
        node{ 
            def pkgVersion = ""
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
                            pkgVersion = readJSON file: 'package.json'
                            sh("mv Dockerfile Dockerfile-${pkgVersion}")
                            sh("kubectl cp Dockerfile-${pkgVersion} dood:/home -n gorilla-logic") 
                            sh("kubectl exec dood -- docker image build -f /home/Dockerfile-${pkgVersion} /home/ --tag fabianrp7/timeoff:${pkgVersion}") 
                            sh("kubectl exec dood -- docker login --username=fabianrp7 --password=fabrodpe2077*") 
                            sh("kubectl exec dood -- docker push fabianrp7/timeoff:${pkgVersion}")
                            sh("kubectl exec dood -- docker rmi fabianrp7/timeoff:${pkgVersion}")
                            sh("kubectl exec dood -- rm /home/Dockerfile-${pkgVersion}")                          
                }

                stage('Kubernetes Deploy') {
                            sh("sed -i 's/timeoff:tag/timeoff:${pkgVersion}/g' deployment.yaml")
                            sh("kubectl apply -f deployment.yaml -n gorilla-logic")                                                     
                }
    }
}