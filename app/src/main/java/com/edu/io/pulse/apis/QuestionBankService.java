package com.edu.io.pulse.apis;

import com.edu.io.pulse.apis.models.Question;
import com.edu.io.pulse.apis.models.Score;
import com.edu.io.pulse.apis.models.Sets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionBankService {
    @GET("/api/qSets/list/{examid}")
    Call<List<Sets>> getSets(@Path("examid") Long examid);

    @GET("/api/questions/getQuestionsBySet/{setid}")
    Call<List<Question>> getQuestionBySet(@Path("setid") Long setid);

    @POST("/api/scoreBoards/submit")
    Call<Void> submitScore(@Body List<Score> scores);

    @GET("/api/auth/login")
    Call<Void> login(@Query("username") String username, @Query("password") String password);

}
