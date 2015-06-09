package com.example.krishna.badgecountapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.ArrayList;


public class ImageLoaderActivity extends ActionBarActivity {

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        imageLoader = ImageLoader.getInstance();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(this)
                        // default = device screen dimensions
                .threadPoolSize(5).threadPriority(Thread.MAX_PRIORITY).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(80 * 1024 * 1024).diskCacheFileCount(200).build();

        imageLoader.init(config);

        ListView lvFiles= (ListView) findViewById(R.id.lv_images);
        ArrayList<String> imgList=new ArrayList<>();
        imgList.add(""+R.drawable.ball);
        imgList.add(""+R.drawable.wood);
        imgList.add(""+R.drawable.frame_one);
        imgList.add(""+R.drawable.frame_two);
        imgList.add(""+R.drawable.ball);
        imgList.add(""+R.drawable.wood);
        imgList.add(""+R.drawable.frame_one);
        imgList.add(""+R.drawable.frame_two);
        imgList.add(""+R.drawable.ball);
        imgList.add(""+R.drawable.wood);
        imgList.add(""+R.drawable.frame_one);
        imgList.add(""+R.drawable.frame_two);
        //CustomAdapter adapter=new CustomAdapter(loadUriImages());
        CustomAdapter adapter=new CustomAdapter(imgList);
        lvFiles.setAdapter(adapter);
    }

    class CustomAdapter extends BaseAdapter {

        private ArrayList<String> list;

        CustomAdapter(ArrayList<String> list){

            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.imageloader_item, null);
            }
            ImageView loaderItem = (ImageView) convertView.findViewById(R.id.iv_loader_item);
            loaderItem.setImageBitmap(null);

           // imageLoader.displayImage("file://"+list.get(position), loaderItem);
            imageLoader.displayImage("drawable://"+list.get(position), loaderItem);
            return convertView;
        }
    }

    public ArrayList<String> loadUriImages(){

        ArrayList<String> filePaths=new ArrayList<>();

        // which image properties are we querying
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };

        // Get the base URI for the People table in the Contacts content provider.
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cur = getContentResolver().query(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );

        Log.i("ListingImages", " query count=" + cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String filePath;
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATE_TAKEN);
            int filePathCol=cur.getColumnIndex(MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                filePath=cur.getString(filePathCol);

                // Do something with the values.
                Log.i("ListingImages", " bucket=" + bucket
                        + "  date_taken=" + date);
                filePaths.add(filePath);
            } while (cur.moveToNext());
        }
        cur.close();
        return filePaths;
    }
}
