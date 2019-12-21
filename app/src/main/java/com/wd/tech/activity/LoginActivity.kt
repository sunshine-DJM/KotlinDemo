package com.wd.tech.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.RsaCoder
import com.wd.tech.bean.BeanLogin
import com.wd.tech.bean.BeanRegisstered
import com.wd.tech.utli.Netutli
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /**
         * 点击登陆
         * */
        login_button.setOnClickListener({ view -> doLogin() })
        /**
         * 去注册
         * */
        login_zc.setOnClickListener({ view -> doRegistered() })

    }

    /* *
     点击登录
     */
    fun doLogin() {
        val phone = login_userName.text.toString()
        val pwd = RsaCoder.encryptByPublicKey(login_userPwd.text.toString())

       if (phone==null&&pwd==null){
//           Toast.makeText(this,"账号密码不能为空",)
           return
       }


        var fieldMap = HashMap<String, String>()
        fieldMap.put("phone", phone);
        fieldMap.put("pwd", pwd);


        Netutli.instance
            .baseApi
            .doLogin(fieldMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<BeanLogin>() {
                override fun onComplete() {
                }
               /**
                * 登录成功*/
                override fun onNext(t: BeanLogin) {
                    if (t is BeanLogin) {
                        if ("0000".equals(t.status)) {
                            Log.i("aaa", ""+t.message)
                            loginSuccess()
                        }
                    }
                }
                /**
                 * 登录失败*/
                override fun onError(e: Throwable) {
                    Log.i("aaa", "" + e.toString())
                }
            })

    }

//     跳转
   fun loginSuccess(){
       val intent = Intent()
       //获取intent对象
       intent.setClass(this, HomeActvity::class.java)
       // 获取class是使用::反射(那么问题来了,反射是个什么鬼?👻👻👻👻小白的悲哀啊,赶紧研究研究去)
       startActivity(intent)
    }



    /**
     * 点击注册*/
    fun doRegistered() {
        val intent = Intent()
        //获取intent对象
        intent.setClass(this, RegisteredActivity::class.java)
        // 获取class是使用::反射(那么问题来了,反射是个什么鬼?👻👻👻👻小白的悲哀啊,赶紧研究研究去)
        startActivity(intent)
    }
}
