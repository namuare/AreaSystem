package me.mocha.spongeplugin.area.provider

import me.mocha.spongeplugin.area.util.AreaInfo
import me.mocha.spongeplugin.area.util.Vector3

interface AreaProvider {
    fun createArea(id: String, world: String, start: Vector3, end: Vector3): AreaInfo
    fun close()
}
