package com.wd.tech.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.RsaCoder
import com.wd.tech.bean.BeanRegisstered
import com.wd.tech.utli.Netutli
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registered.*

class RegisteredActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registered)
        /**
         * 去登录
         * */
        registered_dl.setOnClickListener({ view -> doLogin() })
        /**
         * 去注册*/
        registered_button.setOnClickListener({ view -> doRegistered() })

    }

    /**
     * 注册*/
    fun doRegistered() {
        val phone = registered_phone.text.toString()
        val pwd = RsaCoder.encryptByPublicKey(registered_Pwd.text.toString())
        val nickname = registered_nickName.text.toString()

        var fieldMap = HashMap<String, String>()
        fieldMap.put("phone", phone);
        fieldMap.put("pwd", pwd);
        fieldMap.put("nickName", nickname);

        Netutli.instance
            .baseApi
            .doRegistered(fieldMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<BeanRegisstered>() {
                override fun onComplete() {
                }

                override fun onNext(t: BeanRegisstered) {
                    if (t is BeanRegisstered) {
                        if ("0000".equals(t.status)) {
                            Log.i("aaa", " "+t.message)
                            finish();
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("aaa", "" + e.toString())
                }
            })

    }

    /**
     * 点击去登录*/
    fun doLogin() {
        val intent = Intent()
        //获取intent对象
        intent.setClass(this, LoginActivity::class.java)
        // 获取class是使用::反射(那么问题来了,反射是个什么鬼?👻👻👻👻小白的悲哀啊,赶紧研究研究去)
        startActivity(intent)
    }

}
