package com.example.aluno.cadastroprofessores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText edNome, edDisciplina, edEmail;
    ListView listView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Professor> professorList = new ArrayList<>();
    ArrayAdapter<Professor> professorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edNome       = findViewById(R.id.editTextProfessorId);
        edDisciplina = findViewById(R.id.editTextDisciplinaId);
        edEmail      = findViewById(R.id.editTextEmailId);
        listView     = findViewById(R.id.listViewId);


            iniciarFirebase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void iniciarFirebase(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id== R.id.menu_novo){

            Professor p = new Professor();
            p.setNome(edNome.getText().toString());
            p.setDisciplina(edDisciplina.getText().toString());
            p.setEmail(edEmail.getText().toString());
            p.setId(UUID.randomUUID().toString());

            databaseReference.child("Professor").child(p.getId()).setValue(p);
        }

        if(id== R.id.menu_atualizar){

        }

        if(id== R.id.menu_deletar){

        }




        return super.onOptionsItemSelected(item);
    }
}
