package org.zeniafrik.ui.user;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.zeniafrik.R;
import org.zeniafrik.di.Injectable;
import org.zeniafrik.extras.Validator;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.helper.Status;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.ProductCategory;
import org.zeniafrik.ui.extras.ActivityIndicatorDialog;
import org.zeniafrik.ui.extras.GenericAlertDialog;
import org.zeniafrik.ui.market.interfaces.OnItemClickListener;
import org.zeniafrik.ui.user.adapters.ImageListAdapter;
import org.zeniafrik.ui.user.viewmodels.PostSharedViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Objects.requireNonNull;

public class AdDetailActivity extends BaseActivity implements OnItemClickListener, Injectable {

    private final String[] selectItems = {"Camera","Gallery"};
    private String currentCameraImagePath;
    @Inject
    ViewModelFactory factory;

    @Inject
    PostSharedViewModel viewModel;

    @BindView(R.id.category_item_name)
    TextView category_name;

    @BindView(R.id.product_name)
    TextView product_name_input;

    @BindView(R.id.product_price)
    TextView product_price_input;

    @BindView(R.id.product_abt)
    TextView product_abt_input;

    @BindView(R.id.product_colors)
    TextView product_colors_input;

    @BindView(R.id.product_sizes)
    TextView product_sizes_input;

    @BindView(R.id.product_gender_select)
    AppCompatSpinner spinner;

    @BindString(R.string.everyone)
    public String gender_select;

    @BindString(R.string.something_went_wrong)
    public String something_wrong;

