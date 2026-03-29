package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import com.pedropathing.control.PIDFController
import com.pedropathing.geometry.Pose
import com.pedropathing.math.MathFunctions
import dev.nextftc.core.commands.Command
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.extensions.pedro.PedroComponent.Companion.follower
import dev.nextftc.extensions.pedro.PedroDriverControlled
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.nextftc.util.enums.*
import kotlin.math.pow
import kotlin.math.sign


object Drivetrain: Subsystem {
    val targetHeading: Double = Math.toRadians(Alliance.goal.heading)
    val controller: PIDFController = PIDFController(follower.constants.coefficientsHeadingPIDF)
    var headingLock: Boolean = true

    val resetPose: Pose = if (Alliance.current == Alliance.BLUE) Pose() else Pose().mirror()

    val drive = PedroDriverControlled(
        Gamepads.gamepad1.leftStickY.map { x -> x.pow(2) * sign(x) },
        Gamepads.gamepad1.leftStickX.map { x -> x.pow(2) * sign(x) },
        {
            if (headingLock) {
                controller.coefficients = follower.constants.coefficientsHeadingPIDF
                controller.updateError(headingError())
                controller.run()
            } else {
                Gamepads.gamepad1.rightStickX.map { x -> x.pow(2) * sign(x) }.get()
            }
        }
    )

    val localize = instant { follower.pose = resetPose }

    fun headingError(): Double {
        if (follower.currentPath == null){
            return 0.0
        }
        val error = MathFunctions.getTurnDirection(follower.heading, targetHeading) * MathFunctions.getSmallestAngleDifference(follower.heading, targetHeading)
        return error
    }

    override fun initialize() {
        if (OpModeType.current == OpModeType.AUTONOMOUS) {
            follower.setStartingPose(Alliance.startAuto)
        } else {
            follower.setStartingPose(Alliance.startTele ?: Pose())
        }
    }

    override val defaultCommand: Command
        get() = drive
}