package com.example.pets_donation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.List;

public class AnimalAdocaoListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ProcessoAdocao> processoAdocaoList;
    private Adotante adotante;
    private Conexao_Banco banco;

    public AnimalAdocaoListAdapter(Context context, int layout, List<ProcessoAdocao> processoAdocaoList) {
        this.context = context;
        this.layout = layout;
        this.processoAdocaoList = processoAdocaoList;
    }


    @Override
    public int getCount() {
        return processoAdocaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return processoAdocaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textNome, textInfo;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        banco = new Conexao_Banco(context);
        View row = convertView;
        AnimalAdocaoListAdapter.ViewHolder viewHolder = new AnimalAdocaoListAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            row.setOnClickListener(null);

            viewHolder.textNome = (TextView) row.findViewById(R.id.title);
            viewHolder.textInfo = (TextView) row.findViewById(R.id.subtitle);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.icon);
            row.setTag(viewHolder);
        } else {
            viewHolder = (AnimalAdocaoListAdapter.ViewHolder) row.getTag();
        }

        ProcessoAdocao processoAdocao = processoAdocaoList.get(position);

        Animal animal = banco.obterAnimal(processoAdocao.getIDAnimal());
        viewHolder.textNome.setText(animal.getNome());
        viewHolder.textInfo.setText("Status: " + processoAdocao.getStatus());

        viewHolder.imageView.setImageBitmap(animal.getFoto());

        viewHolder.imageView.setFocusableInTouchMode(false);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LISTA", "Nome: " + animal.getNome());
                Intent intent = new Intent(context, ProcessoAdocao_Visualizar.class);
                intent.putExtra("adocao", processoAdocao);
                context.startActivity(intent);
            }
        });
        return row;
    }

}