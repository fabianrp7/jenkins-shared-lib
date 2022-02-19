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
                            sleep(800) 
                            sh("npm publish")                                                            
                            }
                        }
                }
        }
    }
}                

// static String urlShareLibrary = "https://github.com/fabianrp7/jsl.git"
//    static String gitCredentialsId = "github_login"
//    static String nameJenkinsSharedLib = "jsl"
//    static String nexusCredentialsId = "nexus-login" 
//    static String nexusUrl = "34.123.87.228:30777"   
//    static String nexusGroupId = "raw"
//    static String nexusVersion = "nexus3"  
//    static String nexusRepository = 'artifacts_'


//         void nexusArtifactUpload(project){
//         def now = new Date()
//         def creationDate = now.format("yyyyMMddHHmm", TimeZone.getTimeZone("GMT-5:00"))
//             nexusArtifactUploader(
//                     nexusVersion: 'nexus3',
//                     protocol: 'http',
//                     nexusUrl: 'http://34.122.90.105:30707/',
//                     groupId: '',
//                     version: scriptPL.env.JOB_BASE_NAME+"_${scriptPL.env.BUILD_ID}_${creationDate}",                    
//                     repository: GlobalVars.nexusRepository+scriptPL.env.JOB_BASE_NAME,
//                     credentialsId: 'nexus-login',
//                     artifacts: [
//                         [artifactId: project.name,
//                         classifier: '',
//                         file: project.artifactFile,
//                         type: 'zip']
//                     ]
//                     )
//     }





//     }

                   

                
                
//         }
//     }
// }