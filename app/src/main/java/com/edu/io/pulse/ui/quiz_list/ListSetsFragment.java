package com.edu.io.pulse.ui.quiz_list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edu.io.pulse.R;
import com.edu.io.pulse.core.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListSetsFragment extends Fragment {

    static String ARG_COLUMN_COUNT = "1";
     int mColumnCount;
    List<SetsDomain> setsDomainList;
    public ListSetsFragment() {
        setsDomainList = new ArrayList<>(0);
        setsDomainList.add(new SetsDomain("set 11"));
        setsDomainList.add(new SetsDomain("set 13"));
        setsDomainList.add(new SetsDomain("set 14"));

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListSetsFragment newInstance(int columnCount) {
        ListSetsFragment fragment = new ListSetsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new SetsRecyclerViewAdapter(setsDomainList, (viewHolder, position, item) -> {
//                Toast.makeText(context, ((SetsDomain)item).getSetName(), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putInt("set_index", position);
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_nav_quiz_list_to_quiz_main);
            }));
        }

        return view;
    }


}