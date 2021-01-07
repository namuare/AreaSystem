package me.mocha.spongeplugin.area

import com.google.common.reflect.TypeToken
import com.google.inject.Inject
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
import org.spongepowered.api.event.game.state.GameStoppedServerEvent
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin

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
    fun onInit(event: GameInitializationEvent) {
        instance = this
        TypeSerializerCollection.defaults().register(TypeToken.of(AreaInfo::class.java), AreaSerializer)
        Sponge.getEventManager().registerListeners(this, EventListener)
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