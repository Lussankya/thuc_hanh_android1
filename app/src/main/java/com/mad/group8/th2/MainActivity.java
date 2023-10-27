package com.mad.group8.th2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mad.group8.th2.adapter.CatAdapter;
import com.mad.group8.th2.adapter.CustomImageSpinnerAdapter;
import com.mad.group8.th2.dao.CatDAO;
import com.mad.group8.th2.model.Cat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CatAdapter catAdapter;
    private CatDAO catDAO;
    private CustomImageSpinnerAdapter spinnerAdapter;
    private EditText editTextCatName;
    private EditText editTextCatPrice;
    private EditText editTextCatDescription;
    private Spinner spinnerCatImage;
    private RecyclerView recyclerViewCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCatName = findViewById(R.id.editTextCatName);
        editTextCatPrice = findViewById(R.id.editTextCatPrice);
        editTextCatDescription = findViewById(R.id.editTextCatDescription);
        spinnerCatImage = findViewById(R.id.spinnerCatImage);
        recyclerViewCats = findViewById(R.id.recyclerViewCats);

        catDAO = new CatDAO(this);
        catDAO.open();

        List<Cat> catList = catDAO.getAllCats();

        catAdapter = new CatAdapter(this, catList);
        recyclerViewCats.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCats.setAdapter(catAdapter);

        List<Integer> imageResources = getImageResources();
        spinnerAdapter = new CustomImageSpinnerAdapter(this, imageResources);
        spinnerCatImage.setAdapter(spinnerAdapter);

        Button buttonAddCat = findViewById(R.id.buttonAddCat);
        Button buttonUpdateCat = findViewById(R.id.buttonUpdateCat);

        buttonAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddCatButtonClick();
            }
        });

        buttonUpdateCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateCatButtonClick();
            }
        });

        catAdapter.setOnItemClickListener(new CatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Cat selectedCat = catAdapter.getCatAtPosition(position);
                if (selectedCat != null) {
                    editTextCatName.setText(selectedCat.getName());
                    editTextCatPrice.setText(String.valueOf(selectedCat.getPrice()));
                    editTextCatDescription.setText(selectedCat.getDescription());
                    spinnerCatImage.setSelection(spinnerAdapter.getPosition(selectedCat.getImgId()));
                    catAdapter.setSelectedPosition(position); // Highlight the selected item
                }
            }

            @Override
            public void onDeleteClick(int position) {
                Cat catToDelete = catAdapter.getCatAtPosition(position);
                if (catToDelete != null) {
                    catDAO.deleteCat(catToDelete.getId());
                    catAdapter.deleteCat(position);
                }
            }

            @Override
            public void onUpdateClick(int position, Cat updatedCat) {
                catDAO.updateCat(updatedCat);
                catAdapter.updateCat(position, updatedCat);
            }
        });
    }

    private List<Integer> getImageResources() {
        List<Integer> imageResources = new ArrayList<>();
        imageResources.add(R.drawable.cat1);
        imageResources.add(R.drawable.cat2);
        imageResources.add(R.drawable.cat3);
        return imageResources;
    }

    private void onAddCatButtonClick() {
        String catName = editTextCatName.getText().toString();
        double catPrice = Double.parseDouble(editTextCatPrice.getText().toString());
        String catDescription = editTextCatDescription.getText().toString();
        int catImageResource = spinnerAdapter.getItem(spinnerCatImage.getSelectedItemPosition());

        Cat newCat = new Cat(0, catName, catPrice, catDescription, catImageResource);

        long catId = catDAO.createCat(newCat);

        newCat.setId((int) catId);
        catAdapter.addCat(newCat);

        editTextCatName.setText("");
        editTextCatPrice.setText("");
        editTextCatDescription.setText("");
    }

    private void onUpdateCatButtonClick() {
        int selectedPosition = catAdapter.getSelectedPosition();
        Cat selectedCat = catAdapter.getSelectedCat();

        if (selectedPosition != RecyclerView.NO_POSITION) {
            selectedCat.setName(editTextCatName.getText().toString());
            selectedCat.setPrice(Double.parseDouble(editTextCatPrice.getText().toString()));
            selectedCat.setDescription(editTextCatDescription.getText().toString());
            selectedCat.setImgId(spinnerAdapter.getItem(spinnerCatImage.getSelectedItemPosition()));

            catAdapter.updateCat(selectedPosition, selectedCat);
            catDAO.updateCat(selectedCat);

            editTextCatName.setText("");
            editTextCatPrice.setText("");
            editTextCatDescription.setText("");
            catAdapter.setSelectedPosition(RecyclerView.NO_POSITION); // Deselect the item
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        catDAO.close();
    }
}
