package haw_landshtu.de.digitalerbilderrahmen;




import android.content.Context;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.LinkedList;



public class SlideShowAdapter extends PagerAdapter {


    private LinkedList<ImageObject> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlideShowAdapter(Context context, LinkedList<ImageObject> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slideshow_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        SystemClock.sleep(100);

        Glide
                .with(context)

                .load(IMAGES.get(position).getUri())

                .transition(DrawableTransitionOptions.withCrossFade(1000))

                .apply(new RequestOptions()

                        .fitCenter()
                        .skipMemoryCache(true)

                        .diskCacheStrategy(DiskCacheStrategy.NONE))

                .into(imageView);
        // imageView.setImageResource(IMAGES.get(position));

        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}