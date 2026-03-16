package org.firstinspires.ftc.teamcode.nextftc.util

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.delays.WaitUntil
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.extensions.pedro.FollowPath


fun followPath(pathChain: PathChain, holdEnd: Boolean? = true, maxPower: Double? = 1.0): Command = FollowPath(pathChain, holdEnd, maxPower ?: 1.0)
fun until(boolean: () -> Boolean): Command = WaitUntil { boolean() }
fun sequential(vararg commands: Command): Command = SequentialGroup(commands = commands)
fun parallel(vararg commands: Command): Command = ParallelGroup(commands = commands)