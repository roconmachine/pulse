package com.edu.io.pulse.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.edu.io.pulse.R;
import com.edu.io.pulse.databinding.FragmentHomeBinding;
import com.edu.io.pulse.databinding.FragmentQuizStartBinding;
import com.edu.io.pulse.utils.Database;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizStartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizStartFragment extends Fragment {
    private static final String ARG_PARAM1 = "set_no";
    private static final String ARG_PARAM2 = "param2";
    private int set;

    private FragmentQuizStartBinding binding;

    public QuizStartFragment() {
        // Required empty public constructor
    }

    public static QuizStartFragment newInstance(int set) {
        QuizStartFragment fragment = new QuizStartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, set);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            set = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button myButton = view.findViewById(R.id.btn_start);

        myButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_PARAM1, set);
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_quizStartFragment_to_quiz_main, bundle);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentQuizStartBinding.inflate(inflater, container, false);
        binding.setName.setText(Database.getSetName(set));

        return binding.getRoot();
    }
}