package com.example.as9221.kmeancluster;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.example.as9221.kmeancluster.models.NavBarItem;

//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.LoaderCallbackInterface;
//import org.opencv.android.OpenCVLoader;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
	private DrawerLayout drawer;
	Uri outputFileUri;
	ImageView screenImg;

	protected static final String TAG = null;

	private static int REQUEST_CAMERA = 0;
	private static int RESULT_LOAD_IMAGE = 1;

	/*static {

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
	}*/


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.CAMERA};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

		if (savedInstanceState != null)
		{
			outputFileUri= savedInstanceState.getParcelable("outputFileUri");
		}

        //inflateViews();
        //PicasaClient.get().attachActivity(this);
        //instruction = (TextView) findViewById(R.id.instMessage);
        TextView title = (TextView) findViewById(R.id.Title);
		if (Build.VERSION.SDK_INT >= 24) {
			title.setText(Html.fromHtml(getString(R.string.app_title),Html.FROM_HTML_MODE_LEGACY));
			// for 24 api and more
		} else {
			title.setText(Html.fromHtml(getString(R.string.app_title)));
		}

        screenImg = (ImageView) findViewById(R.id.mainscreenimg);
        final Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadImage);
        final Button buttonTakeImage = (Button) findViewById(R.id.buttonTakePicture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //cImageView = (CircleImageView) findViewById(R.id.circleImageView);
        setSupportActionBar(toolbar);

        FloatingActionButton fabFeedback = (FloatingActionButton) findViewById(R.id.fab_feedback);
		fabFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("mailto:")); // only email apps should handle this
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email)});
				intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
				intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivity(Intent.createChooser(intent, getString(R.string.title_send_feedback)));
				}

            }
        });

		FloatingActionButton fabHelp = (FloatingActionButton) findViewById(R.id.fab_help);
		//fabHelp.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.help_icon));

		fabHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				startActivity(new Intent(MainActivity.this, HelpActivity.class));

			}
		});


		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setNavBarButtons();


		buttonTakeImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				boolean permission_check = hasPermissions(MainActivity.this, Manifest.permission.CAMERA);
				Log.d(TAG, "I can be here" );

				if (permission_check) {
                    Log.i(TAG, "permission granted");

					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (takePictureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
						File exportDir = new File(Environment.getExternalStorageDirectory(), "TempFolder");
						if (!exportDir.exists()) {
							exportDir.mkdirs();
						}
						File mTempCameraPhotoFile = new File(exportDir, "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
						Log.d(TAG, "path to file: " + "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
						outputFileUri = Uri.fromFile(mTempCameraPhotoFile);
						takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
						startActivityForResult(takePictureIntent, REQUEST_CAMERA);
					}

					} else {
                    // give message here
                    Toast.makeText(MainActivity.this, "Camera permission has not granted!. You can not use this function", Toast.LENGTH_SHORT).show();
                    buttonTakeImage.setEnabled(false);
                    buttonTakeImage.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.divider_color));
                }
            }
		});

		buttonLoadImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
                boolean permission_check = hasPermissions(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission_check) {


                    Intent i = new Intent(
							Intent.ACTION_PICK,
							MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(i, RESULT_LOAD_IMAGE);
				}
                else {
                    // give message here
                    Toast.makeText(MainActivity.this, "Access files and resources permission has not granted!. You can not use this function", Toast.LENGTH_SHORT).show();
                    buttonLoadImage.setEnabled(false);
                    buttonLoadImage.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.divider_color));
                }
            }
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Intent intent = new Intent(MainActivity.this,CameraActivity.class);
			intent.putExtra("imagePath", picturePath);
			intent.putExtra("Title", "Load_Image");
			Log.i(TAG, picturePath);
			startActivity(intent);

        }
        else {
			if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {

				Intent intent = new Intent(MainActivity.this, CameraActivity.class);
				//intent.putExtra("ID_EXTRA", new String[] { bytes.toByteArray(), "Load_Image"});
				intent.putExtra("imageUri", outputFileUri);
				intent.putExtra("Title", "Take_Image");
				startActivity(intent);

			}
		}
  	}


    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.select_account) {
            //PicasaClient.get().pickAccount();
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private void setNavBarButtons() {
        for (NavBarItem item : NavBarItem.values()) {
            TextView itemView = (TextView) findViewById(item.getItemId());
            itemView.setOnClickListener(this);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (NavBarItem.fromViewId(v.getId())) {

            case TERMS:
				drawer.closeDrawer(GravityCompat.START);
				startActivity(new Intent(MainActivity.this, TermsActivity.class));
                break;

            case ABOUTUS:
				drawer.closeDrawer(GravityCompat.START);
				startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
				break;

            case HELP:
                drawer.closeDrawer(GravityCompat.START);
				startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;

            case CHECKSYMPTOM:
				drawer.closeDrawer(GravityCompat.START);
				startActivity(new Intent(MainActivity.this, CheckSymptomActivity.class));
                break;

			/*case PROFESSIONAL:
				drawer.closeDrawer(GravityCompat.START);
				startActivity(new Intent(MainActivity.this, RiskActivity.class));
				break;*/
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putParcelable("outputFileUri", outputFileUri);
	}

	@Override
	protected void onDestroy() {
		//finish();
		screenImg = null;
		drawer = null;
		outputFileUri = null;
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}
}


