package org.firstinspires.ftc.teamcode.util.pedropathing

import com.pedropathing.follower.Follower
import com.pedropathing.geometry.BezierCurve
import com.pedropathing.geometry.BezierLine
import com.pedropathing.geometry.Pose
import com.pedropathing.paths.HeadingInterpolator
import com.pedropathing.paths.PathChain
import org.firstinspires.ftc.teamcode.util.Alliance
import org.firstinspires.ftc.teamcode.util.subsystems.Robot

class Trajectories {
    val startPose = Pose(23.5, 125.0, Math.toRadians(144.0))
    val scorePose = Pose(36.0, 114.0, Math.toRadians(144.0))
    val line2StartPose = Pose(42.0, 59.0, Math.toRadians(180.0))
    val line2EndPose = Pose(24.0, 59.0, Math.toRadians(180.0))
    val openGatePose = Pose(24.0, 63.0, Math.toRadians(180.0))
    val gateIntakePose = Pose(13.0, 54.0, Math.toRadians(140.0))
    val line1StartPose = Pose(45.0, 84.0, Math.toRadians(180.0))
    val line1EndPose = Pose(24.0, 84.0, Math.toRadians(180.0))
    val line3StartPose = Pose(45.0, 36.0, Math.toRadians(180.0))
    val line3EndPose = Pose(23.0, 36.0, Math.toRadians(180.0))
    val leavePose = Pose(43.0, 123.0, Math.toRadians(170.0))

    private val cLine2 = Pose(77.0, 56.0)
    private val cLine1 = Pose(62.0, 81.0)
    private val cLine3 = Pose(68.0, 30.0)
    private val cScore2 = Pose(70.0, 67.0)
    private val cScore3 = Pose(77.0, 66.0)
    private val cScore4 = Pose(50.0, 92.0)
    private val cOpenGate = Pose(70.0, 61.0)
    private val cGateIntake = Pose(26.0, 57.0)

    lateinit var score1: PathChain
    lateinit var line2: PathChain
    lateinit var score2: PathChain
    lateinit var openGate: PathChain
    lateinit var score3: PathChain
    lateinit var line1: PathChain
    lateinit var score4: PathChain
    lateinit var line3: PathChain
    lateinit var leave: PathChain


    fun paths(follower: Follower) {
        follower.setStartingPose(startPose)

        if (Robot.alliance == Alliance.BLUE) {
            score1 = follower.pathBuilder()
                .addPath(BezierLine(startPose, scorePose))
                .setConstantHeadingInterpolation(scorePose.heading)
                .build()

            line2 = follower.pathBuilder()
                .addPath(BezierCurve(scorePose, cLine2, line2StartPose))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        scorePose.heading,
                        line3StartPose.heading
                    )
                )
                .addPath(BezierLine(line2StartPose, line2EndPose))
                .setConstantHeadingInterpolation(line2EndPose.heading)
                .build()

