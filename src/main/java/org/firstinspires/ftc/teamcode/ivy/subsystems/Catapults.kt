package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.groups.Groups.parallel
import com.pedropathing.ivy.groups.Groups.sequential
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.pedropathing.ivy.groups.Groups.repeat
import dev.nextftc.ftc.ActiveOpMode

object Catapults {
    private val right: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "right") }
    private val left: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "left") }

    init {
        right.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        left.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        left.direction = DcMotorSimple.Direction.REVERSE
    }

    fun up(): Command = parallel(
        instant { right.power = 1.0 },
        instant { left.power = 1.0 }
    ).requiring(this)

    fun down(): Command = parallel(
        instant { right.power = -1.0 },
        instant { left.power = -1.0 }
    ).requiring(this)

    fun slow(): Command = parallel(
        instant { right.power = 0.35 },
        instant { left.power = 0.35 }
    ).requiring(this)

    fun stabilize(): Command = repeat(sequential(slow(), waitMs(0.0009), down()), 2).requiring(this)


}