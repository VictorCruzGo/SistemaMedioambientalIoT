package vcg.com.estadosaludpersona;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCheckedChangeListener  {

    Button boton1;
    Button boton2;
    EditText editTextNombre;
    EditText editTextPrimerApellido;
    EditText editTextSegundoApellido;
    RadioGroup radioGroupGenero;
    RadioButton radioButtonFemenino;
    RadioButton radioButtonMasculino;
    EditText editTextFechaNacimiento;
    String generoElegido;

    private NotificationManager notificacion;

    protected static final int MY_NOTIFICATION=100;
    private static final int REQUEST_RESULT=200;
    protected static final String MY_NOTIFICATION_TAG="mynotificationtag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creando el archivo SharedPreferences
        SharedPreferences shareSesion=getSharedPreferences("ArchivoSP", Context.MODE_PRIVATE);

        boton1=(Button) findViewById(R.id.button1);
        boton2=(Button) findViewById(R.id.button2);
        editTextNombre=(EditText)findViewById(R.id.editTextNombre);
        editTextPrimerApellido=(EditText)findViewById(R.id.editTextPrimerApellido);
        editTextSegundoApellido=(EditText)findViewById(R.id.editTextSegundoApellido);
        radioGroupGenero=(RadioGroup)findViewById(R.id.radioGroupGenero);
        radioButtonFemenino=(RadioButton)findViewById(R.id.radioButtonFemenino);
        radioButtonMasculino=(RadioButton)findViewById(R.id.radioButtonMasculino);
        editTextFechaNacimiento=(EditText)findViewById(R.id.editTextFechaNacimiento);

        radioGroupGenero.setOnCheckedChangeListener(this);

        boton1.setOnClickListener(this);
        boton2.setOnClickListener(this);
        editTextFechaNacimiento.setOnClickListener(this);

        notificacion=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);


    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        RadioButton radioButtonSeleccionado=(RadioButton) findViewById(checkedId);
        generoElegido= radioButtonSeleccionado.getText().toString();
        Toast.makeText(getApplicationContext(),generoElegido,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case     R.id.button1:
                Notificacion1(MY_NOTIFICATION,R.mipmap.ic_notificacion,"Atencion","Prueba de notificacions");
                /*SharedPreferences shareSesion=getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=shareSesion.edit();
                editor.putString("spNombre", editTextNombre.getText().toString());
                editor.putString("spPrimerApellido", editTextPrimerApellido.getText().toString());
                editor.putString("spSegundoApellido", editTextSegundoApellido.getText().toString());
                RadioButton radioButtonElegido=(RadioButton) findViewById(radioGroupGenero.getCheckedRadioButtonId());
                editor.putString("spGenero", radioButtonElegido.getText().toString());
                editor.putString("spfechaNacimiento", editTextFechaNacimiento.getText().toString());
                editor.commit();*/
                break;
            case R.id.button2:
                SharedPreferences shareSesionMostrar=getPreferences(Context.MODE_PRIVATE);
                String spNombre=shareSesionMostrar.getString("spNombre","Sin Nombre");
                String spPrimerApellido=shareSesionMostrar.getString("spPrimerApellido","Sin Primer Apellido");
                String spSegundoApellido=shareSesionMostrar.getString("spSegundoApellido","Sin Segundo Apellido");
                String spGenero=shareSesionMostrar.getString("spGenero","Sin Genero");
                String spfechaNacimiento=shareSesionMostrar.getString("spfechaNacimiento","Sin Fecha de Nacimiento");
                Toast.makeText(this,spfechaNacimiento,Toast.LENGTH_SHORT).show();
                Toast.makeText(this,spGenero,Toast.LENGTH_LONG).show();

                break;
            case R.id.editTextFechaNacimiento:
                final Calendar calendar=Calendar.getInstance();
                int dia=calendar.get(Calendar.DAY_OF_MONTH);
                int mes=calendar.get(Calendar.MONTH);
                int anio=calendar.get(Calendar.YEAR);

                Toast.makeText(this,"dia:"+dia+"mes:"+mes+"anio:"+anio,Toast.LENGTH_LONG).show();

                DatePickerDialog datePickerDialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextFechaNacimiento.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },dia,mes,anio);
                datePickerDialog.show();
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void Notificacion1(int id, int iconid, String titulo, String contenido)
    {
        NotificationCompat.Builder builder=(NotificationCompat.Builder) new NotificationCompat.Builder(this);
        builder.setSmallIcon(iconid);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_notificacionthink));
        builder.setContentTitle(titulo);
        builder.setContentText(contenido);
        builder.setColor(getResources().getColor(R.color.colorAccent,null));
        builder.setLights(Color.RED,0,1);

        Intent intent=new Intent(this, caritasActivity.class);
        intent.putExtra(MY_NOTIFICATION_TAG,MY_NOTIFICATION);

        PendingIntent pendingIntent=PendingIntent.getActivity(this, REQUEST_RESULT, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.mipmap.ic_notificacion, "hecho",pendingIntent);

        notificacion.notify(id, builder.build());

    }


}
