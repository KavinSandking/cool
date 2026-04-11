package org.firstinspires.ftc.teamcode.ivy.opModes

import org.firstinspires.ftc.teamcode.ivy.wrapperClasses.TeleOpMode
import com.pedropathing.ivy.groups.Groups.repeat
import kotlin.math.abs


class Teleop: TeleOpMode() {
    override fun loop() {
        if (abs(gamepad2.right_stick_y) < 0.01) robot.intake.tele.schedule()
        else robot.intake.off.schedule()

        if (gamepad2.right_bumper) robot.catapults.toggle.schedule()
        if (gamepad2.left_bumper) robot.catapults.stabilize.schedule()
    }
}