package com.diplomnikiiitu.dostarapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import static com.diplomnikiiitu.dostarapp.MainActivity.currentUser;
import static com.diplomnikiiitu.dostarapp.MainActivity.isAdmin;

public class NavViewListener implements NavigationView.OnNavigationItemSelectedListener{
    Context context;
    DrawerLayout drawer;
    public NavViewListener(Context context, DrawerLayout drawer){
        this.context = context;
        this.drawer = drawer;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent myIntent = new Intent(context, MenuActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_courses) {
            Intent myIntent = new Intent(context, CoursesActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_vacancies) {
            Intent myIntent = new Intent(context, VacanciesActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_forum) {
            Intent myIntent = new Intent(context, ForumRublickActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_support) {
            if(isAdmin == false){
                Intent myIntent = new Intent(context, ChatActivity.class);
                myIntent.putExtra("collection", "support");
                myIntent.putExtra("document", currentUser.getEmail());
                myIntent.putExtra("title", context.getString(R.string.support));
                context.startActivity(myIntent);
            }else{
                Intent myIntent = new Intent(context, AdminSupportActivity.class);
                context.startActivity(myIntent);
            }
        }
        if (id == R.id.nav_settings) {
            Intent myIntent = new Intent(context, SettingsActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_calendar) {
            Intent myIntent = new Intent(context, CalendarActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_sign_out) {
            ((SampleActivity)context).signOut();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
