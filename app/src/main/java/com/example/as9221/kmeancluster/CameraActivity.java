package com.example.as9221.kmeancluster;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;


//use this for your personal checks
import static java.lang.Math.abs;
import static org.opencv.highgui.Highgui.imwrite;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;


public class CameraActivity extends AppCompatActivity {
    ImageView imageViewDisplay;
    Button buttonDiagnosis;
    Bitmap receivedImg;
    protected static final String TAG = null;
    private static Bitmap resultBitmap;
    private static Mat keepSize;
    private static boolean fileExist;
    //private static MultilayerPerceptron classifierMLPerceptron;
    private static RandomForest classifierRF;

    ProgressDialog progDailog;
    AsyncTask task;

    static {

        if (!OpenCVLoader.initDebug())
            Log.d("ERROR", "Unable to load OpenCV");
        else
            Log.d("SUCCESS", "OpenCV loaded");
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageViewDisplay = (ImageView) findViewById(R.id.imgViewDis);
        try {
            receivedImg = getImage();
            imageViewDisplay.setImageBitmap(receivedImg);

        } catch (IOException e) {
            e.printStackTrace();
        }

        resultBitmap = null;
        keepSize = null;
        // classifierMLPerceptron = null;
        classifierRF = null;
        //fileExist = true;

        buttonDiagnosis = (Button) findViewById(R.id.buttonDiagnosis);

        buttonDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the ProgressDialog on this thread

                progDailog = new ProgressDialog(CameraActivity.this);
                progDailog.setMessage("please wait....");
                progDailog.setTitle("Process");
                progDailog.setIndeterminate(true);
                progDailog.setCancelable(false);

                Drawable drawable = new ProgressBar(CameraActivity.this).getIndeterminateDrawable().mutate();
                    drawable.setColorFilter(ContextCompat.getColor(CameraActivity.this, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN);
                progDailog.setIndeterminateDrawable(drawable);


                progDailog.show();

                task = new ProcessImage().execute("Any parameters my download task needs here");

            }

        });

    }

        class ProcessImage extends AsyncTask<String, Void, Object> {
            protected Object doInBackground(String... args) {
                Log.i("MyApp", "Background thread starting");

                // This is where you would do all the work of image processing of your data
                Log.d(TAG, "Load Model Now");
                AssetManager assetManager = getAssets();

                try {
                    //mClassifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("iris_model_logistic_allfeatures.model"));
                    //classifierMLPerceptron = (MultilayerPerceptron) weka.core.SerializationHelper.read(assetManager.open("MLPModel120.model"));
                    classifierRF = (RandomForest) weka.core.SerializationHelper.read(assetManager.open("RFModel180New1.model"));


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    // Weka "catch'em all!"
                    e.printStackTrace();
                }
                /*if (classifierMLPerceptron == null) {
                    Log.d(TAG, "Model not loaded successfully!");
                    Toast.makeText(CameraActivity.this, "Prediction Model not loaded successfully!. Please contact the developer", Toast.LENGTH_LONG).show();
                    return null;
                } else {
                    Toast.makeText(CameraActivity.this, "Model loaded.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Model loaded successfully");
                }*/


                Log.i(TAG, "org Size " + receivedImg.getHeight() + " " + receivedImg.getWidth());

                colorMapKMeans(receivedImg);


                return "replace this with your data object";
            }

            protected void onPostExecute(Object result) {
                // Pass the result data back to the main activity
                //CameraActivity.this.data = result;

                if (CameraActivity.this.progDailog != null) {
                    CameraActivity.this.progDailog.dismiss();

                    View view = View.inflate(CameraActivity.this, R.layout.dialog_layout, null);


                    final AlertDialog dialog = new AlertDialog.Builder(CameraActivity.this, R.style.AlertDialogCustom)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File imageTFile = null;
                                    // Create an image file name
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                    String imageFileName = "TB_" + timeStamp + "_";
                                    File storageDir = new File(Environment.getExternalStorageDirectory(), "TempFolder");
                                    if (!storageDir.exists()) {
                                        storageDir.mkdirs();
                                    }
                                    //File storageDir = Environment.getExternalStoragePublicDirectory(
                                    //        Environment.DIRECTORY_PICTURES);
                                    try {
                                        imageTFile = File.createTempFile(imageFileName, ".jpg", storageDir);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    FileOutputStream out = null;
                                    try {
                                        out = new FileOutputStream(imageTFile);
                                        resultBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                                        // PNG is a lossless format, the compression factor (100) is ignored
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Toast.makeText(CameraActivity.this, "Result Image Saved to" + imageTFile , Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setView(view)
                            .create();
                    dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                                  @Override
                                                  public void onShow(DialogInterface arg0) {
                                                      dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.colorPrimary));
                                                      dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.colorPrimary));

                                                  }
                                              });

                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();

                    ImageView imgRefInflatedRes = (ImageView) view.findViewById(R.id.resultDialogImage);
                    imgRefInflatedRes.setImageBitmap(resultBitmap);
                    ImageView imgRefInflatedOrg = (ImageView) view.findViewById(R.id.orgDialogImage);
                    imgRefInflatedOrg.setImageBitmap(receivedImg);
                    TextView resultTitle =(TextView) view.findViewById(R.id.resultTitleTView);
                    if (Build.VERSION.SDK_INT >= 24) {

                        resultTitle.setText(Html.fromHtml(getString(R.string.result_title), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        resultTitle.setText(Html.fromHtml(getString(R.string.result_title)));
                    }

                }
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //in here i can make what i want on the action bar visible or invisible
        //iconMenu = menu.findItem(R.id.action_mainMenu2);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_mainMenu2).getActionView();

        //accountMenu.setVisible(false);
        return true;
    }

    private Bitmap getImage() throws IOException {
        Bitmap returnImage = null;
        //InputStream in = null;
        ExifInterface exifInterface = null;

        String title1 = getIntent().getExtras().getString("Title");

        if  (title1.equals("Load_Image")) {
            String ps = getIntent().getStringExtra("imagePath"); //it should be same as you send it
            //full image
            returnImage = BitmapFactory.decodeFile(ps);
            //reduced size image
            //returnImage = decodeFile(ps);
            exifInterface = new ExifInterface(ps);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            /*ExifInterface exif = new ExifInterface(ps);
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            int deg = rotationInDegrees;*/
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    //rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    //rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    //rotation = 270;
                    break;
            }
            Log.i(TAG, "Size of bitmap after rotation: "  + returnImage.getHeight() + " " + returnImage.getWidth());

            returnImage = Bitmap.createBitmap(returnImage, 0, 0, returnImage.getWidth(), returnImage.getHeight(), matrix, true);

            /*if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
                returnImage = Bitmap.createBitmap(returnImage, 0, 0, returnImage.getWidth(), returnImage.getHeight(), matrix, true);*/

            //imageViewDisplay.setImageBitmap(returnImage);

        } else {
            if (title1.equals("Take_Image")) {

                Uri myUri = getIntent().getParcelableExtra("imageUri");

                Log.i(TAG, "I am here: ");

                try {
                    returnImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //this method return string, do I need File as well
                String path = getRealPathFromURI(myUri);
                //full image
                File fileFromUri = new File(myUri.getPath());
                //reduced image
                //returnImage = decodeFile(path);

                /*try {
                    in = getContentResolver().openInputStream(myUri);
                    //exifInterface = new ExifInterface(in);

                    exifInterface = new ExifInterface(myUri.toString());
                    // Now you can extract any Exif tag you want
                    // Assuming the image is a JPEG or supported raw format
                } catch (IOException e) {
                    // Handle any errors
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ignored) {}
                    }
                }*/
                Log.i(TAG, "I am here too: ");

                exifInterface = new ExifInterface(path);

                int rotation = 0;
                Matrix matrix = new Matrix();
                int orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        rotation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        rotation = 270;
                        break;
                }
                Log.i(TAG, "I am here too too: ");


                Log.i(TAG, "Size of bitmap after rotation: "  + returnImage.getHeight() + " " + returnImage.getWidth());
                returnImage = Bitmap.createBitmap(returnImage, 0, 0, returnImage.getWidth(), returnImage.getHeight(), matrix, true); // rotating bitmap

                //ImageView img = (ImageView) findViewById(R.id.imgTakingPic);
                //img.setImageBitmap(myBitmap);
            }
        }

        return returnImage;
   }

    public static void colorMapKMeans(Bitmap srcOrig) {

        Mat srcOrigMat = null;

        srcOrigMat = new Mat (srcOrig.getHeight(), srcOrig.getWidth(), CvType.CV_8U, new Scalar(4));
        // this code working correctly
        //we may need to resize image
        //Imgproc.resize(src, src, new Size(src.rows()/4,src.cols()/4));
        Bitmap myBitmap32 = srcOrig.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(myBitmap32, srcOrigMat);

        Mat src = new Mat();
        Mat bestGrayImg = new Mat();
        Mat bestcoloredImg = new Mat();
        Mat resultEMat = new Mat();

        keepSize = srcOrigMat.clone();

        Log.i(TAG, "Source Image Size before resizing: " + srcOrigMat.size().width + " " + srcOrigMat.size().height);
        Log.i(TAG, "Keep Size " + keepSize);
        Log.i(TAG, "preprocessing start ");

        Imgproc.cvtColor(srcOrigMat, srcOrigMat, Imgproc.COLOR_RGBA2RGB);

        // do not change the size, the size does not affect the performance
        int scaleFactor = calcScaleFactor(srcOrigMat.rows(), srcOrigMat.cols());
        Imgproc.resize(srcOrigMat, src, new Size(srcOrigMat.cols()/scaleFactor, srcOrigMat.rows()/scaleFactor));
        // resize to 300X300
        //Imgproc.resize(srcOrigMat, src, new Size(300, 300));
        // scale to 0.25 of the image
        //Imgproc.resize(srcOrigMat, src, new Size(srcOrigMat.rows()*0.25, srcOrigMat.cols()*0.25));
        Imgproc.GaussianBlur(src, src, new Size(5,5), 1);
        Log.i(TAG, "preprocessing end ");


        Log.i(TAG, "kmean preprocessing start ");

        //convert to lab with three channels
        Mat imgLab = new Mat();
        Imgproc.cvtColor(src, imgLab, Imgproc.COLOR_RGB2Lab, 3);

        // separate channels
        List<Mat> lab_planes = new ArrayList<Mat>(3);
        Core.split(imgLab, lab_planes);

        Mat channel = lab_planes.get(2);
        channel = Mat.zeros(imgLab.rows(), imgLab.cols(), CvType.CV_8UC1);

        // use only AB channels in Lab color space
        lab_planes.set(2, channel);
        Core.merge(lab_planes,imgLab);

        //unrolls 3 channels into columns
        Mat samples = imgLab.reshape(1, src.cols() * src.rows());
        samples.convertTo(samples, CvType.CV_32F);

        // Apply K-Means
        int clusterCount = 4;
        Mat labels = new Mat();
        int attempts = 20;
        Mat centers = new Mat();
        Log.i(TAG, "kmean preprocessing end ");

        Log.i(TAG, "kmean processing start ");

        Core.kmeans(samples, clusterCount, labels, new TermCriteria(TermCriteria.MAX_ITER|
                TermCriteria.EPS , 10000, 0.0001), attempts, Core.KMEANS_PP_CENTERS, centers);
        // create array of the centers
        double [] dstCenter = new double[clusterCount];
        for (int i = 0; i < clusterCount; i++) {
            dstCenter[i] = calcWhiteDist(centers.get(i, 0)[0], centers.get(i, 1)[0], centers.get(i, 2)[0]);
            Log.i(TAG, "Printing clusters " + dstCenter[i]);
        }

        int indexd = 0;
        double maxim = dstCenter[0];
        for (int i = 0; i < clusterCount; i++) {
            double temp = 0;
            temp=dstCenter[i];
            Log.i(TAG, "Printing the white distance " + temp);
            if(maxim<temp)
            {
                maxim=temp;
                indexd=i;
            }
        }

        int BestCluster = indexd;
        Log.i(TAG, "Printing best cluster " + BestCluster);

        centers.convertTo(centers, CvType.CV_8UC1, 255.0);
        centers.reshape(3);
        //Imgcodecs.imwrite("/mnt/sdcard/DCIM/srcIMG.jpg", src);//check

        List<Mat> clusters = new ArrayList<Mat>();
        for (int i = 0; i < centers.rows(); i++) {
            clusters.add(Mat.zeros(src.size(), src.type()));
        }

        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for(int i = 0; i < centers.rows(); i++) counts.put(i, 0);
        //Imgcodecs.imwrite("/mnt/sdcard/DCIM/srcIMG.jpg", src);//check

        for( int y = 0; y < src.rows(); y++ ) {
            for( int x = 0; x < src.cols(); x++ )
            {
                int cluster_idx = (int)labels.get(x + y*src.cols(),0)[0];
                int bOrgi = (int) src.get(cluster_idx,2)[2];
                int gOrgi = (int) src.get(cluster_idx,1)[1];
                int rOrgi = (int) src.get(cluster_idx,0)[0];
                counts.put(cluster_idx, counts.get(cluster_idx) + 1);
                clusters.get(cluster_idx).put(y, x, rOrgi, gOrgi, bOrgi);
            }
        }

        for(int z = 0; z < clusters.size(); z++) {
            //we may need those later
            //Imgproc.erode(clusters.get(z),clusters.get(z), new Mat(), new Point(-1,-1),3);
            //Imgproc.morphologyEx( clusters.get(z), clusters.get(z), MORPH_OPEN, new Mat(),new Point(-1,-1), 3 );
            //Imgproc.morphologyEx( clusters.get(z), clusters.get(z), MORPH_CLOSE, new Mat(),new Point(-1,-1), 3 );
            //Imgproc.morphologyEx( clusters.get(z), clusters.get(z), Imgproc.MORPH_GRADIENT, new Mat(),new Point(-1,-1), 3 );

            Mat resultMat = new Mat();
            src.copyTo(resultMat, clusters.get(z));
            //int nonZeroValues1 = Core.countNonZero(resultMat);
            //Log.i(TAG, "Non zero pixel in the cluster " + nonZeroValues1);
            imwrite("/mnt/sdcard/DCIM/Cluster" + z + ".jpg", resultMat);//check
            // now enhance the result using threshold & morphological processing
            Imgproc.cvtColor(resultMat, resultMat, Imgproc.COLOR_RGB2GRAY);
            //new method of thresholding
            // global thresholding
            //Imgproc.threshold(resultMat, resultMat, 0, 255, Imgproc.THRESH_BINARY);
            /*imwrite("/mnt/sdcard/DCIM/gThreshold" + z + ".jpg", resultMat);//check

            // Otsu's thresholding
            Imgproc.threshold(resultMat, resultMat, 0, 255, Imgproc.THRESH_BINARY + THRESH_OTSU);
            imwrite("/mnt/sdcard/DCIM/oThreshold" + z + ".jpg", resultMat);//check
            // Otsu's thresholding after Gaussian filtering
            Imgproc.GaussianBlur(resultMat, resultMat, new Size(5,5), 0);
            Imgproc.threshold(resultMat, resultMat, 0, 255, Imgproc.THRESH_BINARY + THRESH_OTSU);
            imwrite("/mnt/sdcard/DCIM/soThreshold" + z + ".jpg", resultMat);//check
            //Imgcodecs.imwrite("/mnt/sdcard/DCIM/MLImage.jpg", img);//check
            Imgproc.dilate(resultMat,resultMat, new Mat(), new Point(-1,-1),1);
            Imgproc.erode(resultMat,resultMat, new Mat(), new Point(-1,-1),4);*/

            //old code which working perfectly
            Imgproc.threshold(resultMat, resultMat, 0, 255, Imgproc.THRESH_OTSU);
            // closure, i.e. a dilatation followed by an erosion.
            //close those two lines to check whether enhance contour filtering
            //Imgproc.dilate(resultMat,resultMat, new Mat(), new Point(-1,-1),1);
            Imgproc.erode(resultMat,resultMat, new Mat(), new Point(-1,-1),4);

            //imwrite("/mnt/sdcard/DCIM/Cluster" + z + ".jpg", resultMat);//check


            //send grayscale image
            if (z==BestCluster) {
                bestGrayImg = resultMat.clone();
                imwrite("/mnt/sdcard/DCIM/bestGrayImage.jpg", bestGrayImg);//check
            }

            if (z==BestCluster) {
                src.copyTo(resultEMat, bestGrayImg);
                bestcoloredImg = resultEMat.clone();
                imwrite("/mnt/sdcard/DCIM/bestBGRImage.jpg", bestcoloredImg); //check
            }

        }
        Log.i(TAG, "kmean processing end ");

        // now start feature extraction
       /* bestGrayImg = srcOrigMat;
        bestcoloredImg = resultMat1;*/
        featureExtraction(bestGrayImg, bestcoloredImg); // this will be for the full app
        //featureExtraction1(bestGrayImg, bestcoloredImg); // this will be for constructing the dataset & features using images is much better than hist.

    }

    private static void featureExtraction(Mat srcImg, Mat maskImg)
    {
        double maxVal;
        fileExist = true;
        Mat l_hist = new Mat();
        Mat a_hist = new Mat();
        Mat b_hist = new Mat();
        MatOfFloat l_range = new MatOfFloat(0, 100);
        MatOfFloat a_range = new MatOfFloat(-127, 127);
        MatOfFloat b_range = new MatOfFloat(-127, 127);
        boolean Accumulate = false;
        String featureVector = null;
        List<double[]> coordinateValTest = new ArrayList<double[]>();;

        // count correct contours
        int countContours = 0;
        //List<String> prediction = new ArrayList<String>();
        List<String> stringTemp = new ArrayList<String>();
        List<Double> contourArea = new ArrayList<Double>();


        Log.i(TAG, "Source Image Size: " + srcImg.size().width + " " + srcImg.size().height);

        //Give us the correct cluster which contains many wells
        //Imgproc.Canny(srcImg, srcImg, 50, 150);
        //imwrite("/mnt/sdcard/DCIM/srcImgcanny.jpg", srcImg);//check

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        List<double[]> coordinateValues = new ArrayList<double[]>();

        Mat hierarchy = new Mat();
        Imgproc.findContours(srcImg, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        //Log.i(TAG, "contours size " + contours.size());

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            double temp;
            temp = Imgproc.contourArea(contours.get(contourIdx));
            Log.i(TAG, "Contour Size < 50: " + temp + " ID: " + contourIdx);


            //first conditions
            if (temp > 1) { //temp =8 will not work for one image
                Log.i(TAG, "Contour Size > 50: " + temp + "ID: " + contourIdx);

                //add another condition for contouring filter
                MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());
                double arch = Imgproc.arcLength(contour2f, true);
                double squareness = 4 * Math.PI * temp / Math.pow(arch, 2);
                Log.i(TAG, "Squareness: " + squareness + "ID: " + contourIdx);

                //boolean convex = Imgproc.isContourConvex(contours.get(contourIdx));
                //Log.i(TAG, "Convex: " + convex + "ID: " + contourIdx);
                if (temp > 5000) {
                    Log.i(TAG, "ID > 3000: " + contourIdx + " " + temp);
                }

                //if (squareness >= 0.2 && temp > 90) {
                //if (temp < 3000) {
                    // was mop
                    /*Moments moments = Imgproc.moments(contours.get(contourIdx));

                    Point centroid = new Point();

                    centroid.x = moments.get_m10() / moments.get_m00();
                    centroid.y = moments.get_m01() / moments.get_m00();

                    Log.i(TAG, "I can get x,y from contours" + centroid.x + " " + centroid.y);
                    //Log.i(TAG, "Size of x & y" + (srcImg.rows()-30) + " " + (srcImg.cols()-30));


                    if (centroid.x < (srcImg.cols()-20) && centroid.y < (srcImg.rows()-20)) {


                        double[] coordinates = new double[]{centroid.x, centroid.y};

                        coordinateValues.add(coordinates);*/

                        Mat drawing = Mat.zeros(srcImg.size(), CvType.CV_8UC1);


                        //contours.remove(contourIdx);
                        // if (squareness <= 0.6) {
                        //float[] radius = new float[1];
                        //Point center = new Point();
                        //Imgproc.minEnclosingCircle(new MatOfPoint2f(contours.get(contourIdx).toArray()), center, radius);
                        //Mat drawing = Mat.zeros(srcImg.size(), CvType.CV_8UC1);
                        Imgproc.drawContours(drawing, contours, contourIdx, new Scalar(255, 0, 0), -1);
                        if (temp>4000) {
                            Log.i(TAG, "I am here for temp > 900");
                            Imgproc.erode(drawing,drawing, new Mat(), new Point(-1,-1),7);
                            imwrite("/mnt/sdcard/DCIM/erode" + contourIdx + ".jpg", drawing);//check

                        }
                        //Core.circle( drawing, center, (int) radius[1], new Scalar(0,255,0), -1);
                        //Core.circle( drawing, center, (int) radius[1], new Scalar(0,0,255), 1);
                        // Imgproc.dilate(drawing, drawing, new Mat(), new Point(-1, -1), 1);
                        //Imgproc.erode(drawing, drawing, new Mat(), new Point(-1, -1), 1);

                       /* } else {
                            //contours.add(new Circle(center.x, center.y, calib.getBallRadius()));


                            //Mat drawing = Mat.zeros(srcImg.size(), CvType.CV_8UC1);
                            Imgproc.drawContours(drawing, contours, contourIdx, new Scalar(255, 0, 0), -1);
                            if (temp>900) {
                                Log.i(TAG, "I am here for temp > 900");
                                Imgproc.erode(drawing,drawing, new Mat(), new Point(-1,-1),8);
                                imwrite("/mnt/sdcard/DCIM/erode" + contourIdx + ".jpg", drawing);//check

                            }*/

                        //imwrite("/mnt/sdcard/DCIM/drawingImg" + contourIdx + ".jpg", drawing);//check
                        //}


                        //imwrite("/mnt/sdcard/DCIM/drawingImg" + contourIdx + ".jpg", drawing);//check

                        //Mat m = new Mat();
                        //Core.extractChannel(drawing, m, 0);

                        int nonZeroValues = Core.countNonZero(drawing);
                        Log.i(TAG, "non zero value" + nonZeroValues);


                        if (nonZeroValues > 0) {


                            //Mat location = Mat.zeros(srcImg.size(), CvType.CV_8UC1);
                            //Core.findNonZero(drawing, location);
                            //MatOfPoint mop = new MatOfPoint(location);


                            Mat resultMat = new Mat();
                            //src.copyTo(resultEMat, drawing);
                            maskImg.copyTo(resultMat, drawing);
                            imwrite("/mnt/sdcard/DCIM/resultImg" + contourIdx + ".jpg", resultMat);//check

                            // convert to Lab
                            Imgproc.cvtColor(resultMat, resultMat, Imgproc.COLOR_RGB2Lab, 3);
                            //Imgcodecs.imwrite("/mnt/sdcard/DCIM/labImg" + contourIdx + ".jpg", resultMat);//check

                            //Mat result32 = imgLab.reshape(1, src.cols() * src.rows());
                            resultMat.convertTo(resultMat, CvType.CV_32F);

                            // normalize values to fit lab color space
                            for (int y = 0; y < resultMat.rows(); y++) {
                                for (int x = 0; x < resultMat.cols(); x++) {
                                    double[] data = resultMat.get(y, x); //Stores element in an array
                                    //for (int k = 0; k < 3; k++) //Runs for the available number of channels
                                    //{
                                    data[0] = data[0] * 100 / 255; //Pixel modification done here
                                    data[1] = data[1] - 128;
                                    data[2] = data[2] - 128;
                                    resultMat.put(y, x, data); //Puts element back into matrix
                                }
                            }

                            // separate channels
                            List<Mat> labsrc_planes = new ArrayList<Mat>(3);
                            Core.split(resultMat, labsrc_planes);
                            Mat l_channel = labsrc_planes.get(0);
                            Mat a_channel = labsrc_planes.get(1);
                            Mat b_channel = labsrc_planes.get(2);

                            // Using the image
                            MatOfDouble l_meanL = new MatOfDouble();
                            MatOfDouble l_stddevL = new MatOfDouble();
                            Core.meanStdDev(l_channel, l_meanL, l_stddevL, drawing);
                            double l_meanValL = l_meanL.get(0, 0)[0];
                            double l_stddevValL = l_stddevL.get(0, 0)[0];

                            MatOfDouble a_meanA = new MatOfDouble();
                            MatOfDouble a_stddevA = new MatOfDouble();
                            Core.meanStdDev(a_channel, a_meanA, a_stddevA, drawing);
                            double a_meanValA = a_meanA.get(0, 0)[0];
                            double a_stddevValA = a_stddevA.get(0, 0)[0];

                            MatOfDouble b_meanB = new MatOfDouble();
                            MatOfDouble b_stddevB = new MatOfDouble();
                            Core.meanStdDev(b_channel, b_meanB, b_stddevB, drawing);
                            double b_meanValB = b_meanB.get(0, 0)[0];
                            double b_stddevValB = b_stddevB.get(0, 0)[0];

                            // calculating the mode using the minMaxLoc
                            Core.MinMaxLocResult l_mmrL = Core.minMaxLoc(l_channel, drawing);
                            double l_modeValL = l_mmrL.maxVal;

                            Core.MinMaxLocResult a_mmrA = Core.minMaxLoc(a_channel, drawing);
                            double a_modeValA = a_mmrA.maxVal;

                            Core.MinMaxLocResult b_mmrB = Core.minMaxLoc(b_channel, drawing);
                            //double b_modeValB = b_mmrB.maxVal;

                            maxVal = b_channel.get(0, 0)[0];
                            for (int y = 0; y < b_channel.rows(); y++) {
                                for (int x = 0; x < b_channel.cols(); x++) {
                                    double tempVal;
                                    tempVal = b_channel.get(y, x)[0];
                                    if (maxVal == 0 || maxVal < tempVal && tempVal != 0) {
                                        maxVal = tempVal;
                                        //index=contourIdx;
                                    }
                                }
                            }
                            double b_modeValB = maxVal;

                            // Calc skew
                            double l_skewValL = (l_meanValL - l_modeValL) / l_stddevValL;
                            double a_skewValA = (a_meanValA - a_modeValA) / a_stddevValA;
                            double b_skewValB = (b_meanValB - b_modeValB) / b_stddevValB;

                            // Calc amp
                            Scalar l_sumL = Core.sumElems(l_channel);
                            double l_ampValL = Math.pow(l_sumL.val[0], 2);

                            Scalar a_sumA = Core.sumElems(a_channel);
                            double a_ampValA = Math.pow(a_sumA.val[0], 2);

                            Scalar b_sumB = Core.sumElems(b_channel);
                            double b_ampValB = Math.pow(b_sumB.val[0], 2);

                            // Calc entropy
                            Mat a_logA = new Mat();
                            Core.log(a_channel, a_logA);
                            double a_entropyA = Core.sumElems(a_channel.mul(a_logA)).val[0];

                            Mat b_logB = new Mat();
                            Core.log(b_channel, b_logB);
                            double b_entropyB = Core.sumElems(b_channel.mul(b_logB)).val[0];

                            Log.i(TAG, "Printing statistics");
                            Log.i(TAG, "meanL = " + l_meanValL + " " + "stdL = " + l_stddevValL + " " + "modeL = " + l_modeValL + " " + "skewL = " + l_skewValL + " " + "ampL = " + l_ampValL);
                            Log.i(TAG, "meanA = " + a_meanValA + " " + "stdA = " + a_stddevValA + " " + "modeA = " + a_modeValA + " " + "skewA = " + a_skewValA + " " + "ampA = " + a_ampValA + " " + "entropyA = " + a_entropyA);
                            Log.i(TAG, "meanB = " + b_meanValB + " " + "stdB = " + b_stddevValB + " " + "modeB = " + b_modeValB + " " + "skewB = " + b_skewValB + " " + "ampB = " + b_ampValB + " " + "entropyB = " + b_entropyB);

                            if(a_meanValA > 0) {
                                if (a_stddevValA != 0 && b_stddevValB != 0) {
                                    //countContours++;
                                    // was mop
                                    Moments moments = Imgproc.moments(contours.get(contourIdx));

                                    Point centroid = new Point();

                                    centroid.x = moments.get_m10() / moments.get_m00();
                                    centroid.y = moments.get_m01() / moments.get_m00();

                                    Log.i(TAG, "I can get x,y from contours" + centroid.x + " " + centroid.y);
                                    Log.i(TAG, "correct contours :" + contourIdx);

                                    if (centroid.x < (srcImg.cols()-20) && centroid.y < (srcImg.rows()-20)) {


                                            countContours++;
                                            moments = Imgproc.moments(contours.get(contourIdx));

                                            centroid = new Point();

                                            centroid.x = moments.get_m10() / moments.get_m00();
                                            centroid.y = moments.get_m01() / moments.get_m00();

                                            Log.i(TAG, "I can get x,y from contours" + centroid.x + " " + centroid.y);
                                            Log.i(TAG, "correct contours :" + contourIdx);

                                                double[] coordinates = new double[]{centroid.x, centroid.y};

                                                coordinateValues.add(coordinates);
                                                contourArea.add(temp);

                                                // now write the feature values into a file
                                                featureVector = "featureVectorTest";
                                                double[] element1 = new double[]{l_meanValL, l_stddevValL, l_modeValL, l_skewValL, l_ampValL, 0};
                                                double[] element2 = new double[]{a_meanValA, a_stddevValA, a_modeValA, a_skewValA, a_ampValA, a_entropyA};
                                                double[] element3 = new double[]{b_meanValB, b_stddevValB, b_modeValB, b_skewValB, b_ampValB, b_entropyB};

                                                List<double[]> values = new ArrayList<double[]>(1);

                                                values.add(element1);
                                                values.add(element2);
                                                values.add(element3);


                                                List<Double> doubleTemp = new ArrayList<Double>();
                                                //for(int j=0;j<values.size(); j++) {
                                                for (int i = 0; i < values.get(0).length; i++) {
                                                    doubleTemp.add(values.get(0)[i]);
                                                    doubleTemp.add(values.get(1)[i]);
                                                    doubleTemp.add(values.get(2)[i]);

                                                }

                                                //for (Double d : doubleTemp) {
                                                //Log.i(TAG, "Data of double temp " + doubleTemp.indexOf(d) + " " + d);
                                                //}
                                                // send the array list for prediction
                                                // do prediction
                                                String stringValue = doPrediction(doubleTemp);
                                                stringTemp.add(countContours - 1, stringValue);
                                                //for (String d : stringTemp) {
                                                //Log.i(TAG, "Prediction value " + stringTemp.indexOf(d) + " " + d);
                                                //}
                                                Iterator<String> iterator = stringTemp.iterator();
                                                while (iterator.hasNext()) {
                                                    Log.i(TAG, "Prediction value " + iterator.next());
                                                }

                                                Log.i(TAG, "write to file " + countContours);
                                                writeToFile1(featureVector, doubleTemp);
                                    }//centroid
                                }//std
                            }//mean_a
                        }//none zero value
                        //} // else write a toast message to user saying there is no contours
                //}// size < 5000
            }//size
        }
        Log.i(TAG, "Number of correct contours " + countContours);
        int[] classList = new int[countContours];
        Mat classifiedMat = new Mat();

        for (int i = 0; i < stringTemp.size(); i ++) {
            // i is the index
            if (stringTemp.get(i)=="tested_negative") {
                classList[i] = -1;
            }
            else {
                classList[i] = 1;
            }
            Mat classMat = new Mat(1, 1, CvType.CV_32S, new Scalar(classList[i]));
            classifiedMat.push_back(classMat);
        }

        // call the classifier
        //Mat classifiedMat = SVMClassifier.SVMTBTest(countContours);
        // now draw the result
        //Log.i(TAG, "I reach here " );
        drawResult(classifiedMat, coordinateValues, srcImg, countContours, contourArea);
    }

    private static void featureExtraction1(Mat srcImg, Mat maskImg)
    {
        double maxVal;
        fileExist = true;
        Mat l_hist = new Mat();
        Mat a_hist = new Mat();
        Mat b_hist = new Mat();
        MatOfFloat l_range = new MatOfFloat(0, 100);
        MatOfFloat a_range = new MatOfFloat(-127, 127);
        MatOfFloat b_range = new MatOfFloat(-127, 127);
        boolean Accumulate = false;
        String featureVector = null;
        // count correct contours
        int countContours = 0;


        //Give us the correct cluster which contains many wells
        //imwrite("/mnt/sdcard/DCIM/srcImg.jpg", srcImg);//check

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        List<double[]> coordinateValues = new ArrayList<double[]>();

        Mat hierarchy = new Mat();
        Imgproc.findContours(srcImg, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
        //Log.i(TAG, "contours size " + contours.size());

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            double temp;
            temp = Imgproc.contourArea(contours.get(contourIdx));
            if (temp > 50) {

                Mat drawing = Mat.zeros(srcImg.size(), CvType.CV_8UC1);
                Imgproc.drawContours(drawing, contours, contourIdx, new Scalar(255,0,0), -1);
                if (temp>900) {
                    Imgproc.erode(drawing,drawing, new Mat(), new Point(-1,-1),8);
                    imwrite("/mnt/sdcard/DCIM/erode" + contourIdx + ".jpg", drawing);//check

                }
                //imwrite("/mnt/sdcard/DCIM/drawingImg" + contourIdx + ".jpg", drawing);//check

                //Mat m = new Mat();
                //Core.extractChannel(drawing, m, 0);
                int nonZeroValues = Core.countNonZero(drawing);
                Log.i(TAG, "non zero value" + nonZeroValues);

                if (nonZeroValues > 0) {
                    countContours++;

                    //Mat location = Mat.zeros(srcImg.size(), CvType.CV_8UC1);
                    //Core.findNonZero(drawing, location);
                    //MatOfPoint mop = new MatOfPoint(location);

                    // was mop
                    Moments moments = Imgproc.moments(contours.get(contourIdx));

                    Point centroid = new Point();

                    centroid.x = moments.get_m10() / moments.get_m00();
                    centroid.y = moments.get_m01() / moments.get_m00();

                    Log.i(TAG, "I can get x,y from contours" + centroid.x + " " + centroid.y);

                    double[] coordinates = new double[]{centroid.x, centroid.y};

                    coordinateValues.add(coordinates);

                    Mat resultMat = new Mat();
                    //src.copyTo(resultEMat, drawing);
                    maskImg.copyTo(resultMat, drawing);
                    //imwrite("/mnt/sdcard/DCIM/resultImg" + contourIdx + ".jpg", resultMat);//check

                    // convert to Lab
                    Imgproc.cvtColor(resultMat, resultMat, Imgproc.COLOR_RGB2Lab, 3);
                    //Imgcodecs.imwrite("/mnt/sdcard/DCIM/labImg" + contourIdx + ".jpg", resultMat);//check

                    //Mat result32 = imgLab.reshape(1, src.cols() * src.rows());
                    resultMat.convertTo(resultMat, CvType.CV_32F);

                    // normalize values to fit lab color space
                    for (int y = 0; y < resultMat.rows(); y++) {
                        for (int x = 0; x < resultMat.cols(); x++) {
                            double[] data = resultMat.get(y, x); //Stores element in an array
                            //for (int k = 0; k < 3; k++) //Runs for the available number of channels
                            //{
                            data[0] = data[0] * 100 / 255; //Pixel modification done here
                            data[1] = data[1] - 128;
                            data[2] = data[2] - 128;
                            resultMat.put(y, x, data); //Puts element back into matrix
                        }
                    }

                    // separate channels
                    List<Mat> labsrc_planes = new ArrayList<Mat>(3);
                    Core.split(resultMat, labsrc_planes);
                    Mat l_channel = labsrc_planes.get(0);
                    Mat a_channel = labsrc_planes.get(1);
                    Mat b_channel = labsrc_planes.get(2);

                    //Using histogram
                    //imwrite("/mnt/sdcard/DCIM/l_Img" + contourIdx + ".jpg", l_channel);//check
                    //imwrite("/mnt/sdcard/DCIM/a_Img" + contourIdx + ".jpg", a_channel);//check
                    //imwrite("/mnt/sdcard/DCIM/b_Img" + contourIdx + ".jpg", b_channel);//check*/

                    java.util.List<Mat> l_matList = new LinkedList<Mat>();
                    l_matList.add(l_channel);
                    java.util.List<Mat> a_matList = new LinkedList<Mat>();
                    a_matList.add(a_channel);
                    java.util.List<Mat> b_matList = new LinkedList<Mat>();
                    b_matList.add(b_channel);

                    //Imgcodecs.imwrite("/mnt/sdcard/DCIM/bestImage.jpg", maskImg);//check
                    //Imgcodecs.imwrite("/mnt/sdcard/DCIM/AImage.jpg", srcImg);//check


                    Imgproc.calcHist(l_matList, new MatOfInt(0), drawing, l_hist, new MatOfInt(256), l_range, Accumulate);
                    Imgproc.calcHist(a_matList, new MatOfInt(0), drawing, a_hist, new MatOfInt(256), a_range, Accumulate);
                    Imgproc.calcHist(b_matList, new MatOfInt(0), drawing, b_hist, new MatOfInt(256), b_range, Accumulate);

                    //Imgcodecs.imwrite("/mnt/sdcard/DCIM/ContImage.jpg", l_hist);//check
                    //imwrite("/mnt/sdcard/DCIM/l_hist" + contourIdx + ".jpg", l_hist);//check
                    //imwrite("/mnt/sdcard/DCIM/a_hist" + contourIdx + ".jpg", a_hist);//check
                    //imwrite("/mnt/sdcard/DCIM/b_hist" + contourIdx + ".jpg", b_hist);//check*/

                    // Calc mean & stddev
                    MatOfDouble meanL = new MatOfDouble();
                    MatOfDouble stddevL = new MatOfDouble();
                    Core.meanStdDev(l_hist, meanL, stddevL);
                    double meanValL = meanL.get(0, 0)[0];
                    double stddevValL = stddevL.get(0, 0)[0];

                    MatOfDouble meanA = new MatOfDouble();
                    MatOfDouble stddevA = new MatOfDouble();
                    Core.meanStdDev(a_hist, meanA, stddevA);
                    double meanValA = meanA.get(0, 0)[0];
                    double stddevValA = stddevA.get(0, 0)[0];

                    MatOfDouble meanB = new MatOfDouble();
                    MatOfDouble stddevB = new MatOfDouble();
                    Core.meanStdDev(b_hist, meanB, stddevB);
                    double meanValB = meanB.get(0, 0)[0];
                    double stddevValB = stddevB.get(0, 0)[0];

                    // Calc mode
                    Core.MinMaxLocResult l_mmr = Core.minMaxLoc(l_hist);
                    double modeValL = l_mmr.maxVal;

                    Core.MinMaxLocResult a_mmr = Core.minMaxLoc(a_hist);
                    double modeValA = a_mmr.maxVal;

                    Core.MinMaxLocResult b_mmr = Core.minMaxLoc(b_hist);
                    double modeValB = b_mmr.maxVal;

                    // Calc skew
                    double skewValL = (meanValL - modeValL) / stddevValL;
                    double skewValA = (meanValA - modeValA) / stddevValA;
                    double skewValB = (meanValB - modeValB) / stddevValB;

                    // Calc amp
                    Scalar sumL = Core.sumElems(l_hist);
                    double ampValL = Math.pow(sumL.val[0], 2);

                    Scalar sumA = Core.sumElems(a_hist);
                    double ampValA = Math.pow(sumA.val[0], 2);

                    Scalar sumB = Core.sumElems(b_hist);
                    double ampValB = Math.pow(sumB.val[0], 2);

                    // Calc entropy
                    Mat logA = new Mat();
                    Core.log(a_hist, logA);
                    double entropyA = Core.sumElems(a_hist.mul(logA)).val[0];

                    Mat logB = new Mat();
                    Core.log(b_hist, logB);
                    double entropyB = Core.sumElems(b_hist.mul(logB)).val[0];



                    //printing statistics
                    Log.i(TAG, "Printing statistics");
                    Log.i(TAG, "meanL = " + meanValL + " " + "stdL = " + stddevValL + " " + "modeL = " + modeValL + " " + "skewL = " + skewValL + " " + "ampL = " + ampValL);
                    Log.i(TAG, "meanA = " + meanValA + " " + "stdA = " + stddevValA + " " + "modeA = " + modeValA + " " + "skewA = " + skewValA + " " + "ampA = " + ampValA + " " + "entropyA = " + entropyA);
                    Log.i(TAG, "meanB = " + meanValB + " " + "stdB = " + stddevValB + " " + "modeB = " + modeValB + " " + "skewB = " + skewValB + " " + "ampB = " + ampValB + " " + "entropyB = " + entropyB);

                    // now write the feature values into a file, featureVector for building the dataset
                    //featureVector = "featureVector";

                    // now write the feature values into a file, featureVectorTest for testing
                    featureVector = "featureVectorTest";

                    double[] element1 = new double[]{meanValL, stddevValL, modeValL, skewValL, ampValL, 0};
                    double[] element2 = new double[]{meanValA, stddevValA, modeValA, skewValA, ampValA, entropyA};
                    double[] element3 = new double[]{meanValB, stddevValB, modeValB, skewValB, ampValB, entropyB};

                    List<double[]> values = new ArrayList<double[]>(1);

                    values.add(element1);
                    values.add(element2);
                    values.add(element3);


                    List<Double> doubleTemp = new ArrayList<Double>();
                    //for(int j=0;j<values.size(); j++) {
                    for (int i = 0; i < values.get(0).length; i++) {
                        doubleTemp.add(values.get(0)[i]);
                        doubleTemp.add(values.get(1)[i]);
                        doubleTemp.add(values.get(2)[i]);

                    }

                    for (Double d : doubleTemp) {
                        Log.i(TAG, "Data of double temp " + doubleTemp.indexOf(d) + " " + d);
                    }
                    Log.i(TAG, "write to file " + countContours);
                    //writeToFile1(featureVector, doubleTemp); // writeToFile1 is for the building the dataset
                    //writeToFile1(featureVector, doubleTemp); // writeToFile is for testing

                } // else write a toast message to user saying there is no contours
            }
        }
        Log.i(TAG, "Number of correct contours " + countContours);
        // call the classifier
        //Mat classifiedMat = SVMClassifier.SVMTBTest(countContours);
        // now draw the result
        Log.i(TAG, "I reach here " );
        //drawResult(classifiedMat, coordinateValues, srcImg, countContours);
    }

    //public static List<String> doPrediction(final List<Double> doubleValues) {
    public static String doPrediction(final List<Double> doubleValues) {
        String className = null;

        Log.d(TAG, "Start the process for prediction");

        //if(mClassifier==null){

        //if (classifierMLPerceptron == null) {
        if(classifierRF == null) {
            Log.d(TAG, "Model not loaded successfully!");
           // Toast.makeText(CameraActivity.this, "Model not loaded successfully!", Toast.LENGTH_SHORT).show();
            return null;
        } else {

            // we need those for creating new instances later
            // order of attributes/classes needs to be exactly equal to those used for training

            final Attribute attributeMean_l = new Attribute("l_meal");
            final Attribute attributeMean_a = new Attribute("a_meal");
            final Attribute attributeMean_b = new Attribute("b_mean");
            final Attribute attributeStd_l = new Attribute("l_std");
            final Attribute attributeStd_a = new Attribute("a_std");
            final Attribute attributeStd_b = new Attribute("b_std");
            final Attribute attributeMode_l = new Attribute("l_mode");
            final Attribute attributeMode_a = new Attribute("a_mode");
            final Attribute attributeMode_b = new Attribute("b_mode");
            final Attribute attributeSkew_l = new Attribute("l_skew");
            final Attribute attributeSkew_a = new Attribute("a_skew");
            final Attribute attributeSkew_b = new Attribute("b_skew");
            final Attribute attributeEnergy_l = new Attribute("l_energy");
            final Attribute attributeEnergy_a = new Attribute("a_energy");
            final Attribute attributeEnergy_b = new Attribute("b_energy");
            final Attribute attributeEntropy_l = new Attribute("l_entropy");
            final Attribute attributeEntropy_a = new Attribute("a_entropy");
            final Attribute attributeEntropy_b = new Attribute("b_entropy");
            final List<String> classes = new ArrayList<String>() {
                {
                    add("tested_negative"); // cls nr 1
                    add("tested_positive"); // cls nr 2
                }

            };

            // Instances(...) requires ArrayList<> instead of List<>...
            ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
                {
                    add(attributeMean_l);
                    add(attributeMean_a);
                    add(attributeMean_b);
                    add(attributeStd_l);
                    add(attributeStd_a);
                    add(attributeStd_b);
                    add(attributeMode_l);
                    add(attributeMode_a);
                    add(attributeMode_b);
                    add(attributeSkew_l);
                    add(attributeSkew_a);
                    add(attributeSkew_b);
                    add(attributeEnergy_l);
                    add(attributeEnergy_a);
                    add(attributeEnergy_b);
                    add(attributeEntropy_l);
                    add(attributeEntropy_a);
                    add(attributeEntropy_b);
                    Attribute attributeClass = new Attribute("@@class@@", classes);
                    add(attributeClass);
                }
            };
            // unpredicted data sets (reference to sample structure for new instances)
            Instances dataUnpredicted = new Instances("TestInstances",
                    attributeList, 1);
            // last feature is target variable
            dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

            // create new instance: this one should fall into the setosa domain
            //final Sample s = mSamples[mRandom.nextInt(mSamples.length)];
            for (Double d : doubleValues) {
                Log.i(TAG, "Data of double temp " + doubleValues.indexOf(d) + " " + d);
            }
            DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
                {
                    setValue(attributeMean_l, doubleValues.get(0));
                    setValue(attributeMean_a, doubleValues.get(1));
                    setValue(attributeMean_b, doubleValues.get(2));
                    setValue(attributeStd_l, doubleValues.get(3));
                    setValue(attributeStd_a, doubleValues.get(4));
                    setValue(attributeStd_b, doubleValues.get(5));
                    setValue(attributeMode_l, doubleValues.get(6));
                    setValue(attributeMode_a, doubleValues.get(7));
                    setValue(attributeMode_b, doubleValues.get(8));
                    setValue(attributeSkew_l, doubleValues.get(9));
                    setValue(attributeSkew_a, doubleValues.get(10));
                    setValue(attributeSkew_b, doubleValues.get(11));
                    setValue(attributeEnergy_l, doubleValues.get(12));
                    setValue(attributeEnergy_a, doubleValues.get(13));
                    setValue(attributeEnergy_b, doubleValues.get(14));
                    setValue(attributeEntropy_l, doubleValues.get(15));
                    setValue(attributeEntropy_a, doubleValues.get(16));
                    setValue(attributeEntropy_b, doubleValues.get(17));
                }
            };

            Log.i(TAG, "Data of instance " + newInstance);

            //the following lines needed when read from file
			/*Instances test = new Instances(new BufferedReader(new FileReader("/mnt/sdcard/pictures/Labelled_TB_Dataset_10_Test.arff")));
			test.setClassIndex(test.numAttributes() - 1);

			//j48tree.buildClassifier(train);

			try {
				for (int i = 0; i < test.numInstances(); i++) {

					//double index = j48tree.classifyInstance(test.instance(i));
					double index = classifierMLPerceptron.classifyInstance(test.instance(i));
					//String className = train.attribute(lastIndex).value((int)index);
					String className = classes.get(new Double(index).intValue());
					String msg = "predicted: " + className;
					Log.d(WEKA_TEST, msg);
					Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

					//System.out.println(className);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
            // reference to dataset
            newInstance.setDataset(dataUnpredicted);

            // predict new sample
            try {
                //double result = classifierMLPerceptron.classifyInstance(newInstance);
                double result = classifierRF.classifyInstance(newInstance);
                className = classes.get(new Double(result).intValue());
                //String msg = "Nr: " + s.nr + ", predicted: " + className + ", actual: " + classes.get(s.label);
                String msg = "predicted: " + className;
                //prediction.add(className);
                Log.d(TAG, msg);
                //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return className;
        }
    }

    public static void drawResult(Mat result, List<double[]> coordinateVal, Mat src, int count, List<Double> contourArea) {
        /*boolean matExist = false;
        //for (int j = 0; j < coordinateVal.size()-1; j++) {
            //for (int k = j + 1; k < coordinateVal.size(); k++) {
        Mat newclassMat = new Mat();
        List<double[]> coordinateValueTest = new ArrayList<double[]>(coordinateVal);
        List<Integer> intTemp = new ArrayList<Integer>();

        //coordinateValueTest = coordinateVal;


        Log.i(TAG, "coordinate value size + contour size " + coordinateValueTest.size() + " " + contourArea.size());


        for (int j = 0; j < coordinateValueTest.size()-1; j++) {
            for (int k = j+1; k < coordinateValueTest.size(); k++) {
                if ((abs(coordinateValueTest.get(j)[0] - coordinateValueTest.get(k)[0]) < 20) && (abs(coordinateValueTest.get(j)[1] - coordinateValueTest.get(k)[1])) < 20) {
                    matExist = true;
                    if (contourArea.get(j) > contourArea.get(k)) {
                        coordinateValueTest.remove(k);
                        contourArea.remove(k);
                        intTemp.add(k);
                    } else {
                        coordinateValueTest.remove(j);
                        contourArea.remove(j);
                        intTemp.add(k);
                    }

                }
            }
        }
        Log.i(TAG, "coordinate value size + contour size after " + coordinateValueTest.size() + " " + contourArea.size());

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 1; j++) {

                if (intTemp.isEmpty() == true ) {
                    Mat classMat = new Mat(1, 1, CvType.CV_32S, new Scalar(result.get(i, j)[0]));
                    newclassMat.push_back(classMat);
                    Log.i(TAG, "new mat " + newclassMat.get(i, j)[0]);
                } else {
                    for (Integer iT : intTemp) {
                        Log.i(TAG, "Data of int temp " + intTemp.indexOf(iT) + " " + iT);
                        if (iT != i) {
                            Mat classMat = new Mat(1, 1, CvType.CV_32S, new Scalar(result.get(i, j)[0]));
                            newclassMat.push_back(classMat);
                            Log.i(TAG, "new mat " + newclassMat.get(i, j)[0]);
                        }
                        else {
                            count--;
                            Log.i(TAG, "count " + count);

                        }
                    }

                }

            }
        }

        // make a mat and draw something

        Mat m = Mat.zeros(src.size(), CvType.CV_8UC3);
        m.setTo(new Scalar(255,255,255));
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 1; j++) {
                Log.i(TAG, "Data of result " + newclassMat.get(i, j)[0]);
                double x_value = coordinateVal.get(i)[0];
                double y_value = coordinateVal.get(i)[1];
                Log.i(TAG, "I can get x & y " + x_value + " " + y_value);

                if (newclassMat.get(i, j)[0] == 1) {
                    Core.putText(m, "P", new Point(x_value, y_value), Core.FONT_HERSHEY_PLAIN, 2.2, new Scalar(48, 63, 159), 2);
                    Log.i(TAG, "I can get x & y here too " + x_value + " " +y_value);
                    //imwrite("/mnt/sdcard/DCIM/mResultImg" + i + ".jpg", m);//check

                }
                else {
                    Core.putText(m, "N", new Point(x_value, y_value), Core.FONT_HERSHEY_PLAIN, 2.2, new Scalar(159, 48,	91), 2);
                }

            }
        }*/


        // make a mat and draw something

        Mat m = Mat.zeros(src.size(), CvType.CV_8UC3);
        m.setTo(new Scalar(255,255,255));
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 1; j++) {
                Log.i(TAG, "Data of result " + result.get(i, j)[0]);
                double x_value = coordinateVal.get(i)[0];
                double y_value = coordinateVal.get(i)[1];
                Log.i(TAG, "I can get x & y " + x_value + " " +y_value);

                if (result.get(i, j)[0] == 1) {
                    Core.putText(m, "P", new Point(x_value, y_value), Core.FONT_HERSHEY_PLAIN, 2.2, new Scalar(48, 63, 159), 2);
                    Log.i(TAG, "I can get x & y here too " + x_value + " " +y_value);
                    //imwrite("/mnt/sdcard/DCIM/mResultImg" + i + ".jpg", m);//check

                }
                else {
                    Core.putText(m, "N", new Point(x_value, y_value), Core.FONT_HERSHEY_PLAIN, 2.2, new Scalar(159, 48,	91), 2);
                }

            }
        }

        Imgproc.resize(m, m, keepSize.size());
        //imwrite("/mnt/sdcard/DCIM/mResultImg.jpg", m);//check
        Imgproc.copyMakeBorder(m, m, 10, 10, 10, 10, Imgproc.BORDER_CONSTANT, new Scalar(48, 63, 159));

        // convert to bitmap:
        resultBitmap = Bitmap.createBitmap(m.cols(), m.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(m, resultBitmap);

        Log.d(TAG, "time processing finish");


    }

    /*private static List<Double> readFromFile(String fileName) {
        Scanner scan;
        List<Double> newList = new ArrayList<Double>();

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Directory_Feature/" );
            final File myFile = new File(dir, fileName + ".txt");
            scan = new Scanner(myFile);

            while(scan.hasNextDouble())
            {
                //Log.i(TAG,"double value" + scan.nextDouble());
                newList.add(scan.nextDouble());

            }
            scan.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return newList;
    }

    private static void writeToFile(String fileName, List<Double> doubleValues) {
        FileOutputStream fos = null;
        String body;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Directory_Feature/" );

            if (!dir.exists()) {
                if(!dir.mkdirs()) {
                    Log.e("ALERT","could not create the directories");
                }
            }

            final File myFile = new File(dir, fileName + ".csv");

            if (myFile.exists() && fileExist == true) {
                myFile.delete();
                myFile.createNewFile();
                fileExist = false;
            }


            fos = new FileOutputStream(myFile, true);
            OutputStreamWriter osw;

            for (Double d : doubleValues) {
                body = Double.toString(d);
                fos.write(body.getBytes());
                osw = new OutputStreamWriter(fos);
                osw.append(",");
                osw.flush();

            }
            // add a line
            osw = new OutputStreamWriter(fos);
            osw.append("\r\n");
            osw.flush();
            osw.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFileTitles(String fileName, List<String> stringValues) {
        FileOutputStream fos = null;
        String body;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Directory_Feature/" );

            if (!dir.exists()) {
                if(!dir.mkdirs()) {
                    Log.e("ALERT","could not create the directories");
                }
            }

            final File myFile = new File(dir, fileName + ".csv");

            if (myFile.exists() && fileExist == true) {
                myFile.delete();
                myFile.createNewFile();
                fileExist = false;
            }


            fos = new FileOutputStream(myFile, true);
            OutputStreamWriter osw;

            for (String d : stringValues) {
                body = d;
                fos.write(body.getBytes());
                osw = new OutputStreamWriter(fos);
                osw.append(",");
                osw.flush();

            }
            // add a line
            osw = new OutputStreamWriter(fos);
            osw.append("\r\n");
            osw.flush();
            osw.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile1(String fileName, List<Double> doubleValues) {
        //do not delete file for the dataset, just append to it
        Log.d("ALERT","I can reach here");

        FileOutputStream fos = null;
        String body;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Directory_Feature/" );

            if (!dir.exists()) {
                if(!dir.mkdirs()) {
                    Log.e("ALERT","could not create the directories");
                }
            }

            final File myFile = new File(dir, fileName + ".txt");

            if (myFile.exists()) {
                myFile.createNewFile();
            }


            fos = new FileOutputStream(myFile, true);
            OutputStreamWriter osw;

            for (Double d : doubleValues) {
                body = Double.toString(d);
                fos.write(body.getBytes());
                osw = new OutputStreamWriter(fos);
                osw.append(" ");
                osw.flush();

            }
            // add a line
            osw = new OutputStreamWriter(fos);
            osw.append("\r\n");
            osw.flush();
            osw.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("ALERT","I can reach here either");

    }*/

    //calc Euclidian distance to black color
    static double calcWhiteDist(double r, double g, double b){
        return Math.sqrt(Math.pow(255 - r, 2) +
                Math.pow(255 - g, 2) + Math.pow(255 - b, 2));
    }

    private static int calcScaleFactor(int rows, int cols){
        int idealRow, idealCol;
        if(rows<cols){
            idealRow = 240;
            idealCol = 320;
        } else {
            idealCol = 240;
            idealRow = 320;
        }
        int val = Math.min(rows / idealRow, cols / idealCol);
        if(val<=0){
            return 1;
        } else {
            Log.i(TAG, "scale factor" + val);
            return val;
        }
    }


    // Decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(String path) {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=300;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private static void writeToFile1(String fileName, List<Double> doubleValues) {
        //do not delete file for the dataset, just append to it
        Log.d("ALERT","I can reach here");

        FileOutputStream fos = null;
        String body;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Directory_Feature/" );

            if (!dir.exists()) {
                if(!dir.mkdirs()) {
                    Log.e("ALERT","could not create the directories");
                }
            }

            final File myFile = new File(dir, fileName + ".txt");

            if (myFile.exists()) {
                myFile.createNewFile();
            }


            fos = new FileOutputStream(myFile, true);
            OutputStreamWriter osw;

            for (Double d : doubleValues) {
                body = Double.toString(d);
                fos.write(body.getBytes());
                osw = new OutputStreamWriter(fos);
                osw.append(" ");
                osw.flush();

            }
            // add a line
            osw = new OutputStreamWriter(fos);
            osw.append("\r\n");
            osw.flush();
            osw.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("ALERT","I can reach here either");

    }

    private static void deleteFromFile(String fileName) {
        //do not delete file for the dataset, just append to it
        Log.d("ALERT", "I can reach here");

        FileOutputStream fos = null;
        String body;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Directory_Feature/");

            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e("ALERT", "could not create the directories");
                }
            }

            final File myFile = new File(dir, fileName + ".txt");


            //fos = new FileOutputStream(myFile, true);
            //OutputStreamWriter osw;
            RandomAccessFile f = new RandomAccessFile(myFile, "rw");
            long length = f.length() - 1;
            byte b;
            do {
                length -= 1;
                f.seek(length);
                b = f.readByte();
            } while (b != 10);
            f.setLength(length + 1);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        //try even this way to reduce memory
    /*public static Bitmap decodeSampledBitmapFromResource(Resources res, String resId, int    reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // use this kind of config to reduce memory consumption but this can reduce the quality of the image
        options.inPreferredConfig = Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeFile(resId, options);
    }*/

    @Override
    protected void onDestroy() {
        if (task != null) {
            task.cancel(true);
        }
        if(receivedImg!=null)
            {
                receivedImg.recycle();
                receivedImg=null;
            }

        if(resultBitmap!=null)
        {
            resultBitmap.recycle();
            resultBitmap=null;
        }

        imageViewDisplay.setImageBitmap(null);
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
    }

}
