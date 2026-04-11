package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands.infinite
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.commands.Commands.waitUntil
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.DigitalChannel
import dev.nextftc.ftc.ActiveOpMode

class Intake {
    val intake: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "intake")
        .apply { direction = DcMotorSimple.Direction.REVERSE }
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
    }

    val beam: DigitalChannel by lazy { ActiveOpMode.hardwareMap.get(DigitalChannel::class.java, "breakBeam")
        .apply { mode = DigitalChannel.Mode.INPUT }
    }

    var count = 0
    var lastDetected = false

    val auto: Command = instant { intake.power = 1.0 }.requiring(this)
    val off: Command = instant { intake.power = 0.0 }.requiring(this)
    val reverse: Command = instant { intake.power = -1.0 }.requiring(this)
    val tele: Command = instant { intake.power = -ActiveOpMode.gamepad2.right_stick_y.toDouble() }.requiring(this)

    val reset: Command = instant { count = 0 }
    val over: Command = waitUntil(this::isFull)
        .then(reverse)
        .then(waitMs(250.0))
        .requiring(this)

    val periodic: Command = infinite {
        val detected = beam.state

        if (detected && !lastDetected) {
            count++
        }

        lastDetected = detected

        over.schedule()
    }

    fun isFull(): Boolean {
        return count > 2
    }
}