package org.firstinspires.ftc.teamcode.nextftc.subsystems

import com.bylazar.configurables.annotations.Configurable
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.util.functions.SlidesController

@Configurable
object Slides: Subsystem {
    val slides = MotorGroup(
        MotorEx("leftSlide").zeroed(), MotorEx("rightSlide").zeroed().reversed()
    )

    val controller = SlidesController(20.0, 40.0, slides)

    val down = instant("down") { controller.target = 0.0 }
    val up = instant("up") { controller.target = 4000.0 }
    val middle = instant("middle") { controller.target = 2000.0 }

    override fun periodic() {
        controller.calculate(slides.currentPosition, slides.velocity)
    }

    override fun initialize() {
        down()
    }
}