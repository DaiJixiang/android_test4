package com.example.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends AppCompatActivity {


    List<UserInfo> userInfos = new ArrayList<>(Arrays.asList(new UserInfo("张三", "12345"),
            new UserInfo("李四", "12345"), new UserInfo("王五", "12345")));

    myBaseAdapter myAdapter = new myBaseAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView listView = findViewById(R.id.listView_main);
        listView.setAdapter(myAdapter);
        registerForContextMenu(listView);

        //item单击跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userName = userInfos.get(position).getUsername();
                String phoneNumber = userInfos.get(position).getPhoneNumber();
                Intent intent = new Intent(MainActivity.this,ContactDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                bundle.putString("phoneNumber", phoneNumber);
                intent.putExtra("userInfo", bundle);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"点击的是：  "+userInfos.get(position).getUsername(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "删除");
        menu.add(0, 2, 0, "更新");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //View listView = MainActivity.this.findViewById(R.id.listView_main);
        View view = View.inflate(MainActivity.this, R.layout.dialog_layout,null);
        final EditText name = view.findViewById(R.id.add_name);
        final EditText phoneNumber = view.findViewById(R.id.add_phoneNumber);
        switch (item.getItemId()) {
            case R.id.item_add:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(view)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String usr_name = name.getText().toString();
                                String usr_phone = phoneNumber.getText().toString();
                                if (!usr_name.equals("")&&!usr_phone.equals("")) {
                                    userInfos.add(new UserInfo(usr_name,usr_phone));
                                    myAdapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this,
                                             usr_name + ": " + usr_phone+"  添加成功！",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                EditText et_addPhoneNumber = alertDialog.findViewById(R.id.add_phoneNumber);
                et_addPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        switch (item.getItemId()) {
            case 1:
                new AlertDialog.Builder(this)
                        .setTitle("删除联系人")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("你确定删除该联系人吗")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userName = userInfos.get(position).getUsername();
                                userInfos.remove(position);
                                myAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this,
                                        "联系人:  "+userName+"  " +
                                                "删除成功！",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;

            case 2:
                final View updateDialogView = View.inflate(MainActivity.this,
                        R.layout.update_dialog_layout,null);
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setView(updateDialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_userName =
                                        updateDialogView.findViewById(R.id.update_name);
                                EditText et_phoneNumber =
                                        updateDialogView.findViewById(R.id.update_phoneNumber);
                                String userName = et_userName.getText().toString();
                                String phoneNumber = et_phoneNumber.getText().toString();
                                if (!userName.equals("")&&!phoneNumber.equals("")) {
                                    userInfos.get(position).setUsername(userName);
                                    userInfos.get(position).setPhoneNumber(phoneNumber);
                                    myAdapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this, et_userName.getText().toString() + " - " + et_phoneNumber.getText().toString() + "  修改成功！", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                //alertDialog.show();根据组件id获取内容需要等待这个组件所在的view生成之后再获取，否则获取到的是空指针
                EditText et_userName = alertDialog.findViewById(R.id.update_name);
                EditText et_phoneNumber = alertDialog.findViewById(R.id.update_phoneNumber);
                et_userName.setText(userInfos.get(position).getUsername());
                et_phoneNumber.setText(userInfos.get(position).getPhoneNumber());
                et_phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

        }
        return super.onContextItemSelected(item);
    }

    class myBaseAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return userInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                convertView  = View.inflate(MainActivity.this, R.layout.base_item, null);
            }
            TextView textView = convertView.findViewById(R.id.tv_item);
            textView.setText(userInfos.get(position).getUsername());
            return convertView;
        }
    }
}
