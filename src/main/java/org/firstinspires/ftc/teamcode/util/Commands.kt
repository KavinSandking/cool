package org.firstinspires.ftc.teamcode.util

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.delays.WaitUntil
import dev.nextftc.extensions.pedro.FollowPath


fun followPath(pathChain: PathChain, holdEnd: Boolean? = true, maxPower: Double? = 1.0) = FollowPath(pathChain, holdEnd, maxPower ?: 1.0)
fun until(boolean: () -> Boolean) = WaitUntil { boolean() }