package me.mocha.area

import org.bukkit.util.Vector

class AreaInfo(
    val id: Int,
    val world: String,
    val start: Vector,
    val end: Vector,
) {

    fun serialize(): Map<String, Any> {
        return mapOf(
            "world" to world,
            "start" to start.serialize(),
            "end" to end.serialize()
        )
    }

}