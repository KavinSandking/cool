package org.firstinspires.ftc.teamcode.nextftc.opModes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.bindings.*
import org.firstinspires.ftc.teamcode.nextftc.util.enums.OpModeType
import org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses.OpMode

@TeleOp(name = "teleop")
class Teleop: OpMode(OpModeType.TELEOP) {
    val localizeButton = button { gamepad1.left_bumper }
    val intakeButton = range { gamepad2.right_stick_y }
    val catapultButton = button { gamepad2.right_bumper }
    val stabilizeButton = button { gamepad2.left_bumper }

    override fun onStartButtonPressed() {
        localizeButton whenBecomesTrue { drivetrain.localize }
        intakeButton inRange(-0.1..0.1) whenTrue { intake.off() }  whenFalse { intake.manual() }
        catapultButton whenBecomesTrue catapults.volt whenBecomesFalse catapults.down
        stabilizeButton whenBecomesTrue catapults.stabilize
    }
}