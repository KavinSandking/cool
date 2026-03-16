package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.control.PIDFController
import com.pedropathing.follower.Follower
import com.pedropathing.geometry.Pose
import com.pedropathing.ivy.Command
import com.pedropathing.math.MathFunctions
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.ivy.Alliance
import org.firstinspires.ftc.teamcode.pedropathing.Constants


object Drivetrain: Subsystem {
    val fl = MotorEx("leftFront").brakeMode()
    val bl = MotorEx("leftBack").brakeMode()
    val fr = MotorEx("rightFront").brakeMode().reversed()
    val br = MotorEx("rightBack").brakeMode().reversed()
    lateinit var follower: Follower

    val startPose: Pose = Alliance.start
    val headingGoal: Double = Alliance.goal.heading
    val controller = PIDFController(follower.constants.coefficientsHeadingPIDF)

    val drive: Command = Command.build()
        .setStart { follower.startTeleopDrive(true) }
        .setExecute {
            follower.setTeleOpDrive(
                -ActiveOpMode.gamepad1.left_stick_y.toDouble(),
                -ActiveOpMode.gamepad1.left_stick_x.toDouble(),
                -ActiveOpMode.gamepad1.right_stick_x.toDouble()
        ) }.requiring(this)

    val headingLock: Command = Command.build()
        .setExecute {
            controller.coefficients = follower.constants.coefficientsHeadingPIDF
            controller.updateError(getHeadingError())
            follower.setTeleOpDrive(
                -ActiveOpMode.gamepad1.left_stick_y.toDouble(),
                -ActiveOpMode.gamepad1.left_stick_x.toDouble(),
                controller.run()
            )
        }
        .setEnd { follower.breakFollowing() }

    fun getHeadingError(): Double {
        if (follower.currentPath == null) return 0.0

        val headingError = MathFunctions.getTurnDirection(
            follower.heading,
            headingGoal
        ) * MathFunctions.getSmallestAngleDifference(follower.heading, headingGoal)
        return headingError
    }

    override fun initialize() {
        follower = Constants.createFollower(ActiveOpMode.hardwareMap)
        follower.setStartingPose(startPose)
    }

    override fun periodic(){
        follower.update()
    }
}