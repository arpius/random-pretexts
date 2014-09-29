package biz.randompretexts;

import utilidades.TextViewEx;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity implements SensorEventListener {
	
	TextViewEx fraseInicial = null;
	ImageView imagen = null;
	private String[] excusas = null;
	private SensorManager sensorManager;
	private float aceleracion; //aceleración sin incluir la gravedad
	private float aceleracionActual; //aceleración actual incluída la gravedad
	private float ultimaAceleracion; //última aceleración incluída la gravedad
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		excusas = getResources().getStringArray(R.array.pretextos);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		aceleracion = 0.00f;
		aceleracionActual = SensorManager.GRAVITY_EARTH;
		ultimaAceleracion = SensorManager.GRAVITY_EARTH;
		
		fraseInicial = (TextViewEx) findViewById(R.id.txtFraseInicial);
		fraseInicial.setText(getString(R.string.frase_inicial), true);
				
		Typeface fuente = Typeface.createFromAsset(getAssets(), "foo.ttf"); //fuente personalizada
		fraseInicial.setTypeface(fuente);
		
		imagen = (ImageView) findViewById(R.id.imgAgitar);
		imagen.setImageResource(R.drawable.movil);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		sensorManager.registerListener(this, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		
		ultimaAceleracion = aceleracionActual;
		aceleracionActual = (float) Math.sqrt(x*x + y*y + z*z);
		
		float delta = aceleracionActual - ultimaAceleracion;
		
		aceleracion = aceleracion * 0.9f + delta;
		
		//si la aceleración es mayor a 2 el teléfono se ha "sacudido"
		if(aceleracion > 2) {
			int excusaAleatoria = (int)(Math.random() * excusas.length); //elegimos una excusa al azar
			
			switch (excusaAleatoria) { //elegimos la imagen correspondiente
				case 0:
					imagen.setImageResource(R.drawable.perro);
					break;
				case 1:
					imagen.setImageResource(R.drawable.reloj);
					break;
				case 2:
					imagen.setImageResource(R.drawable.tren);
					break;
				case 3:
					imagen.setImageResource(R.drawable.chica);
					break;
				case 4:
					imagen.setImageResource(R.drawable.bola);
					break;
				case 5:
					imagen.setImageResource(R.drawable.ciego);
					break;
				case 6:
					imagen.setImageResource(R.drawable.virrete);
					break;
				case 7:
					imagen.setImageResource(R.drawable.fiesta);
					break;
				case 8:
					imagen.setImageResource(R.drawable.coche);
					break;
				case 9:
					imagen.setImageResource(R.drawable.covertura);
					break;
				case 10:
					imagen.setImageResource(R.drawable.santo);
					break;
				case 11:
					imagen.setImageResource(R.drawable.jaula);
					break;
				default:
					imagen.setImageResource(R.drawable.movil);
					break;
			}
			
			fraseInicial.setText(excusas[excusaAleatoria], true);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.autor)
			startActivity( new Intent(this, InfoAutor.class) );
		
		return true;
	}
}
