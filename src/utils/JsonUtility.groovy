package utils

import groovy.json.JsonOutput

class JsonUtility {

    static String mapAsJSONString(Map map) {
        def mapAsJson = JsonOutput.toJson(map)
        JsonOutput.prettyPrint(mapAsJson)
    }

    static String mapAsEscapedJSONString(Map map) {
        def mapAsJson = JsonOutput.toJson(map)
        mapAsJson.replace("\"", "\\\"")
    }  
}