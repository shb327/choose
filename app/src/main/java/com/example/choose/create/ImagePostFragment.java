package com.example.choose.create;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.home.HomeActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagePostFragment extends Fragment {

    public ImagePostFragment() { }

    ImageView tmp;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        tmp.setImageURI(uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_image_post, container, false);
        button = inflate.findViewById(R.id.sendBtn);

        TextInputEditText descriptionTxt = inflate.findViewById(R.id.descriptionTxt);
        TextInputLayout descriptionLayout = inflate.findViewById(R.id.descriptionLayout);
        TextInputEditText titleTxt = inflate.findViewById(R.id.titleTxt);
        TextInputLayout titleLayout = inflate.findViewById(R.id.titleLayout);

        tmp = inflate.findViewById(R.id.tmpResult);
        Button gallery = inflate.findViewById(R.id.gallery);
        Button camera = inflate.findViewById(R.id.camera);
        ImageView block = inflate.findViewById(R.id.block);

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ImagePostFragment.this).crop().compress(1024)
                        .maxResultSize(1080, 1080).start();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ImagePostFragment.this).galleryOnly().crop().compress(1024)
                        .maxResultSize(1080, 1080).start();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ImagePostFragment.this).cameraOnly().crop().compress(1024)
                        .maxResultSize(1080, 1080).start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postController.createTextPost(new CreatePostRequestDTO(
//                        editText2.getText().toString(),
//                        editText1.getText().toString()))
//                        .enqueue(new Callback<PostDTO>() {
//                            @Override
//                            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
//                                startActivity(new Intent(inflate.getContext(), HomeActivity.class));
//                            }
//
//                            @Override
//                            public void onFailure(Call<PostDTO> call, Throwable t) {
//                                Toast.makeText(inflate.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
            }
        });


        descriptionTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(descriptionTxt.getText().length()>256){
                    descriptionLayout.setErrorEnabled(true);
                    descriptionLayout.setError("Character limit exceeded");
                    descriptionTxt.setTextColor(Color.parseColor("#F75010"));
                    descriptionLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                }
                else {
                    descriptionLayout.setErrorEnabled(false);
                    descriptionLayout.setError(null);
                    descriptionTxt.setTextColor(Color.parseColor("#68B2A0"));
                    descriptionLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
        });

        titleTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(titleTxt.getText().length()==20){
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Character limit exceeded");
                    titleTxt.setTextColor(Color.parseColor("#F75010"));
                }
                else {
                    titleLayout.setErrorEnabled(false);
                    titleLayout.setError(null);
                    titleTxt.setTextColor(Color.parseColor("#68B2A0"));
                }
            }
        });

        Button btn = inflate.findViewById(R.id.closeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseType.close();
            }
        });
        return inflate;
    }
}