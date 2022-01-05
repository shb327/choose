package com.example.choose.create;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.retrofit.RetrofitUtils;
import com.example.choose.api.PostController;
import com.example.choose.dto.CreatePostRequestDTO;
import com.example.choose.dto.PostDTO;
import com.example.choose.home.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextPostFragment extends Fragment {

    public TextPostFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextInputEditText editText1;
    TextInputEditText editText2;
    TextInputLayout titleLayout;
    TextInputLayout contentLayout;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View inflate =  inflater.inflate(R.layout.fragment_text_post, container, false);
       PostController postController = RetrofitUtils.getInstance().getRetrofit().create(PostController.class);

       button = inflate.findViewById(R.id.sendBtn);
       editText1 = inflate.findViewById(R.id.titleTxt);
       editText2 = inflate.findViewById(R.id.contentTxt);
       titleLayout = inflate.findViewById(R.id.titleLayout);
       contentLayout = inflate.findViewById(R.id.contentLayout);
       titleLayout.setErrorEnabled(true);
       contentLayout.setErrorEnabled(true);
       titleLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));


       editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText1.getText().length() == 21) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("Character Limit Exceeded");
                    editText1.setTextColor(Color.parseColor("#F75010"));

                } else {
                    titleLayout.setErrorEnabled(false);
                    titleLayout.setError(null);
                    editText1.setTextColor(Color.parseColor("#68B2A0"));
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    titleLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
       });


       editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText2.getText().length() > 256) {
                    contentLayout.setErrorEnabled(true);
                    contentLayout.setError("Character limit exceeded");
                    editText2.setTextColor(Color.parseColor("#F75010"));
                } else {
                    contentLayout.setErrorEnabled(false);
                    contentLayout.setError(null);
                    editText2.setTextColor(Color.parseColor("#68B2A0"));
                    contentLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    contentLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                    contentLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#68B2A0")));
                }
            }
       });

       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editText1.getText().length() == 0) && (editText2.getText().length() == 0)) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("The title cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText1.setTextColor(Color.parseColor("#F75010"));
                    contentLayout.setErrorEnabled(true);
                    contentLayout.setError("The description cannot be empty");
                    contentLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText2.setTextColor(Color.parseColor("#F75010"));
                    contentLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    return;
                } else if (contentLayout.isErrorEnabled()) { return;
                } else if (titleLayout.isErrorEnabled()) { return;
                } else if (editText1.getText().length() == 0) {
                    titleLayout.setErrorEnabled(true);
                    titleLayout.setError("The title cannot be empty");
                    titleLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText1.setTextColor(Color.parseColor("#F75010"));
                    return;
                } else if (editText2.getText().length() == 0) {
                    contentLayout.setErrorEnabled(true);
                    contentLayout.setError("The description cannot be empty");
                    contentLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    editText2.setTextColor(Color.parseColor("#F75010"));
                    contentLayout.setCounterTextColor(ColorStateList.valueOf(Color.parseColor("#F75010")));
                    return;
                }

                postController.createPost(new CreatePostRequestDTO(
                        editText2.getText().toString(),
                        editText1.getText().toString()))
                        .enqueue(new Callback<PostDTO>() {
                            @Override
                            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                                startActivity(new Intent(inflate.getContext(), HomeActivity.class));
                            }

                            @Override
                            public void onFailure(Call<PostDTO> call, Throwable t) {
                                Toast.makeText(inflate.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
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
