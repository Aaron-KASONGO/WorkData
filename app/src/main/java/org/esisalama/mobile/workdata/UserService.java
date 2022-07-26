package org.esisalama.mobile.workdata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/users/{user_id}")
    Call<User> getUser(@Path("user_id") int id);
}
