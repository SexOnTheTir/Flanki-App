package sexonthetir.flanki;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Witaj, " + email.substring(0,email.indexOf("@")) + "!");
    }
}
