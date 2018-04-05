package com.example.namikkaya.lottoservicetr_android.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.namikkaya.lottoservicetr_android.R;

/**
 * Fragment kontrollünde ki ilk görüntülenen sayfa.
 */
public class mainPageFragment extends Fragment {
    private final String TAG = "mainPage";


    private mainPageDelegate mListener;

    public mainPageFragment() {
        // Required empty public constructor
    }

    public static mainPageFragment newInstance() {
        mainPageFragment fragment = new mainPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        Button sayisalLotoButton = (Button) view.findViewById(R.id.sayisalLotoButton);
        sayisalLotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSelectedLottoType(0);
            }
        });

        Button superLotoButton = (Button) view.findViewById(R.id.superLotoButton);
        superLotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSelectedLottoType(1);
            }
        });

        Button myLuckyNumberButton = (Button) view.findViewById(R.id.myLuckyNumbersButton);
        myLuckyNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.mainPageGoMyLuckyNumbers();
                }
            }
        });

        return view;
    }

    public void sendSelectedLottoType(int _type) {
        if (mListener != null) {
            mListener.mainPageSelectedTypeLotto(_type);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof mainPageDelegate) {
            mListener = (mainPageDelegate) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " mainPageDelegate implement eklemelisin");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Delegate
     */
    public interface mainPageDelegate {
        void mainPageSelectedTypeLotto(int lType);
        void mainPageGoMyLuckyNumbers ();
    }
}
