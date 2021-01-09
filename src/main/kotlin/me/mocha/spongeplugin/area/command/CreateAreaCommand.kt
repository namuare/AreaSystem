package me.mocha.spongeplugin.area.command

import com.sk89q.worldedit.IncompleteRegionException
import me.mocha.spongeplugin.area.service.AreaService
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

object CreateAreaCommand : CommandExecutor {
    val spec: CommandSpec = CommandSpec.builder()
        .description(Text.of("create an area."))
        .permission("areasystem.command.create")
        .arguments(
            GenericArguments.onlyOne(GenericArguments.string(Text.of("id")))
        )
        .executor(this)
        .build()

    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        return if (src is Player) {
            val id = args.getOne<String>(Text.of("id")).get()

            try {
                val area = AreaService.createArea(src, id)
                src.sendMessage(Text.builder("A area with id ${area.id} has been created.").color(TextColors.LIGHT_PURPLE).build())
                CommandResult.success()
            } catch (e: IncompleteRegionException) {
                src.sendMessage(Text.builder("please specify the cuboid area using worldedit.").color(TextColors.LIGHT_PURPLE).build())
                CommandResult.empty()
            }
        } else {
            src.sendMessage(Text.builder("this command can use only player.").color(TextColors.RED).build())
            CommandResult.empty()
        }
    }

}