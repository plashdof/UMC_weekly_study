package com.a9week

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.a9week.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    val TAG = "debugging"
    private lateinit var binding : ActivityMainBinding
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setResultSignUp()


        // 카카오 로그인
        
        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")
        KakaoSdk.init(this, "838fc4e08a57b548debcba38b943a928")
        binding.btnKakao.setOnClickListener {
            // 카카오톡 설치 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->

                    // 로그인 실패 부분
                    if (error != null) {
                        Log.e(TAG, "앱 로그인 실패 $error")
                        // 사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                            return@loginWithKakaoTalk
                        }
                        // 다른 오류
                        else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
                        }
                    }
                    // 로그인 성공 부분
                    else if (token != null) {
                        Log.d(TAG, "앱 로그인 성공 ${token.accessToken}")
                        val intent = Intent(this,HomeActivity::class.java)
                            .putExtra("kakao","kakao")
                        startActivity(intent)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
            }
        }


        // 구글 로그인
        
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestProfile()
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)
        binding.btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }

    }

    // 카카오톡 이메일 로그인 콜백
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "이메일 로그인 실패 $error")
        } else if (token != null) {
            Log.d(TAG, "이메일 로그인 성공 ${token.accessToken}")
        }
    }

    // 구글로그인 콜백
    private fun setResultSignUp(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if(result.resultCode == Activity.RESULT_OK){
                // 로그인 유저정보 불러오기
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)

                val email = account?.email.toString()
                val familyName = account?.familyName.toString()
                val givenName = account?.givenName.toString()
                val displayName = account?.displayName.toString()
                val photoUrl = account?.photoUrl.toString()
                val id = account?.id.toString()
                val idToken = account?.idToken

                Log.d(TAG,"이메일 $email\n 이름정보 $familyName $givenName $displayName\n 포토url $photoUrl\n id $id\n idToken $idToken\n")

                val intent = Intent(this,HomeActivity::class.java)
                    .putExtra("google","google")
                startActivity(intent)
            }
        }
    }

}