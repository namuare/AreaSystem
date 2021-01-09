package me.mocha.spongeplugin.area

import com.google.common.reflect.TypeToken
import com.google.inject.Inject
import me.mocha.spongeplugin.area.command.CreateAreaCommand
import me.mocha.spongeplugin.area.provider.AreaProvider
import me.mocha.spongeplugin.area.provider.ConfigAreaProvider
import me.mocha.spongeplugin.area.util.AreaInfo
import me.mocha.spongeplugin.area.util.AreaSerializer
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.loader.ConfigurationLoader
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection
import org.slf4j.Logger
import org.spongepowered.api.Sponge
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.event.game.state.GamePostInitializationEvent
import org.spongepowered.api.event.game.state.GamePreInitializationEvent
import org.spongepowered.api.event.game.state.GameStoppedServerEvent
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin

@Plugin(
    id = "areasystem",
    name = "AreaSystem",
    version = "1.0-SNAPSHOT",
    description = "area system for sponge api",
    dependencies = [
        Dependency(id = "kponge", version = "1.3.72"),
        Dependency(id = "worldedit", optional = false, version = "6.1.10")
    ]
)
class AreaSystem {

    @Inject
    lateinit var logger: Logger

    @Inject
    @DefaultConfig(sharedRoot = true)
    lateinit var config: ConfigurationLoader<CommentedConfigurationNode>

    companion object {
        private lateinit var instance: AreaSystem
        fun getInstance() = instance
    }

    init {
        instance = this
    }

    @Listener
    fun onPreInit(event: GamePreInitializationEvent) {
        TypeSerializerCollection.defaults().register(TypeToken.of(AreaInfo::class.java), AreaSerializer)
    }

    @Listener
    fun onInit(event: GameInitializationEvent) {
        Sponge.getEventManager().registerListeners(this, EventListener)
        Sponge.getCommandManager().register(this, CreateAreaCommand.spec, "createarea")
    }

    @Listener
    fun onPostInit(event: GamePostInitializationEvent) {
        Sponge.getServiceManager().setProvider(this, AreaProvider::class.java, ConfigAreaProvider)
    }

    @Listener
    fun onGameStopped(event: GameStoppedServerEvent) {
        ConfigAreaProvider.close()
    }

}