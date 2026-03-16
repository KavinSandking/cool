package org.firstinspires.ftc.teamcode.nextftc.opModes

import dev.nextftc.bindings.BindingManager
import dev.nextftc.bindings.button
import dev.nextftc.bindings.range
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.Alliance
import org.firstinspires.ftc.teamcode.pedropathing.Constants
import org.firstinspires.ftc.teamcode.nextftc.subsystems.*

class Teleop: NextFTCOpMode() {
    init {
        addComponents(
            SubsystemComponent(Drivetrain, Intake, Catapults, Robot),
            PedroComponent(Constants::createFollower),
            BulkReadComponent,
            BindingsComponent
        )
    }

    override fun onInit() {
        button { gamepad1.a } whenBecomesTrue { Robot.alliance = Alliance.BLUE_GOAL }
        button { gamepad1.y } whenBecomesTrue { Robot.alliance = Alliance.RED_GOAL }

        if (Robot.alliance == Alliance.BLUE_GOAL) PedroComponent.follower.setStartingPose(PoseStorage.endPoseBlue)
        else PedroComponent.follower.setStartingPose(PoseStorage.endPoseRed)

        BindingManager.reset()
    }

    override fun onStartButtonPressed() {
        button { gamepad1.a } whenTrue Drivetrain.autoAim
        range { gamepad2.right_stick_y }.inRange(-0.1, 0.1) whenFalse { Intake.custom }
        button { gamepad2.right_bumper } whenBecomesTrue Catapults.upTeleop whenBecomesFalse Catapults.down
        button { gamepad2.left_bumper } whenBecomesTrue Catapults.stabilize
    }
}