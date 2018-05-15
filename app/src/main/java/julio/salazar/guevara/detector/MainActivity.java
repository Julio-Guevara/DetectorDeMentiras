package julio.salazar.guevara.detector;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton IBtnDetectarJAGS;
    TextView tvVerdaderoJAGS, tvFalsoJAGS;
    ImageView ivTrueJAGS, ivFalseJAGS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IBtnDetectarJAGS = findViewById(R.id.iBtnImage);
        tvVerdaderoJAGS = findViewById(R.id.tvVerdadero);
        tvFalsoJAGS = findViewById(R.id.tvFalso);
        ivTrueJAGS = findViewById(R.id.ivVerdadero);
        ivFalseJAGS = findViewById(R.id.ivFalso);

        imagenesInvisibles();

        IBtnDetectarJAGS.setBackgroundColor(getResources().getColor(android.R.color.white));

        IBtnDetectarJAGS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new DetectarMentira().execute();
                return false;
            } 
        });
    }
    private class DetectarMentira extends AsyncTask<Void,Integer,Void>{

        @Override
        protected void onPreExecute() {
            imagenesInvisibles();
            tvVerdaderoJAGS.setTextColor(getResources().getColor(android.R.color.black));
            tvFalsoJAGS.setTextColor(getResources().getColor(android.R.color.black));
            tvVerdaderoJAGS.setAllCaps(false);
            tvFalsoJAGS.setAllCaps(false);
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Analizando...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= 5; i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(200);
            if (values[0]%2==0){
                IBtnDetectarJAGS.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }else{
                IBtnDetectarJAGS.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (aleatorio(0,2)==0){
                tvFalsoJAGS.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                tvFalsoJAGS.setAllCaps(true);
                ivFalseJAGS.setVisibility(View.VISIBLE);
                IBtnDetectarJAGS.setBackgroundColor(getResources().getColor(android.R.color.white));
                MediaPlayer mpw = MediaPlayer.create(getApplicationContext(),R.raw.wrongmp3);
                mpw.start();
            }else{
                ivTrueJAGS.setVisibility(View.VISIBLE);
                tvVerdaderoJAGS.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                tvVerdaderoJAGS.setAllCaps(true);
                IBtnDetectarJAGS.setBackgroundColor(getResources().getColor(android.R.color.white));
                MediaPlayer mpt = MediaPlayer.create(getApplicationContext(),R.raw.truemp3);
                mpt.start();
            }
            super.onPostExecute(aVoid);
        }
    }
    private int aleatorio(int minimo, int maximo){
        Random r = new Random();
        return r.nextInt(maximo-minimo)+minimo;
    }
    private void imagenesInvisibles(){
        ivTrueJAGS.setVisibility(View.INVISIBLE);
        ivFalseJAGS.setVisibility(View.INVISIBLE);
    }
}
