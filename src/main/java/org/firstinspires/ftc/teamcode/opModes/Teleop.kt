package org.firstinspires.ftc.teamcode.opModes

import dev.nextftc.bindings.BindingManager
import dev.nextftc.bindings.button
import dev.nextftc.bindings.range
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.util.Alliance
import org.firstinspires.ftc.teamcode.util.subsystems.Catapults
import org.firstinspires.ftc.teamcode.util.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.util.subsystems.Intake
import org.firstinspires.ftc.teamcode.util.subsystems.Robot

class Teleop: NextFTCOpMode() {
    init {
        addComponents(
            SubsystemComponent(Drivetrain, Intake, Catapults),
            BulkReadComponent,
            BindingsComponent
        )
    }

    override fun onInit() {
        button { gamepad1.a } whenBecomesTrue { Robot.alliance = Alliance.BLUE }
        button { gamepad1.y } whenBecomesTrue { Robot.alliance = Alliance.RED }
        BindingManager.reset()
    }

    override fun onStartButtonPressed() {
        button { gamepad1.a } whenTrue { Robot.drivetrain.autoAim }
        range { gamepad2.right_stick_y }.inRange(-0.1, 0.1) whenFalse { Robot.intake.custom }
        button { gamepad2.right_bumper } whenBecomesTrue Robot.catapults.upTeleop whenBecomesFalse Robot.catapults.down
        button { gamepad2.left_bumper } whenBecomesTrue Robot.catapults.stabilize
    }
}