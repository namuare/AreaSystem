package me.mocha.spongeplugin.area.provider

import com.google.common.reflect.TypeToken
import me.mocha.spongeplugin.area.AreaSystem
import me.mocha.spongeplugin.area.util.AreaInfo
import me.mocha.spongeplugin.area.util.Vector3

object ConfigAreaProvider : AreaProvider {

    private val config = AreaSystem.getInstance().config
    private val configRoot = config.load()

    override fun createArea(id: String, world: String, start: Vector3, end: Vector3): AreaInfo {
        val created = AreaInfo(id, world, start, end)
        configRoot.getNode(id).setValue(TypeToken.of(AreaInfo::class.java), created)
        save()
        return created
    }

    private fun save() {
        config.save(configRoot)
    }

    override fun close() {
        this.save()
    }

}