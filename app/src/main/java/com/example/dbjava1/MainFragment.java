package com.example.dbjava1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener, RecyclerviewAdapter.OnUserClickListener {
    RecyclerView recyclerView;
    EditText edtName, edtAge;
    Button btnsubmit;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<PersonBean> listPersonInfo;


    public MainFragment() {
        //Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        edtName = view.findViewById(R.id.edtname);
        edtAge = view.findViewById(R.id.edttage);
        btnsubmit = view.findViewById(R.id.btnsubmit);

        btnsubmit.setOnClickListener(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        listPersonInfo = databaseHelper.selectUserData();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(context, listPersonInfo, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnsubmit){
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            PersonBean currentPerson = new PersonBean();
            String btnstatus = btnsubmit.getText().toString();
            if (btnstatus.equals("Submit")){
                currentPerson.setName(edtName.getText().toString());
                currentPerson.setAge(Integer.parseInt(edtAge.getText().toString()));
                databaseHelper.insert(currentPerson);
            }
            if (btnstatus.equals("Update")){
                currentPerson.setName(edtName.getText().toString());
                currentPerson.setAge(Integer.parseInt(edtAge.getText().toString()));
                databaseHelper.update(currentPerson);
            }

            setupRecyclerView();
            edtName.setText("");
            edtAge.setText("");
            edtName.setFocusable(true);
            btnsubmit.setText("Submit");
        }
    }

    @Override
    public void onUserClick(PersonBean currentPerson, String action) {
        if (action.equals("Edit")){
            edtName.setText(currentPerson.getName());
            edtName.setFocusable(false);
            edtAge.setText(currentPerson.getAge()+"");
            btnsubmit .setText("Update");
        }
        if (action.equals("Delete")){
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.delete(currentPerson.getName());
            setupRecyclerView();
        }
    }
}
