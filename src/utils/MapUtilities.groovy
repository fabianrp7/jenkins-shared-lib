package utils

class MapUtilities {
    /**
     * Deep Merge
     * Deep merges both maps, applying map B onto map A, and returns the result as a new map.
     * This function does not modify either existing map.
     * @param a The base map to apply values to.
     * @param b The map of values to apply over the base.
     * @return A new map of the merged values.
     */
    static Map deepMerge(Map a, Map b) {
        return (Map) b.inject(a.clone()) { Map map, entry ->
            if (map[entry.key] instanceof Map && entry.value instanceof Map) {
                map[entry.key] = deepMerge((Map) map[entry.key], (Map) entry.value)
            } else {
                map[entry.key] = entry.value
            }

            return map
        }
    }
}
