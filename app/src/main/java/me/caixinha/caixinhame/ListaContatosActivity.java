package me.caixinha.caixinhame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import me.caixinha.caixinhame.dao.ContatoDAO;
import me.caixinha.caixinhame.modelo.Contato;

public class ListaContatosActivity extends AppCompatActivity {

    private ListView listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        listaContatos = (ListView) findViewById(R.id.lista_contatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);
                Intent intentVaiProFormularioContatos = new Intent(ListaContatosActivity.this, FormularioContatosActivity.class);
                intentVaiProFormularioContatos.putExtra("contato", contato);
                startActivity(intentVaiProFormularioContatos);
            }
        });

        Button novoContato = (Button) findViewById(R.id.novo_contato);
        novoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormularioContatos = new Intent(ListaContatosActivity.this, FormularioContatosActivity.class);
                startActivity(intentVaiProFormularioContatos);
            }
        });

        registerForContextMenu(listaContatos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);
                Toast.makeText(ListaContatosActivity.this, "Deletar o contato " + contato.getNome(), Toast.LENGTH_SHORT).show();

                ContatoDAO dao = new ContatoDAO(ListaContatosActivity.this);
                dao.deleta(contato);
                dao.close();

                carregaLista();
                return false;
            }
        });
    }

    private void carregaLista() {
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contato = dao.buscaContatos();
        dao.close();

        ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contato);
        listaContatos.setAdapter(adapter);
    }
}