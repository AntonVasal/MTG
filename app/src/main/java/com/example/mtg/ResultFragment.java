package com.example.mtg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ResultFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        
        //Create tasks spinner
        Spinner taskSpinner = view.findViewById(R.id.kind_of_task_spinner);
        //Make and set adapter
        ArrayAdapter<?> taskAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.types_of_task, android.R.layout.simple_spinner_item);
        taskAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        taskSpinner.setAdapter(taskAdapter);

        //Create number types spinner
        Spinner numberSpinner = view.findViewById(R.id.kind_of_number_spinner);
        //Make and set adapter
        ArrayAdapter<?> numberAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.types_of_number, android.R.layout.simple_spinner_item);
        numberAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        numberSpinner.setAdapter(numberAdapter);

        return view;
    }
}