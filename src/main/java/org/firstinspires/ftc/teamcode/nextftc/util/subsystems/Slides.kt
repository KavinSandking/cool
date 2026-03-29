package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import com.bylazar.configurables.annotations.Configurable
import dev.nextftc.control2.feedback.PIDCoefficients
import dev.nextftc.control2.feedback.PIDController
import dev.nextftc.control2.feedforward.ElevatorFeedforward
import dev.nextftc.control2.feedforward.GravityFeedforwardParameters
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx

@Configurable
object Slides: Subsystem {
    val left = MotorEx("leftSlide").zeroed()
    val right = MotorEx("rightSlides").zeroed()

    val slides = MotorGroup(left, right)

    @JvmField var target = 0.0
    @JvmField val pidCoefficients = PIDCoefficients(0.0, 0.0, 0.0)
    @JvmField val ffCoefficients = GravityFeedforwardParameters(0.0, 0.0)

    val feedback = PIDController(pidCoefficients)
    val feedforward = ElevatorFeedforward(ffCoefficients)

    val down = instant { target = 0.0 }
    val up = instant { target = 4000.0 }
    val middle = instant { target = 2000.0 }
    val increment = instant { target += 50 }

    override fun periodic(){
        val pid = feedback.calculate(reference = target, measured = slides.currentPosition)
        val ff = feedforward.calculate(0.0, 0.0)

        val power = pid + ff
        slides.power = power
    }

    override fun initialize() {
        down()
    }
}