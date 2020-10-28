package ke.co.corellia.psklok;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class uploady extends AppCompatActivity {
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    Uri imageUri;

    ProgressDialog dialog = null;
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;

    String upLoadServerUri = null;

    /**********  File Path *************/

   // final String uploadFileName = "service_lifecycle.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploady);

        uploadButton = (Button)findViewById(R.id.uploadButton);
        messageText  = (TextView)findViewById(R.id.messageText);
        LazyInitializedSingletonExample lz;
        lz=LazyInitializedSingletonExample.getInstance();




        /************* Php script path ****************/
        upLoadServerUri = "https://www.psklok.com/klok/fileUpload.php";

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(new File(getApplicationContext().getExternalFilesDir(null), "fname_" +
                        String.valueOf(System.currentTimeMillis()) + ".jpg"));


                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);




                Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);




                photoPickerIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
                photoPickerIntent.putExtra("outputX", 100);

                photoPickerIntent.putExtra("outputY", 100);
                photoPickerIntent.putExtra("aspectX", 1);
                photoPickerIntent.putExtra("android.intent.extras.QUALITY_HIGH", 0);
                photoPickerIntent.putExtra("aspectY", 1);
                photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

             //   photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());


                if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(photoPickerIntent, 1);
                }

               // startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);


            }

        });
    }

    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }
    private File getTempFile() {
        String TEMP_PHOTO_FILE = "temporary_holder.jpg";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(getApplicationContext().getExternalFilesDir(null),TEMP_PHOTO_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {}

            return file;
        } else {

            return null;
        }
    }


    public Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        Bitmap bitmap =BitmapFactory.decodeFile(sourceFileUri);

        int maxHeight = 200;
        int maxWidth = 200;
        float scale = Math.min(((float)maxHeight / bitmap.getWidth()), ((float)maxWidth / bitmap.getHeight()));

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        storeImage(bitmap);




        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"
                    + sourceFileUri);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :"
                             + "" + sourceFileUri);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                /*
                BitmapFactory.decodeFile(imageUri.getPath());

                Bitmap b=getResizedBitmap(BitmapFactory.decodeFile(imageUri.getPath()),34,3);

                OutputStream od = new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {

                    }
                };
                b.compress(Bitmap.CompressFormat.JPEG,80,od);*/








                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + fileName + " + lineEnd");

                   dos.writeBytes(lineEnd);

                   // create a buffer of  maximum size
                   bytesAvailable = fileInputStream.available();



                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   buffer = new byte[bufferSize];

                   // read file and write it into form...
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                   while (bytesRead > 0) {

                     dos.write(buffer, 0, bufferSize);
                     bytesAvailable = fileInputStream.available();
                     bufferSize = Math.min(bytesAvailable, maxBufferSize);
                     bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    }

                   // send multipart form data necesssary after file data...
                   dos.writeBytes(lineEnd);
                   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                   // Responses from the server (code and message)
                   serverResponseCode = conn.getResponseCode();
                   String serverResponseMessage = conn.getResponseMessage();

                   Log.i("uploadFile", "HTTP Response is : "
                           + serverResponseMessage + ": " + serverResponseCode);

                   if(serverResponseCode == 200){

                       runOnUiThread(new Runnable() {
                            public void run() {

                                String msg = "File Upload Completed.";

                                messageText.setText(msg);
                                Toast.makeText(uploady.this, "File Upload Complete.",
                                             Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                   }

                   //close the streams //
                   fileInputStream.close();
                   dos.flush();
                   dos.close();

              } catch (MalformedURLException ex) {

                  dialog.dismiss();
                  ex.printStackTrace();

                  runOnUiThread(new Runnable() {
                      public void run() {
                          messageText.setText("MalformedURLException Exception : check script url.");
                          Toast.makeText(uploady.this, "MalformedURLException",
                                                              Toast.LENGTH_SHORT).show();
                      }
                  });

                  Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
              } catch (Exception e) {

                  dialog.dismiss();
                  e.printStackTrace();

                  runOnUiThread(new Runnable() {
                      public void run() {
                          messageText.setText("Got Exception : see logcat ");
                          Toast.makeText(uploady.this, "Got Exception : see logcat ",
                                  Toast.LENGTH_SHORT).show();
                      }
                  });
                  Log.e("Upload file ", "Exception : "
                                                   + e.getMessage(), e);
              }
              dialog.dismiss();
              return serverResponseCode;

           } // End else block
         }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


       // Toast.makeText(this,imageUri.toString(),Toast.LENGTH_LONG).show();
        messageText.setText(imageUri.toString());







        dialog = ProgressDialog.show(uploady.this, "", "Uploading file...", true);

        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("uploading started.....");
                    }
                });

                uploadFile(imageUri.getPath());

            }
        }).start();







    }


    private void storeImage(Bitmap image) {


        File pictureFile =new File(imageUri.toString());
        if (pictureFile == null) {
            Log.d("s", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageUri.getPath());
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("e", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("d", "Error accessing file: " + e.getMessage());
        }
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(getApplicationContext().getExternalFilesDir(null)
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


}