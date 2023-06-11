package com.a9week

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.a9week.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

class HomeActivity : AppCompatActivity() {

    val TAG = "debugging"
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if( intent.hasExtra("kakao")){

            binding.btnLogout.setOnClickListener {
                // 카카오 로그아웃
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    }
                    else {
                        Log.d(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            binding.btnDelete.setOnClickListener {
                // 카카오 연결 끊기
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e(TAG, "연결 끊기 실패", error)
                    }
                    else {
                        Log.d(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }


            // 토큰 유효성 검사
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            Log.d(TAG,"로그인 필요")
                        }
                        else {
                            Log.d(TAG,"기타 에러")
                        }
                    }
                    else {
                        Log.d(TAG,"토큰 유효성 체크 성공")
                    }
                }
            }
            else {
                Log.d(TAG,"로그인 필요")
            }


            // 로그인 유저정보 불러오기
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e(TAG, "사용자 정보 요청 실패 $error")
                } else if (user != null) {
                    Log.d(TAG, "사용자 정보 요청 성공 : $user")
                    val nickname = user.kakaoAccount?.profile?.nickname
                    val birthday = user.kakaoAccount?.birthday
                    val email = user.kakaoAccount?.email
                    val age = user.kakaoAccount?.ageRange.toString()
                    binding.tvInfo.text = nickname + "\n" +birthday + "\n" + email + "\n" + age
                }
            }

        }else if(intent.hasExtra("google")){


            val account = GoogleSignIn.getLastSignedInAccount(this)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
            val givenName = account?.givenName.toString()
            val displayName = account?.displayName.toString()
            val photoUrl = account?.photoUrl.toString()
            val id = account?.id.toString()
            val idToken = account?.idToken

            binding.tvInfo.text = displayName + "\n" + email + "\n" + id

            binding.btnLogout.setOnClickListener{
                // 구글 로그아웃
                val googleSignInClient = GoogleSignIn.getClient(this@HomeActivity, GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                googleSignInClient.signOut().addOnCompleteListener {
                    Log.d(TAG,"구글로그인 로그아웃")
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }

        }





    }
}