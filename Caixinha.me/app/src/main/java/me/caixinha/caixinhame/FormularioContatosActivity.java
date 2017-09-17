package me.caixinha.caixinhame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import me.caixinha.caixinhame.dao.ContatoDAO;
import me.caixinha.caixinhame.modelo.Contato;

public class FormularioContatosActivity extends AppCompatActivity {

    private FormularioContatosHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_contatos);

        helper = new FormularioContatosHelper(this);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencheFormularioContatos(contato);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_contatos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_contatos_ok:
                Contato contato = helper.getContato();
                ContatoDAO dao = new ContatoDAO(this);

                if(contato.getId() != null){
                    dao.altera(contato);
                }else{
                    dao.insere(contato);
                }


                dao.close();
                Toast.makeText(FormularioContatosActivity.this, "Contato " + contato.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}