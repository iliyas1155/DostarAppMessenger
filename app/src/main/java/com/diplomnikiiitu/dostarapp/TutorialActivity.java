package com.diplomnikiiitu.dostarapp;

import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;

public class TutorialActivity extends com.hololo.tutorial.library.TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addFragment(new Step.Builder().setTitle("Добро пожаловать!")
                .setContent("Наше приложение создано для помощи в поиске работы сиротам.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.main_menu) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("Вакансии")
                .setContent("В разделе Вакагнсии вы можете выбрать из предложенных вакансий.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.vacancies) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("Вакансия")
                .setContent("Нажав на кнопку \"" + getString(R.string.respond) + "\" вы отправите свои данные из Профиля тому, кто разместил данную вакансию.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.vacancy) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("Профиль")
                .setContent("В разделе Профиль вы можете редактировать свои данные для отзыва на вакансии.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.profile) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("Курсы")
                .setContent("В разделе Курсы вы можете выбрать курс себе по душе. Например \"музыка\", и научиться играть на гитаре. Или выбрать \"программирование\", и научиться программировать.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.courses_filtered) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("Курс")
                .setContent("В самом курсе вам будет предоставлено обучающее видео и описание под ним.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.course) // int top drawable
                .build());
        addFragment(new Step.Builder().setTitle("Форум")
                .setContent("В разделе Форум вы можете выбрать интересующую вас тему, и пообщаться с другими людьми.")
                .setBackgroundColor(Color.parseColor("#5c638c")) // int background color
                .setDrawable(R.drawable.forum) // int top drawable
                .build());
        addFragment(new PermissionStep.Builder().setTitle("Начнём!")
                .setContent("В начале вам будет нужно зарегистрироваться")
                .setBackgroundColor(Color.parseColor("#5c638c"))
                .setDrawable(R.drawable.lets_go)
                .build());
    }
}
