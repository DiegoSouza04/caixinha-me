package me.caixinha.caixinhame.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import me.caixinha.caixinhame.modelo.Contato;

/**
 * Created by dsouz on 16/09/2017.
 */

public class ContatoDAO extends SQLiteOpenHelper{

    public ContatoDAO(Context context) {
        super(context, "Contato", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Contatos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, telefone1 TEXT, telefone2 TEXT, email TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Contatos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoContato(contato);

        db.insert("Contatos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoContato(Contato contato) {
        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("telefone1", contato.getTelefone1());
        dados.put("telefone2", contato.getTelefone2());
        dados.put("email", contato.getEmail());
        return dados;
    }

    public List<Contato> buscaContatos() {
        String sql = "SELECT * from Contatos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<>();
        while(c.moveToNext()){
            Contato contato = new Contato();
            contato.setId(c.getLong(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setTelefone1(c.getString(c.getColumnIndex("telefone1")));
            contato.setTelefone2(c.getString(c.getColumnIndex("telefone2")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contatos.add(contato);
        }
        c.close();
        return contatos;
    }

    public void deleta(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {contato.getId().toString()};
        db.delete("Contatos", "id = ?", parametros);
    }

    public void altera(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoContato(contato);

        String[] parametros = {contato.getId().toString()};
        db.update("Contatos", dados, "id = ?", parametros);
    }
}