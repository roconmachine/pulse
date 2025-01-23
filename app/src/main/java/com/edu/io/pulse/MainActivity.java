package com.edu.io.pulse;

import android.os.Bundle;
import android.view.Menu;

import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.utils.Database;
import com.edu.io.pulse.utils.Util;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.edu.io.pulse.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,  R.id.nav_quiz_list)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Database.setQuestions(readQuestionsFromJson());
    }

    private List<QuizQuestion> readQuestionsFromJson()  {

        List<QuizQuestion> questions = new ArrayList<>();
        String jsonString = Util.getAssetJsonData(getApplicationContext());
        try {
            JSONArray array = new JSONArray(jsonString);
            QuizQuestion question;
            for (int i =0; i < array.length(); i++){
                JSONObject ques = array.getJSONObject(i);
                question = new QuizQuestion();
                question.setId(ques.optInt("id"));
                question.setQuestion(ques.optString("q"));
                question.setAnswer(ques.optInt("answer"));
                JSONArray options = ques.optJSONArray("options");
                String[] optionString = new String[4];
                for (int j =0; j < options.length(); j++){
                    optionString[j] = options.optString(j);
                }
                question.setOptions(optionString);

                questions.add(question);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return questions;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}