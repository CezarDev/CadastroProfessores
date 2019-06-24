package com.example.aluno.cadastroprofessores;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText edNome, edDisciplina, edEmail;
    ListView listView;
    Professor professorSelecionado;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Professor> professorList = new ArrayList<>();
    ArrayAdapter<Professor> professorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edNome = findViewById(R.id.editTextProfessorId);
        edDisciplina = findViewById(R.id.editTextDisciplinaId);
        edEmail = findViewById(R.id.editTextEmailId);
        listView = findViewById(R.id.listViewId);


        iniciarFirebase();

        eventoFirebase();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                professorSelecionado = (Professor) parent.getItemAtPosition(position);
                edNome.setText(professorSelecionado.getNome());
                edDisciplina.setText(professorSelecionado.getDisciplina());
                edEmail.setText(professorSelecionado.getEmail());
            }
        });


    }

    private void eventoFirebase() {
        databaseReference.child("Professor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                professorList.clear();

                for (DataSnapshot obDataSnapshot1 : dataSnapshot.getChildren()) {
                    Professor p = obDataSnapshot1.getValue(Professor.class);
                    professorList.add(p);
                }

                professorArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, professorList);

                listView.setAdapter(professorArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_novo) {

            Professor p = new Professor();
            p.setNome(edNome.getText().toString());
            p.setDisciplina(edDisciplina.getText().toString());
            p.setEmail(edEmail.getText().toString());
            p.setId(UUID.randomUUID().toString());

            databaseReference.child("Professor").child(p.getId()).setValue(p);

            limparCampos();
        }
            if (id == R.id.menu_atualizar) {

            }

            if (id == R.id.menu_deletar) {

            }


            return super.onOptionsItemSelected(item);
        }

        private void limparCampos(){
                edNome.setText("");
                edDisciplina.setText("");
                edEmail.setText("");
        }

}

