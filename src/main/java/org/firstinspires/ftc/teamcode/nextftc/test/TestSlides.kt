package org.firstinspires.ftc.teamcode.nextftc.test

import dev.nextftc.bindings.button
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Slides

class TestSlides: NextFTCOpMode() {
    val slides = Slides

    init {
        addComponents(
            SubsystemComponent(slides),
            BindingsComponent,
            BulkReadComponent
        )
    }

    override fun onStartButtonPressed() {
        button { gamepad2.right_bumper }.toggleOnBecomesTrue() whenBecomesTrue slides.up whenBecomesFalse slides.down
        button { gamepad2.left_bumper } whenBecomesTrue slides.middle
        button { gamepad2.a } whenBecomesTrue slides.increment
    }
}