package com.example.ricardo.smssaldo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo on 08/06/2015.
 */
public class Banco extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "saldo";
    public static final String COLUNA_ID = "_ID";
    public static final String COLUNA_SALDO = "saldo";
    public static final String COLUNA_VALIDADE = "validade";
    public static final String COLUNA_RECEBIMENTO = "recebimento";
    public static final String COLUNA_ORIGINALID = "originalid";
    public static final String COLUNA_TIPO = "tipo";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUNA_ID + " INTEGER PRIMARY KEY," +
                    COLUNA_SALDO + " TEXT, " +
                    COLUNA_VALIDADE + " TEXT, " +
                    COLUNA_RECEBIMENTO + " INTEGER, " +
                    COLUNA_ORIGINALID + " INTEGER UNIQUE, " +
                    COLUNA_TIPO + " INTEGER " +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String DB_NOME= "teste.db";
    public static final int BD_VERSAO = 5;

    public Banco(Context context) {
        super(context, DB_NOME, null, BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase vd, int i, int i1) {
        vd.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(vd);
    }

    // Adicionar
    public void Adicionar(Saldo saldo){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_SALDO, saldo.getSaldo());
        valores.put(COLUNA_VALIDADE, saldo.getValidade());
        valores.put(COLUNA_RECEBIMENTO, saldo.getRecebimento());
        valores.put(COLUNA_ORIGINALID, saldo.getOriginalid());
        valores.put(COLUNA_TIPO, saldo.getTipo());
        bd.insertWithOnConflict(TABLE_NAME, null, valores, SQLiteDatabase.CONFLICT_IGNORE);
        bd.close();
    }
    // Obter por id
    // Listar todos
    public List<Saldo> Listar(){
        List<Saldo> lista = new ArrayList<Saldo>();
        String comando = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor c = bd.rawQuery(comando, null);
        if(!c.moveToFirst()) return lista;
        do{
            Saldo s = new Saldo(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getLong(3),
                    c.getInt(4),
                    c.getInt(5)
            );
            lista.add(s);
        }while (c.moveToNext());

        return lista;
    }
    // Contar
    // Atualizar
    // Apagar
    // Limpar
    public void Limpar(){
        String comando = "DELETE FROM " + TABLE_NAME;
        SQLiteDatabase bd = this.getWritableDatabase();
        bd.execSQL(comando);
    }


}
