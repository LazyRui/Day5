package com.bw.day5;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * ProjectName: Day5
 * PackageName: com.bw.day5
 * ClassName:   ShopService
 * Description: Java类的作用
 * Author: LazyRui
 * CreateDate: 2020/3/4 16:24
 */
public interface ShopService {

    @GET("small/commodity/v1/commodityList")
    Observable<ShopEntity> getData();
}
