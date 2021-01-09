package me.mocha.spongeplugin.area.service

import com.sk89q.worldedit.IncompleteRegionException
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.sponge.SpongeWorldEdit
import me.mocha.spongeplugin.area.provider.AreaProvider
import me.mocha.spongeplugin.area.provider.ConfigAreaProvider
import me.mocha.spongeplugin.area.util.AreaInfo
import me.mocha.spongeplugin.area.util.Vector3
import org.spongepowered.api.entity.living.player.Player

object AreaService {
    private val provider: AreaProvider = ConfigAreaProvider()

    @Throws(IncompleteRegionException::class)
    fun createArea(player: Player, id: String): AreaInfo {
        val session = SpongeWorldEdit.inst().getSession(player)

        val world = session.selectionWorld
        val sel = session.getSelection(world)

        if (sel is CuboidRegion) {

            val min = sel.minimumPoint
            val max = sel.maximumPoint
            val start = Vector3(min.blockX, min.blockY, min.blockZ)
            val end = Vector3(max.blockX, max.blockY, max.blockZ)

            return createArea(id, world.name, start, end)
        } else throw IncompleteRegionException()
    }

    fun createArea(id: String, world: String, start: Vector3, end: Vector3): AreaInfo {
        return provider.createArea(id, world, start, end)
    }

    fun getAll(): List<AreaInfo> {
        return provider.getAll()
    }

    fun close() {
        provider.close()
    }
}