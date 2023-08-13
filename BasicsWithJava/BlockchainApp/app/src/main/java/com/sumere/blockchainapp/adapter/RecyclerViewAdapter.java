package com.sumere.blockchainapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumere.blockchainapp.R;
import com.sumere.blockchainapp.view.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CryptoHolder> {
    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#a4c2f4","#8ee5ee","#cd950c"};
    private Integer colorsSize = 6;

    private ArrayList<CryptoModel> cryptoModels;

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoModels) {
        this.cryptoModels = cryptoModels;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CryptoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new CryptoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.CryptoHolder holder, int position) {
        holder.bind(cryptoModels.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return this.cryptoModels.size();
    }

    public class CryptoHolder extends RecyclerView.ViewHolder{
        private TextView currencyName;
        private TextView valueName;
        public CryptoHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(CryptoModel cryptoModel,String[] colors,int position){
            itemView.setBackgroundColor(Color.parseColor(colors[position%colorsSize]));
            currencyName = itemView.findViewById(R.id.recyclerViewCurrencyRow);
            valueName = itemView.findViewById(R.id.recyclerViewValueRow);
            currencyName.setText(cryptoModel.getCurrency());
            valueName.setText(cryptoModel.getPrice());
        }
    }
}
