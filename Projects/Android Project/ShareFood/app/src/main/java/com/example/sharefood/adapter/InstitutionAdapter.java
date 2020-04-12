package com.example.sharefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.R;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.Institution;

import java.util.ArrayList;
import java.util.List;

public class InstitutionAdapter extends RecyclerView.Adapter<InstitutionAdapter.InstitutionHolder> {

    private List<Institution> institutionList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public InstitutionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_institute, parent, false);

        return new InstitutionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstitutionHolder holder, int position) {
        Institution institution = institutionList.get(position);
        holder.textViewTitle.setText(institution.getNome());
        holder.textViewDescription.setText(institution.getMissao());
        holder.textViewDistance.setText("100m");
    }

    @Override
    public int getItemCount() {
        return institutionList.size();
    }

    public void setInstitutionList(List<Institution> institutionList){
        this.institutionList = institutionList;
        notifyDataSetChanged();
    }

    class InstitutionHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDistance;
        private ImageView instituteImage;

        public InstitutionHolder(@NonNull View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.institute_title);
            textViewDescription = view.findViewById(R.id.institute_description);
            textViewDistance = view.findViewById(R.id.institute_distance);
            instituteImage = view.findViewById(R.id.institute_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(institutionList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Institution institution);
    }

    public void filterList(List<Institution> filteredInstitutions) {
        this.institutionList = filteredInstitutions;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
