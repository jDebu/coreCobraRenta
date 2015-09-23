package nl.everlutions.recyclerviewdragndrop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import nl.everlutions.recyclerviewdragndrop.dialogs.FDialogDetalle;

/**
 * Created by jose on 14/09/15.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            crearFullScreenDialog();
        }
    }

    private void crearFullScreenDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FDialogDetalle newFragment = new FDialogDetalle();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment, "FDialogDetalle")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
