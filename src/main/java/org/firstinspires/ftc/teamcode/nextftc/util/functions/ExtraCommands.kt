package org.firstinspires.ftc.teamcode.nextftc.util.functions

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.conditionals.IfElseCommand
import dev.nextftc.core.commands.utility.NullCommand
import dev.nextftc.extensions.pedro.FollowPath


fun repeat(cmd: Command, times: Int): Command = RepeatCommand(cmd, times)
fun stateful(condition: () -> Boolean, trueCmd: Command, falseCmd: Command = NullCommand()): Command =
    IfElseCommand(condition, trueCmd, falseCmd)
fun follow(pathChain: PathChain, holdEnd: Boolean = true, maxPower: Double = 1.0): Command = FollowPath(pathChain, holdEnd, maxPower)
