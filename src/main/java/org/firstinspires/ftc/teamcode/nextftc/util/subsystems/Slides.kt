package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import com.bylazar.configurables.annotations.Configurable
import dev.nextftc.control2.feedback.PIDCoefficients
import dev.nextftc.control2.feedback.PIDController
import dev.nextftc.control2.feedforward.ElevatorFeedforward
import dev.nextftc.control2.feedforward.GravityFeedforwardParameters
import dev.nextftc.control2.model.MotionState
import dev.nextftc.control2.profiles.TrapezoidProfile
import dev.nextftc.control2.profiles.TrapezoidProfileConstraints
import dev.nextftc.core.commands.utility.AdvancingCommand
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx
import dev.nextftc.units.Inches

@Configurable
object Slides: Subsystem {
    val slides = MotorGroup(
        MotorEx("leftSlide").zeroed(), MotorEx("rightSlide").zeroed().reversed()
    )

    @JvmField var target = 0.0
    @JvmField val pidCoefficients = PIDCoefficients(0.0, 0.0, 0.0)
    @JvmField val ffCoefficients = GravityFeedforwardParameters(0.0, 0.0)

    var useManual = false

    val feedback = PIDController(pidCoefficients)
    val feedforward = ElevatorFeedforward(ffCoefficients)
    val profileConstraints = TrapezoidProfileConstraints.linear(20.0, 40.0)
    val trapezoidProfile = TrapezoidProfile(profileConstraints)

    val down = instant("down") { set(0) }
    val up = instant("up") { set(4000) }
    val middle = instant("middle") { set(2000) }
    val test = AdvancingCommand(down, middle, up)
    val manual = instant("manual") { slides.power = -Gamepads.gamepad2.rightStickY.get() }

    override fun periodic(){
        if (useManual) {
            val currentState = MotionState(Inches, slides.currentPosition, slides.velocity, 0.0)
            val goalState = MotionState(Inches, target, 0.0, 0.0)
            val desired = trapezoidProfile.calculate(current = currentState, goal = goalState)

            val pid = feedback.calculate(
                reference = desired.position.magnitude,
                measured = slides.currentPosition
            )
            val ff =
                feedforward.calculate(desired.velocity.magnitude, desired.acceleration.magnitude)

            val power = (pid + ff).coerceIn(-1.0..1.0)
            slides.power = power
        }
    }

    override fun initialize() {
        down()
    }

    fun set(newTarget: Double){
        useManual = true
        if (newTarget != target){
            target = newTarget
            trapezoidProfile.reset()
        }
    }

    fun set(newTarget: Int){
        useManual = true
        if (newTarget.toDouble() != target){
            target = newTarget.toDouble()
            trapezoidProfile.reset()
        }
    }
}