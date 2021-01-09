package me.mocha.spongeplugin.area.command

import me.mocha.spongeplugin.area.service.AreaService
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

object AreaListCommand : CommandExecutor {
    val spec: CommandSpec = CommandSpec.builder()
        .description(Text.of("print all areas."))
        .permission("areasystem.command.list")
        .arguments(
            GenericArguments.optional(GenericArguments.string(Text.of("id")))
        )
        .executor(this)
        .build()

    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        val id = args.getOne<String>(Text.of("id")).orElseGet { "" }

        val areas = AreaService.getAll().map { it.id }.filter { it.startsWith(id) }

        src.sendMessage(Text.builder("all area(${areas.size}): ${areas.joinToString()}").color(TextColors.LIGHT_PURPLE).build())
        return CommandResult.successCount(areas.size)
    }
}