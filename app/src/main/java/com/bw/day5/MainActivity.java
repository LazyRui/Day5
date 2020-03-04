package com.bw.day5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mR1;
    private RecyclerView mR2;
    private RecyclerView mR3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mR1 = findViewById(R.id.rv1);
        mR2 = findViewById(R.id.rv2);
        mR3 = findViewById(R.id.rv3);

        mR1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mR2.setLayoutManager(new LinearLayoutManager(this));
        mR3.setLayoutManager(new GridLayoutManager(this, 2));

        OkHttpClient build = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(build)
                .baseUrl("http://mobile.bwstudent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        retrofit.create(ShopService.class).getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShopEntity>() {
                    @Override
                    public void accept(ShopEntity shopEntity) throws Exception {
                        if (shopEntity != null) {

                            Toast.makeText(MainActivity.this, "" + shopEntity.getMessage(), Toast.LENGTH_SHORT).show();

                            List<ShopEntity.ResultBean.MlssBean.CommodityListBeanXX> mlsh = shopEntity.getResult().getMlss().getCommodityList();
                            List<ShopEntity.ResultBean.PzshBean.CommodityListBeanX> pzsh = shopEntity.getResult().getPzsh().getCommodityList();
                            List<ShopEntity.ResultBean.RxxpBean.CommodityListBean> rxxp = shopEntity.getResult().getRxxp().getCommodityList();


                            RAdapter rAdapter = new RAdapter(MainActivity.this, rxxp);
                            mR1.setAdapter(rAdapter);
                            MAdapter mAdapter = new MAdapter(MainActivity.this, mlsh);
                            mR2.setAdapter(mAdapter);
                            PAdapter pAdapter = new PAdapter(MainActivity.this, pzsh);
                            mR3.setAdapter(pAdapter);


                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


    }

    //2、解决热销新品滑动事件冲突，滑到最后不能让底部导航到下一个页面（20分）

  /*  @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int width = getWindowManager().getDefaultDisplay().getWidth();

        mR1.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                ImageView view1 = (ImageView) view;

                if (i <width){
                  //  mR1.setAdapter();
                }
            }
        });
        return false;

    }*/
}
