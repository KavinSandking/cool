package org.firstinspires.ftc.teamcode.nextftc.util.functions

import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Slides

abstract class TestOpMode(vararg subsystems: Subsystem): NextFTCOpMode() {
    init {
        addComponents(
            SubsystemComponent(subsystems = subsystems),
            BindingsComponent,
            BulkReadComponent
        )
    }
}