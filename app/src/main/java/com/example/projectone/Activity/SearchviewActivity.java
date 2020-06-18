package com.example.projectone.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.projectone.R;

import java.util.ArrayList;

public class SearchviewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ListView lvToolbarSerch;

    String[] bookList = new String[]{"In Search of Lost Time", "Ulysses", "Don Quixote", " The Great Gatsby", "One Hundred Years of Solitude", "Moby Dick","War and Peace","Lolita"," Hamlet",
    "The Catcher in the Rye","The Odyssey","The Brothers Karamazov ","Crime and Punishment","Madame Bovary","The Divine Comedy","The Adventures of Huckleberry Finn","Alice's Adventures in Wonderland",
    "Pride and Prejudice","Wuthering Heights","To the Lighthouse"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);
        setUpViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.widget.SearchView searchView = (android.widget.SearchView) menu.findItem(R.id.search).getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setQueryHint("Search Books");
        searchView.setOnQueryTextListener(this);

        return true;
    }
    private void setUpViews() {

        lvToolbarSerch =(ListView) findViewById(R.id.lv_toolbarsearch);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bookList);
        lvToolbarSerch.setAdapter(adapter);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
}