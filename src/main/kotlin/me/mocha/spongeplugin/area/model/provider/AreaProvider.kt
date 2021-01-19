package me.mocha.spongeplugin.area.model.provider

import me.mocha.spongeplugin.area.model.entity.Area
import me.mocha.spongeplugin.area.util.Vector3

interface AreaProvider {
    fun create(id: String, world: String, start: Vector3, end: Vector3): Area
    fun getById(id: String): Area?
    fun getAll(): List<Area>
    fun close()
}
