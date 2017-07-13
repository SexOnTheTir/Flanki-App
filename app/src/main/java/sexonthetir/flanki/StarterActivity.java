package sexonthetir.flanki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("0e8049b955ce5335bd2cf03dd48a8b6a25f81af3")
                .clientKey("6c6b844b42596dd11dcf13a0944bebcf1f80ea23")
                .server("http://ec2-13-59-155-22.us-east-2.compute.amazonaws.com:80/parse").build());
        Log.i("Hello", "Work");
        ParseUser.enableAutomaticUser();
        ParseACL def = new ParseACL();
        def.setDefaultACL(def, true);
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
