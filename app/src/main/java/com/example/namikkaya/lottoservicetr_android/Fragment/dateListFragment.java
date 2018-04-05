package com.example.namikkaya.lottoservicetr_android.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.namikkaya.lottoservicetr_android.R;
import com.example.namikkaya.lottoservicetr_android.adapters.dateListAdapter;
import com.example.namikkaya.lottoservicetr_android.model.dateData;
import com.example.namikkaya.lottoservicetr_android.model.lottoType;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * listeleme sayfası
 */
public class dateListFragment extends Fragment implements AdapterView.OnItemClickListener{
    private final String TAG = "dateList";

    private static final String in_param = "in_param";

    private String m_inParam;
    private dateListDelegate mListener;

    private ListView listView;
    private dateListAdapter dlAdapter;
    private List<dateData> dlList;

    private String path = "http://www.millipiyango.gov.tr/sonuclar/listCekilisleriTarihleri.php";
    private Context context;

    final Handler handler = new Handler();

    public dateListFragment() {
        // Required empty public constructor
    }

    /**
     * @param inParam Parameter 1.
     * @return newInstance.
     */
    public static dateListFragment newInstance(String inParam) {
        dateListFragment fragment = new dateListFragment();
        Bundle args = new Bundle();
        args.putString(in_param, inParam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            m_inParam = getArguments().getString(in_param);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_list, container, false);

        listView = (ListView) view.findViewById(R.id.dateDatalistView);
        dlList = new ArrayList<dateData>();

        dlAdapter = new dateListAdapter(getContext(),dlList);
        listView.setAdapter(dlAdapter);
        listView.setOnItemClickListener(this);

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
        return view;
    }

    // event dispach => root Activity(MainActivity)
    public void sendSelectedDate(dateData item) {
        if (mListener != null) {
            mListener.selectedDateData(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        if (context instanceof dateListDelegate) {
            mListener = (dateListDelegate) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " ekle dateListDelegate");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dlList.removeAll(dlList);
        dlList = null;
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * interface
     */
    public interface dateListDelegate {
        void selectedDateData(dateData data);
    }

    /**
     * Tarihlere göre yapılmış çekilişleri çeker
     */
    public void getServiceData(){
        HashMap<String, String> postData = new HashMap<String, String>();
        if (lottoType.getLottoTypeHolder() == 0) {
            postData.put("tur", "sayisal");
        }else{
            postData.put("tur", "superloto");
        }

        PostResponseAsyncTask task1 = new PostResponseAsyncTask(getActivity(), postData, "Lütfen Bekleyiniz", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.matches("")){
                    Toast toast= Toast.makeText(getActivity(),
                            "Çok meşgulüz. Lütfen daha sonra tekrar deneyiniz.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                try {
                    JSONArray dn = new JSONArray(s);
                    for (int i=0; i<dn.length(); i++){
                        JSONObject item = dn.getJSONObject(i);
                        String tarih = item.getString("tarih");
                        String tarihView = item.getString("tarihView");
                        dateData itemDateData = new dateData(tarih,tarihView);
                        dlList.add(itemDateData);
                    }
                    dlAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        task1.execute(path);
    }

    //++
    //-------------------------------------------------------ListView Delegate
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dateData item = dlList.get(position);
        sendSelectedDate(item);
    }

}
