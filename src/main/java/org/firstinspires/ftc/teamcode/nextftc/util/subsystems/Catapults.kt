package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.util.functions.repeat

object Catapults: Subsystem {
    val catapults = MotorGroup(
        MotorEx("right").brakeMode(), MotorEx("left").brakeMode().reversed()
    )

    val up = instant { catapults.power = 1.0 }
    val down = instant { catapults.power = -1.0 }
    val stop = instant { catapults.power = 0.0 }
    val slow = instant { catapults.power = 0.35 }
    val volt = up.then(wait(0.25), stop)
    val stabilize = repeat(slow.then(wait(0.000009), down, wait(0.1)), 2)
}