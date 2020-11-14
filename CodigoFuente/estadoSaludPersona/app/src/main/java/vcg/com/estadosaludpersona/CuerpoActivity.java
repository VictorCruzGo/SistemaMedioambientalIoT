package vcg.com.estadosaludpersona;

import android.app.Activity;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CuerpoActivity extends AppCompatActivity implements View.OnClickListener {
    HashMap<String,String> partesSeleccionadas=new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuerpo);

        inicializarHashMapPartesSeleccionadas();

        //Recuperando el valor de la carita elegido
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            int caritaSeleccionado=bundle.getInt("caritaSeleccionado");
            Log.d(caritasActivity.TAG,"el valor seleccionado es::"+caritaSeleccionado);
        }

        ImageView imageViewEyeBrow=(ImageView)findViewById(R.id.imageViewEyeBrow);
        ImageView imageViewEye=(ImageView)findViewById(R.id.imageViewEye);
        ImageView imageViewNose=(ImageView)findViewById(R.id.imageViewNose);
        ImageView imageViewMouth=(ImageView)findViewById(R.id.imageViewMouth);
        ImageView imageViewChin=(ImageView)findViewById(R.id.imageViewChin);
        ImageView imageViewChest=(ImageView)findViewById(R.id.imageViewChest);
        ImageView imageViewArm=(ImageView)findViewById(R.id.imageViewArm);
        ImageView imageViewHand=(ImageView)findViewById(R.id.imageViewHand);
        ImageView imageViewLeg=(ImageView)findViewById(R.id.imageViewLeg);
        ImageView imageViewToe=(ImageView)findViewById(R.id.imageViewToe);
        ImageView imageViewFoot=(ImageView)findViewById(R.id.imageViewFoot);
        ImageView imageViewKnee=(ImageView)findViewById(R.id.imageViewKnee);
        ImageView imageViewStomach=(ImageView)findViewById(R.id.imageViewStomach);
        ImageView imageViewLip=(ImageView)findViewById(R.id.imageViewLip);
        ImageView imageViewTonque=(ImageView)findViewById(R.id.imageViewTonque);
        ImageView imageViewTooth=(ImageView)findViewById(R.id.imageViewTooth);
        ImageView imageViewCheek=(ImageView)findViewById(R.id.imageViewCheek);
        ImageView imageViewEar=(ImageView)findViewById(R.id.imageViewEar);
        ImageView imageViewForeHead=(ImageView)findViewById(R.id.imageViewForeHead);
        Button buttonFinalizar=(Button)findViewById(R.id.buttonFinalizar);

        imageViewEyeBrow.setOnClickListener(this);
        imageViewEye.setOnClickListener(this);
        imageViewNose.setOnClickListener(this);
        imageViewMouth.setOnClickListener(this);
        imageViewChin.setOnClickListener(this);
        imageViewChest.setOnClickListener(this);
        imageViewArm.setOnClickListener(this);
        imageViewHand.setOnClickListener(this);
        imageViewLeg.setOnClickListener(this);
        imageViewToe.setOnClickListener(this);
        imageViewFoot.setOnClickListener(this);
        imageViewKnee.setOnClickListener(this);
        imageViewStomach.setOnClickListener(this);
        imageViewLip.setOnClickListener(this);
        imageViewTonque.setOnClickListener(this);
        imageViewTooth.setOnClickListener(this);
        imageViewCheek.setOnClickListener(this);
        imageViewEar.setOnClickListener(this);
        imageViewForeHead.setOnClickListener(this);
        buttonFinalizar.setOnClickListener(this);

        /*ImageView imageViewFondo=(ImageView) findViewById(R.id.imageViewFondo);
        imageViewFondo.setOnTouchListener(this);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageViewEyeBrow:
                guardarParteSeleccionada("eyebrow");
                break;
            case R.id.imageViewEye:
                guardarParteSeleccionada("eye");
                break;
            case R.id.imageViewNose:
                guardarParteSeleccionada("nose");
                break;

            case R.id.imageViewMouth:
                guardarParteSeleccionada("mouth");
                break;
            case R.id.imageViewChin:
                guardarParteSeleccionada("chin");
                break;
            case R.id.imageViewChest:
                guardarParteSeleccionada("chest");
                break;
            case R.id.imageViewArm:
                guardarParteSeleccionada("arm");
                break;
            case R.id.imageViewHand:
                guardarParteSeleccionada("hand");
                break;
            case R.id.imageViewLeg:
                guardarParteSeleccionada("leg");
                break;
            case R.id.imageViewToe:
                guardarParteSeleccionada("toe");
                break;
            case R.id.imageViewFoot:
                guardarParteSeleccionada("foot");
                break;
            case R.id.imageViewKnee:
                guardarParteSeleccionada("knee");
                break;
            case R.id.imageViewStomach:
                guardarParteSeleccionada("stomach");
                break;
            case R.id.imageViewLip:
                guardarParteSeleccionada("lip");
                break;
            case R.id.imageViewTonque:
                guardarParteSeleccionada("tonque");
                break;
            case R.id.imageViewTooth:
                guardarParteSeleccionada("tooth");
                break;
            case R.id.imageViewCheek:
                guardarParteSeleccionada("cheek");
                break;
            case R.id.imageViewEar:
                guardarParteSeleccionada("ear");
                break;
            case R.id.imageViewForeHead:
                guardarParteSeleccionada("head");
                break;
            case R.id.buttonFinalizar:
                guardarYenviarDatosAlaNube();
                break;
            default: Toast.makeText(this,"No selecciono ninguna parte del cuerpo...",Toast.LENGTH_SHORT).show(); break;
        }
    }

    private void guardarParteSeleccionada(String parteSeleccionada)
    {
        if (partesSeleccionadas.get(parteSeleccionada).equals("0"))
        {
            partesSeleccionadas.remove(parteSeleccionada);
            partesSeleccionadas.put(parteSeleccionada,"1");
        }
        else
        {
            Toast.makeText(this,"La "+parteSeleccionada+" ya fue elegida,",Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarPartesSeleccionadas()
    {
        Toast.makeText(this,"------->me encuentro adentro de metodo mostrarPartesSeleccionadas",Toast.LENGTH_SHORT).show();
        for (Map.Entry parte:partesSeleccionadas.entrySet())
        {
            Log.d("AplicacionPRUEBA","Clave: "+parte.getKey()+" - "+"Valor:"+parte.getValue());
        }
    }

    private void inicializarHashMapPartesSeleccionadas()
    {
        partesSeleccionadas.put("eyebrow","0");
        partesSeleccionadas.put("eye","0");
        partesSeleccionadas.put("nose","0");
        partesSeleccionadas.put("mouth","0");
        partesSeleccionadas.put("chin","0");
        partesSeleccionadas.put("chest","0");
        partesSeleccionadas.put("arm","0");
        partesSeleccionadas.put("hand","0");
        partesSeleccionadas.put("leg","0");
        partesSeleccionadas.put("toe","0");
        partesSeleccionadas.put("foot","0");
        partesSeleccionadas.put("knee","0");
        partesSeleccionadas.put("stomach","0");
        partesSeleccionadas.put("lip","0");
        partesSeleccionadas.put("tonque","0");
        partesSeleccionadas.put("cheek","0");
        partesSeleccionadas.put("ear","0");
        partesSeleccionadas.put("head","0");
    }

    private void guardarYenviarDatosAlaNube() {
        mostrarPartesSeleccionadas();
    }

/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        */
/*float x=event.getX();
        float y=event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(this,"Haz activado el evento DOWN",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"X="+x+", y="+y,Toast.LENGTH_LONG).show();
                break;
            case MotionEvent.ACTION_MOVE:
            Toast.makeText(this,"Haz realizado un moviento con el dedo",Toast.LENGTH_SHORT).show();
            break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(this,"Haz retirado el dedo de la pantall",Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Xx="+x+", yy="+y,Toast.LENGTH_LONG).show();
        }*//*


        return false;
    }
*/

}