    @BindString(R.string.ad_created_success)
    public String ad_created;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image_list)
    RecyclerView image_list;

    private ImageListAdapter adapter;
    private boolean hasImage = false;
    private int position = 0, category_id;


    private ActivityIndicatorDialog indicatorDialog;

    private final int CAMERA_REQUEST_CODE = 1,
            GALLERY_REQUEST_CODE = 2;

    @OnClick(R.id.next_btn)
    public void onNext() {
        String name = product_name_input.getText().toString(),
                price = product_price_input.getText().toString(),
                description = product_abt_input.getText().toString(),
                message = null;

        double priceValue = !price.isEmpty() ? Double.parseDouble(price) : 0;

        View view = null;
        if (Validator.isLessThan(name, 10)) {
            message = "Ad name must be at least 10 characters";
            view = product_name_input;
        } else if (priceValue < 1) {
            message = "Ad price must be at least GHc 1";
            view = product_price_input;
        } else if (Validator.isLessThan(description, 60)) {
            message = "Provide enough description for this ad, a minimum of 60 characters will be okay.";
            view = product_abt_input;
        } else if (!hasImage) {
            message = "Please select at least one photo.";
            view = image_list;
        }
        if (message != null) {
            view.requestFocus();
            makeText(message);
            return;
        }

        String sizes = product_sizes_input.getText().toString(),
                colors = product_colors_input.getText().toString();

        BaseProduct mLocal = new BaseProduct(name, priceValue, description, sizes, colors, gender_select, category_id);
        viewModel.setCreatedProduct(mLocal);
        indicatorDialog.show();
    }

    @OnClick(R.id.change_btn)
    public void goBack() {
        onBackPressed();
    }


    @OnClick(R.id.tns)
    public void openTNS() {

    }

    private void checkPermission(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (code == CAMERA_REQUEST_CODE) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, code);
                else do_next(code);
            } else {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                else do_next(code);
            }

            return;
        }
        do_next(code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 0) return;

        int GRANTED = PackageManager.PERMISSION_GRANTED;

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults[0] == GRANTED)
                    do_next(requestCode);
                break;
            case GALLERY_REQUEST_CODE:
                int WRITE_PERM = grantResults[0],
                        READ_PERM = grantResults[1];
                if ((WRITE_PERM == GRANTED) || (READ_PERM == GRANTED))
                    do_next(requestCode);
        }
    }

    private void do_next(int request_code) {
        if (request_code == CAMERA_REQUEST_CODE) {
            try {
                access_device_camera();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else access_device_gallery();
    }

    private void access_device_gallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQUEST_CODE);
    }

    private void access_device_camera() throws IOException {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File image = createImageFile();

                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "org.zeniafrik.fileprovider", image);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            } else
                Toast.makeText(this, "No camera application found!", Toast.LENGTH_SHORT).show();
            return;
        }
        access_device_gallery();
    }

    @NonNull
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = SimpleDateFormat.getTimeInstance().format(new Date());

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                timeStamp,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentCameraImagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAMERA_REQUEST_CODE && currentCameraImagePath!=null){
            File f = new File(currentCameraImagePath);
            Uri imageUri =  Uri.fromFile(f);
            viewModel.setImageUriList(position,imageUri);
        }
        if (resultCode == Activity.RESULT_OK && data != null) {
            viewModel.setImageUriList(position, data.getData());
        }
        if (!hasImage) hasImage = true;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product_x2);
        ButterKnife.bind(this);
        indicatorDialog = new ActivityIndicatorDialog(this);
        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        adapter = new ImageListAdapter(this);

        viewModel = ViewModelProviders.of(this, factory).get(PostSharedViewModel.class);
        viewModel.getImageUriList().observe(this, uris -> {
            if (uris != null) adapter.setUris(uris);
        });

        viewModel.getSelectedCategory().observe(this, category -> {
            if (category != null) {
                category_name.setText(category.getName());
                category_id = category.getId();
            }
        });

        viewModel.getCreatedProduct().observe(this, product -> {
            if (product != null) {
                product_abt_input.setText(product.getLocalDes());
                product_colors_input.setText(product.getColors());
                product_name_input.setText(product.name);
                product_price_input.setText(String.valueOf(product.getPrice()));
                String a = product.getTarget() == null ? gender_select : product.getTarget();
                spinner.setSelection(((ArrayAdapter<String>) spinner.getAdapter()).getPosition(a));
            }
        });

        image_list.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        image_list.setAdapter(adapter);
        image_list.setHasFixedSize(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ProductCategory category = getIntent().getParcelableExtra("org.zeniafrik.models.ProductCategory");
        viewModel.setSelectedCategory(category);
        viewModel.initImageList();

        viewModel.getProduct_id().observe(this, product -> {
            if (product != null) {
                if (product.status == Status.ERROR) {
                    makeText(something_wrong);
                    indicatorDialog.hide();
                } else if (product.status == Status.SUCCESS) {
                    if (product.data != null) {
                        int b = product.data.getData();
                        extractBitmapFromURI(b);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fixInputMethod(this);
        adapter = null;
        indicatorDialog.dismiss();
        indicatorDialog = null;
    }

    @Override
    public void onClick(View view, int position) {
        this.position = position;
        new AlertDialog.Builder(AdDetailActivity.this)
                .setTitle("Get photo from...")
                .setItems(selectItems,(d,i)->{
                    if(i==0) checkPermission(CAMERA_REQUEST_CODE);
                    else  checkPermission(GALLERY_REQUEST_CODE);
                }).show();
    }

    private void makeText(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(AdDetailActivity.this)
                .setTitle("Continue posting ad?")
                .setNegativeButton("No", (dialogInterface, i) -> super.onBackPressed())
                .setPositiveButton("Yes",(di,i)->di.dismiss())
                .show();
    }

    private String getBase64String(Uri uri) {
        try {
            InputStream stream = getContentResolver().openInputStream(uri);
            if (stream != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeStream(stream,null,options);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void extractBitmapFromURI(int product_id) {
        ArrayList imageURIS = viewModel.getImageUriList().getValue();
        if (imageURIS != null) {
            int size = imageURIS.size();

            ArrayList<String> imageStrings = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Uri imageUri = (Uri) imageURIS.get(i);
                if (imageUri != null){
                    String base64String = getBase64String(imageUri);

                    if (base64String != null) imageStrings.add(base64String);
                }
            }
            if (!imageStrings.isEmpty()) {
                viewModel.uploadImages(imageStrings, product_id).observe(AdDetailActivity.this,v->{
                    if(v != null){
                        if(v.status == Status.SUCCESS)
                            new GenericAlertDialog(this, true, dialogInterface -> finish(), ad_created, 1).show();
                    }
                });
            }
        }

    }

}
