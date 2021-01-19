package me.mocha.spongeplugin.area.model.entity

import me.mocha.spongeplugin.area.util.Vector3

data class Area(
    val id: String,
    val world: String,
    val start: Vector3,
    val end: Vector3
)