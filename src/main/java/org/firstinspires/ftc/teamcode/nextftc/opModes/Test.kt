package org.firstinspires.ftc.teamcode.nextftc.opModes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.bindings.button
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Claw as claw
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Slides as slides

@TeleOp(name = "test slides", group = "test")
class Test: NextFTCOpMode() {

    init {
        addComponents(
            SubsystemComponent(slides, claw),
            BindingsComponent,
            BulkReadComponent
        )
    }

    override fun onStartButtonPressed() {
        button { gamepad2.right_bumper }.toggleOnBecomesTrue() whenBecomesTrue slides.up whenBecomesFalse slides.down
        button { gamepad2.left_bumper } whenBecomesTrue slides.middle
        button { gamepad2.a }.toggleOnBecomesTrue() whenBecomesTrue claw.toggle
    }
}