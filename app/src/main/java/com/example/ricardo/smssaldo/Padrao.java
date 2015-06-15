package com.example.ricardo.smssaldo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ricardo on 14/06/2015.
 */
public class Padrao {
    public static final String P_VALIDADE = "\\d{2}/\\d{2}/\\d{4}";
    public static final String P_SALDO = "R\\$[ ]?[0-9\\.,]*";
    public static final String P_DIAS = "\\d\\d?\\d dias";
    public ArrayList<String> numeros;

    public static ArrayList<Saldo> Lista(Context context) {
        //Padrao vivo
        String msgrecarregado = "^Voce recarregou.*";
        String msgsaldo = "^Saldo Recarga.*";
        Pattern pdata = Pattern.compile(P_VALIDADE);
        Pattern psaldo = Pattern.compile(P_SALDO);
        Pattern pdias = Pattern.compile(P_DIAS);

        Uri inboxUri = Uri.parse("content://sms/inbox");
        String[] colunas = new String[] {"_id", "address", "date", "body"};
        String[] argz = new String[] {"1515"};
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(inboxUri, colunas, "address = ?", argz, null);

        String smst;
        int tipo;
        ArrayList<Saldo> lSaldo = new ArrayList<>();

        while(c.moveToNext()) {
            smst = c.getString(3);
            // Encontrando os padroes iniciais
            if(smst.matches(msgrecarregado))
                tipo = Saldo.CREDITO_INSERIDO;
            else if(smst.matches(msgsaldo))
                tipo = Saldo.SALDO_ATUAL;
            else
                continue;

            // Encontrando valores de saldo e validade
            String saldo = "";
            String data = "";

            Matcher res = pdata.matcher(smst);
            if(res.find()) {
                data = res.group();
            }
            if(data.length() == 0 ){
                res = pdias.matcher(smst);
                if(res.find()) {
                    data = res.group();
                }
            }

            res = psaldo.matcher(smst);
            if(res.find()) {
                saldo = res.group().trim();
            }

            if(saldo.length() > 0){
                //id saldo validade recebimento
                Saldo saldoz = new Saldo(0, saldo, data, c.getLong(2), c.getInt(0), tipo);
                lSaldo.add(saldoz);
            }

        }
        return lSaldo;

    }

}
