package com.edu.io.pulse.ui.quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.utils.Database;
import java.util.List;

public class QuizViewModel extends ViewModel {

    // Private MutableLiveData that can be changed within the ViewModel
    private final MutableLiveData<List<QuizQuestion>> _questions = new MutableLiveData<>();
    private final MutableLiveData<String> _error = new MutableLiveData<>();

    // Public LiveData that is immutable and can be observed by the UI
    public LiveData<List<QuizQuestion>> getQuestions() {
        return _questions;
    }

    public LiveData<String> getError() {
        return _error;
    }

    // Method to fetch data. The UI will call this.
    public void fetchQuestionsBySet(Long setId) {
        // Use the Database class to make the network call
        Database.getQuestionBySet(setId, new Database.BackendCallback<List<QuizQuestion>>() {
            @Override
            public void onReceived(List<QuizQuestion> questions) {
                // Post the value to LiveData. This is safe to call from any thread.
                _questions.postValue(questions);
            }

            @Override
            public void onError(Throwable t) {
                _error.postValue("Failed to load questions: " + t.getMessage());
            }
        });
    }
}

