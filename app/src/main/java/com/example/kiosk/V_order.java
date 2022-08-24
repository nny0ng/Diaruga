package com.example.kiosk;

import static android.speech.tts.TextToSpeech.ERROR;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class V_order extends Service {

    public TextToSpeech tts;
    Intent sttIntent;
    SpeechRecognizer mRecognizer;

    public V_order() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TTS 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // tts: label 3
        int result;
        result = tts.speak("현재 장바구니에는 '제'가 있습니다. 추가를 원하시면 추가, 삭제를 원하시면 삭제, 주문 확정을 원하시면 확정 이라고 말해주세요", TextToSpeech.QUEUE_FLUSH, null);
        Log.d("TTS state", String.valueOf(result));
        try {
            Thread.sleep(9500); // 초 계산하기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // STT 설정
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
        mRecognizer.setRecognitionListener(listener);

        // STT 시작
        mRecognizer.startListening(sttIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Listener listener = new Listener() {
        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));
            cart(matches.toString());
            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }

        public void cart(String matches) {
            Intent intent;
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
            mRecognizer.setRecognitionListener(cartListener);

            if (matches.contains("추가")) {
                tts.speak("무엇을 추가하시겠습니까?", TextToSpeech.QUEUE_FLUSH, null);
                mRecognizer.startListening(sttIntent);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            } else if (matches.contains("삭제")) {
                tts.speak("리조또 메뉴들", TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            } else if (matches.contains("확정")) {
                tts.speak("파스타 메뉴들", TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
                startActivity(intent);
                stopSelf();
            }
        }
    };

    private Listener cartListener = new Listener(){
        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));
            cart(matches.toString());
            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }

        public void cart(String matches) {
            Intent intent;
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
            mRecognizer.setRecognitionListener(cartListener);

            if (matches.contains("추가")) {
                tts.speak("무엇을 추가하시겠습니까?", TextToSpeech.QUEUE_FLUSH, null);
                mRecognizer.startListening(sttIntent);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            } else if (matches.contains("삭제")) {
                tts.speak("리조또 메뉴들", TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            } else if (matches.contains("확정")) {
                tts.speak("파스타 메뉴들", TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
                startActivity(intent);
                stopSelf();
            }
        }
    };
}