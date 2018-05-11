package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Vacancy;
import com.example.madina.dostarapp.R;

import java.util.List;

public class VacanciesAdapter extends RecyclerView.Adapter<VacanciesAdapter.VacancyViewHolder>{

    private List<Vacancy> vacancies;
    public VacanciesAdapter(List<Vacancy> vacancies){
        this.vacancies = vacancies;
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        VacancyViewHolder pvh = new VacancyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder vacancyViewHolder, int i) {
        vacancyViewHolder.vacancy_name.setText(vacancies.get(i).name);
        vacancyViewHolder.vacancy_desc.setText(vacancies.get(i).desc);
    }

    @Override
    public int getItemCount() {
        return vacancies.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class VacancyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView vacancy_name;
        TextView vacancy_desc;
        VacancyViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            vacancy_name = itemView.findViewById(R.id.item_name);
            vacancy_desc = itemView.findViewById(R.id.item_desc);
        }
    }
}