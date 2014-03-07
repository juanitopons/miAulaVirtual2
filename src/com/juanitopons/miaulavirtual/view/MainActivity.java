package com.juanitopons.miaulavirtual.view;

import java.util.Locale;

import com.juanitopons.miaulavirtual.R;
import com.juanitopons.miaulavirtual.model.ConnectionDetector;
import com.juanitopons.miaulavirtual.model.ConnectionTask;
import com.juanitopons.miaulavirtual.model.AulaVirtualAdapter;
import com.juanitopons.miaulavirtual.model.MainAdapter;
import com.juanitopons.miaulavirtual.model.MyModel;
import com.juanitopons.miaulavirtual.model.MyRequest;
import com.juanitopons.miaulavirtual.model.Parser;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private static MyModel model;
    private static MyRequest request;
    private static Parser parser;
    private static ConnectionDetector connector;
    private static MainAdapter[] adapters = new MainAdapter[2];
    private static Context context;
    
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); /** IMPORTANT **/
        model = MyModel.getInstance();
        connector = ConnectionDetector.getInstance(model.getContext());
        parser = Parser.getInstance();
        request = MyRequest.getInstance();
        context = this.getBaseContext();
        adapters[MyModel.AULAVIRTUAL] = new AulaVirtualAdapter(this);

        ConnectionTask taskToWait = new ConnectionTask(this, request, parser, Integer.valueOf(MyModel.AULAVIRTUAL));
        new ConnectionTask(this, request, parser, MyModel.POST, taskToWait).execute(""); /** IMPORTANT **/

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public static void showError(int error) {
        switch(error) {
            case 0:
                Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.toast_5), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.bad_data), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.context, MainActivity.context.getString(R.string.toast_5), Toast.LENGTH_LONG).show();
                break;
        }
    }
    
    public void moveToLogin() {
        model.setUser("0");
        model.setPass("0");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    /**
     * @return the adapters
     */
    public static MainAdapter[] getAdapters() {
        return adapters;
    }

    /**
     * @return the adapter
     */
    public static MainAdapter getAdapter(int mode) {
        return adapters[mode];
    }

    /**
     * @param adapters the adapter to set
     */
    public static void setAdapters(MainAdapter[] adapters) {
        MainActivity.adapters = adapters;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";
        private static View[] rootView = new View[3];

        public DummySectionFragment() {}

        public View getViewByFragment(int i) {
            return  rootView[i-1];
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int fragment = getArguments().getInt(ARG_SECTION_NUMBER);
            switch(fragment) {
                case 1: /** Aula Virtual **/
                    rootView[fragment-1] = inflater.inflate(R.layout.activity_display_message, container, false);
                    
                    // Actualizamos nombre de LblSubTitulo
                    TextView headerTitle = (TextView) rootView[fragment-1].findViewById(R.id.LblSubTitulo); // Título Header
                    headerTitle.setTextColor(getResources().getColor(R.color.list_title));
                    headerTitle.setTypeface(null, 1);
                    headerTitle.setText("Documentos");
                    
                    ListView lstDocs = (ListView) rootView[fragment-1].findViewById(R.id.LstDocs); // Declaramos la lista
                    lstDocs.setAdapter(adapters[MyModel.AULAVIRTUAL]); // Declaramos nuestra propia clase adaptador como adaptador
                    
                    /** FIN **/
                    break;
                case 3: /** Tareas **/
                    rootView[fragment-1] = inflater.inflate(R.layout.load, container, false);
                    break;
                default:
                    rootView[fragment-1] = inflater.inflate(R.layout.fragment_main_dummy, container, false);
                    TextView dummyTextView = (TextView) rootView[fragment-1].findViewById(R.id.section_label);
                    dummyTextView.setText(Integer.toString(fragment));
                    break;     
            }
            
            /*TextView dummyTextView = (TextView) rootView
                    .findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
                    */
            
            
            return getViewByFragment(fragment);
        }
    }
}
