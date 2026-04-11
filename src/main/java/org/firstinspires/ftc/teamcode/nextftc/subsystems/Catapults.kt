package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.functions.decider
import org.firstinspires.ftc.teamcode.nextftc.functions.sequential

object Catapults: Subsystem {
    val catapults = MotorGroup(
        MotorEx("right").brakeMode(), MotorEx("left").brakeMode().reversed()
    )

    var isUp = false

    val up = instant("up") { catapults.power = 1.0 }
    val down = instant("down") { catapults.power = -1.0 }
    val idle = instant("idle") { catapults.power = 0.0 }
    val slow = instant("slow") { catapults.power = 0.35 }

    val volt = sequential(up, wait(0.25), idle).requires(this)
    val shoot = sequential(up, wait(0.1), down).requires(this)
    val stabilize = sequential(slow, wait(0.000009), down, wait(0.1))
    val toggle = sequential(instant { isUp = !isUp }, decider({ isUp }, down, volt))
}