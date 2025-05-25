package ru.mirea.nesterovpv.employeedb;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etName, etRealName, etPower, etOrigin;
    TextView tvHeroes;
    Button btnAdd;

    SuperheroDao heroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etRealName = findViewById(R.id.etRealName);
        etPower = findViewById(R.id.etPower);
        etOrigin = findViewById(R.id.etOrigin);
        tvHeroes = findViewById(R.id.tvHeroes);
        btnAdd = findViewById(R.id.btnAdd);

        heroDao = App.getInstance().getDatabase().superheroDao();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Superhero hero = new Superhero();
                hero.name = etName.getText().toString();
                hero.realName = etRealName.getText().toString();
                hero.power = etPower.getText().toString();
                hero.origin = etOrigin.getText().toString();

                heroDao.insert(hero);
                showHeroes();
            }
        });

        showHeroes();
    }

    private void showHeroes() {
        List<Superhero> heroes = heroDao.getAll();
        StringBuilder sb = new StringBuilder();
        for (Superhero h : heroes) {
            sb.append("🦸 ").append(h.name)
                    .append(" (").append(h.realName).append(")\n")
                    .append("Сила: ").append(h.power).append("\n")
                    .append("Откуда: ").append(h.origin).append("\n\n");
        }
        tvHeroes.setText(sb.toString());
    }
}