// PEMROGRAMAN DeteksiDanAutonomous

package org.firstinspires.ftc.teamcode;

import java.util.Locale;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition; */

@Autonomous(name = "Autonomous_PowerPlay", group = "Concept")

public class Autonomous_PowerPlay extends LinearOpMode {
    
     // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorKanan1 = null;
    private DcMotor motorKanan2 = null;
    private DcMotor motorKiri1 = null; 
    private DcMotor motorKiri2 = null; 

    private CRServo servoclaw = null;

    private DcMotor lifterRobot = null;
    
    // Deteksi
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };
    private static final String VUFORIA_KEY =
             "AcAL+xP/////AAABmYKVJ2L9+U4QpQac2O3fYMBrPrMtySA8JMt/ccngTHTiXl63EjXWPhuheYQn6EJA8jwhobSFJZrTMxiQqbJB+GZE77MNN6JWO6JODS9+4NVd7eryXXC7s8qeMn4CaXwH1U/OPrw4IijLsfd9dwP02ifHM8CFka1+EntA7Yd/MGNfwHpvSQayV0OjW0jeqP6Bf3t7d0IB0eVikUnZWK5bVA3mkYrLJ674x60qT8Wc4h33w+Qm0utmgorzCpWhkg/lreVjh6ED4GhrdP20a51XTaqyPpMHXU+hahhUJhYU0SZ82gHqXhX4sW0dYWCrztYYEqeVpBeTEDbehrp8YLI/DyG9P5sU1vp5DQ11P05WO6N0";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        
        motorKanan1 = hardwareMap.get(DcMotor.class,"kanan1");
        motorKanan2 = hardwareMap.get(DcMotor.class,"kanan2");
        motorKiri1 = hardwareMap.get(DcMotor.class,"kiri1");
        motorKiri2 = hardwareMap.get(DcMotor.class,"kiri2");

        servoclaw = hardwareMap.get(CRServo.class,"servoclaw");

        lifterRobot = hardwareMap.get(DcMotor.class,"lifter");
        telemetry.update();
        
        motorKanan1.setDirection(DcMotor.Direction.REVERSE);
        motorKanan2.setDirection(DcMotor.Direction.REVERSE);
        motorKiri1.setDirection(DcMotor.Direction.FORWARD);
        motorKiri2.setDirection(DcMotor.Direction.FORWARD);
    
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        
                //Pergerakan awal sebelum mendeteksi objek
                //motorKanan1.setPower(-0.5);
                //motorKanan2.setPower(-0.5);
                //motorKiri1.setPower(-0.5);
                //motorKiri2.setPower(-0.5);
                //sleep(1000);
                //motorKanan.setPower(0);
                //motorKiri.setPower(0);
                //sleep(1000);
                
                // Posisi awal intake
                servoclaw.setPower(-1);
                sleep(500);
                
        
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can increase the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.0, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Objects Detected", updatedRecognitions.size());

                        // step through the list of recognitions and display image position/size information for each one
                        // Note: "Image number" refers to the randomized image orientation/number
                        for (Recognition recognition : updatedRecognitions) {
                            double col = (recognition.getLeft() + recognition.getRight()) / 2 ;
                            double row = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                            double width  = Math.abs(recognition.getRight() - recognition.getLeft()) ;
                            double height = Math.abs(recognition.getTop()  - recognition.getBottom()) ;

                            telemetry.addData(""," ");
                            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100 );
                            telemetry.addData("- Position (Row/Col)","%.0f / %.0f", row, col);
                            telemetry.addData("- Size (Width/Height)","%.0f / %.0f", width, height);
                        
                            telemetry.addData( "LabelCone", recognition.getLabel());
                            String LabelCone;
                            LabelCone = recognition.getLabel();
                            if (LabelCone == "1 Bolt") {
                                telemetry.addData("Lokasi Tujuan","zona_1"); //Edit nama zonanya sesuai manual game
                                autonomous_01(); // panggil program gerakan autonomous 1
                            }
                            if (LabelCone == "2 Bulb") {
                                telemetry.addData("Lokasi Tujuan","zona_2"); //Edit nama zonanya sesuai manual game
                                autonomous_02(); // panggil program gerakan autonomous 2
                            }
                            if (LabelCone == "3 Panel") {
                                telemetry.addData("Lokasi Tujuan","zona_3"); //Edit nama zonanya sesuai manual game
                                autonomous_03(); // panggil program gerakan autonomous 3
                            }
                            
                        }
                        telemetry.update();
                    }
                }
            }
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
    
    private void autonomous_01() {
        // tulis program gerakan autonomous 1 disini
        //setelah scan lifter naik
        lifterRobot.setPower(1);
        sleep(6800);
        //lifter stop di atas
        lifterRobot.setPower(0);
        sleep(2000);
        //maju mendekati high junction
        motorKanan1.setPower(-1);
        motorKanan2.setPower(-1);
        motorKiri1.setPower(-1);
        motorKiri2.setPower(-1);
        sleep(2200);
        //belok ke kanan menuju high junction
        motorKanan1.setPower(0.5);
        motorKanan2.setPower(0.5);
        motorKiri1.setPower(-0.5);
        motorKiri2.setPower(-0.5);
        sleep(600);
        //maju ke high junction
        motorKanan1.setPower(-0.5);
        motorKanan2.setPower(-0.5);
        motorKiri1.setPower(-0.5);
        motorKiri2.setPower(-0.5);
        sleep(1500);
        //claw membuka
        servoclaw.setPower(1);
        sleep(500);
        motorKanan1.setPower(0);
        motorKanan2.setPower(0);
        motorKiri1.setPower(0);
        motorKiri2.setPower(0);
        sleep(300000);
        
        //akhir program autonomous motor drivebase: setPower(0) sleep (300000) agar
        //program tidak segera looping kembali ke program awal
        //motorKanan.setPower(0);
        //motorKiri.setPower (0);
        //sleep(10000);
    }
    
    private void autonomous_02() {
        // tulis program gerakan autonomous 2 disini
        // maju
        lifterRobot.setPower(1);
        sleep(6800);
        //lifter stop di atas
        lifterRobot.setPower(0);
        sleep(2000);
        //maju mendekati high junction
        motorKanan1.setPower(-1);
        motorKanan2.setPower(-1);
        motorKiri1.setPower(-1);
        motorKiri2.setPower(-1);
        sleep(2200);
        //belok ke kanan menuju high junction
        motorKanan1.setPower(0.5);
        motorKanan2.setPower(0.5);
        motorKiri1.setPower(-0.5);
        motorKiri2.setPower(-0.5);
        sleep(600);
        //maju ke high junction
        motorKanan1.setPower(-0.5);
        motorKanan2.setPower(-0.5);
        motorKiri1.setPower(-0.5);
        motorKiri2.setPower(-0.5);
        sleep(1500);
        servoclaw.setPower(1);
        sleep(500);
        motorKanan1.setPower(0);
        motorKanan2.setPower(0);
        motorKiri1.setPower(0);
        motorKiri2.setPower(0);
        sleep(300000);
        
        //akhir program autonomous motor drivebase: setPower(0) sleep (300000) agar
        //program tidak segera looping kembali ke program awal
        //motorKanan.setPower(0);
        //motorKiri.setPower (0);
        //sleep(10000);
    }
    
    private void autonomous_03() {
        // tulis program gerakan autonomous 3 disini
        // maju
        lifterRobot.setPower(1);
        sleep(6800);
        //lifter stop di atas
        lifterRobot.setPower(0);
        sleep(2000);
        //maju mendekati high junction
        motorKanan1.setPower(-1);
        motorKanan2.setPower(-1);
        motorKiri1.setPower(-1);
        motorKiri2.setPower(-1);
        sleep(2200);
        //belok ke kanan menuju high junction
        motorKanan1.setPower(0.5);
        motorKanan2.setPower(0.5);
        motorKiri1.setPower(-0.5);
        motorKiri2.setPower(-0.5);
        sleep(600);
        motorKanan1.setPower(0);
        motorKanan2.setPower(0);
        motorKiri1.setPower(0);
        motorKiri2.setPower(0);
        sleep(200);
        //maju ke high junction
        motorKanan1.setPower(-0.5);
        motorKanan2.setPower(-0.5);
        motorKiri1.setPower(-0.5);
        motorKiri2.setPower(-0.5);
        sleep(1500);
        servoclaw.setPower(1);
        sleep(500);
        motorKanan1.setPower(0);
        motorKanan2.setPower(0);
        motorKiri1.setPower(0);
        motorKiri2.setPower(0);
        sleep(300000);
        
        /* akhir program autonomous motor drivebase: setPower(0) sleep (300000) agar
        program tidak segera looping kembali ke program awal */
        //motorKanan.setPower(0);
        //motorKiri.setPower (0);
        //sleep(10000);
    }
}
    



