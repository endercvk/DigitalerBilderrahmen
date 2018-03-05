package haw_landshtu.de.digitalerbilderrahmen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    LinkedList<ImageObject> images = new LinkedList<>();
    String TAG = "";
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        permissionTMP();

        getFiles();
        initPager();


        uiChanges();



    }



    private void getFiles(){


        ImageView image = new ImageView(MainActivity.this);

        // which image properties are we querying
        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };

        // WHERE folder = blablabal
        String selection = "bucket_display_name = 'WhatsApp Images'";


        Cursor mCursor = getContentResolver()
                .query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        selection,
                        null,
                        MediaStore.Images.Media.DATE_ADDED+" DESC");

        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()) {
            String imageuri;
            String bucketname;
            String imageid;
            String imagedate;

            int imageidColumn = mCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int imageuriColumn = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int bucketColumn = mCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int imagedateColumn = mCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);

            imageid = mCursor.getString(imageidColumn);
            imageuri = mCursor.getString(imageuriColumn);
            bucketname = mCursor.getString(bucketColumn);
            imagedate = mCursor.getString(imagedateColumn);

            Log.d(TAG,
                    " XX BucketName = " + bucketname
                            + " || ImageID = " + imageid
                            + " || ImageDate = " + imagedate
                            + " || ImageUri = " + imageuri
            );


            ImageObject imageObject = new ImageObject(imageid,bucketname,imagedate,imageuri);

            images.add(imageObject);



            mCursor.moveToNext();

        }
        mCursor.close();
    }



    private void permissionTMP(){

        //FIXME handle response
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            SystemClock.sleep(10000);

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


    protected void initPager(){

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(1);

        mPager.setAdapter(new SlideShowAdapter(MainActivity.this,images));






    }

    protected void playtimer(){
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {





                handler.post(Update);
            }
            // Delay = delay before task is executed
            // Period = between successive task execution
        }, 3000, 10000);



    }



    private void uiChanges(){
        //Removes action bar
        getSupportActionBar().hide();

        //Removes status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Keeps screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    //https://developer.android.com/guide/topics/ui/menus.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mainMenuPlay:

                // FIXME Runs non-stop,
                    playtimer();

                return true;


            case R.id.mainMenuSettings:

                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



