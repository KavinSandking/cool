package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.groups.Groups.sequential
import com.pedropathing.ivy.groups.Groups.repeat
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx

object Catapults: Subsystem {
    private val right = MotorEx("right").brakeMode()
    private val left = MotorEx("left").brakeMode().reversed()
    private val catapults = MotorGroup(right, left)

    val up: Command = Commands.instant { catapults.power = 1.0 }.requiring(this)
    val down: Command = Commands.instant { catapults.power = -1.0 }.requiring(this)
    val slow: Command = Commands.instant { catapults.power = 0.35 }.requiring(this)
    val stabilize: Command = repeat(sequential(slow, waitMs(0.0009), down), 2).requiring(this)
}