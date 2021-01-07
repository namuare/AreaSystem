package me.mocha.spongeplugin.area

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.filter.cause.Root
import org.spongepowered.api.event.item.inventory.InteractItemEvent

const val contractId = "item.contract.landcontract"

object EventListener {

    private val logger = AreaSystem.getInstance().logger

    @Listener
    fun onInteract(event: InteractItemEvent, @Root player: Player) {
    }

}