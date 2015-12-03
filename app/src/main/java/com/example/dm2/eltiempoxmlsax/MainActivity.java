package com.example.dm2.eltiempoxmlsax;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResultado= (TextView)findViewById(R.id.txtResultado);


        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute("http://www.aemet.es/xml/municipios/localidad_01059.xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> { ///// Create inner asyncTask

        List<Tiempo> tiempos = new ArrayList<Tiempo>();
        protected Boolean doInBackground(String... params) {

            RssParserSax2 saxparser =
                    new RssParserSax2(params[0]);

            tiempos = saxparser.parse();

            return true;
        }

        protected void onPostExecute(Boolean result) {

            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            txtResultado.setText("");
            for(int i=0; i<tiempos.size(); i++)
            {
                txtResultado.setText(
                        txtResultado.getText().toString() +
                                System.getProperty("line.separator") + "Día: "+
                                tiempos.get(i).getFecha());




                txtResultado.setText(
                        txtResultado.getText().toString() +
                                System.getProperty("line.separator") + "Mínima: "+
                                tiempos.get(i).getMinima());

                txtResultado.setText(
                        txtResultado.getText().toString() +
                                System.getProperty("line.separator") + "Máxima: "+
                                tiempos.get(i).getMaxima());


            }
        }
    }

}
