package me.mocha.area.provider

import me.mocha.area.AreaInfo
import me.mocha.area.plugin
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.util.Vector
import java.io.File

class YamlProvider : Provider {
     private val file = File(plugin.dataFolder, "area.yml")
     private val config = YamlConfiguration.loadConfiguration(file)

     override fun createArea(id: Int, world: String, start: Vector, end: Vector): AreaInfo {
          val info = AreaInfo(id, world, start, end)
          config[info.id.toString()] = info.serialize()
          save()
          return info
     }

     override fun save() {
          config.save(file)
     }
}