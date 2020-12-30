package me.mocha.area.provider

import me.mocha.area.AreaInfo
import org.bukkit.util.Vector

interface Provider {
    fun save()
    fun createArea(id: Int, world: String, start: Vector, end: Vector): AreaInfo
}