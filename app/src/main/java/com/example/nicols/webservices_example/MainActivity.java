package com.example.nicols.webservices_example;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {
    private ImageView mImageView;
    private EditText n,d,h;
    private TextView resultado;
    private Button JasonRequest;
    private Button cargarImagen;
    private Button UnStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n=(EditText) findViewById(R.id.n);
        d=(EditText) findViewById(R.id.d);
        h=(EditText) findViewById(R.id.h);
        resultado=(TextView) findViewById(R.id.tresultado);
        JasonRequest=(Button) findViewById(R.id.botonCalcular);
        cargarImagen=(Button) findViewById(R.id.buttoncargarImagen);
        UnStringRequest=(Button) findViewById(R.id.calcular);
        JasonRequest.setOnClickListener(this);
        cargarImagen.setOnClickListener(this);
        UnStringRequest.setOnClickListener(this);
    }

    public void Peticion_Json() {
        if(!n.getText().toString().equals("") && !d.getText().toString().equals("")&& !h.getText().toString().equals("")){
            String url="http://appmovtest.appspot.com/";
            Map<String, String> params3 = new HashMap<String, String>();
            params3.put("n", n.getText().toString());
            params3.put("d", d.getText().toString());
            params3.put("h", h.getText().toString());

            final JSONObject Jason=new JSONObject(params3);

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST,url,Jason,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                resultado.setText(response.getString("resultado"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener()
            {       @Override
                    public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error en peticion Json",Toast.LENGTH_SHORT).show();
                }
            }
            );

            MySingleton.getInstance(this).addToRequestQueue(getRequest);
        }
        else{
            Toast.makeText(getApplicationContext(), "Ingresa los Datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Peticion_String(){
        if(!n.getText().toString().equals("") && !d.getText().toString().equals("")&& !h.getText().toString().equals(""))
        {
            String url="http://appmovtest2.appspot.com/";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            resultado.setText(response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resultado.setText("No Respuesta");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params2 = new HashMap<String, String>();
                    params2.put("n", n.getText().toString());
                    params2.put("d", d.getText().toString());
                    params2.put("h", h.getText().toString());
                    return params2;
                }
            };

            MySingleton.getInstance(this).addToRequestQueue(postRequest);
        }else
        {
            Toast.makeText(getApplicationContext(), "Ingresa los Datos", Toast.LENGTH_SHORT).show();
        }

    }

    public void Peticion_imaagen(){
        String url =" http://www.publitell.com/system/fotos/15425/elektronika-home.jpg";
        mImageView = (ImageView) findViewById(R.id.idMyimagen);

        ImageRequest request =new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    public void onResponse(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }

                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error con el servidor", Toast.LENGTH_SHORT).show();
                        mImageView.setImageResource(android.R.drawable.alert_light_frame);
                    }

                });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.botonCalcular) Peticion_Json();
        else if(v.getId()==R.id.buttoncargarImagen) Peticion_imaagen();
        else if(v.getId()==R.id.calcular)Peticion_String();

    }
}