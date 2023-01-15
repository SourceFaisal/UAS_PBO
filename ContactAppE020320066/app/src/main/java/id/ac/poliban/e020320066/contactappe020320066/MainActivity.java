package id.ac.poliban.e020320066.contactappe020320066;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

// add dependency
// add color code

public class MainActivity extends AppCompatActivity {
    //view
    private FloatingActionButton fab;
    private RecyclerView contactRv;

    // db
    private DbHelper dbHelper;

    // adapter
    private AdapterContact adapterContact;


    //sort Category
    private String sortByNewest = Constants.C_ADDED_TIME + " ASC ";
    private String sortByOldest = Constants.C_ADDED_TIME + " DESC ";
    private String sortByNameAsc = Constants.C_NAME + " ASC ";
    private String sortByNameDesc = Constants.C_NAME + " DESC ";

    //set current sort order
    private String currentSort = sortByNewest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // init db
        dbHelper = new DbHelper(this);

        //initialization
        fab = findViewById(R.id.fab);
        contactRv = findViewById(R.id.contactRv);

        contactRv.setHasFixedSize(true);

        // add listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // move to new activity to add contact
                Intent intent = new Intent(MainActivity.this, AddEditContact.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
            }
        });
        
        loadData(currentSort);
    }

    private void loadData(String currentSort) {
        adapterContact = new AdapterContact(this,dbHelper.getAllData(currentSort));
        contactRv.setAdapter(adapterContact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(currentSort); // refresh data
    }

    // Make override method onCreateOptionMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_top_menu, menu);

        //get search item
        MenuItem item = menu.findItem(R.id.searchContact);

        //search area
        SearchView searchView = (SearchView) item.getActionView();

        //set max value for width
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContact(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContact(newText);
                return true;
            }
        });

        return true;
    }

    private void searchContact (String query) {

        adapterContact = new AdapterContact(this, dbHelper.getSearchContact(query));
        contactRv.setAdapter(adapterContact);
    }

    // Make override method onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteAllContact:
                dbHelper.deleteAllContact();
                onResume();
                break;
            case R.id.sortContact:
                sortDialog();
                break;
        }

        return true;
    }

    private void sortDialog() {
        //option for alert dialog
        String[] option = {"Newest","Oldest","Name Asc","Name Desc"};

        //alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort By");
    }
}