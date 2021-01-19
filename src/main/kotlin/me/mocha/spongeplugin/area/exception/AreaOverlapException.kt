package me.mocha.spongeplugin.area.exception

import me.mocha.spongeplugin.area.model.entity.Area

class AreaOverlapException(val overlaps: List<Area>) : RuntimeException("it overlaps with ${overlaps.size} areas that already exist")