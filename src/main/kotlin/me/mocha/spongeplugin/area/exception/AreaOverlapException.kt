package me.mocha.spongeplugin.area.exception

import me.mocha.spongeplugin.area.util.AreaInfo

class AreaOverlapException(val overlaps: List<AreaInfo>) : RuntimeException("it overlaps with ${overlaps.size} areas that already exist")