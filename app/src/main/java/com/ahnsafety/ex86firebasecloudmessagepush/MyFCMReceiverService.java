package com.ahnsafety.ex86firebasecloudmessagepush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMReceiverService extends FirebaseMessagingService {

    //알림을 받았을 때 자동으로 실행되는 메소드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);

        //서비스는 액티비티가 아니므로 화면이 없음
        //그래서 Logcat으로 메세지 받았는지 확인
        Log.w("TAG", "onMessageReceived!!!!");

        //파라미터 remoteMessage : 받은 원격 메세지
        String notiTitle= "title";
        String notiBody=  "body";
        if(remoteMessage.getNotification()!=null){
            notiTitle= remoteMessage.getNotification().getTitle();
            notiBody= remoteMessage.getNotification().getBody();
            Log.w("TAG", notiTitle+" : " +notiBody);
        }

        //잘 받았는지 사용자에게 알려주기 위해
        //Notification(알림)을 공지!!!


        //알림매니져 소환하기
        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //알림을 만들어주는 건축가 객체
        NotificationCompat.Builder builder;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //Oreo에서 처음 도입된 채널객체 생성

            NotificationChannel channel= new NotificationChannel("ch01", "channel 01", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            builder= new NotificationCompat.Builder(this, "ch01");
        }else{
            builder= new NotificationCompat.Builder(this, null);
        }

        //건축가에게 알림설정 하기
        builder.setSmallIcon(R.drawable.ic_noti);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiBody);
        builder.setAutoCancel(true);

        //FCM푸시 메세지에 추가데이터가 있을 경우
        //[key:value]형태로 송신된 데이터.. 받기
        Map<String, String> datas= remoteMessage.getData();
        if(datas!=null){
            //전달된 데이터 가져오기
            String name=null;
            String msg=null;
            if(datas.size()>0){
                name= datas.get("name");//key로 value얻어오기
                msg= datas.get("msg");
                Log.w("TAG", name +" : "+ msg);
            }

            //알림에 name, msg를 보여주는
            //액티비티로 전환하도록..PendingIntent적용
            Intent intent= new Intent(this, MessageActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("msg", msg);

            //액티비티에서 액티비티를 실행할 때는 문제없지만
            //액티비티가 없는 상태에서 새로운 액티비티를 실행하려면 아래 설정값(flag) 필요
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent= PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(pendingIntent);
        }




        //알림객체 만들기
        Notification notification= builder.build();

        //알림매니져에게 알림공지 요청
        notificationManager.notify(10, notification);


    }
}
