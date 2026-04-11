package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands.conditional
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.groups.Groups.*
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import dev.nextftc.ftc.ActiveOpMode

class Catapults {
    val right: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "right")
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
    }

    val left: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "left")
        .apply { direction = DcMotorSimple.Direction.REVERSE }
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
    }

    var isUp = false

    val up: Command = parallel(
        instant { right.power = 1.0 },
        instant { left.power = 1.0 }
    ).requiring(this)

    val down: Command = parallel(
        instant { right.power = -1.0 },
        instant { left.power = -1.0 }
    ).requiring(this)

    val idle: Command = parallel(
        instant { right.power = 0.0 },
        instant { left.power = 0.0 }
    ).requiring(this)

    val slow: Command = parallel(
        instant { right.power = 0.35 },
        instant { left.power = 0.35 }
    ).requiring(this)

    val volt: Command = sequential(up, waitMs(100.0), idle)
    val shoot: Command = sequential(up, waitMs(100.0), down)
    val stabilize: Command = sequential(slow, waitMs(0.001), down)
    val toggle: Command = sequential(
        instant { isUp = !isUp },
        conditional({ isUp }, down, volt)
    )

}