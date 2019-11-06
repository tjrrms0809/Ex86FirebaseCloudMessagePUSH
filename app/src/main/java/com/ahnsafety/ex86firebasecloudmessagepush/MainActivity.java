package com.ahnsafety.ex86firebasecloudmessagepush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    //1. FCM에 앱 연결
    //2. 토큰(token :  FCM서버에서 디바이스 구별하는 식별자 문자열) 받기
    //3. FCM서버로 부터 보내진 알림(Notification)받으면 자동 실행되는 Service클래스를 작성
    // = 즉 FirebaseMessagingService클래스르 상속(extends : 확장)한 Service클래스를 만들어라..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickGetToken(View view) {
        //앱을 FCM서버에 등록하는 과정
        //앱을 FCM서버에 등록하면 앱을 식별할 수 있는 고유 토큰값(문자열)을 줌.
        //이 토큰값(인스턴스ID)을 통해 앱들(디바이스들)을 구별하여 메세지가 전달되어 오는 것임.

        //get token
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        //위 코드로 얻어온 token(문자열)을
                        //확인하기 위해 Logcat에 토큰값 표시
                        //실제
                        //하므로
                        //웹서버(ex.dothome)에 token을 전송하여 메세지를 보낼 수 있또록 해야함.

                        // Log and toast
                        Log.w("TAG", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
