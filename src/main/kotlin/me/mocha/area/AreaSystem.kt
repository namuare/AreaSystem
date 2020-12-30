package me.mocha.area

import me.mocha.area.command.CreateAreaCommand
import me.mocha.area.provider.YamlProvider
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: AreaSystem

class AreaSystem : JavaPlugin() {
    override fun onLoad() {
        dataFolder.mkdirs()
        saveDefaultConfig()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(EventListener, this)
        registerCommands()
        plugin = this
        AreaManager.provider = YamlProvider()
    }

    override fun onDisable() {
        saveConfig()
        AreaManager.save()
    }

    private fun registerCommands() {
        getCommand("createarea")?.setExecutor(CreateAreaCommand())
    }

    fun prefix() = config.getString("messages.prefix") ?: "[Area]"

    fun getMessage(path: String, vararg replace: Pair<String, Any>): String {
        val prefix = prefix()
        var message = config.getString(if (path.startsWith("messages.")) path else "messages.${path}") ?: ""

        replace.forEach { message = message.replace("%${it.first}%", it.second.toString()) }

        return "$prefix $message"
    }
}
