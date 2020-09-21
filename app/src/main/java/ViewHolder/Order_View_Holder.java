package ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppyadmin.R;

import Interface.ItemClickListner;

public class Order_View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView textusername,textphone,textprice,textdatetime,textcity;
    public ItemClickListner listner;
    public Button showallproduct;

    public Order_View_Holder(@NonNull View itemView)
    {
        super(itemView);

        textusername = (itemView).findViewById(R.id.aousername);
        textphone = (itemView).findViewById(R.id.aophone);
        textprice = (itemView).findViewById(R.id.aototalprice);
        textdatetime = (itemView).findViewById(R.id.aodatetime);
        textcity = (itemView).findViewById(R.id.aoaddcity);

        showallproduct = (itemView).findViewById(R.id.aoshowallprobtn);
    }

    public void setItemclickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
            listner.onClick(v, getAdapterPosition(),false);
    }
}
