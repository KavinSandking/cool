package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.control.PIDFController
import com.pedropathing.follower.Follower
import com.pedropathing.geometry.Pose
import com.pedropathing.ivy.Command
import com.pedropathing.math.MathFunctions
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import dev.nextftc.ftc.ActiveOpMode
import org.firstinspires.ftc.teamcode.nextftc.Alliance
import org.firstinspires.ftc.teamcode.pedropathing.Constants


object Drivetrain {

    val fl: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "leftFront") }
    val bl: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "leftBack") }
    val fr: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "rightFront") }
    val br: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "rightBack") }


    val startPose: Pose
        get() = if (Alliance.current == Alliance.BLUE) Alliance.BLUE_START_POSE.pose
        else Alliance.RED_START_POSE.pose

    val headingGoal: Double
        get() = if(Alliance.current == Alliance.BLUE) Alliance.BLUE_GOAL.pose.heading
        else Alliance.RED_GOAL.pose.heading

    val follower: Follower by lazy {
        Constants.createFollower(ActiveOpMode.hardwareMap).apply {
            setStartingPose(startPose)
        }
    }

    init {
        listOf(fl, bl, fr, br).forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
    }

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
}