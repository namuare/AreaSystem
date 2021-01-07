package me.mocha.spongeplugin.area

import com.google.inject.Inject
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.loader.ConfigurationLoader
import org.slf4j.Logger
import org.spongepowered.api.Sponge
import org.spongepowered.api.config.ConfigDir
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin

private lateinit var instance: AreaSystem

@Plugin(
    id = "areasystem",
    name = "AreaSystem",
    version = "1.0-SNAPSHOT",
    description = "area system for sponge api",
    dependencies = [Dependency(id = "kponge", optional = false, version = "1.0")]
)
class AreaSystem {

    @Inject
    lateinit var logger: Logger

    companion object {
        fun getInstance() = instance
    }

    @Listener
    fun onPreInit(event: GameInitializationEvent) {
        instance = this
        Sponge.getEventManager().registerListeners(this, EventListener)
    }

}