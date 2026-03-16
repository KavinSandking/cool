package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands.instant
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import dev.nextftc.ftc.ActiveOpMode

object Intake {
    private val intake: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "intake") }

    init {
        intake.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        intake.direction = DcMotorSimple.Direction.REVERSE
    }

    fun manual(): Command = instant { intake.power = -ActiveOpMode.gamepad2.right_stick_y.toDouble() }.requiring(this)
    fun auto(): Command = instant { intake.power = 1.0 }.requiring(this)
    fun off(): Command = instant { intake.power = 0.0 }.requiring(this)
    fun reverse(): Command = instant { intake.power = -1.0 }.requiring(this)

}