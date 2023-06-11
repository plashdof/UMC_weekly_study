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
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class HomeActivity : AppCompatActivity() {

    val TAG = "debugging"
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if( intent.hasExtra("kakao")){

            binding.btnLogout.setOnClickListener {
                kakaoLogout()
            }

            binding.btnUnlink.setOnClickListener {
                kakaoUnlink()
            }

            kakaoCheckToken()
            kakaoCallInfo()

        }else if(intent.hasExtra("google")){

            googleCallInfo()
            binding.btnLogout.setOnClickListener{
                googleLogout()
            }

        } else if(intent.hasExtra("naver")){

            naverCallInfo()

            binding.btnLogout.setOnClickListener {
                naverLogout()
            }

            binding.btnUnlink.setOnClickListener {
                naverUnlink()
            }
        }
    }

    // 카카오 로그아웃
    private fun kakaoLogout(){
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


    // 카카오 연결 끊기
    private fun kakaoUnlink(){

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

    // 카카오 토큰 유효성 검사
    private fun kakaoCheckToken(){
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
    }


    // 카카오 유저정보 불러오기
    private fun kakaoCallInfo(){
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
    }



    // 구글 로그아웃
    private fun googleLogout(){
        val googleSignInClient = GoogleSignIn.getClient(this@HomeActivity, GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN).build())
        googleSignInClient.signOut().addOnCompleteListener {
            Log.d(TAG,"구글로그인 로그아웃")
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    // 구글 유저정보 불러오기
    private fun googleCallInfo(){
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val email = account?.email.toString()
        val familyName = account?.familyName.toString()
        val givenName = account?.givenName.toString()
        val displayName = account?.displayName.toString()
        val photoUrl = account?.photoUrl.toString()
        val id = account?.id.toString()
        val idToken = account?.idToken

        binding.tvInfo.text = displayName + "\n" + email + "\n" + id
    }

    // 네이버 로그아웃
    private fun naverLogout(){
        NaverIdLoginSDK.logout()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    // 네이버 연결끊기
    private fun naverUnlink(){
        NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                val intent = Intent(this@HomeActivity,MainActivity::class.java)
                startActivity(intent)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }

    // 네이버 유저정보 가져오기
    private fun naverCallInfo(){
        NidOAuthLogin().callProfileApi(profileCallback)
    }


    // 네이버 유저정보 콜백
    private val profileCallback = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(response: NidProfileResponse) {
            val id = response.profile?.id
            val name = response.profile?.name
            val nick = response.profile?.nickname
            val age = response.profile?.age
            val email = response.profile?.email
            val birthYear = response.profile?.birthYear

            binding.tvInfo.text = name + "\n" + email + "\n" + id + "\n" + nick + "\n" + age + "\n" + birthYear
        }
        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

}