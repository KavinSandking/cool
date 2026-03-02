package org.firstinspires.ftc.teamcode;

import static java.lang.Math.hypot;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;

import dev.nextftc.control2.feedback.PIDController;
import dev.nextftc.control2.feedforward.SimpleFeedforward;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.hardware.impl.MotorEx;

@Config
public class Shooter implements Subsystem {
    public static Shooter INSTANCE = new Shooter();

    private Shooter() {
    }

    private final MotorEx shooter = new MotorEx("flywheel");
    public static double kp, ki, kd;
    public static double kv, ks, ka;
    public static double target = 0;
    private final PIDController pid = new PIDController(kp, ki, kd);
    private final SimpleFeedforward feedforward = new SimpleFeedforward(kv, ks, ka);

    public Command off = instant("shooter off", () -> target = 0);

    public double setTarget(double d){
        return MathFunctions.clamp(212.1298 * Math.pow(d, 2) +  32.23223 * d + 712.398, -4.0, 4.0);
    }

    @Override
    public void initialize(){
        off.schedule();
    }

    @Override
    public void periodic(){
        double power = pid.calculate(target - shooter.getVelocity()) + feedforward.calculate(target);
        Pose pose = PedroComponent.follower().getPose();
        Pose goal = new Pose(144.0, 144.0);

        double dx = goal.getX() - pose.getX();
        double dy = goal.getY() - pose.getY();
        double distance = hypot(dx, dy);

        target = setTarget(distance);
        shooter.setPower(power);
    }


}
