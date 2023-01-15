package id.ac.poliban.e020320066.contactappe020320066;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {
    //view
    private TextView tv_Name, tv_Phone, tv_Email, tv_Added_Time, tv_Updated_Time, tv_Note;
    private ImageView ivProfile;
    private String id;

    //database
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //init db
        dbHelper = new DbHelper(this);

        // get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("ContactId");

        //init view
        tv_Name  = findViewById(R.id.tv_Name);
        tv_Phone = findViewById(R.id.tv_Phone);
        tv_Email = findViewById(R.id.tv_email);
        tv_Added_Time = findViewById(R.id.tv_Added_Time);
        tv_Updated_Time = findViewById(R.id.tv_Updated_Time);
        tv_Note = findViewById(R.id.tv_Note);

        ivProfile = findViewById(R.id.iv_Profile);

        loadDataById();

    }

    private void loadDataById() {
        //get data from database
        //query for find data by id
        String selectQuery = "SELECT * FROM " +Constants.TABLE_NAME + "WHERE " + Constants.C_ID +
                " = \"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                //get data
                String name  = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                String phone =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String note  = ""+cursor.getString (cursor.getColumnIndexOrThrow(Constants.C_NOTE));
                String addTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                String updateTime = "" +cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                // convert time to dd/mm/yy hh:mm:aa format
                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = "" + DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = "" + DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                //set data
                tv_Name.setText(name);
                tv_Phone.setText(phone);
                tv_Email.setText(email);
                tv_Note.setText(note);
                tv_Added_Time.setText(timeAdd);
                tv_Updated_Time.setText(timeUpdate);

                if (image.equals("null")) {
                    ivProfile.setImageResource(R.drawable.ic_baseline_person_24);
                } else {
                    ivProfile.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }

        db.close();

    }

}

// run app
// we have error in profileIv,dbHelper initialization
// we successfully read our db and show details