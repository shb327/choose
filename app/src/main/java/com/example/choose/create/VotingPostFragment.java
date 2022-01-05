package com.example.choose.create;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.choose.R;
import com.example.choose.create.ChooseType;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class VotingPostFragment extends Fragment {

    public VotingPostFragment() { }

    AutoCompleteTextView textIn;
    Button buttonAdd;
    LinearLayout container;

    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_voting_post, container, false);

        final int[] counter = {1};

        adapter = new ArrayAdapter<String>(inflate.getContext(), android.R.layout.simple_dropdown_item_1line);

        textIn = (AutoCompleteTextView) inflate.findViewById(R.id.textin);
        textIn.setAdapter(adapter);

        buttonAdd = (Button) inflate.findViewById(R.id.add);
        container = (LinearLayout) inflate.findViewById(R.id.container);

        TextInputEditText titleTxt = inflate.findViewById(R.id.titleTxt);
        TextInputLayout titleLayout = inflate.findViewById(R.id.titleLayout);

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

        textIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (textIn.getRight() - textIn.getCompoundDrawables()[2].getBounds().width())) {
                        textIn.getText().clear();
                        return true;
                    }
                }
                return false;
            }
        });

        ViewGroup finalContainer = container;
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(counter[0] < 10) {
                    counter[0]++;
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    AutoCompleteTextView textOut = (AutoCompleteTextView) addView.findViewById(R.id.textout);
                    textOut.setAdapter(adapter);
                    textOut.setText(textIn.getText().toString());
                    textOut.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= (textOut.getRight() - textOut.getCompoundDrawables()[2].getBounds().width())) {
                                    ((LinearLayout) addView.getParent()).removeView(addView);
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    textOut.setText("");
                    finalContainer.addView(addView);
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