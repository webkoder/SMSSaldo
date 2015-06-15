package com.example.ricardo.smssaldo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.nobetauser.teste.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
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
        if (id == R.id.action_apagar) {
            Apagar();
            return true;
        }else if(id == R.id.action_sincronizar){
            Sincronizar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Sincronizar() {

        ArrayList<Saldo> lSaldo = Padrao.Lista(getApplicationContext());

        Context contexto = getApplicationContext();
        SharedPreferences pref = contexto.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(lSaldo.size()==0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Nenhuma mensagem foi encontrado ou se encaixou no padrao", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        editor.putString(getString(R.string.validade), lSaldo.get(0).getValidade());
        editor.putString(getString(R.string.saldo), lSaldo.get(0).getSaldo());
        editor.commit();

        Banco bd = new Banco(this);

        for(Saldo s : lSaldo) {
            bd.Adicionar(s);
        }
        Popular();

    }

    private void Apagar() {
        Banco banco = new Banco(this);
        banco.Limpar();
        Popular();
    }

    private void Popular(){
        LinearLayout historico = (LinearLayout) this.findViewById(R.id.listaHistorico);
        TextView strSaldo = (TextView) findViewById(R.id.txtSaldo);
        TextView strValidade = (TextView) findViewById(R.id.txtValidade);
        // Limpar objetos
        historico.removeAllViews();

        // Popular lista

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Banco bd = new Banco(this);
        List<Saldo> lista = bd.Listar();
        if(lista.size() == 0) {
            strSaldo.setText("0.00");
            strValidade.setText("nao detectado");
            return;
        }
        for(Saldo s: lista){
            TextView tv = new TextView(this);
            if(s.getTipo() == Saldo.CREDITO_INSERIDO)
                tv.setTextColor(Color.rgb(0,150, 0) );

            tv.setLayoutParams(lparams);
            SimpleDateFormat dataz = new SimpleDateFormat("dd/MM/yyyy");
            tv.setText(dataz.format(s.getRecebimento()) + ": " + s.getSaldo() + " val. " + s.getValidade());
            historico.addView(tv);
        }
        strSaldo.setText(lista.get(0).getSaldo());
        strValidade.setText(lista.get(0).getValidade());
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        /*@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }*/
    }

    @Override
    public void onStart(){
        super.onStart();

        Popular();

    }

    public void enviarMensagem(View view){

        /*
        Uri inboxUri = Uri.parse("content://sms/inbox");
        String[] colunas = new String[] {"_id", "address", "date", "body"};
        String[] argz = new String[] {"1515"};
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(inboxUri, colunas, "address = ?", argz , null);

        String smst = "";
        int z = 0;
        if(c.moveToFirst()){
            while(c.moveToNext()){
                smst += c.getString(2) + " " + c.getString(3) + "\r\n\r\n";
            }
        }

        tc.setText(smst);*/

        /*Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String mensagem = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, mensagem);
        startActivity(intent);*/
    }
}
