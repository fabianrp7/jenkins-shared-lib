package utils

class ParameterUtilities {
    /**
     * Resolve Env Param
     *
     * Resolves a parameter to its literal value if its not a map, otherwise resolves it to the mapped value based on the environment set.
     *
     * @param environment The environment/key used to look up the value in the map.
     * @param parameter The parameter object/value itself.
     * @param parameterName The name of the parameter (for error messaging only).
     * @param msgTitle The msg title (for error messaging only).
     *
     * @return The resolved parameter.
     */
    static resolveEnvParam(environment, parameter, parameterName, msgTitle) {
        if (parameter instanceof java.util.Map) {
            if (!environment) {
                error "${msgTitle} Environment needs to be set when a parameter definition is a map."
            }
            if (!parameter[environment]) {
                error "${msgTitle} Missing value for environment '${environment}' on parameter '${parameterName}'."
            }

            return parameter[environment]
        } else {
            return parameter
        }
    }
}
