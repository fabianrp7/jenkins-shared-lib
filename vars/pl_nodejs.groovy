 def call(body) {
    def plParameters = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = plParameters
    body()
    plParameters.each { println(it) }
    node(label:plParameters.node){

    }
}