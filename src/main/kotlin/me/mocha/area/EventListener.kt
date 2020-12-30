package me.mocha.area

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.util.Vector
import kotlin.math.max
import kotlin.math.min

object EventListener : Listener {

    private val createQueue: MutableMap<Player, Pair<Int, Location?>> = mutableMapOf()

    fun isInQueue(player: Player): Boolean {
        return createQueue.containsKey(player)
    }

    fun addCreateQueue(player: Player, height: Int): Boolean {
        if (isInQueue(player)) return false
        createQueue[player] = height to null
        return true
    }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        if (isInQueue(player)) {
            val value = createQueue[player]!!
            val height = value.first
            val first = value.second

            if (first == null) {
                createQueue[player] = height to block.location
            } else {
                val second = block.location
                createQueue.remove(player)

                if (first.world.name != block.world.name) {
                    player.sendMessage(plugin.getMessage("worldnotsame"))
                    return
                }
                val startX = min(first.blockX, second.blockX)
                val startY = min(first.blockY, second.blockY)
                val startZ = min(first.blockZ, second.blockZ)

                val endX = max(first.blockX, second.blockX)
                val endY = startY + height
                val endZ = max(first.blockZ, second.blockZ)

                val start = Vector(startX, startY, startZ)
                val end = Vector(endX, endY, endZ)

                val area = AreaManager.createArea(block.world, start, end)
                player.sendMessage(plugin.getMessage("created", "id" to area.id))
            }
            event.isCancelled = true
        }
    }


}