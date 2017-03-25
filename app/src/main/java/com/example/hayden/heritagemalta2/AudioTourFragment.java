package com.example.hayden.heritagemalta2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioTourFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioTourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioTourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
AudioPlayer ap;

    boolean isPaused = true;
    Button PlayButton;
    Button StopButton;
    TextView AudioTitle;
    TextView AudioDesc;
    ImageView AudioImage;
    ProgressBar prog;
    private OnFragmentInteractionListener mListener;

    public AudioTourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioTourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioTourFragment newInstance(String param1, String param2) {
        AudioTourFragment fragment = new AudioTourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        // Inflate the layout for this fragmenttry{
        //Gets the QR string from the QRScanner
        String strText = getArguments().getString("qrResult");
        View scrl = inflater.inflate(R.layout.fragment_audio_tour,container,false);
        prog = (ProgressBar)scrl.findViewById(R.id.audProgressBar);
        //Establishes a new connection to the CMS
        ContentfulConnection conn = new ContentfulConnection(strText);
        //Runs the Async Task to get the Audio Tour content
        GetContentTask tsk = new GetContentTask(conn);
        tsk.execute();

        //Binds UI Elements
        PlayButton = (Button)scrl.findViewById(R.id.btnPlay);
        AudioDesc  = (TextView)scrl.findViewById(R.id.txtAudioDesc);
        AudioTitle = (TextView)scrl.findViewById(R.id.txtAudioTitle);
        AudioImage = (ImageView)scrl.findViewById(R.id.audioTourPic);
        StopButton = (Button)scrl.findViewById(R.id.btnStop);


        return scrl;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAudioTourFragmentInteraction(uri);
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
        void onAudioTourFragmentInteraction(Uri uri);
    }
    //Controller for the AudioPlayer UI
    public void PlayButtonClicked(View view){

        if(isPaused){

            Play();
            try{ Thread.sleep(300); }catch(InterruptedException e){ }
            PlayButton.setText("Pause");
            isPaused = false;
        }
        else{
            Pause();
            try{ Thread.sleep(300); }catch(InterruptedException e){ }
            PlayButton.setText("Play");
            isPaused = true;

        }

    }
    public void Play() {

        ap.StartAudio();

    }
    public void Pause(){

        ap.PauseAudio();
    }

    public void Stop(View view){
        isPaused = true;
        PlayButton.setText("Play");
        ap.StopAudio();
    }
    class GetContentTask extends AsyncTask<String,AudioTour,AudioTour>{
        private ContentfulConnection conn;
        GetContentTask(ContentfulConnection c){

            this.conn = c;
        }
        @Override
        protected AudioTour doInBackground(String... params) {

            AudioTour at = new AudioTour();

            conn.testGetContent();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(CheckTour(conn.at)){

                at = conn.at;
                return at;
            }
           
            return null;







        }

        @Override
        protected void onPostExecute(AudioTour at) {
            if(CheckTour(at)){
                prog.setVisibility(View.GONE);
                ap = new AudioPlayer(at.AudioTourURL);
                AudioTitle.setText(at.AudioTourTitle);
                AudioDesc.setText(at.AudioTourText);
                //Populates the AudioTour image in the UI
                Picasso.with(getContext()).load(at.AudioTourImageURL).into(AudioImage);
                Log.i("AudioPlayer",at.AudioTourURL);
                AudioDesc.setMovementMethod(new ScrollingMovementMethod());

                PlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isPaused){

                            Play();
                            try{ Thread.sleep(300); }catch(InterruptedException e){ }
                            PlayButton.setText("Pause");
                            isPaused = false;
                        }
                        else{
                            Pause();
                            try{ Thread.sleep(300); }catch(InterruptedException e){ }
                            PlayButton.setText("Play");
                            isPaused = true;

                        }
                    }
                });
                StopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPaused = true;
                        PlayButton.setText("Play");
                        ap.StopAudio();
                    }
                });
            }
            else{
                Toast.makeText(getContext(), "No Content Found", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),MainActivity.class);
                getActivity().startActivity(intent);
                
            }
            

            };

        @Override
        protected void onProgressUpdate(AudioTour... values) {
            prog.setVisibility(View.VISIBLE);
        }
    }
//Checks to see if an audio tour has any null values in it
public boolean CheckTour(AudioTour a){
if(a != null){if(a.AudioTourTitle == null || a.AudioTourText == null || a.AudioTourURL == null || a.AudioTourImageURL == null){

    return false;
}
else{
    return true;
}}
    else{
    return false;
}

}
    }