            score2 = follower.pathBuilder()
                .addPath(BezierCurve(line2EndPose, cScore2, scorePose))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        line2EndPose.heading,
                        scorePose.heading
                    )
                )
                .build()

            openGate = follower.pathBuilder()
                .addPath(BezierCurve(scorePose, cOpenGate, openGatePose))
                .setLinearHeadingInterpolation(scorePose.heading, openGatePose.heading)
                .addPath(BezierCurve(openGatePose, cGateIntake, gateIntakePose))
                .setLinearHeadingInterpolation(openGatePose.heading, gateIntakePose.heading)
                .build()

            score3 = follower.pathBuilder()
                .addPath(BezierCurve(gateIntakePose, cScore3, scorePose))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        line3EndPose.heading,
                        scorePose.heading
                    )
                )
                .build()

            line1 = follower.pathBuilder()
                .addPath(BezierCurve(scorePose, cLine1, line1StartPose))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        scorePose.heading,
                        line1StartPose.heading
                    )
                )
                .addPath(BezierLine(line1StartPose, line1EndPose))
                .setConstantHeadingInterpolation(line1EndPose.heading)
                .build()

            score4 = follower.pathBuilder()
                .addPath(BezierCurve(line1EndPose, cScore4, scorePose))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        line1EndPose.heading,
                        scorePose.heading
                    )
                )
                .build()

            line3 = follower.pathBuilder()
                .addPath(BezierCurve(scorePose, cLine3, line3StartPose))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        scorePose.heading,
                        line3StartPose.heading
                    )
                )
                .addPath(BezierLine(line3StartPose, line3EndPose))
                .setConstantHeadingInterpolation(line3EndPose.heading)
                .build()

            leave = follower.pathBuilder()
                .addPath(BezierLine(scorePose, leavePose))
                .setConstantHeadingInterpolation(leavePose.heading)
                .build()
        } else {
            follower.setStartingPose(startPose.mirror())
            score1 = follower.pathBuilder()
                .addPath(BezierLine(startPose.mirror(), scorePose.mirror()))
                .setConstantHeadingInterpolation(scorePose.mirror().heading)
                .build()

            line2 = follower.pathBuilder()
                .addPath(BezierCurve(scorePose.mirror(), cLine2.mirror(), line2StartPose.mirror()))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        scorePose.mirror().heading,
                        line3StartPose.mirror().heading
                    )
                )
                .addPath(BezierLine(line2StartPose.mirror(), line2EndPose.mirror()))
                .setConstantHeadingInterpolation(line2EndPose.mirror().heading)
                .build()

            score2 = follower.pathBuilder()
                .addPath(BezierCurve(line2EndPose.mirror(), cScore2.mirror(), scorePose.mirror()))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        line2EndPose.mirror().heading,
                        scorePose.mirror().heading
                    )
                )
                .build()

            openGate = follower.pathBuilder()
                .addPath(BezierCurve(scorePose.mirror(), cOpenGate.mirror(), openGatePose.mirror()))
                .setLinearHeadingInterpolation(scorePose.mirror().heading, openGatePose.mirror().heading)
                .addPath(BezierCurve(openGatePose.mirror(), cGateIntake.mirror(), gateIntakePose.mirror()))
                .setLinearHeadingInterpolation(openGatePose.mirror().heading, gateIntakePose.mirror().heading)
                .build()

            score3 = follower.pathBuilder()
                .addPath(BezierCurve(gateIntakePose.mirror(), cScore3.mirror(), scorePose.mirror()))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        line3EndPose.mirror().heading,
                        scorePose.mirror().heading
                    )
                )
                .build()

            line1 = follower.pathBuilder()
                .addPath(BezierCurve(scorePose.mirror(), cLine1.mirror(), line1StartPose.mirror()))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        scorePose.mirror().heading,
                        line1StartPose.mirror().heading
                    )
                )
                .addPath(BezierLine(line1StartPose.mirror(), line1EndPose.mirror()))
                .setConstantHeadingInterpolation(line1EndPose.mirror().heading)
                .build()

            score4 = follower.pathBuilder()
                .addPath(BezierCurve(line1EndPose.mirror(), cScore4.mirror(), scorePose.mirror()))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        line1EndPose.mirror().heading,
                        scorePose.mirror().heading
                    )
                )
                .build()

            line3 = follower.pathBuilder()
                .addPath(BezierCurve(scorePose.mirror(), cLine3.mirror(), line3StartPose.mirror()))
                .setHeadingInterpolation(
                    HeadingInterpolator.linear(
                        scorePose.mirror().heading,
                        line3StartPose.mirror().heading
                    )
                )
                .addPath(BezierLine(line3StartPose.mirror(), line3EndPose.mirror()))
                .setConstantHeadingInterpolation(line3EndPose.mirror().heading)
                .build()

            leave = follower.pathBuilder()
                .addPath(BezierLine(scorePose.mirror(), leavePose.mirror()))
                .setConstantHeadingInterpolation(leavePose.mirror().heading)
                .build()
        }
    }
}