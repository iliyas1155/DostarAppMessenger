package com.example.madina.dostarapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Vacancy;
import com.example.madina.dostarapp.R;

import java.util.ArrayList;
import java.util.List;

public class VacanciesAdapter extends RecyclerView.Adapter<VacanciesAdapter.VacancyViewHolder> 
        implements Filterable {

    private List<Vacancy> vacancies;
    private List<Vacancy> filteredVacancies;
    
    public VacanciesAdapter(List<Vacancy> vacancies){
        this.vacancies = new ArrayList<>(vacancies);
        this.filteredVacancies = new ArrayList<>(vacancies);
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        VacancyViewHolder pvh = new VacancyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder vacancyViewHolder, int i) {
        vacancyViewHolder.vacancy_name.setText(filteredVacancies.get(i).name);
        vacancyViewHolder.vacancy_desc.setText(filteredVacancies.get(i).desc);
    }

    @Override
    public int getItemCount() {
        return filteredVacancies.size();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                List<Vacancy> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = vacancies;
                } else {
                    for (Vacancy vacancy : vacancies) {
                        if (vacancy.name.contains(query)) {
                            filtered.add(vacancy);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence query, FilterResults filterResults) {
                filteredVacancies = (ArrayList<Vacancy>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setFilter(String chosenCategory, String chosenRegion, String chosenCity) {
        if (chosenCategory == null && chosenRegion == null && chosenCity == null) {
            filteredVacancies = new ArrayList<>(vacancies);
        } else {
            filteredVacancies.clear();
            for (Vacancy vacancy : vacancies) {
                Log.d("check", vacancy.category + "\t" + vacancy.region + "\t" + vacancy.city);
                if ((chosenCategory == null || vacancy.category.equals(chosenCategory))
                        && (chosenRegion == null || vacancy.region.equals(chosenRegion))
                        && (chosenCity == null || vacancy.city.equals(chosenCity))) {
                    filteredVacancies.add(vacancy);
                }
            }
        }
        notifyDataSetChanged();
    }
}