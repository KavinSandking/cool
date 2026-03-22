package org.firstinspires.ftc.teamcode.nextftc.opModes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.bindings.*
import org.firstinspires.ftc.teamcode.nextftc.util.functions.OpMode

@TeleOp(name = "teleop")
class Teleop: OpMode() {
    override fun onStartButtonPressed() {
        intake.overload()

        button { gamepad1.left_bumper } whenBecomesTrue { drivetrain.localize }

        range { gamepad2.right_stick_y } inRange(-0.1..0.1) whenTrue { intake.off() }  whenFalse { intake.manual() }
        button { gamepad2.right_bumper } whenBecomesTrue catapults.volt whenBecomesFalse catapults.down
        button { gamepad2.left_bumper } whenBecomesTrue catapults.stabilize
    }
}