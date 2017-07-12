package com.philong.radioonline.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.philong.radioonline.R;
import com.philong.radioonline.adapter.RadioAdapter;
import com.philong.radioonline.model.Radio;
import com.philong.radioonline.service.RadioService;
import com.philong.radioonline.util.ApiClient;
import com.philong.radioonline.util.ResponeRadio;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //View
    private RelativeLayout mediaController;
    private ImageView imgThumbnailCurrent, imgPlayPause;
    private TextView txtNameCurrent;

    private ResponeRadio mResponeRadio;
    private Call<List<Radio>> mCall;
    private RecyclerView mRecyclerView;
    private RadioAdapter mRadioAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private  List<Radio> mRadios;
    private int page = 1;
    private static final int PER_PAGE = 10;
    private boolean isLastPage = false;
    private int firstVisible;
    private int visibleItemCount;
    private int totalItemCount;
    private boolean isPlay = false;
    private boolean isBack = false;
    private static final int visibleHold = 5;

    public static Intent newIntent(Context context, String name, String hinh){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("Name", name);
        intent.putExtra("Hinh", hinh);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regisBroadcastMedia();
        mediaController = (RelativeLayout) findViewById(R.id.mediacontroller);
        imgThumbnailCurrent = (ImageView) findViewById(R.id.imgThumbnailCurrent);
        imgPlayPause = (ImageView) findViewById(R.id.imgPlayPause);
        txtNameCurrent = (TextView) findViewById(R.id.txtNameCurrent);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addOnScrollListener(recOnScrollListener);
        mResponeRadio = ApiClient.getApiClient().create(ResponeRadio.class);
        mCall = mResponeRadio.getRadioCountries(page, PER_PAGE, ApiClient.API_KEY);
        mCall.enqueue(new Callback<List<Radio>>() {
            @Override
            public void onResponse(Call<List<Radio>> call, Response<List<Radio>> response) {
                mRadios = response.body();
                mRadioAdapter = new RadioAdapter(mRadios, MainActivity.this);
                mRecyclerView.setAdapter(mRadioAdapter);
            }

            @Override
            public void onFailure(Call<List<Radio>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
        imgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = !isPlay;
                if(isPlay){
                    imgPlayPause.setImageResource(R.drawable.ic_play_arrow);
                    Intent intent = new Intent(RadioService.PAUSE);
                    sendBroadcast(intent);
                }else{
                    imgPlayPause.setImageResource(R.drawable.ic_pause);
                    Intent intent = new Intent(RadioService.PLAY);
                    sendBroadcast(intent);
                }
            }
        });

    }

    private RecyclerView.OnScrollListener recOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            firstVisible = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
            visibleItemCount = mLinearLayoutManager.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            if(!isLastPage){
                if((totalItemCount - visibleItemCount) <= (firstVisible + visibleHold)){
                    page+=1;
                    loadMore(page);
                }
            }
        }
    };

    private void loadMore(int page){
        mCall = mResponeRadio.getRadioCountries(page, PER_PAGE, ApiClient.API_KEY);
        mCall.enqueue(new Callback<List<Radio>>() {
            @Override
            public void onResponse(Call<List<Radio>> call, Response<List<Radio>> response) {
                if(response.body().size() < PER_PAGE){
                    isLastPage = true;
                }
                mRadios.addAll(response.body());
                mRadioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Radio>> call, Throwable t) {

            }
        });
    }

    private BroadcastReceiver broadcastMedia = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mediaController.setVisibility(View.VISIBLE);
            Picasso.with(MainActivity.this).load(intent.getStringExtra("Hinh")).error(R.drawable.placeholder).into(imgThumbnailCurrent);
            txtNameCurrent.setText(intent.getStringExtra("Name"));
            imgPlayPause.setImageResource(R.drawable.ic_pause);
        }
    };

    private void regisBroadcastMedia(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RadioAdapter.SEND_RADIO);
        registerReceiver(broadcastMedia, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastMedia);
    }

    @Override
    public void onBackPressed() {
        if(isBack){
            super.onBackPressed();
        }
        isBack = true;
        Toast.makeText(this, "Bấm thêm 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               isBack = false;
            }
        }, 2000);
    }
}
