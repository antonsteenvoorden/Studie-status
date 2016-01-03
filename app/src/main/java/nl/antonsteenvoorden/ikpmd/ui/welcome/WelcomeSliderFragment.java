package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.antonsteenvoorden.ikpmd.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WelcomeSliderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WelcomeSliderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeSliderFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private int count = 0;

    List<ImageView> steps;
    @Bind(R.id.slider_step1) ImageView sliderStep1;
    @Bind(R.id.slider_step2) ImageView sliderStep2;
    @Bind(R.id.slider_step3) ImageView sliderStep3;

    public WelcomeSliderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeSliderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeSliderFragment newInstance() {
        WelcomeSliderFragment fragment = new WelcomeSliderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_slider, container, false);
        ButterKnife.bind(this, view);

        steps = new ArrayList<>();
        steps.add(sliderStep1);
        steps.add(sliderStep2);
        steps.add(sliderStep3);

        return view;
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

    @OnClick(R.id.next)
    void nextStep(View view) {
        count = mListener.nextStep();
        swapSliderIcon();
    }

    private void swapSliderIcon() {
        ImageView oldIcon = steps.get(count-1);
        ImageView newIcon = steps.get(count);
        LinearLayout.LayoutParams oldParams = (LinearLayout.LayoutParams) oldIcon.getLayoutParams();
        LinearLayout.LayoutParams newParams = (LinearLayout.LayoutParams) newIcon.getLayoutParams();

        oldIcon.setImageResource(R.drawable.slider_progress);
        oldIcon.setLayoutParams(newParams);

        newIcon.setImageResource(R.drawable.slider_progress_active);
        newIcon.setLayoutParams(oldParams);
    }

    public void updateSliderIcon(int position) {
        count = position;
        swapSliderIcon();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        int nextStep();
    }
}
