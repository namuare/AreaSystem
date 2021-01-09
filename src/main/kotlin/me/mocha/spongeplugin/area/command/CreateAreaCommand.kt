package me.mocha.spongeplugin.area.command

import com.sk89q.worldedit.IncompleteRegionException
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.sponge.SpongeWorldEdit
import me.mocha.spongeplugin.area.provider.ConfigAreaProvider
import me.mocha.spongeplugin.area.util.Vector3
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
        if (src is Player) {
            val id = args.getOne<String>(Text.of("id")).get()

            try {
                val session = SpongeWorldEdit.inst().getSession(src)

                val world = session.selectionWorld
                val sel = session.getSelection(world)

                if (sel is CuboidRegion) {
                    val min = sel.minimumPoint
                    val max = sel.maximumPoint
                    val start = Vector3(min.blockX, min.blockY, min.blockZ)
                    val end = Vector3(max.blockX, max.blockY, max.blockZ)

                    val area = ConfigAreaProvider.createArea(id, world.name, start, end)

                    src.sendMessage(Text.builder("A area with id ${area.id} has been created.").color(TextColors.LIGHT_PURPLE).build())
                    return CommandResult.success()
                } else {
                    src.sendMessage(Text.builder("Only cuboid selection is valid.").color(TextColors.LIGHT_PURPLE).build())
                    return CommandResult.empty()
                }
            } catch (e: IncompleteRegionException) {
                src.sendMessage(Text.builder("please specify the cuboid range using worldedit.").color(TextColors.LIGHT_PURPLE).build())
                return CommandResult.empty()
            }
        } else {
            src.sendMessage(Text.builder("this command can use only player.").color(TextColors.RED).build())
            return CommandResult.empty()
        }
    }

}