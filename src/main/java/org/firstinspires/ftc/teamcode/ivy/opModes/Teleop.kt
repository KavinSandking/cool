package org.firstinspires.ftc.teamcode.ivy.opModes

import org.firstinspires.ftc.teamcode.ivy.wrapperClasses.TeleOpMode
import kotlin.math.abs


class Teleop: TeleOpMode() {
    override fun loop() {
        if (abs(gamepad2.right_stick_y) < 0.01) robot.intake.tele.schedule()
        else robot.intake.off.schedule()

        if (gamepad2.right_bumper) robot.catapults.toggle.schedule()
        if (gamepad2.left_bumper) robot.catapults.stabilize.schedule()

        if (gamepad1.right_bumper) robot.drivetrain.aim.schedule()
        if (gamepad1.left_bumper) robot.drivetrain.relocalize.schedule()
    }
}