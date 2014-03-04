package com.juanitopons.miaulavirtual.view;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.select.Elements;

import com.juanitopons.miaulavirtual.R;
import com.juanitopons.miaulavirtual.model.ConnectionDetector;
import com.juanitopons.miaulavirtual.model.ListAdapter;
import com.juanitopons.miaulavirtual.model.BadDataException;
import com.juanitopons.miaulavirtual.model.MyModel;
import com.juanitopons.miaulavirtual.model.MyRequest;
import com.juanitopons.miaulavirtual.model.Parser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private static MyModel model;
    private static MyRequest request;
    private static Parser parser;

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
        setContentView(R.layout.activity_main);

        model = MyModel.modelInstance;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

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
        private View[] rootView = new View[3];
        private ConnectionDetector connector;

        public DummySectionFragment() {}

        public View getViewByFragment(int i) {
            return  rootView[i-1];
        }
        
        public void showError(int error) {
            switch(error) {
                case 0:
                    Toast.makeText(this.getActivity().getBaseContext() ,getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(this.getActivity().getBaseContext() ,getString(R.string.toast_5), Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(this.getActivity().getBaseContext() ,getString(R.string.bad_data), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this.getActivity().getBaseContext() ,getString(R.string.toast_5), Toast.LENGTH_LONG).show();
                    break;
            }
        }
        
        public void setViewOnError(ViewGroup container) {
            /** rootView[0] = inflater.inflate(R.layout.error_page, container, false);  sección aulavirtual
            rootView[2] = inflater.inflate(R.layout.error_page, container, false); sección tareas **/
                    
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            
            connector = new ConnectionDetector(this.getActivity().getApplicationContext());
            parser = new Parser();
            request = new MyRequest(connector, model);
            
            int fragment = getArguments().getInt(ARG_SECTION_NUMBER);
            DoConnection taskConnection = new DoConnection(this.getTargetFragment(), container); /* IMPORTANT */
            try {
                taskConnection.execute("").get(10*1000, TimeUnit.MILLISECONDS);
                switch(fragment) {
                    case 1: /** Aula Virtual **/
                        rootView[fragment-1] = inflater.inflate(R.layout.load, container, false);
                        
                        /** Reformatear **/
                        Elements elements = null;
                        String[] carpetas;
                        String[] enlaces;
                        String[] tipos;
                        
                        taskConnection = new DoConnection(this.getTargetFragment(), container);
                        taskConnection.execute(model.getPanel()).get(10*1000, TimeUnit.MILLISECONDS); /** IMPORTANT **/
                        
                        try {
                            elements = parser.parseDocuments(Jsoup.parse(request.getResp().body()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        carpetas = parser.carpetasToArray(elements);
                        enlaces = parser.enlacesToArray(elements);
                        tipos = parser.tiposToArray(elements, enlaces.length);
                        
                        rootView[fragment-1] = inflater.inflate(R.layout.activity_display_message, container, false);
                        
                        // Actualizamos nombre de LblSubTitulo
                        TextView headerTitle = (TextView) rootView[fragment-1].findViewById(R.id.LblSubTitulo); // Título Header
                        headerTitle.setTextColor(getResources().getColor(R.color.list_title));
                        headerTitle.setTypeface(null, 1);
                        headerTitle.setText("Documentos");
                        
                        ListView lstDocs = (ListView) rootView[fragment-1].findViewById(R.id.LstDocs); // Declaramos la lista
                        ListAdapter lstAdapter = new ListAdapter(this.getActivity(), carpetas, tipos);
                        lstDocs.setAdapter(lstAdapter); // Declaramos nuestra propia clase adaptador como adaptador
                        lstDocs.setAdapter(lstAdapter);
                        
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
                
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (TimeoutException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                
            }
            
            /*TextView dummyTextView = (TextView) rootView
                    .findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
                    */
            
            
            return getViewByFragment(fragment);
        }
    }
    
    private static class DoConnection extends AsyncTask<String, Integer, Integer> {
        DummySectionFragment dummy;
        ViewGroup container;
        
        protected DoConnection(Fragment dummy, ViewGroup container) {
            this.dummy = (DummySectionFragment) dummy;
            this.container = container;
        }
        
        protected Integer doInBackground(String... parameters) {
            Integer state = -1;
            try {
                if(!parameters[0].isEmpty()) {
                    request.doGet(parameters[0]);
                } else {
                    request.doPostUrl1();
                    request.doGetUrl2();
                }
            } catch (SocketTimeoutException e) {
                state = MyModel.NOINTERNET;
                this.cancel(true);
            } catch (IOException e) {
                state = MyModel.RANDOM;
                this.cancel(true);
            } catch (BadDataException e) {
                state = MyModel.BADDATA;
                this.cancel(true);
            }
            return state;
        }

        protected void onProgressUpdate(Integer... progress) {}
        
        protected void onCancelled(Integer state) {
            dummy.showError(state);
            dummy.setViewOnError(container);
            
        }

        protected void onPostExecute(Integer state) {
            // Aquí la tarea siempre habrá finalizado correctamente
        }
    }

}
