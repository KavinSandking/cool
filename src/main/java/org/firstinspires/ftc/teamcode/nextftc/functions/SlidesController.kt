package org.firstinspires.ftc.teamcode.nextftc.functions

import dev.nextftc.control2.feedback.PIDController
import dev.nextftc.control2.feedforward.ElevatorFeedforward
import dev.nextftc.control2.model.MotionState
import dev.nextftc.control2.profiles.TrapezoidProfile
import dev.nextftc.control2.profiles.TrapezoidProfileConstraints
import dev.nextftc.hardware.controllable.Controllable
import dev.nextftc.units.Inches

class SlidesController(
    maxVel: Double,
    maxAccel: Double,
    private val controllable: Controllable
) {

    val profileConstraints = TrapezoidProfileConstraints.linear(maxVel, maxAccel)
    val trapezoidProfile = TrapezoidProfile(profileConstraints)

    val feedback = PIDController(0.0, 0.0, 0.0)
    val feedforward = ElevatorFeedforward(0.0, 0.0, 0.0, 0.0)

    var target = 0.0
        set(value) {
            if (field != value) {
                resetProfile()
                field = value
            }
        }

    fun calculate(currentPos: Double, currentVel: Double){
        val currentState = MotionState(Inches, currentPos, currentVel, 0.0)
        val goalState = MotionState(Inches, target, 0.0, 0.0)
        val desired = trapezoidProfile.calculate(current = currentState, goal = goalState)

        val pid = feedback.calculate(reference = desired.position.magnitude, measured = currentPos)
        val ff = feedforward.calculate(desired.velocity.magnitude, desired.acceleration.magnitude)

        val power = (pid + ff).coerceIn(-1.0..1.0)
        controllable.power = power
    }

    fun resetProfile(){
        trapezoidProfile.reset()
    }
}