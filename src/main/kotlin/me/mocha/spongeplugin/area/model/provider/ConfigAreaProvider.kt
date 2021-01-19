package me.mocha.spongeplugin.area.model.provider

import com.google.common.reflect.TypeToken
import me.mocha.spongeplugin.area.AreaSystem
import me.mocha.spongeplugin.area.model.entity.Area
import me.mocha.spongeplugin.area.model.serializer.AreaSerializer
import me.mocha.spongeplugin.area.util.Vector3

class ConfigAreaProvider : AreaProvider {
    private val token = TypeToken.of(Area::class.java)

    private val config = AreaSystem.getInstance().config
    private val configRoot = config.load()

    override fun create(id: String, world: String, start: Vector3, end: Vector3): Area {
        val created = Area(id, world, start, end)
        configRoot.getNode(id).setValue(token, created)
        this.save()
        return created
    }

    override fun getById(id: String): Area? {
        return configRoot.getNode(id).getValue(token)
    }

    override fun getAll(): List<Area> {
        return configRoot.childrenMap.map { AreaSerializer.deserialize(token, it.value) }
    }

    private fun save() {
        config.save(configRoot)
    }

    override fun close() {
        this.save()
    }

}