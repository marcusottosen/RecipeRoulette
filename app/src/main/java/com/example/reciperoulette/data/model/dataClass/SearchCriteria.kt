package com.example.reciperoulette.data.model.dataClass

data class SearchCriteria(
    val cuisine: List<String>? = null,
    val diet: List<String>? = null,
    val intolerances: List<String>? = null,
    val type: String? = null,
    val maxReadyTime: Int? = null
) {
    fun mergeWith(other: SearchCriteria): SearchCriteria {
        return SearchCriteria(
            cuisine = this.cuisine ?: other.cuisine,
            diet = this.diet ?: other.diet,
            type = this.type ?: other.type,
            maxReadyTime = this.maxReadyTime ?: other.maxReadyTime
        )
    }

    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()

        cuisine?.let { map["cuisine"] = it.joinToString(",") }
        diet?.let { map["diet"] = it.joinToString(",") }
        intolerances?.let { map["intolerances"] = it.joinToString(",") }
        type?.let { map["type"] = it }
        maxReadyTime?.let { map["maxReadyTime"] = it.toString() }
        return map
    }
}
