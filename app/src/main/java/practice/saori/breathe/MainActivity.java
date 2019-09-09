package practice.saori.breathe;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.AnimationListener.Stop;
import com.github.florent37.viewanimator.ViewAnimator;

import org.w3c.dom.Text;

import practice.saori.breathe.util.Prefs;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView totalBreatheTxt, totalSessionTxt, lastTimeTxt, durationTxt, guideTxt;
    private Button startButton;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new Prefs(this);

        imageView = findViewById(R.id.lotusImageView);
        totalBreatheTxt = findViewById(R.id.breatheCntTextView);
        totalSessionTxt = findViewById(R.id.totalSessionTextView);
        durationTxt = findViewById(R.id.durationTextView);
        lastTimeTxt = findViewById(R.id.lastTimeTextView);
        guideTxt = findViewById(R.id.guideTextView);
        startButton = findViewById(R.id.startButton);

        totalSessionTxt.setText(prefs.getSessions() + " minutes today");
        totalBreatheTxt.setText(prefs.getBreaths() + " times today");
        lastTimeTxt.setText(prefs.getDate());


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        startIntroAnimation();
    }

    private void startIntroAnimation() {
        ViewAnimator.animate(guideTxt)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                }).start();
    }

    private void startAnimation() {
        ViewAnimator.animate(imageView)
                .scale(0, 1)
                .alpha(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale....Exhale");
                    }
                }).decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.1f, 1.2f, 0.1f)
                .rotation(360)
                .repeatCount(6)
                .accelerate()
                .duration(1000)
                .onStop(new Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good Job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(5000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                guideTxt.setText("start in " + (int)(millisUntilFinished/1000));
                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();
                    }
                })
    .start();
    }
}
