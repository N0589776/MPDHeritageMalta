package com.example.hayden.heritagemalta2;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.core.ViewFinderView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchActivityfrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchActivityfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchActivityfrag extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lstItems;
    ProgressBar prog;
    ArrayList<Museum> museums;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchActivityfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchActivityfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchActivityfrag newInstance(String param1, String param2) {
        SearchActivityfrag fragment = new SearchActivityfrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }











    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View rl = (RelativeLayout)inflater.inflate(R.layout.fragment_search_activityfrag,container, false);
       lstItems= (ListView)rl.findViewById(R.id.lstItems);
        prog = (ProgressBar)rl.findViewById(R.id.musProgressBar);
        prog.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);


         /*       ContentfulConnection conn = new ContentfulConnection("thing");

                conn.Initialise();
                try{ Thread.sleep(300); }catch(InterruptedException e){ }
                conn.testMultiContent();
                try{ Thread.sleep(700); }catch(InterruptedException e){ }
                museums = conn.Museums;
                Contentgot = true;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        ContentfulConnection conn = new ContentfulConnection();
        GetContentTask tsk = new GetContentTask(conn);
        tsk.execute();

        return rl;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSearchFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSearchFragmentInteraction(Uri uri);
    }

class GetContentTask extends AsyncTask<String,ArrayList<Museum>,ArrayList<Museum>>{
private ContentfulConnection conn;
    GetContentTask(ContentfulConnection c){

        this.conn = c;
    }
    @Override
    protected ArrayList<Museum> doInBackground(String... params) {



            conn.testMultiContent();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return conn.Museums;







    }

    @Override
    protected void onPostExecute(ArrayList<Museum> musea) {
        prog.setVisibility(View.GONE);
        MuseumListAdapter adapter = new MuseumListAdapter(getContext(),musea);
        try{lstItems.setAdapter(adapter);}
        catch(Exception e){

            Log.e("error line 195",e.toString());
        };
        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Museum value = (Museum)lstItems.getAdapter().getItem(position);

                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.putExtra("ITEM_EXTRA",value);
                intent.putExtra("ActivityType",1);
                getActivity().startActivity(intent);


            }
        });
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(ArrayList<Museum>... values) {
        prog.setVisibility(View.VISIBLE);
    }
}
}


