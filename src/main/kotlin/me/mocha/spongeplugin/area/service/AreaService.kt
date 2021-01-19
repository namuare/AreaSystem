package me.mocha.spongeplugin.area.service

import com.flowpowered.math.vector.Vector3d
import com.sk89q.worldedit.IncompleteRegionException
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.sponge.SpongeWorldEdit
import me.mocha.spongeplugin.area.exception.AreaOverlapException
import me.mocha.spongeplugin.area.model.provider.AreaProvider
import me.mocha.spongeplugin.area.model.provider.ConfigAreaProvider
import me.mocha.spongeplugin.area.model.entity.Area
import me.mocha.spongeplugin.area.util.Vector3
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.world.World

object AreaService {
    private val provider: AreaProvider = ConfigAreaProvider()

    @Throws(IncompleteRegionException::class)
    fun createArea(player: Player, id: String): Area {
        val session = SpongeWorldEdit.inst().getSession(player)

        val world = session.selectionWorld
        val sel = session.getSelection(world)

        if (sel is CuboidRegion) {
            val min = sel.minimumPoint
            val max = sel.maximumPoint
            val start = Vector3(min.blockX, min.blockY, min.blockZ)
            val end = Vector3(max.blockX, max.blockY, max.blockZ)

            val overlaps = getOverlaps(world.name, start, end)
            if (overlaps.isNotEmpty()) {
                throw AreaOverlapException(overlaps)
            }

            return createArea(id, world.name, start, end)
        } else throw IncompleteRegionException()
    }

    fun createArea(id: String, world: String, start: Vector3, end: Vector3): Area {
        return provider.create(id, world, start, end)
    }

    fun getAreaByPlayer(player: Player): Area {
        return getAreaByPosition(player.world, player.position)
    }

    fun getAreaByPosition(world: World, pos: Vector3d): Area {
        return getAreaByPosition(world.name, Vector3(pos.floorX, pos.floorY, pos.floorZ))
    }

    fun getAreaByPosition(world: String, pos: Vector3): Area {
        return getAll().first {
            it.world == world &&
                    ((pos.x >= it.start.x && pos.x <= it.end.x) &&
                            (pos.z >= it.start.z && pos.z <= it.end.z) &&
                            (pos.y >= it.start.y && pos.y <= it.end.y))
        }
    }

    fun getAreaById(id: String): Area? {
        return provider.getById(id)
    }

    fun getAll(): List<Area> {
        return provider.getAll()
    }

    fun getOverlaps(world: String, start: Vector3, end: Vector3): List<Area> {
        return getAll().filter {
            it.world == world && (
                    ((start.x >= it.start.x && start.x <= it.end.x) || (end.x >= it.start.x && end.x <= it.end.x)) &&
                            ((start.z >= it.start.z && start.z <= it.end.z) || (end.z >= it.start.z && end.z <= it.end.z)) &&
                            ((start.y >= it.start.y && start.y <= it.end.y) || (end.y >= it.start.y && end.y <= it.end.y))
                    )
        }
    }

    fun close() {
        provider.close()
    }
}