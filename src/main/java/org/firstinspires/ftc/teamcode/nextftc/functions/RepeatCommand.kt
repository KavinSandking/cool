package org.firstinspires.ftc.teamcode.nextftc.functions

import dev.nextftc.core.commands.Command

class RepeatCommand(
    private val command: Command,
    private val timesToRun: Int
) : Command() {

    private var count = 0
    private var wasRunning = false

    override val isDone get() = count >= timesToRun

    override fun start() {
        count = 0
        wasRunning = false
    }

    override fun update() {
        if (!command.isScheduled && !wasRunning) {
            command.schedule()
            wasRunning = true
        } else if (wasRunning && !command.isScheduled) {
            count++
            wasRunning = false
        }
    }

    override fun stop(interrupted: Boolean) {
        if (command.isScheduled) command.cancel()
    }
}