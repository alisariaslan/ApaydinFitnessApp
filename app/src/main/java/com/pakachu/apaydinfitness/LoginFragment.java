package com.pakachu.apaydinfitness;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

//import com.pakachu.apaydinfitness.databinding.FragmentLoginBinding;

import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.databinding.FragmentLoginBinding;
import com.pakachu.apaydinfitness.db.DBLogin;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private DBLogin dbLogin;
    private boolean okudumVeKabulEdiyorum = false;
    private String id = "", pass = "";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbLogin = new DBLogin(getActivity());
        Cursor cursor = dbLogin.getData();
        if (cursor.getCount() == 0) {
            dbLogin.addData("apaydin2017", "2017", 0, 0, 100, 0);
            dbLogin.addData("antrenor2017", "2017", 0, 0, 51, 0);
            dbLogin.addData("test", "1234", 0, 0, 0, 0);
        }

        binding.button32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pinString = "";
                pinString = binding.editTextTextPersonName3.getText().toString();
                if(!pinString.equals("")){
                    pinString = pinString.substring(0,pinString.length()-1);
                    binding.editTextTextPersonName3.setText(pinString);
                    binding.button4.setTag("0");
                }
            }
        });

        binding.button33.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button33.getText().toString()));
        binding.button5.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button5.getText().toString()));
        binding.button7.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button7.getText().toString()));
        binding.button8.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button8.getText().toString()));
        binding.button9.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button9.getText().toString()));
        binding.button10.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button10.getText().toString()));
        binding.button11.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button11.getText().toString()));
        binding.button12.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button12.getText().toString()));
        binding.button13.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button13.getText().toString()));
        binding.button14.setOnClickListener(v -> binding.editTextTextPersonName3.setText(binding.editTextTextPersonName3.getText().toString() + binding.button14.getText().toString()));

        binding.editTextTextPersonName2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.checkBox2.setChecked(false);
                String username = binding.editTextTextPersonName2.getText().toString().trim();
                id = username;
                binding.editTextTextPersonName3.setText("");
                if (binding.editTextTextPersonName2.getTag().equals("0")) {
                    binding.button4.setTag("0");
                    binding.button4.setText("Giri?? Yap / Kay??t Ol");
                    Cursor cursor = dbLogin.getData("SELECT * FROM login where username='" + username + "'");
                    if (cursor.getCount() > 0) {
                        binding.checkBox2.setChecked(true);
                    }
                }
            }
        });

        binding.editTextTextPersonName3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    v.setEnabled(false);
                    binding.constraintLayout3.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.bounce));
                    binding.constraintLayout3.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.editTextTextPersonName3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.checkBox3.setChecked(false);
                String password = "";
                password +=binding.editTextTextPersonName3.getText().toString();
                if(!password.equals("")) {
                    pass = password;
                    Log.e("login",""+password);
                    if (binding.editTextTextPersonName3.getTag().equals("0")) {

                        if (password.length() == 4) {
                            Cursor cursor = dbLogin.getData("SELECT * FROM login where username='" + id + "' and password='" + password + "'");
                            if (cursor.getCount() > 0) {
                                binding.checkBox3.setChecked(true);
                                binding.button4.setText("Giri?? Yap");
                                binding.button4.setTag("2");
                            } else {
                                binding.editTextTextPersonName3.setText("");
                            }
                        }

                    }
                }
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.button4.getTag().equals("0")) {
                    String info = "";
                    MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
                    myCustomDialog.setButtons("Olu??tur", "??ptal");
                    myCustomDialog.setContent("Giri?? i??in onay almad??n??z.\n\nYeni kay??t olu??turmak ister misiniz?");
                    myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myCustomDialog.dissmiss();
                            binding.textView13.setText("Yeni Kay??t");
                            binding.button4.setText("Kay??t Ol");
                            binding.button4.setTag("1");
                            binding.editTextTextPersonName2.setTag("1");
                            binding.editTextTextPersonName3.setTag("1");
                            binding.editTextTextPersonName2.setText("");
                            binding.editTextTextPersonName3.setText("");
                            binding.checkBox2.setVisibility(View.GONE);
                            binding.checkBox3.setVisibility(View.GONE);
                            binding.toggleButton.setVisibility(View.GONE);
                            binding.toggleButton2.setVisibility(View.GONE);
                            binding.cardView4.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));
                        }
                    });
                    myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myCustomDialog.dissmiss();
                        }
                    });
                    myCustomDialog.show(false);
                } else if (binding.button4.getTag().equals("1")) {
                    if (!id.equals("")) {
                        if (!pass.equals("")) {
                            if (pass.length() == 4) {
                                if(!okudumVeKabulEdiyorum) {
                                    MyCustomDialog myCustomDialog =new MyCustomDialog(getActivity());
                                    myCustomDialog.setButtons("Kabul Ediyorum","??ptal");
                                    myCustomDialog.setCaption("Gizlilik Politikas??");
                                    myCustomDialog.setContent("Apayd??n Fitness gizlilik politikas??, verilerinizin nas??l kullan??ld??????n?? ve\n" +
                                            "uygulamam??z?? kulland??????n??zda bize sa??lad??????n??z verileri korur.\n" +
                                            "Bu politikay?? herhangi bir zamanda de??i??tirme hakk??m??z sakl??d??r.\n" +
                                            "Hangi Kullan??c?? Verilerini Topluyoruz:\n" +
                                            "??? Ad??n??z, soyad??n??z, ya????n??z, kilonuz, boyunuz.\n" +
                                            "??? V??cut ??l????leriniz.\n" +
                                            "Verilerinizi Neden Topluyoruz:\n" +
                                            "??? ??htiya??lar??n??z?? daha iyi anlamak i??in.\n" +
                                            "??? Hizmetlerimizi ve ??r??nlerimizi geli??tirmek.\n" +
                                            "Verilerin Korunmas?? ve G??venli??inin Sa??lanmas??\n" +
                                            "Apayd??n Fitness, verilerinizi g??vence alt??na almay?? ve gizli tutmay?? taahh??t eder.\n" +
                                            "Apayd??n Fitness, veri h??rs??zl??????n??, yetkisiz eri??imi engellemek i??in elinden geleni yap??yor.\n" +
                                            "Apayd??n Fitness en son teknolojileri kullan??r.\n" + "\n" +
                                            "Bu gizlilik politikas??n?? okudum ve kabul ediyorum.");
                                    myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            okudumVeKabulEdiyorum=true;
                                            myCustomDialog.dissmiss();
                                            binding.editTextTextPersonName2.setEnabled(false);
                                            binding.editTextTextPersonName3.setEnabled(false);
                                        }
                                    });
                                    myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            okudumVeKabulEdiyorum=false;
                                            myCustomDialog.dissmiss();
                                        }
                                    });
                                    myCustomDialog.show(true);
                                } else {
                                    okudumVeKabulEdiyorum = false;
                                    binding.editTextTextPersonName2.setEnabled(true);
                                    binding.editTextTextPersonName3.setEnabled(true);
                                    dbLogin.deleteData("login");
                                    dbLogin.addData(id, pass, 0, 0, 0, 0);
                                    new MyCustomDialog(getActivity()).Toast("Kullan??c?? ba??ar??yla olu??turuldu.");
                                    binding.button4.setTag("0");
                                    binding.editTextTextPersonName2.setTag("0");
                                    binding.editTextTextPersonName3.setTag("0");

                                    binding.checkBox2.setVisibility(View.VISIBLE);
                                    binding.checkBox3.setVisibility(View.VISIBLE);

                                    binding.toggleButton.setVisibility(View.VISIBLE);
                                    binding.toggleButton2.setVisibility(View.VISIBLE);

                                    binding.editTextTextPersonName2.setText("");
                                    binding.textView13.setText("Kullan??c?? Giri??");
                                    binding.button4.setText("Giri?? Yap / Kay??t Ol");

                                    binding.cardView4.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));
                                }
                            } else {
                                new MyCustomDialog(getActivity()).Toast("Hata! ??ifreniz 4 karakter uzunlu??unda de??il");
                            }
                        } else {
                            new MyCustomDialog(getActivity()).Toast("Hata! ??ifrenizi bo?? b??rakamazs??n??z");
                        }
                    } else {
                        new MyCustomDialog(getActivity()).Toast("Hata! Kullan??c?? ad??n?? bo?? b??rakamazs??n??z");
                    }
                } else if (binding.button4.getTag().equals("2")) {
                    dbLogin.executeSQL("UPDATE login SET remember=0");
                    dbLogin.executeSQL("UPDATE login SET easylogin=0");
                    dbLogin.executeSQL("UPDATE login SET logged=0");
                    if (binding.toggleButton.isChecked())
                        dbLogin.executeSQL("UPDATE login SET remember=1 WHERE username='" + id + "'");
                    if (binding.toggleButton2.isChecked())
                        dbLogin.executeSQL("UPDATE login SET easylogin=1 WHERE username='" + id + "'");
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.mainFragment);
                    dbLogin.executeSQL("UPDATE login SET logged=1 WHERE username='" + id + "'");
                    DBLogin.loggedIn = true;
                }
            }
        });

        binding.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setTextColor(Color.GREEN);

                } else {
                    buttonView.setTextColor(Color.RED);
                    dbLogin.executeSQL("UPDATE login SET remember=0");
                    binding.toggleButton2.setChecked(false);
                }

            }
        });

        binding.toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.toggleButton.setChecked(true);
                    buttonView.setTextColor(Color.GREEN);
                } else {
                    buttonView.setTextColor(Color.RED);
                    dbLogin.executeSQL("UPDATE login SET easylogin=0");
                }

            }
        });

        if (cursor.getCount() != 0) {
            cursor = dbLogin.getData("SELECT * FROM login WHERE remember=1");
            if(cursor.getCount()==0)
                cursor = dbLogin.getData();
            cursor.moveToFirst();
            int remember;
            int easylogin;
            remember = cursor.getInt(3);
            easylogin = cursor.getInt(4);
            if (remember == 1) {
                binding.editTextTextPersonName2.setText(cursor.getString(1));
                binding.toggleButton.toggle();
            }
            if (easylogin == 1) {
                binding.editTextTextPersonName3.setText(cursor.getString(2));
                binding.toggleButton2.toggle();
                if (!DBLogin.loggedIn)
                    binding.button4.performClick();
            }

        }

        binding.checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        binding.textView14.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        binding.cardView4.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}