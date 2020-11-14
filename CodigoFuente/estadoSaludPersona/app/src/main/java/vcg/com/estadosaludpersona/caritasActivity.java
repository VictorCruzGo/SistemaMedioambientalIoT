package vcg.com.estadosaludpersona;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class caritasActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG="CARITAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caritas);

        //elimina la notificacion
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(MainActivity.MY_NOTIFICATION);

        Toast.makeText(this,"mensaje eliminado", Toast.LENGTH_SHORT).show();

        //Evento Onclick de las imagenes
        ImageView imageViewDueleMas=(ImageView) findViewById(R.id.imageViewDueleMas);
        ImageView imageViewDuelePocoMas=(ImageView) findViewById(R.id.imageViewDuelePocoMas);
        ImageView imageViewDueleMucho=(ImageView) findViewById(R.id.imageViewDueleMucho);
        ImageView imageViewDuelePoco=(ImageView) findViewById(R.id.imageViewDueloPoco);
        ImageView imageViewPeorDolor=(ImageView) findViewById(R.id.imageViewPeorDolor);
        ImageView imageViewSinDolor=(ImageView) findViewById(R.id.imageViewSinDolor);

        imageViewDueleMas.setOnClickListener(caritasActivity.this);
        imageViewDuelePocoMas.setOnClickListener(caritasActivity.this);
        imageViewDueleMucho.setOnClickListener(caritasActivity.this);
        imageViewDuelePoco.setOnClickListener(caritasActivity.this);
        imageViewPeorDolor.setOnClickListener(caritasActivity.this);
        imageViewSinDolor.setOnClickListener(caritasActivity.this);


/*
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            int id=bundle.getInt(MainActivity.MY_NOTIFICATION_TAG);

            NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(id);

            Context context=getApplicationContext();
            CharSequence text= "Se elimino el mensaje";
            int duration=Toast.LENGTH_SHORT;

            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
*/


    }

    public void onClick(View view)
    {
        /*
        Valores de las caritas:
        *0=Sin dolor
        *2=Duele un poco
        *4=Duele un poco mas
        *6=Duele aun mas
        *8=Duele mucho
        *10=El peor dolor
        * */
        int caritaSeleccionado=-1;
        switch (view.getId())
        {
            case R.id.imageViewDueleMas:
                caritaSeleccionado=6;
                Log.d(TAG, "666");
                break;
            case R.id.imageViewDueleMucho:
                caritaSeleccionado=8;
                Log.d(TAG,"888");
                break;
            case R.id.imageViewDuelePocoMas:
                caritaSeleccionado=4;
                Log.d(TAG,"444");
                break;
            case R.id.imageViewDueloPoco:
                caritaSeleccionado=2;
                Log.d(TAG,"222");
                break;
            case R.id.imageViewPeorDolor:
                caritaSeleccionado=10;
                Log.d(TAG,"101010");
                break;
            case R.id.imageViewSinDolor:
                caritaSeleccionado=0;
                Log.d(TAG,"000");
                break;
        }

        Intent intento=new Intent(view.getContext(), CuerpoActivity.class);
        intento.putExtra("caritaSeleccionado",caritaSeleccionado);
        startActivity(intento);
    }





}
