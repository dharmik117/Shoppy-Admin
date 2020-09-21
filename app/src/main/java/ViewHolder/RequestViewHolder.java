package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppyadmin.R;

import Interface.ItemClickListner;

public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtshopname,txtshopdescryption,txtshopcategory,txtpermission;
    public ImageView imageView;
    public ItemClickListner listner;
    public Switch pswitch;

    public RequestViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.pic);
        txtshopname = (TextView) itemView.findViewById(R.id.tvshopname);
        txtshopdescryption = (TextView) itemView.findViewById(R.id.tvshopdes);
        txtshopcategory = (TextView) itemView.findViewById(R.id.tvshopcat);
        txtpermission = (TextView) itemView.findViewById(R.id.tvpermission);
        pswitch = (Switch) itemView.findViewById(R.id.permissionswitch);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(),false);
    }
}
