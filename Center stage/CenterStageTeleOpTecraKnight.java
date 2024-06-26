package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controller", group="Linear Opmode")
public class CenterStageTeleOpTecraKnight extends LinearOpMode {
 // Declare OpMode members.
 private ElapsedTime runtime = new ElapsedTime();
 private DcMotor motorKanan = null;
 private DcMotor motorKiri = null;
 private DcMotor lifter = null;
 private DcMotor armRotation = null;
 private DcMotor armLength = null;
 
 //private DcMotor sliderBelakang = null;
 //private DcMotor sliderDepan = null;

 double speed = 0;
 @Override
 public void runOpMode() {
 telemetry.addData("Status", "Initialized");
 telemetry.update();
 // Initialize the hardware variables. Note that the strings used here as parameters
 // to 'get' must correspond to the names assigned during the robot configuration
 // step (using the FTC Robot Controller app on the phone).
 motorKanan = hardwareMap.get(DcMotor.class, "kanan");
 motorKiri = hardwareMap.get(DcMotor.class, "kiri");
 lifter = hardwareMap.get(DcMotor.class, "lifter");
 armRotation = hardwareMap.get(DcMotor.class, "armRot");
 armLength = hardwareMap.get(DcMotor.class, "armLength");

 //sliderBelakang = hardwareMap.get(DcMotor.class, "sliderBelakang");
 //sliderDepan = hardwareMap.get(DcMotor.class, "sliderDepan");

 // Most robots need the motor on one side to be reversed to drive forward
 // Reverse the motor that runs backwards when connected directly to the battery
 motorKanan.setDirection(DcMotor.Direction.FORWARD);
 motorKiri.setDirection(DcMotor.Direction.REVERSE);
 lifter.setDirection(DcMotor.Direction.FORWARD);
 armRotation.setDirection(DcMotor.Direction.FORWARD);
 armLength.setDirection(DcMotor.Direction.FORWARD);
 
 
 
 //sliderBelakang.setDirection(DcMotor.Direction.FORWARD);
 //sliderDepan.setDirection(DcMotor.Direction.REVERSE);

 // Wait for the game to start (driver presses PLAY)
 waitForStart();
 runtime.reset();
 // run until the end of the match (driver presses STOP)
 while (opModeIsActive()) {
 // Setup a variable for each drive wheel to save power level for telemetry
 
 //DRIVETRAIN
 double kananPower;
 double kiriPower;
 
 // Choose to drive using either Tank Mode, or POV Mode
 // Comment out the method that's not used. The default below is POV.
 // POV Mode uses left stick to go forward, and right stick to turn.
 // - This uses basic math to combine motions and is easier to drive straight.
 if (gamepad1.right_bumper){
  speed = 1;
 } else{
  speed = 0.5;
 }
 
 if (gamepad1.y==true) {
  lifter.setPower(-1);

 }else if (gamepad1.a==true) {
  lifter.setPower(1);
  
 }else {
  lifter.setPower(0);
  
 }
 
 if (gamepad2.b==true){
  armRotation.setPower(-0.7);
  
 }else if (gamepad2.x==true){
  armRotation.setPower(0.7);
  
 }else {
  armRotation.setPower(0);
 }
 
if (gamepad2.y==true){
  armLength.setPower(1);
  
 }else if (gamepad2.a==true){
  armLength.setPower(-1);
  
 }else {
  armLength.setPower(0);
 }
 
 
 double drive = gamepad1.left_stick_y * speed;
 double turn = gamepad1.left_stick_x * speed;
 //double slide_y = gamepad1.right_stick_x;
 
 kananPower = Range.clip(drive - turn, -1.0, 1.0) ;
 kiriPower = Range.clip(drive + turn, -1.0, 1.0) ;
 
 //sliderBelakangPower = Range.clip((slide_y - turn), -1.0, 1.0);
 //sliderDepanPower = Range.clip((slide_y + turn), -1.0, 1.0);

 
 motorKanan.setPower(kananPower);
 motorKiri.setPower(kiriPower);
 //sliderBelakang.setPower(sliderBelakangPower);
 //sliderDepan.setPower(sliderDepanPower);

 
 
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
 
 // Show the elapsed game time and wheel power
 telemetry.addData("Status", "Run Time: " + runtime.toString());
 telemetry.addData("Motors", "kanan (%.2f)", kananPower, kiriPower);
 telemetry.update();
 }
 }
}
