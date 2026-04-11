package org.firstinspires.ftc.teamcode.nextftc.functions

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.conditionals.IfElseCommand
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.ParallelRaceGroup
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.utility.NullCommand
import dev.nextftc.extensions.pedro.FollowPath

fun repeat(n: Int, command: () -> Command) = SequentialGroup(*Array(n) { command() })fun follow(pathChain: PathChain, holdEnd: Boolean = true, maxPower: Double = 1.0): Command = FollowPath(pathChain, holdEnd, maxPower)
fun sequential(vararg commands: Command) = SequentialGroup(*commands)
fun parallel(vararg commands: Command) = ParallelGroup(*commands)
fun race(vararg commands: Command) = ParallelRaceGroup(*commands)
fun deadline(deadline: Command, vararg commands: Command) = ParallelDeadlineGroup(deadline, *commands)

fun decider(condition: () -> Boolean, trueCommand: Command, falseCommand: Command = NullCommand()) = IfElseCommand(condition, trueCommand, falseCommand)
