package me.caixinha.caixinhame;

import android.widget.EditText;
import android.widget.RatingBar;

import me.caixinha.caixinhame.modelo.Contato;

/**
 * Created by dsouz on 16/09/2017.
 */

public class FormularioContatosHelper {

    private final EditText campoNome;
    private final EditText campoTelefone1;
    private final EditText campoTelefone2;
    private final EditText campoEmail;

    private Contato contato;

    public FormularioContatosHelper(FormularioContatosActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_contatos_nome);
        campoTelefone1 = (EditText) activity.findViewById(R.id.formulario_contatos_telefone1);
        campoTelefone2 = (EditText) activity.findViewById(R.id.formulario_contatos_telefone2);
        campoEmail = (EditText) activity.findViewById(R.id.formulario_contatos_email);
        contato = new Contato();
    }

    public Contato getContato() {
        contato.setNome(campoNome.getText().toString());
        contato.setTelefone1(campoTelefone1.getText().toString());
        contato.setTelefone2(campoTelefone2.getText().toString());
        contato.setEmail(campoEmail.getText().toString());

        return contato;
    }

    public void preencheFormularioContatos(Contato contato) {
        campoNome.setText(contato.getNome());
        campoTelefone1.setText(contato.getTelefone1());
        campoTelefone2.setText(contato.getTelefone2());
        campoEmail.setText(contato.getEmail());
        this.contato = contato;
    }
}
