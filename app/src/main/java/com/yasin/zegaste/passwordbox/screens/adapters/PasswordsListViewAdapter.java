package com.yasin.zegaste.passwordbox.screens.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yasin.zegaste.passwordbox.common.SaveAndRestoreUtil;
import com.yasin.zegaste.passwordbox.passwordbox.R;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordCategory;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordDataStructure;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordFirstDataType;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProduct;
import com.yasin.zegaste.passwordbox.passwordentities.PasswordProductOwner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Yasin on 20.11.2016.
 */

public class PasswordsListViewAdapter extends BaseAdapter {

    PasswordDataStructure PasswordDataStructure;
    Context context;
    int layoutResourceId;
    List data = null;
    LayoutInflater layoutInflater;

    public PasswordsListViewAdapter(Activity context, int resource, List<PasswordProduct> data, PasswordDataStructure PasswordDataStructure) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = data;
        this.PasswordDataStructure = PasswordDataStructure;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);
        PasswordProductHolder holder = new PasswordProductHolder();
        holder.btnShow = (ImageButton) row.findViewById(R.id.btnPasswordlistviewShow);
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordProduct(position);
            }
        });
        holder.btnDelete = (ImageButton) row.findViewById(R.id.btnPasswordlistDelete);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePasswordProduct(position);
            }
        });
        holder.btnEdit = (ImageButton) row.findViewById(R.id.btnPasswordlistviewEdit);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPaswordProduct(position);
            }
        });
        holder.tvPasswordProductName = (TextView) row.findViewById(R.id.tvPasswordListviewPasswordName);
        holder.tvPasswordProductCategoryAndOwner = (TextView) row.findViewById(R.id.tvPasswordlistviewPasswordCategoryAndOwner);
        holder.tvPasswordProductCreationDate = (TextView) row.findViewById(R.id.tvPasswordlistviewPassCreationDate);
        row.setTag(holder);

        PasswordProduct passwordProduct = (PasswordProduct) data.get(position);
        holder.tvPasswordProductName.setText(passwordProduct.getName());
        String categoryName = "", ownerName = "";
        PasswordProductOwner passwordProductOwner = PasswordDataStructure.getPasswordProductOwner(passwordProduct.getPassWordProductOwnerEid());
        ownerName = passwordProductOwner.getName();
        PasswordCategory category = PasswordDataStructure.getPasswordCategory(passwordProductOwner.getPassWordCategoryEid());
        categoryName = category.getName();
        holder.tvPasswordProductCategoryAndOwner.setText(categoryName + "\n" + ownerName);
        Date date = passwordProduct.getCreationDate();
        holder.tvPasswordProductCreationDate.setText(date.toString());
        return row;
    }

    private void showPasswordProduct(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        PasswordProduct passwordProduct = (PasswordProduct) data.get(position);
        builder.setTitle(passwordProduct.getName());
        String username = "";
        if (passwordProduct.getPasswordFirstDataType().equals(PasswordFirstDataType.E_MAIL))
            username = "E-Posta";
        else if (passwordProduct.getPasswordFirstDataType().equals(PasswordFirstDataType.USERNAME))
            username = "Kullanıcı Adı";
        String data1 = passwordProduct.getData1() == null ? null : username + ":\n" + passwordProduct.getData1();
        String data2 = "Şifre:\n" + passwordProduct.getData2();
        builder.setMessage(data1 == null ? data2 : data1 + "\n" + data2);
        builder.create().show();
    }

    private void deletePasswordProduct(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        PasswordProduct passwordProduct = (PasswordProduct) data.get(position);
        builder.setMessage(passwordProduct.getName() + " şifresini silmek istediğinizden emin misiniz ?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PasswordProduct passwordProduct = (PasswordProduct) data.get(position);
                PasswordDataStructure.removePassWordProduct(passwordProduct);
                savePasswordDataStructure();
                data.remove(passwordProduct);
                PasswordsListViewAdapter.this.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void editPaswordProduct(int position) {
        PasswordProduct passwordProduct = (PasswordProduct) data.get(position);
        //TODO: edit password product with PasswordDataStructure
        savePasswordDataStructure();
        this.notifyDataSetChanged();
    }

    private void savePasswordDataStructure() {
        try {
            SaveAndRestoreUtil.saveObjectToLocal(PasswordDataStructure, context, SaveAndRestoreUtil.savePasswordDataStructureKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class PasswordProductHolder {

        ImageButton btnDelete;
        ImageButton btnEdit;
        ImageButton btnShow;
        TextView tvPasswordProductName;
        TextView tvPasswordProductData1;
        TextView tvPasswordProductData2;
        TextView tvPasswordProductCategoryAndOwner;
        TextView tvPasswordProductCreationDate;
    }
}
