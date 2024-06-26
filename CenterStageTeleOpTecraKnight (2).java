package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint
        ;

@TeleOp(name="CenterStageTeleOpTecraKnight", group="Linear Opmode")
public class CenterStageTeleOpTecraKnight extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorKanan = null;
    private DcMotor motorKiri = null;
    private DcMotor lifter = null;
    private DcMotor armRotation = null;
    private DcMotor armLength = null;
    private Servo planeLauncher = null;
    private DcMotor claw = null;
    private CRServo pull = null;


    double speed = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables.
        motorKanan = hardwareMap.get(DcMotor.class, "kanan");
        motorKiri = hardwareMap.get(DcMotor.class, "kiri");
        lifter = hardwareMap.get(DcMotor.class, "lifter");
        armRotation = hardwareMap.get(DcMotor.class, "armRot");
        armLength = hardwareMap.get(DcMotor.class, "armLength");
        planeLauncher = hardwareMap.get(Servo.class, "planeLauncher");
        claw = hardwareMap.get(DcMotor.class, "claw");
        pull = hardwareMap.get(CRServo.class, "pull");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        motorKanan.setDirection(DcMotor.Direction.REVERSE);
        motorKiri.setDirection(DcMotor.Direction.FORWARD);
        lifter.setDirection(DcMotor.Direction.FORWARD);
        armRotation.setDirection(DcMotor.Direction.FORWARD);
        armLength.setDirection(DcMotor.Direction.FORWARD);

        // Set default position

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double kananPower;
            double kiriPower;

            if (gamepad1.right_bumper== true) {
                speed = 1;
            } else {
                speed = 0.5;
            }

            if (gamepad1.y== true) {
                lifter.setPower(1);
            } else if (gamepad1.a== true) {
                lifter.setPower(-1);
            } else {
                lifter.setPower(0);
            }

            if (gamepad1.x== true) {
                planeLauncher.setPosition(1.0);
            } else if (gamepad1.b== true) {
                planeLauncher.setPosition(0.0);
            }


            // With these lines
            if (gamepad2.left_bumper == true) {
                claw.setPower(1);
            }else if(gamepad2.right_bumper==true){
                claw.setPower(-1);

            }else{
                claw.setPower(0);
            }

            if (gamepad2.b== true) {
                armRotation.setPower(-0.6);
            } else if (gamepad2.x== true) {
                armRotation.setPower(0.6);
            } else {
                armRotation.setPower(0);
            }

            if (gamepad2.y== true) {
                armLength.setPower(1);
            } else if (gamepad2.a== true) {
                armLength.setPower(-1);
            } else {
                armLength.setPower(0);
            }

            if (gamepad2.dpad_down) {
                pull.setPower(1);
            } else if (gamepad2.dpad_up) {
                pull.setPower(-1);
            } else {
                pull.setPower(0);
            }


            double drive = gamepad1.left_stick_y * speed;
            double turn = gamepad1.left_stick_x * speed;

            kananPower = Range.clip(drive - turn, -1.0, 1.0);
            kiriPower = Range.clip(drive + turn, -1.0, 1.0);

            motorKanan.setPower(kananPower);
            motorKiri.setPower(kiriPower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "kanan (%.2f), kiri (%.2f)", kananPower, kiriPower);
            telemetry.update();
        }
    }
}
