package me.mocha.spongeplugin.area

import com.google.common.reflect.TypeToken
import me.mocha.spongeplugin.area.util.AreaInfo
import me.mocha.spongeplugin.area.util.Vector3

interface AreaService {
    fun createArea(id: String, world: String, start: Vector3, end: Vector3)
    fun save()
}

object SimpleAreaService : AreaService {

    private val config = AreaSystem.instance.config
    private val configRoot = config.load()

    override fun createArea(id: String, world: String, start: Vector3, end: Vector3) {
        configRoot.getNode(id).setValue(TypeToken.of(AreaInfo::class.java), AreaInfo(id, world, start, end))
        save()
    }

    override fun save() {
        config.save(configRoot)
    }

}