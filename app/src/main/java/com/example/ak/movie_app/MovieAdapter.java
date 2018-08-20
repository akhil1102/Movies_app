package com.example.ak.movie_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> list_movies = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context context, ArrayList<Movie> list_movies) {
        super(context, 0, list_movies);
        mContext = context;
        this.list_movies = list_movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Movie currentMovie = list_movies.get(position);
        ImageView image = listItem.findViewById(R.id.image1);
        if(currentMovie != null){
            new DownloadImage(image).execute(currentMovie.getImage_url());
        }

        TextView text1 = listItem.findViewById(R.id.tv_title);
        text1.setText(currentMovie.getTitle());

        return listItem;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView bmImage;
        public DownloadImage(ImageView bmImage){
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bmp = null;
            try{
                InputStream in = new java.net.URL(url).openStream();
                bmp = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Error",e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }
}
