package kr.socar.ttsforsally;

import android.annotation.TargetApi;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TextToSpeech.OnInitListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEditText;
    private Button mButton;
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mTextToSpeech = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.KOREAN);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "onInit: This language is not supported.");
                mButton.setEnabled(false);
            } else {
                mButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                handleTTS();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void handleTTS() {
        String text = mEditText.getText().toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        String utteranceId = String.valueOf(this.hashCode());
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    @Override
    protected void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
