package id.ac.poliban.e020320066.contactappe020320066;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.contactViewHolder> {

    private final Context  context;
    private final ArrayList<ModelContact> contactList;
    private DbHelper dbHelper;

    // add constructor
    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        contactViewHolder contactViewHolder = new contactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {

        ModelContact modelContact = contactList.get(position);

        // get data
        String id = modelContact.getId();
        String image = modelContact.getImage();
        String name = modelContact.getName();
        String phone = modelContact.getPhone();
        String email = modelContact.getEmail();
        String note = modelContact.getNote();
        String addedTime = modelContact.getAddedTime();
        String updatedTime = modelContact.getUpdatedTime();

        // set data in view
        holder.contactName.setText(name);
        if (image.equals("")) {
            holder.contactImage.setImageResource(R.drawable.ic_baseline_person_24);
        }else {
            holder.contactImage.setImageURI(Uri.parse(image));
        }

        //handle click listener
        holder.contactDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // handle item click
        holder.relativeLayout.setOnClickListener(view -> {
            // create intent to move to contactsDetails
            Intent intent = new Intent(context, ContactDetails.class);
            intent.putExtra("ContactId", id);
            context.startActivity(intent);
            Toast.makeText(context, "HelLo", Toast.LENGTH_SHORT).show();
        });

        //handle editBtn click
        holder.contactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create intent to move AddEditActivity
                Intent intent = new Intent(context,AddEditContact.class);
                //pass the value of current position
                intent.putExtra("ID",id);
                intent.putExtra("NAME",name);
                intent.putExtra("PHONE",phone);
                intent.putExtra("EMAIL",email);
                intent.putExtra("NOTE",note);
                intent.putExtra("ADDEDTIME",addedTime);
                intent.putExtra("UPDATEDTIME",updatedTime);
                intent.putExtra("IMAGE",image);

                //pass a boolean data to define it's edit purpose
                intent.putExtra("IsEditMode",true);

                //start intent
                context.startActivity(intent);
            }
        });

        holder.contactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // we need database helper class refrence
                dbHelper.deleteContact(id);

                //refresh data by calling resume state of MainActivity
                ((MainActivity)context).onResume();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

     class contactViewHolder extends RecyclerView.ViewHolder {

        //view for row_contact_item
        ImageView contactImage,contactDial;
        TextView contactName, contactEdit, contactDelete;
        RelativeLayout relativeLayout;

        public contactViewHolder(@NonNull View itemView) {
            super(itemView);

            //init view
            contactImage  = itemView.findViewById(R.id.profile);
            contactDial   = itemView.findViewById(R.id.contact_number_dial);
            contactName   = itemView.findViewById(R.id.contact_name);
            contactEdit   = itemView.findViewById(R.id.contact_edit);
            contactDelete = itemView.findViewById(R.id.contact_delete);
            relativeLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
