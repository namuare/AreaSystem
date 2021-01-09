package me.mocha.spongeplugin.area.util

import com.google.common.reflect.TypeToken
import me.mocha.spongeplugin.area.AreaSystem
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer

object AreaSerializer : TypeSerializer<AreaInfo> {

    override fun deserialize(type: TypeToken<*>, value: ConfigurationNode): AreaInfo {
        val id = value.key.toString()

        AreaSystem.getInstance().logger.info(id)

        val world = value.getNode("world").string!!
        var start: Vector3
        var end: Vector3

        with(value.getNode("start")) {
            start = Vector3(getNode("x").int, getNode("y").int, getNode("z").int)
        }
        with(value.getNode("end")) {
            end = Vector3(getNode("x").int, getNode("y").int, getNode("z").int)
        }

        return AreaInfo(id, world, start, end)
    }

    override fun serialize(type: TypeToken<*>, area: AreaInfo?, value: ConfigurationNode) {
        area!!
        value.getNode("world").value = area.world

        with(value.getNode("start")) {
            this.getNode("x").value = area.start.x
            this.getNode("y").value = area.start.y
            this.getNode("z").value = area.start.z
        }

        with(value.getNode("end")) {
            this.getNode("x").value = area.end.x
            this.getNode("y").value = area.end.y
            this.getNode("z").value = area.end.z
        }
    }

}