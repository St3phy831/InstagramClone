package com.example.instagramclone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener{
    private Context context;
    private List<Post> posts;
    private Post post;

    LinearLayout contain;

    public static final String TAG = "Adapter";


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        contain = view.findViewById(R.id.container);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        post = posts.get(position);
        holder.bind(post);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> postList){
        posts.addAll(postList);
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;

        public ViewHolder (@NonNull View itemView){
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);

        }

        public void bind(final Post post) {
            //Bind data tp view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
            contain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Gets the posts & current user

                    String user = post.getUser().getUsername();
                    String current = ParseUser.getCurrentUser().getUsername();
                    //Shows pop up if user's post
                    if(current.equals(user)){
                        showPopUp(view);
                    }
                }
            });
        }
    }

    //creates pop up
    protected void showPopUp(View view) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.pop_up);
        popup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.delete:
                deletePost();
                return true;
            default:
                return false;
        }
    }


    private void deletePost() {
        //Deletes the entire ParseObject from the database
        post.deleteInBackground();
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
    }
}