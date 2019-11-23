package org.zeniafrik.ui.market;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.zeniafrik.R;
import org.zeniafrik.factory.ViewModelFactory;
import org.zeniafrik.helper.BaseActivity;
import org.zeniafrik.models.BaseProduct;
import org.zeniafrik.models.ProductDetail;
import org.zeniafrik.ui.extras.GenericAlertDialog;
import org.zeniafrik.ui.market.ViewModels.ProductDetailViewModel;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static android.view.View.GONE;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.zeniafrik.helper.Status.ERROR;
import static org.zeniafrik.helper.Status.SUCCESS;

public class ProductDetailActivity extends BaseActivity implements HasSupportFragmentInjector, OnCancelListener {


    @BindView(R.id.product_name)
    TextView product_name;

    @BindView(R.id.product_price)
    TextView product_price;

    @BindView(R.id.product_rating)
    RatingBar product_rating;

    @BindView(R.id.seller)
    TextView seller;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_abt)
    TextView product_abt;

    @BindView(R.id.dess)
    LinearLayout des;

    @BindView(R.id.size)
    TextView size;

    @BindView(R.id.colors)
    TextView color;

    @BindView(R.id.mma)
    TextView mma;

    @BindView(R.id.loading)
    LinearLayout loading;

    @BindString(R.string.something_went_wrong)
    String error;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    ViewModelFactory factory;
    @Inject
    ProductDetailViewModel viewModel;

    private Unbinder unbinder;
    private String slug, name;
    private BaseProduct product;

    @OnClick(R.id.share_btn)
    public void share() {
        String _url = getString(R.string.product_url),
                product_url = format(_url, slug),
                _comment = getString(R.string.share_content),
                share_comment = format(_comment, name, product_url),
                title = getString(R.string.share_title);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, share_comment)
                .setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, title));
    }

    @OnClick(R.id.action_btn)
    public void placeOrder(){
        Intent intent = new Intent(this,OrderPlacementActivity.class);
        intent.putExtra("org.zeniafrik.models.BaseProduct", product);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        product = getIntent().getParcelableExtra("org.zeniafrik.models.BaseProduct");

        product_name.setText(product.name);
        name = product.name;

        seller.setText(product.publisher);
        product_rating.setRating((float) product.getRating());
        product_price.setText(format("%s", product.getPrice()));

        viewModel = ViewModelProviders.of(ProductDetailActivity.this, factory).get(ProductDetailViewModel.class);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final ProductGalleryFragment productGalleryFragment = (ProductGalleryFragment) fragmentManager.findFragmentById(R.id.product_gallery_slide);

        final int id = product.getId();

        viewModel.getProduct(id).observe(ProductDetailActivity.this, product_detail_request -> {
            if (Objects.requireNonNull(product_detail_request).status == SUCCESS) {
                ProductDetail product_detail = product_detail_request.data.getData();

                loading.setVisibility(GONE);
                slug = product_detail.getSlug();

                product_abt.setText(product_detail.getDescription());

                ProductDetail.Extra extra = product_detail.getExtra();
                if (extra != null) {
                    des.setVisibility(View.VISIBLE);
                    color.setText(extra.getColor());
                    size.setText(extra.getSize());
                    mma.setText(extra.getMma());
                }
                if (product_detail.getImage_count() > 1) {
                    productGalleryFragment.addGallery(product_detail.getImages());
                } else {
                    productGalleryFragment.addBaseImage(product.image_url);
                }
                viewModel.setnMap(id, product_detail.getCategory().getId());
            } else if (product_detail_request.status == ERROR)
                new GenericAlertDialog(ProductDetailActivity.this, true, this, error, 2).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        finish();
    }
}
