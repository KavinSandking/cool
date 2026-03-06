package org.firstinspires.ftc.teamcode.util.subsystems

import com.pedropathing.control.PIDFController
import com.pedropathing.geometry.Pose
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver
import dev.nextftc.core.commands.Command
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.core.units.Angle
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.driving.FieldCentric
import dev.nextftc.hardware.driving.MecanumDriverControlled
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import kotlin.math.atan2

object Drivetrain: Subsystem {
    val fl = MotorEx("leftFront").brakeMode()
    val bl = MotorEx("leftBack").brakeMode()
    val fr = MotorEx("rightFront").reversed().brakeMode()
    val br = MotorEx("rightBack").reversed().brakeMode()

    val pinpoint: GoBildaPinpointDriver by lazy { ActiveOpMode.hardwareMap.get(GoBildaPinpointDriver::class.java, "pinpoint") }
    val heading: Double get() = pinpoint.getHeading(AngleUnit.RADIANS)
    private val controller = PIDFController(PedroComponent.follower.constants.coefficientsHeadingPIDF)

    lateinit var goal: Pose

    enum class DriveStates { ROBOT_CENTRIC, FIELD_CENTRIC, AUTO_AIM }
    var state: DriveStates = DriveStates.ROBOT_CENTRIC

    val robotCentric = instant { state = DriveStates.ROBOT_CENTRIC }
    val fieldCentric = instant { state = DriveStates.FIELD_CENTRIC }
    val autoAim = instant { state = DriveStates.AUTO_AIM }

    override fun periodic() {
        pinpoint.update()
    }

    override val defaultCommand: Command
        get() = when(state){
            DriveStates.ROBOT_CENTRIC -> MecanumDriverControlled(
                fl, fr, bl, br,
                -Gamepads.gamepad1.leftStickY,
                Gamepads.gamepad1.leftStickX,
                Gamepads.gamepad1.rightStickX
            )
            DriveStates.FIELD_CENTRIC -> MecanumDriverControlled(
                fl, fr, bl, br,
                -Gamepads.gamepad1.leftStickY,
                Gamepads.gamepad1.leftStickX,
                Gamepads.gamepad1.rightStickX,
                FieldCentric { Angle.fromRad(heading) }
            )
            DriveStates.AUTO_AIM -> MecanumDriverControlled(
                fl, fr, bl, br,
                -Gamepads.gamepad1.leftStickY,
                Gamepads.gamepad1.leftStickX,
                {
                    val currentPose = PedroComponent.follower.pose
                    val difference = goal.minus(currentPose)
                    val target = atan2(difference.y, difference.x)
                    val currentHeading = currentPose.heading
                    controller.updateError(Angle.wrapAnglePiToPi(target - currentHeading))

                    controller.run().coerceIn(-0.4, 0.4)
                }
            )
        }
}