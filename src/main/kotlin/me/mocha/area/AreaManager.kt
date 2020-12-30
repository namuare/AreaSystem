package me.mocha.area

import me.mocha.area.provider.Provider
import org.bukkit.World
import org.bukkit.util.Vector

object AreaManager {
    lateinit var provider: Provider

    fun createArea(world: World, start: Vector, end: Vector): AreaInfo {
        return provider.createArea(addLastAreaId(), world.name, start, end)
    }

    fun addLastAreaId(): Int {
        plugin.config["areaId"] = plugin.config.getInt("areaId") + 1
        plugin.saveConfig()
        return plugin.config.getInt("areaId")
    }

    fun save() {
        provider.save()
    }

}