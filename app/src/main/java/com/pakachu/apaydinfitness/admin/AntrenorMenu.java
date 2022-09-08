package com.pakachu.apaydinfitness.admin;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.databinding.FragmentAntrenorMenuBinding;
import com.pakachu.apaydinfitness.db.DBLocal;
import com.pakachu.apaydinfitness.helpers.Hashing;
import com.pakachu.apaydinfitness.helpers.JSONWorkbench;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AntrenorMenu extends Fragment {

    private FragmentAntrenorMenuBinding binding;
    private ArrayList arrayList;
    private String yenideger;

    private Hashing hashing;
    private boolean timer = false;
    private boolean olusturTimer = false;
    private boolean duzenleBulTimer = false;
    private boolean duzenleDuzenleTimer = false;
    private boolean silBulTimer = false;
    private boolean silSilTimer = false;
    private boolean araTimer = false;

    private DBLocal dbLocal;
    private JSONWorkbench jsonWorkbench;
    private ArrayList<ArrayList> arrayListArrayList;
    private ArrayList<ArrayList> olcuListArrayList;
    private String tarih, ad, yas, cins, kilo, boy, boyun, onkol, kol, bicep, omuz, gogus, karin, kalca, bacak, calf, ant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAntrenorMenuBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jsonWorkbench = new JSONWorkbench(getActivity());
        dbLocal = new DBLocal(getActivity());
        hashing = new Hashing();

        binding.button26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etPersonName = binding.editTextTextPersonName28.getText().toString().trim();
                if (etPersonName.equals(""))
                    new MyCustomDialog(getActivity()).Toast("İsim Soyisim boş olamaz!");
                else {
                    binding.switch1.setChecked(false);
                    binding.textView19.clearAnimation();
                    binding.textView19.setVisibility(View.GONE);
                    binding.constraintLayout8.setVisibility(View.GONE);
                    binding.button26.setEnabled(false);
                    binding.progressBar7.setVisibility(View.VISIBLE);
                    binding.editTextTextPersonName28.setEnabled(false);
                    olcuListArrayList = jsonWorkbench.GET("SELECT * FROM uyeler WHERE ad='" + etPersonName + "' ORDER BY DATE(tarih) DESC");
                    araCountDownTimer = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            araTimer = true;
                            if (jsonWorkbench.finishStatus) {
                                araTimer = false;
                                cancel();
                                binding.button26.setEnabled(true);
                                binding.progressBar7.setVisibility(View.GONE);
                                binding.editTextTextPersonName28.setEnabled(true);
                                if (olcuListArrayList.size() > 0) {
                                    binding.constraintLayout8.setVisibility(View.VISIBLE);
                                    String[] tarihler = new String[olcuListArrayList.size()];
                                    int i = 0;
                                    for (ArrayList arraylist : olcuListArrayList
                                    ) {
                                        tarihler[i] = arraylist.get(1).toString();
                                        i++;
                                    }
                                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tarihler);
                                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                    binding.spinner13.setAdapter(spinnerArrayAdapter);
                                }else
                                    new MyCustomDialog(getActivity()).Toast("Kullanıcı bulunamadı!");
                            }
                        }

                        @Override
                        public void onFinish() {
                            araTimer = false;
                            new MyCustomDialog(getActivity()).Toast("Zaman Aşımı!");
                            binding.button26.setEnabled(true);
                            binding.progressBar7.setVisibility(View.GONE);
                            binding.editTextTextPersonName28.setEnabled(true);
                        }
                    }.start();
                }
            }
        });

        binding.spinner13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.textView49.setText(binding.spinner13.getItemAtPosition(position).toString());
                SetProfileArgs(olcuListArrayList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    SetProfileArgs(olcuListArrayList.get(binding.spinner13.getSelectedItemPosition()));
            }
        });

        binding.button39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer) {
                    countDownTimer.cancel();
                    timer = false;
                }
                if (binding.button39.getTag().equals("0")) {
                    String content = "";
                    if (binding.radioGroup2.getCheckedRadioButtonId() == binding.radioButton4.getId()) {
                        content = hashing.TimeBend(false);
                        Log.e("zaman", "manuel false: " + content);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = null;
                        try {
                            bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        binding.imageView6.setImageBitmap(bitmap);
                        binding.imageView6.setVisibility(View.VISIBLE);
                        binding.imageView6.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));
                        binding.textView18.clearAnimation();
                        binding.textView18.setVisibility(View.GONE);
                    } else {
                        content = hashing.TimeBend(true);
                        Log.e("zaman", "manuel false: " + content);
                        binding.textView18.setText(content);
                        binding.textView18.setVisibility(View.VISIBLE);
                        binding.textView18.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));
                        binding.imageView6.clearAnimation();
                        binding.imageView6.setVisibility(View.GONE);
                    }
                    countDownTimer.start();
                    binding.button39.setTag("1");
                    binding.button39.setText("İptal");
                } else if (binding.button39.getTag().equals("1")) {
                    binding.textView18.clearAnimation();
                    binding.textView18.setVisibility(View.GONE);
                    binding.imageView6.clearAnimation();
                    binding.imageView6.setVisibility(View.GONE);
                    binding.button39.setTag("0");
                    binding.button39.setText("Oluştur");
                    binding.textView39.setText("Kod Oluştur");
                }

            }
        });

        binding.button24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.linearLayout.getVisibility() == View.GONE) {
                    binding.linearLayout.setVisibility(View.VISIBLE);
                    binding.linearLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
                    binding.button24.setText("Şimdi Oluştur");
                    Calendar dateCal = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date = formatter.format(dateCal.getTime());
                    binding.editTextTextPersonName7.setText(date);
                    binding.editTextTextPersonName24.setText(dbLocal.getName());
                } else {
                    if (binding.editTextTextPersonName7.getText().toString().equals(""))
                        new MyCustomDialog(getActivity()).Toast("Tarih boş olamaz!");
                    else {
                        if (binding.editTextTextPersonName8.getText().toString().equals(""))
                            new MyCustomDialog(getActivity()).Toast("Ad boş olamaz!");
                        else {
                            binding.button24.setEnabled(false);
                            binding.linearLayout.clearAnimation();
                            binding.linearLayout.setVisibility(View.GONE);
                            binding.progressBar2.setVisibility(View.VISIBLE);
                            int id = binding.radiogroup.getCheckedRadioButtonId();
                            RadioButton checkedRadioBtn = view.findViewById(id);
                            jsonWorkbench.ADDUSER(
                                    binding.editTextTextPersonName7.getText().toString().trim(),
                                    binding.editTextTextPersonName8.getText().toString().trim(),
                                    binding.editTextTextPersonName10.getText().toString().trim(),
                                    checkedRadioBtn.getText().toString(),
                                    binding.editTextTextPersonName11.getText().toString().trim(),
                                    binding.editTextTextPersonName12.getText().toString().trim(),
                                    binding.editTextTextPersonName13.getText().toString().trim(),
                                    binding.editTextTextPersonName15.getText().toString().trim(),
                                    binding.editTextTextPersonName16.getText().toString().trim(),
                                    binding.editTextTextPersonName17.getText().toString().trim(),
                                    binding.editTextTextPersonName18.getText().toString().trim(),
                                    binding.editTextTextPersonName19.getText().toString().trim(),
                                    binding.editTextTextPersonName20.getText().toString().trim(),
                                    binding.editTextTextPersonName21.getText().toString().trim(),
                                    binding.editTextTextPersonName22.getText().toString().trim(),
                                    binding.editTextTextPersonName23.getText().toString().trim(),
                                    binding.editTextTextPersonName24.getText().toString().trim()
                            );
                            olusturCountDown.start();
                        }
                    }
                }
            }
        });

        binding.editTextTextPersonName25.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!binding.button21.getTag().equals("0"))
                {
                    binding.button21.setTag("0");
                    binding.button21.setText("Bul");
                    binding.constrainlayout.clearAnimation();
                    binding.constrainlayout.setVisibility(View.GONE);
                    binding.textView45.setVisibility(View.GONE);
                    binding.spinner11.setVisibility(View.GONE);
                }
            }
        });

        binding.button21.setOnClickListener(new View.OnClickListener() { //BUL VE GÜNCELLE
            @Override
            public void onClick(View v) {
                if (binding.button21.getTag().equals("0")) { //BUL
                    String etPersonName = binding.editTextTextPersonName25.getText().toString().trim();
                    if (etPersonName.equals(""))
                        new MyCustomDialog(getActivity()).Toast("İsim Soyisim boş olamaz!");
                    else {
                        binding.editTextTextPersonName25.setEnabled(false);
                        binding.button21.setEnabled(false);
                        arrayListArrayList = new ArrayList<>();
                        arrayListArrayList = jsonWorkbench.GET("SELECT * FROM uyeler WHERE ad='" + etPersonName + "' ORDER BY DATE(tarih)");
                        binding.progressBar3.setVisibility(View.VISIBLE);
                        duzenleBulCountDown.start();
                    }
                } else { //GÜNCELLE
                    yenideger = binding.editTextTextPersonName26.getText().toString().trim();
                    String bolge = binding.spinner10.getSelectedItem().toString();
                    arrayList = arrayListArrayList.get(binding.spinner11.getSelectedItemPosition());
                    String id = arrayList.get(0).toString();
                    jsonWorkbench.UPDATEUSER(bolge, yenideger, id);
                    binding.progressBar3.setVisibility(View.VISIBLE);
                    binding.button21.setEnabled(false);
                    binding.editTextTextPersonName26.setEnabled(false);
                    duzenleDuzenleCountDown.start();
                }
            }
        });

        binding.spinner11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.textView45.setText(binding.spinner11.getItemAtPosition(position).toString());
                String[] bolgeler = {"tarih", "ad", "yas", "cinsiyet", "kilo", "boy", "boyun", "onkol", "kol", "bicep",
                        "omuz", "gogus", "karin", "kalca", "bacak", "calf", "antrenor"};
                ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bolgeler);
                spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                binding.spinner10.setAdapter(spinnerArrayAdapter2);
                binding.constrainlayout.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.bounce));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spinner10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.textView44.setText(binding.spinner10.getItemAtPosition(position).toString().toUpperCase(Locale.ROOT));
                ArrayList selectedArrayList = arrayListArrayList.get(binding.spinner11.getSelectedItemPosition());
                binding.editTextTextPersonName26.setText(selectedArrayList.get(position + 1).toString());
                binding.editTextTextPersonName26.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.bounce));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.editTextTextPersonName27.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!binding.button20.getTag().equals("0"))
                {
                    binding.constraintLayout7.setVisibility(View.GONE);
                    binding.button20.setTag("0");
                    binding.button20.setText("Bul");

                    binding.button21.setTag("0");
                    binding.button21.setText("Bul");
                    binding.constrainlayout.clearAnimation();
                    binding.constrainlayout.setVisibility(View.GONE);
                    binding.textView45.setVisibility(View.GONE);
                    binding.spinner11.setVisibility(View.GONE);
                }
            }
        });

        binding.button20.setOnClickListener(new View.OnClickListener() { //BUL SİL
            @Override
            public void onClick(View v) { //BUL
                if (binding.button20.getTag().equals("0")) {
                    String etPersonName = binding.editTextTextPersonName27.getText().toString().trim();
                    if (etPersonName.equals(""))
                        new MyCustomDialog(getActivity()).Toast("İsim soyisim boş olamaz!");
                    else {
                        binding.button20.setEnabled(false);
                        binding.progressBar4.setVisibility(View.VISIBLE);
                        binding.editTextTextPersonName27.setEnabled(false);
                        arrayListArrayList = jsonWorkbench.GET("SELECT * FROM uyeler WHERE ad='" + etPersonName + "' ORDER BY DATE(tarih)");
                        silBulCountDown.start();
                    }
                } else { //SİL
                    ArrayList<String> arrayList = arrayListArrayList.get(binding.spinner12.getSelectedItemPosition());
                    String id = arrayList.get(0);
                    jsonWorkbench.DELETEUSER(id);
                    binding.button20.setEnabled(false);
                    binding.progressBar4.setVisibility(View.VISIBLE);
                    silSilCountDown.start();
                }
            }
        });

        binding.spinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.textView46.setText(binding.spinner12.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private Object CheckNull(Object object) {
        if (object == "" | object == null)
            object = "-";
        return object;
    }

    private void SetProfileArgs(ArrayList arrayList) {
        String info = "";
        if (!binding.switch1.isChecked()) {
            tarih = "Tarih: " + CheckNull(arrayList.get(1));
            ad = "Ad: " + CheckNull(arrayList.get(2));
            yas = "Yaş: " + CheckNull(arrayList.get(3));
            cins = "Cinsiyet: " + CheckNull(arrayList.get(4));
            kilo = "Kilo: " + CheckNull(arrayList.get(5));
            boy = "Boy: " + CheckNull(arrayList.get(6));
            boyun = "Boyun: " + CheckNull(arrayList.get(7));
            onkol = "Ön kol: " + CheckNull(arrayList.get(8));
            kol = "Kol: " + CheckNull(arrayList.get(9));
            bicep = "Bicep: " + CheckNull(arrayList.get(10));
            omuz = "Omuz: " + CheckNull(arrayList.get(11));
            gogus = "Göğüs: " + CheckNull(arrayList.get(12));
            karin = "Karın: " + CheckNull(arrayList.get(13));
            kalca = "Kalça: " + CheckNull(arrayList.get(14));
            bacak = "Bacak: " + CheckNull(arrayList.get(15));
            calf = "Calf: " + CheckNull(arrayList.get(16));
            ant = "Antrenör: " + CheckNull(arrayList.get(17));
        } else {
            tarih += " -> " + CheckNull(arrayList.get(1));
            //ad += CheckNull(arrayList.get(2));
            yas += " -> " + CheckNull(arrayList.get(3));
            //cins += " -> " + CheckNull(arrayList.get(4));
            kilo += " -> " + CheckNull(arrayList.get(5));
            boy += " -> " + CheckNull(arrayList.get(6));
            boyun += " -> " + CheckNull(arrayList.get(7));
            onkol += " -> " + CheckNull(arrayList.get(8));
            kol += " -> " + CheckNull(arrayList.get(9));
            bicep += " -> " + CheckNull(arrayList.get(10));
            omuz += " -> " + CheckNull(arrayList.get(11));
            gogus += " -> " + CheckNull(arrayList.get(12));
            karin += " -> " + CheckNull(arrayList.get(13));
            kalca += " -> " + CheckNull(arrayList.get(14));
            bacak += " -> " + CheckNull(arrayList.get(15));
            calf += " -> " + CheckNull(arrayList.get(16));
            ant += " -> " + CheckNull(arrayList.get(17));
        }
        info = tarih + "\n" + ad + "\n" + yas + "\n" + cins + "\n" + kilo + " kg\n" + boy + " cm\n" + boyun + " cm\n" + onkol + " cm\n" + kol + " cm\n" + bicep + " cm\n" + omuz + " cm\n" + gogus + " cm\n" + karin + " cm\n" + kalca + " cm\n" + bacak + " cm\n" + calf + " cm\n" + ant;
        binding.textView19.setText(info);
        binding.textView19.setVisibility(View.VISIBLE);
        binding.textView19.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
    }

    private CountDownTimer araCountDownTimer;

    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer = true;
            hashing.kalanSure--;
            binding.textView39.setText("Kalan: " + hashing.kalanSure + " Saniye");
            if (hashing.kalanSure == 0) {
                onFinish();
                cancel();
            }
        }

        @Override
        public void onFinish() {
            timer = false;
            binding.textView18.clearAnimation();
            binding.textView18.setVisibility(View.GONE);
            binding.imageView6.clearAnimation();
            binding.imageView6.setVisibility(View.GONE);
            binding.button39.setTag("0");
            binding.button39.setText("Oluştur");
            binding.textView39.setText("Kod Oluştur");
        }
    };

    private CountDownTimer olusturCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            olusturTimer = true;
            if (jsonWorkbench.finishStatus) {
                cancel();
                olusturTimer = false;
                binding.linearLayout.clearAnimation();
                binding.linearLayout.setVisibility(View.GONE);
                binding.progressBar2.setVisibility(View.GONE);
                binding.button24.setEnabled(true);
                binding.button24.setText("Yeni Oluştur");
                binding.editTextTextPersonName8.setText("");
                binding.editTextTextPersonName10.setText("");
                binding.editTextTextPersonName11.setText("");
                binding.editTextTextPersonName12.setText("");
                binding.editTextTextPersonName13.setText("");
                binding.editTextTextPersonName15.setText("");
                binding.editTextTextPersonName16.setText("");
                binding.editTextTextPersonName17.setText("");
                binding.editTextTextPersonName18.setText("");
                binding.editTextTextPersonName19.setText("");
                binding.editTextTextPersonName20.setText("");
                binding.editTextTextPersonName21.setText("");
                binding.editTextTextPersonName22.setText("");
                binding.editTextTextPersonName23.setText("");
            }
        }

        @Override
        public void onFinish() {
            olusturTimer = false;
            binding.progressBar2.setVisibility(View.GONE);
            binding.linearLayout.setVisibility(View.VISIBLE);
            binding.button24.setEnabled(true);
            new MyCustomDialog(getActivity()).Toast("Zaman Aşımı!");
        }
    };

    private CountDownTimer duzenleBulCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            duzenleBulTimer = true;
            if (jsonWorkbench.finishStatus) {
                cancel();
                duzenleBulTimer = false;
                binding.button21.setEnabled(true);
                binding.progressBar3.setVisibility(View.GONE);
                if (arrayListArrayList.size() > 0) {
                    String[] tarihler = new String[arrayListArrayList.size()];
                    int i = 0;
                    for (ArrayList arraylist : arrayListArrayList
                    ) {
                        tarihler[i] = arraylist.get(1).toString();
                        i++;
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tarihler);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    binding.spinner11.setAdapter(spinnerArrayAdapter);
                    binding.spinner11.setVisibility(View.VISIBLE);
                    binding.textView45.setVisibility(View.VISIBLE);
                    binding.constrainlayout.setVisibility(View.VISIBLE);
                    binding.button21.setTag(1);
                    binding.button21.setText("Şimdi Güncelle");
                    binding.editTextTextPersonName25.setEnabled(true);
                } else {
                    new MyCustomDialog(getActivity()).Toast("Kullanıcı bulunamadı!");
                    binding.editTextTextPersonName25.setEnabled(true);
                }

            }
        }

        @Override
        public void onFinish() {
            binding.progressBar3.setVisibility(View.GONE);
            binding.button21.setEnabled(true);
            duzenleBulTimer = false;
            binding.editTextTextPersonName25.setEnabled(true);
            new MyCustomDialog(getActivity()).Toast("Zaman aşımı!");
        }
    };

    private CountDownTimer duzenleDuzenleCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            duzenleDuzenleTimer = true;
            if (jsonWorkbench.finishStatus) {
                cancel();
                duzenleDuzenleTimer = false;
                binding.button21.setEnabled(true);
                binding.editTextTextPersonName26.setEnabled(true);
                binding.editTextTextPersonName25.setEnabled(true);
                binding.progressBar3.setVisibility(View.GONE);
                arrayList.set(binding.spinner10.getSelectedItemPosition() + 1, yenideger);
                arrayListArrayList.set(binding.spinner11.getSelectedItemPosition(), arrayList);
                binding.button21.setTag("0");
                binding.button21.setText("Bul");
                binding.constrainlayout.setVisibility(View.GONE);
                binding.textView45.setVisibility(View.GONE);
                binding.spinner11.setVisibility(View.GONE);
                binding.constraintLayout7.setVisibility(View.GONE);
                binding.button20.setTag("0");
                binding.button20.setText("Bul");
            }
        }

        @Override
        public void onFinish() {
            binding.progressBar3.setVisibility(View.GONE);
            binding.button21.setEnabled(true);
            duzenleDuzenleTimer = false;
            binding.editTextTextPersonName26.setEnabled(true);
            new MyCustomDialog(getActivity()).Toast("Zaman Aşımı!");
        }

    };

    private CountDownTimer silBulCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            silBulTimer = true;
            if (jsonWorkbench.finishStatus) {
                cancel();
                silBulTimer = false;
                if (arrayListArrayList.size() > 0) {
                    binding.constraintLayout7.setVisibility(View.VISIBLE);
                    binding.progressBar4.setVisibility(View.GONE);
                    binding.button20.setTag("1");
                    binding.button20.setText("Şimdi Sil");
                    binding.button20.setEnabled(true);
                    String[] tarihler = new String[arrayListArrayList.size()];
                    int i = 0;
                    for (ArrayList arraylist : arrayListArrayList
                    ) {
                        tarihler[i] = arraylist.get(1).toString();
                        i++;
                    }
                    ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tarihler);
                    spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    binding.spinner12.setAdapter(spinnerArrayAdapter2);
                    binding.editTextTextPersonName27.setEnabled(true);
                } else {
                    binding.progressBar4.setVisibility(View.GONE);
                    binding.editTextTextPersonName27.setEnabled(true);
                    binding.button20.setEnabled(true);
                    new MyCustomDialog(getActivity()).Toast("Kullanıcı bulunamadı!");
                }
            }
        }

        @Override
        public void onFinish() {
            binding.button20.setEnabled(true);
            binding.progressBar4.setVisibility(View.GONE);
            binding.editTextTextPersonName27.setEnabled(true);
            silBulTimer = false;
            new MyCustomDialog(getActivity()).Toast("Zaman aşımı!");
        }

    };

    private CountDownTimer silSilCountDown = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            silSilTimer = true;
            if (jsonWorkbench.finishStatus) {
                cancel();
                silSilTimer = false;
                binding.constraintLayout7.setVisibility(View.GONE);
                binding.button20.setEnabled(true);
                binding.button20.setTag("0");
                binding.button20.setText("Bul");
                binding.editTextTextPersonName27.setEnabled(true);
                binding.progressBar4.setVisibility(View.GONE);
                binding.spinner11.setVisibility(View.GONE);
                binding.textView45.setVisibility(View.GONE);
                binding.constrainlayout.clearAnimation();
                binding.constrainlayout.setVisibility(View.GONE);
                binding.button21.setTag("0");
                binding.button21.setText("Bul");
                binding.editTextTextPersonName25.setEnabled(true);
            }
        }

        @Override
        public void onFinish() {
            silSilTimer = false;
            binding.button20.setEnabled(true);
            binding.progressBar4.setVisibility(View.GONE);
            new MyCustomDialog(getActivity()).Toast("Zaman aşımı!");
        }

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (timer)
            countDownTimer.cancel();
        if (olusturTimer)
            olusturCountDown.cancel();
        if (duzenleBulTimer)
            duzenleBulCountDown.cancel();
        if (duzenleDuzenleTimer)
            duzenleDuzenleCountDown.cancel();
        if (silBulTimer)
            silBulCountDown.cancel();
        if (silSilTimer)
            silSilCountDown.cancel();
        if (araTimer)
            araCountDownTimer.cancel();
    }
}