package me.mocha.area.command

import me.mocha.area.EventListener
import me.mocha.area.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateAreaCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        if (sender !is Player) {
            sender.sendMessage(plugin.getMessage("errors.onlyplayer"))
            return true
        }

        val height = try {
            args[0].toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        if (EventListener.isInQueue(sender)) {
            sender.sendMessage(plugin.getMessage("errors.alreadyinqueue"))
            return true
        }

        EventListener.addCreateQueue(sender, height)
        sender.sendMessage(plugin.getMessage("readytocreate"))
        return true
    }

}