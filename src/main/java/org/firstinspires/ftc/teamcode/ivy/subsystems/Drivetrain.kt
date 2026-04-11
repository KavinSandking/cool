package org.firstinspires.ftc.teamcode.ivy.subsystems

import com.pedropathing.follower.Follower
import com.pedropathing.geometry.Pose
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.CommandBuilder
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.pedro.PedroCommands.follow
import com.pedropathing.ivy.pedro.PedroCommands.hold
import com.pedropathing.ivy.pedro.PedroCommands.turnTo
import com.pedropathing.paths.PathChain
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.ActiveOpMode.gamepad1
import org.firstinspires.ftc.teamcode.pedroPathing.Constants
import org.firstinspires.ftc.teamcode.util.utilityPoses.Poses

class Drivetrain {
    val leftFront: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "leftFront")
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
    }

    val leftBack: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "leftBack")
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
    }

    val rightFront: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "rightFront")
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
        .apply { direction = DcMotorSimple.Direction.REVERSE }
    }

    val rightBack: DcMotorEx by lazy { ActiveOpMode.hardwareMap.get(DcMotorEx::class.java, "rightBack")
        .apply { zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE }
        .apply { direction = DcMotorSimple.Direction.REVERSE }
    }

    val follower: Follower by lazy { Constants.createFollower(ActiveOpMode.hardwareMap)
        .apply { setStartingPose(Poses.startPose) }
    }

    val drive: Command = CommandBuilder()
        .setStart { follower.startTeleopDrive() }
        .setExecute {
            follower.setTeleOpDrive(
                -gamepad1.left_stick_y.toDouble(),
                -gamepad1.left_stick_x.toDouble(),
                -gamepad1.right_stick_x.toDouble()
            )
        }
        .setEnd { follower.breakFollowing() }
        .requiring(this)

    val aim: Command = turnTo(Poses.goalPose.heading)

    fun follow(pathChain: PathChain): Command = follow(follower, pathChain)
    fun hold(pose: Pose): Command = hold(follower, pose)
    fun turnTo(radians: Double): Command = turnTo(follower, radians)
    val relocalize: Command = instant { follower.pose = Poses.relocalizePose }
}