package sexonthetir.flanki;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Witaj, " + ParseUser.getCurrentUser().getUsername());
        onCreateMap();
    }

    private void onCreateMap(){
        Intent mapIntent = new Intent(this,MapsActivity.class);
        this.startActivity(mapIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logoutItem)
        {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Toast.makeText(getApplicationContext(),getString(R.string.logout), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),getString(R.string.logout_error), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
