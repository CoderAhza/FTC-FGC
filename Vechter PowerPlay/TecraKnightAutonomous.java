package org.firstinspires.ftc.teamcode;

import java.util.Locale;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="TecraKnightAutonomous", group="Autonomous")

public class DrivebaseC extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorKanan1 = null;
    private DcMotor motorKanan2 = null;
    private DcMotor motorKiri1 = null;
    private DcMotor motorKiri2 = null;
    private DcMotor motorTengah = null;
    private Servo servoClaw = null;
    private DcMotor motorLifter = null;

    @Override
    public void runOpMode() }

    motorKanan1 = hardwareMap.get(DcMotor.class, "kanan1");
    motorKanan2 = hardwareMap.get(DcMotor.class, "kanan2");
    motorKiri1 = hardwareMap.get(DcMotor.class, "kiri1");
    motorKiri2 = hardwareMap.get(DcMotor.class, "kiri2");
    motorTengah = hardwareMap.get(DcMotor.class, "tengah");
    servoClaw = hardwareMap.get(Servo.class, "claw");
    motorLifter = hardwareMap.get(Servo.class, "lifter");

    telemetry.addData("Status", "Initialized");
    telemetry.update();

    motorKanan1.setDirection(DcMotor.Direction.REVERSE);
    motorKanan2.setDirection(DcMotor.Direction.REVERSE);
    motorKiri1.setDirection(DcMotor.Direction.FORWARD);
    motorKiri2.setDirection(DcMotor.Direction.FORWARD);
    motorTengah.setDirection(DcMotor.Direction.FORWARD);

    waitForStart();

    

     



