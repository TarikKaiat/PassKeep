package  com.project.myprojectdemo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class CustomBroadcast extends BroadcastReceiver {
    public CustomBroadcast(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        // Implicit Intent
        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("uri")));
        context.startActivity(intent1);
        //
        Toast.makeText(context, "First Receive Called", Toast.LENGTH_SHORT).show();
    }
}
