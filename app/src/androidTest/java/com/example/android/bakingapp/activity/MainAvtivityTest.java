package com.example.android.bakingapp.activity;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.example.android.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;


@RunWith(JUnit4.class)
@LargeTest
public class MainAvtivityTest {
    private IdlingResource mIdeling;



    @Rule
    public ActivityTestRule<MainActivity> mActivityTest =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void registerIdelingResourses(){
        mIdeling = mActivityTest.getActivity().getIdlingResource();
        Log.d(".Registring" , "Registring"+mIdeling.getName());
        Espresso.registerIdlingResources(mIdeling);
        IdlingRegistry.getInstance().register(mIdeling);

    }
    @Test
    public void tryWork(){
       onView(ViewMatchers.withId(R.id.backingRecycler))
               .perform(RecyclerViewActions.actionOnItemAtPosition(0 , ViewActions.click()));

       // onView(ViewMatchers.withId(R.id.backingRecycler)).perform(RecyclerViewActions.scrollToPosition(1));
//        onView(withText("Brownies")).check(matches(isDisplayed()));



    }
    @After
    public void unRegisterIdeling(){
        if(mIdeling !=null){
            Espresso.unregisterIdlingResources(mIdeling);
            IdlingRegistry.getInstance().unregister(mIdeling);
        }

    }

    }
