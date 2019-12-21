package com.wd.tech.utli

import com.wd.tech.bean.BeanLogin
import com.wd.tech.bean.BeanRegisstered
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * author: Daijianming
 * data: 2019/12/20 15:15:02
 * function：接口
 */
interface BaseApi {
    @FormUrlEncoded
    @POST("techApi/user/v1/register")
    fun doRegistered(@FieldMap fieldMap: Map<String, String>): Observable<BeanRegisstered>

    @FormUrlEncoded
    @POST("techApi/user/v1/login")
    fun doLogin(@FieldMap fieldMap: Map<String, String>):Observable<BeanLogin>


}
