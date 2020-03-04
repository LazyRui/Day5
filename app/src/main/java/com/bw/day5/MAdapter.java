package com.bw.day5;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * ProjectName: Day5
 * PackageName: com.bw.day5
 * ClassName:   RAdapter
 * Description: Java类的作用
 * Author: LazyRui
 * CreateDate: 2020/3/4 16:34
 */
public class MAdapter extends RecyclerView.Adapter<MAdapter.VH> {


    private final Context mContext;
    private List<ShopEntity.ResultBean.MlssBean.CommodityListBeanXX> mRxxp;

    public MAdapter(Context context, List<ShopEntity.ResultBean.MlssBean.CommodityListBeanXX> rxxp) {

        mContext = context;
        mRxxp = rxxp;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(View.inflate(mContext, R.layout.item2, null));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ShopEntity.ResultBean.MlssBean.CommodityListBeanXX list = mRxxp.get(position);

        Glide.with(mContext).load(list.getMasterPic()).into(holder.iv);
        holder.tv1.setText(list.getCommodityName());
        holder.tv2.setText("￥：" + list.getPrice());

    }

    @Override
    public int getItemCount() {
        return mRxxp.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv1;
        private TextView tv2;

        public VH(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }
}
