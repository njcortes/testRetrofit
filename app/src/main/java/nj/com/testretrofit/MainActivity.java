package nj.com.testretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String  TAG = "MainActivity";
    private ImageView myImageView;
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPost();
    }

    public void myButton_click(View view){
        getPost();
    }

    private void getPost(){

        myImageView = findViewById(R.id.imageView);
        myTextView = findViewById(R.id.txtView);

        //https://dog.ceo/api/breeds/image/random
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breeds/image/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogInterface dogInterface = retrofit.create(DogInterface.class);
        Call<Dog> call = dogInterface.getPosts();


        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "CODIGO: "+response.code());
                    return;
                }

                Dog objDog = response.body();
                new DownloadImageFromInternet((ImageView) myImageView)
                        .execute(objDog.getMessage());
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                Log.d(TAG, "ERROR: " + t.getMessage());
            }

        });
    }


    /**
     *
     * Funcion para traer una foto y desplegarla en un ImageView
     * Funcion obtenida desde la siguinte URL:
     * https://www.viralandroid.com/2015/11/load-image-from-url-internet-in-android.html
     * @author viralandroid
     */

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), R.string.generatePictureWait, Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


}
