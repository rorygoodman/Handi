package com.app.handi.handi.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.handi.handi.Activitys.ChooseHandiTypeActivity;
import com.app.handi.handi.Activitys.ViewJobDescriptionActivity;
import com.app.handi.handi.Adapters.DisplayUserJobsAdapter;
import com.app.handi.handi.DataTypes.HandimanData;
import com.app.handi.handi.DataTypes.Job;
import com.app.handi.handi.DataTypes.Quote;
import com.app.handi.handi.Firebase.HelperQuote;
import com.app.handi.handi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    ListView list;
    HandimanData handimanData;
    private ProgressBar progressBar;
    int jobState;
    DatabaseReference db;
    FirebaseUser user;
    HelperQuote helperQuote;
    android.support.design.widget.FloatingActionButton newJobButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DisplayUserJobsAdapter adapter;
    private static ArrayList<Job> job = new ArrayList<>();
    private ArrayList<Job> jobs = new ArrayList<>();
    Job jobq;
    ArrayList<Quote> quotes = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2,ArrayList<Job> jobs) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        job=jobs;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.home_frag_progressBar);
        list = (ListView) view.findViewById(R.id.fragment_home_list_view_user_jobs);
        for(int i=0;i<job.size();i++){
            if(job.get(i).getStatus().equals("Incomplete"))
                jobs.add(job.get(i));
        }
        helperQuote = new HelperQuote(db);
        //Button for creating new job
        newJobButton = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fragment_home_floating_action_button_fab);
        newJobButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getActivity(), ChooseHandiTypeActivity.class));
            }
        });
        adapter = new DisplayUserJobsAdapter(jobs,getActivity());
        //set the list of user jobs
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                jobq = (Job) view.getTag();
                db.child("HandiMen").child(jobq.getHandiUid()).child("Info").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        handimanData = dataSnapshot.getValue(HandimanData.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                quotes=helperQuote.retrieve(user,jobq);
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), ViewJobDescriptionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Handi",handimanData);
                bundle.putSerializable("LeJob",jobq);
                bundle.putSerializable("Quotes",quotes);
                intent.putExtras(bundle);
                if(jobq.isAccepted()&&!jobq.getQuoteAccepted()) {
                    jobState = 2;
                    intent.putExtra("ViewButton",jobState );
                }
                else if(jobq.isAccepted()&&jobq.getQuoteAccepted()){
                    jobState=1;
                    intent.putExtra("ViewButton",jobState);
                }
                else{
                    jobState =0;
                    intent.putExtra("ViewButton",jobState);
                }
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {

    }

}
