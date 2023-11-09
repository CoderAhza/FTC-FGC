package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TecrsKnight", group="Linear Opmode")
public class TecrsKnight extends LinearOpMode {
 // Declare OpMode members.
 private ElapsedTime runtime = new ElapsedTime();
 private DcMotor motorKanan1 = null;
 private DcMotor motorKanan2 = null;
 private DcMotor motorKiri1 = null;
 private DcMotor motorKiri2 = null;
 private DcMotor motorTengah = null;
 
 private DcMotor lifterRobot = null;
 
 private Servo servoClaw = null;

 double speed = 0;
 @Override
 public void runOpMode() {
 telemetry.addData("Status", "Initialized");
 telemetry.update();
 // Initialize the hardware variables. Note that the strings used here as parameters
 // to 'get' must correspond to the names assigned during the robot configuration
 // step (using the FTC Robot Controller app on the phone).
 motorKanan1 = hardwareMap.get(DcMotor.class, "kanan1");
 motorKanan2 = hardwareMap.get(DcMotor.class, "kanan2");
 motorKiri1 = hardwareMap.get(DcMotor.class, "kiri1");
 motorKiri2 = hardwareMap.get(DcMotor.class, "kiri2");
 motorTengah = hardwareMap.get(DcMotor.class, "tengah");
 
 lifterRobot = hardwareMap.get(DcMotor.class, "lifter");
 
 servoClaw = hardwareMap.get(Servo.class, "servoclaw");
 
 // Most robots need the motor on one side to be reversed to drive forward
 // Reverse the motor that runs backwards when connected directly to the battery
 motorKanan1.setDirection(DcMotor.Direction.REVERSE);
 motorKanan2.setDirection(DcMotor.Direction.REVERSE);
 motorKiri1.setDirection(DcMotor.Direction.FORWARD);
 motorKiri2.setDirection(DcMotor.Direction.FORWARD);
 motorTengah.setDirection(DcMotor.Direction.FORWARD);
 
 // Wait for the game to start (driver presses PLAY)
 waitForStart();
 runtime.reset();
 // run until the end of the match (driver presses STOP)
 while (opModeIsActive()) {
 // Setup a variable for each drive wheel to save power level for telemetry
 
 //DRIVETRAIN
 double kiriPower1;
 double kiriPower2;
 double kananPower1;
 double kananPower2;
 
 // Choose to drive using either Tank Mode, or POV Mode
 // Comment out the method that's not used. The default below is POV.
 // POV Mode uses left stick to go forward, and right stick to turn.
 // - This uses basic math to combine motions and is easier to drive straight.
 if (gamepad1.right_bumper){
  speed = 1;
 } else{
  speed = 0.5;
 }
 double drive = gamepad1.left_stick_y * speed;
 double turn = gamepad1.left_stick_x * speed;
 double slide = gamepad1.right_stick_x;
 kiriPower1 = Range.clip(drive + turn, -1.0, 1.0) ;
 kiriPower2 = Range.clip(drive + turn, -1.0, 1.0) ;
 kananPower1 = Range.clip(drive - turn, -1.0, 1.0) ;
 kananPower2 = Range.clip(drive - turn, -1.0, 1.0) ;
 
 motorKanan1.setPower(kananPower1);
 motorKanan2.setPower(kananPower2);
 motorKiri1.setPower(kiriPower1);
 motorKiri2.setPower(kiriPower2);
 
 motorTengah.setPower(slide);
 
 
 // if (gamepad1.b== true) {
 // motorTengah.setPower(-1);
 // } else {
 // motorTengah.setPower(0);
 // }
 
 // if (gamepad1.x == true) {
 // motorTengah.setPower(1);
 // } else {
 // motorTengah.setPower(0);
 // }
 
 /*
 if (gamepad1.x == true) {
 motorDD.setPower(1);
 }
 if (gamepad1.b == true) {
 motorDD.setPower(-1);
 } else {
 motorDD.setPower(0);
 }
 
 if (gamepad1.dpad_left == true) {
 motorEE.setPower(1);
 }
 if (gamepad1.dpad_right == true) {
 motorEE.setPower(-1);
 } else {
 motorEE.setPower(0);
 }
 */
 
 // LIFTER
 if (gamepad1.y==true) {
  lifterRobot.setPower(1);

 }else if (gamepad1.a==true) {
  lifterRobot.setPower(-1);
  
 }else {
  lifterRobot.setPower(0);
  
 } 
 
 //CLAW
 if (gamepad1.b== true) {
  servoClaw.setPosition(0.5);
 }
 
 if (gamepad1.x== true){
  servoClaw.setPosition(0);
 }
 
 // Show the elapsed game time and wheel power
 telemetry.addData("Status", "Run Time: " + runtime.toString());
 telemetry.addData("Motors", "kiri1 (%.2f), kiri2 (%.2f), kanan1 (%.2f), kanan2 (%.2f)", kiriPower1, kiriPower2, kananPower1, kananPower2);
 telemetry.update();
 }
 }
}
