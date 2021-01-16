package me.mocha.spongeplugin.area.provider

import com.google.common.reflect.TypeToken
import me.mocha.spongeplugin.area.AreaSystem
import me.mocha.spongeplugin.area.util.AreaInfo
import me.mocha.spongeplugin.area.util.AreaSerializer
import me.mocha.spongeplugin.area.util.Vector3

class ConfigAreaProvider : AreaProvider {
    private val token = TypeToken.of(AreaInfo::class.java)

    private val config = AreaSystem.getInstance().config
    private val configRoot = config.load()

    override fun create(id: String, world: String, start: Vector3, end: Vector3): AreaInfo {
        val created = AreaInfo(id, world, start, end)
        configRoot.getNode(id).setValue(token, created)
        this.save()
        return created
    }

    override fun getById(id: String): AreaInfo? {
        return configRoot.getNode(id).getValue(token)
    }

    override fun getAll(): List<AreaInfo> {
        return configRoot.childrenMap.map { AreaSerializer.deserialize(token, it.value) }
    }

    private fun save() {
        config.save(configRoot)
    }

    override fun close() {
        this.save()
    }

}