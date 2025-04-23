package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class InfoScreen extends AppCompatActivity {
    FirebaseFirestore db;
    TextView title, bookisbn, author, description, length, price;
    ImageView image;
    Button back, buy;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        String isbn = getIntent().getStringExtra("book_isbn");
        Toast.makeText(this, isbn, Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.textViewOneBookTitle);
        bookisbn = findViewById(R.id.textViewBookIsbn);
        author = findViewById(R.id.textViewOneBookAuthor);
        description = findViewById(R.id.textViewOneBookDescription);
        length = findViewById(R.id.textViewOneBookLength);
        price = findViewById(R.id.textViewOneBookPrice);
        image = findViewById(R.id.imageViewOneBookImage);
        buy = findViewById(R.id.buttonBuy);
        back = findViewById(R.id.buttonBackToBookListPage);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(InfoScreen.this, ActivitiesRecyclerViewActivity.class);
            startActivity(intent);
        });

        /*buy.setOnClickListener(view -> {
            Intent intent = new Intent(BookPageActivity.this, GoodbyeActivity.class);
            intent.putExtra("bookName", title.getText().toString());
            intent.putExtra("bookPrice", price.getText().toString());
            startActivity(intent);
        });
*/
        db = FirebaseFirestore.getInstance();
        CollectionReference isbnRef = db.collection("Book");

        isbnRef.whereEqualTo("bookIsbn", isbn).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    String bookAuthor = document.getString("bookAuthor");
                    String bookDescription = document.getString("bookDescription");
                    String bookIsbn = document.getString("bookIsbn");
                    Long bookLengthLong = document.getLong("bookLength");
                    String bookGenre = document.getString("bookGenre");
                    Long bookImage = document.getLong("bookImage");
                    String bookName = document.getString("bookName");
                    Long bookPriceLong = document.getLong("bookPrice");

                    int bookLength = (bookLengthLong != null) ? bookLengthLong.intValue() : 0;
                    int bookPrice = (bookPriceLong != null) ? bookPriceLong.intValue() : 0;

                    title.setText(bookName);
                    bookisbn.setText("ISBN: " + bookIsbn);
                    author.setText(bookGenre + ", written by: " + bookAuthor);
                    description.setText("Book description: " + bookDescription);
                    length.setText("The book's length: " + bookLength + " pages");
                    price.setText("Price: " + bookPrice + " NIS");

                    // תוכל להוסיף טעינת תמונה אם תשתמש ב-Glide או בפיירבייס סטורג'
                }
            }
        });
    }
}
