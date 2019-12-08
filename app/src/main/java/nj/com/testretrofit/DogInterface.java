package nj.com.testretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogInterface {

    @GET("random")
    Call<Dog> getPosts();

}
