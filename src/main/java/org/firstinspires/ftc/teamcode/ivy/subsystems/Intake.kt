package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.impl.MotorEx

object Intake: Subsystem {
    private val intake = MotorEx("intake").brakeMode().reversed()

    val manual: Command = Commands.instant { intake.power = -ActiveOpMode.gamepad2.right_stick_y.toDouble() }.requiring(this)
    val auto: Command = Commands.instant { intake.power = 1.0 }.requiring(this)
    val off: Command = Commands.instant { intake.power = 0.0 }.requiring(this)
    val reverse: Command = Commands.instant { intake.power = -1.0 }.requiring(this)
}