package com.example.namikkaya.lottoservicetr_android.Fragment;

import android.content.Context;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namikkaya.lottoservicetr_android.R;
import com.example.namikkaya.lottoservicetr_android.model.dateData;
import com.example.namikkaya.lottoservicetr_android.model.lottoResultData;
import com.example.namikkaya.lottoservicetr_android.model.lottoType;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 */
public class checkFragment extends Fragment {
    private final String TAG = "checkFragment";

    private static final String ARG_PARAM1 = "param1";

    private dateData mParam1;

    private checkFragmentDelegate mListener;
    private TextView dateText;
    private TextView moneyText;
    private TextView luckyNumbersText;
    private Button myLuckyNumbersAddButton;

    // Şanslı sayıları tutar
    private lottoResultData item;

    private String pathBody = "http://www.millipiyango.gov.tr/sonuclar/cekilisler/";
    final Handler handler = new Handler();

    public checkFragment() {
        // Required empty public constructor
    }

    public static checkFragment newInstance(dateData param1) {
        checkFragment fragment = new checkFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (dateData) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);

        dateText = (TextView) view.findViewById(R.id.dateText);
        moneyText = (TextView) view.findViewById(R.id.moneyText);
        luckyNumbersText = (TextView) view.findViewById(R.id.luckyNumbersText);
        myLuckyNumbersAddButton = (Button) view.findViewById(R.id.myLuckyNumbersButton);

        myLuckyNumbersAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast= Toast.makeText(getActivity(),
                        "Numaralar Kaydedildi. Bol Şanslar", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
                addedDBLuckyNumber(item.getRakamlarNumaraSirasi());
            }
        });

        TimerTask serviceTimer = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getServiceData();
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(serviceTimer, 300);
        //getServiceData();
        return view;
    }

    private void getServiceData(){
        HashMap<String, String> postData = new HashMap<String, String>();
        if (lottoType.getLottoTypeHolder() == 0) {
            pathBody += "sayisal/"+mParam1.getTarih()+".json";
        }else{
            pathBody += "superloto/"+mParam1.getTarih()+".json";
        }

        PostResponseAsyncTask task1 = new PostResponseAsyncTask(getActivity(), postData, "Lütfen Bekleyiniz", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.matches("")){
                    Toast toast= Toast.makeText(getActivity(),
                            "Çok meşgulüz. Lütfen daha sonra tekrar deneyiniz.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }

                try {
                    JSONObject lotoData = new JSONObject(s);
                    JSONObject data= lotoData.getJSONObject("data");
                    String rakamlarSiraNumarasi = data.getString("rakamlarNumaraSirasi");
                    String rakamlar = data.getString("rakamlar");
                    String cekilisTarihi = data.getString("cekilisTarihi");
                    Double buyukIkramiye = data.getDouble("buyukIkramiye");

                    item = new lottoResultData(rakamlarSiraNumarasi,rakamlar,buyukIkramiye);

                    dateText.setText(cekilisTarihi);
                    moneyText.setText(doubleToMoney(buyukIkramiye)+ " ₺");
                    luckyNumbersText.setText(rakamlarSiraNumarasi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task1.execute(pathBody);
    }

    private String doubleToMoney(double dbl) {
        Locale locale = new Locale("tr", "TR");
        DecimalFormatSymbols sym = new DecimalFormatSymbols(locale);
        sym.setGroupingSeparator('.');
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern("##,###.00");
        decimalFormat.setDecimalFormatSymbols(sym);
        return decimalFormat.format(dbl);
    }

    public void addedDBLuckyNumber(String numbers) {
        if (mListener != null) {
            mListener.checkFragmentSaveDB(numbers);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof checkFragmentDelegate) {
            mListener = (checkFragmentDelegate) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement checkFragmentDelegate");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // delegate
    public interface checkFragmentDelegate {
        void checkFragmentSaveDB(String numbers);
    }
}
