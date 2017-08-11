package com.wangyd.firstcode.ch06.litepal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wangyd.firstcode.Utils.BaseActivity;
import com.wangyd.firstcode.Utils.DBG;
import com.wangyd.firstcode.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LitepalActivity extends BaseActivity {

    private final static String TAG = "LitepalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);

        Button btn = (Button) findViewById(R.id.createDb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
                Toast.makeText(LitepalActivity.this, "create db", Toast.LENGTH_SHORT).show();
            }
        });

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setAuthor("PAX");
                book.setName("java");
                book.setPages(50);
                book.setPrice(16.96);
                book.setPress("know");
                boolean save = book.save();
                Toast.makeText(LitepalActivity.this, "add=" + save, Toast.LENGTH_SHORT).show();
            }
        });

        Button update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("android");
                int row = book.updateAll();
                Toast.makeText(LitepalActivity.this, "update=" + row, Toast.LENGTH_SHORT).show();
            }
        });


        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("android");
                int row = DataSupport.deleteAll(Book.class, "price < ?", "15");
                Toast.makeText(LitepalActivity.this, "delete=" + row, Toast.LENGTH_SHORT).show();
            }
        });


        Button query = (Button) findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.findAll(Book.class);
                int i = 0;
                for (Book book : books) {
                    DBG.log(TAG, "book" + (++i));
                }
                Toast.makeText(LitepalActivity.this, "query", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
