package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.RadioButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.databinding.FragmentOlculerimBinding;
import com.pakachu.apaydinfitness.db.DBLocal;
import com.pakachu.apaydinfitness.db.DBLogin;
import com.pakachu.apaydinfitness.helpers.AddLoader;
import com.pakachu.apaydinfitness.helpers.Hashing;
import com.pakachu.apaydinfitness.helpers.JSONWorkbench;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OlculerimFragment extends Fragment {

    private FragmentOlculerimBinding binding;
    private DBLocal dbLocal;
    private Cursor data;
    private Hashing hashing;
    private ArrayList<ArrayList> arrayListArrayList;
    private boolean fastVerification = false;
    private boolean timer = false;

    private CountDownTimer countDownTimer;

    private Object CheckNull(Object object) {
        if (object instanceof Float) {
            if ((Float) object < 1f) {
                object = "-";
            }
        } else if (object instanceof Integer) {
            if ((Integer) object < 1f)
                object = "-";
        } else if (object == "" | object == null)
            object = "-";
        return object;
    }

    private void GetSelected(int offset) {
        String info = "", ad = "", tarih = "", cinsiyet = "";
        Float yas = 0f, boy = 0f, kilo = 0f;
        Object boyun, onkol, kol, bicep, omuz, gogus, karin, kalca, bacak, calf;
        String antrenor;
        data.moveToFirst();
        data.move(offset);
        tarih = data.getString(1);
        binding.textView25.setText("Tarih: " + tarih);
        ad = data.getString(2);
        yas = data.getFloat(3);
        cinsiyet = data.getString(4);
        kilo = data.getFloat(5);
        boy = data.getFloat(6);
        boyun = data.getFloat(7);
        onkol = data.getFloat(8);
        kol = data.getFloat(9);
        bicep = data.getFloat(10);
        omuz = data.getFloat(11);
        gogus = data.getFloat(12);
        karin = data.getFloat(13);
        kalca = data.getFloat(14);
        bacak = data.getFloat(15);
        calf = data.getFloat(16);
        antrenor = data.getString(17);
        binding.textView5.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        binding.imageView.setVisibility(View.VISIBLE);
        if (cinsiyet.toUpperCase(Locale.ROOT).equals("ERKEK"))
            binding.imageView.setImageResource(R.drawable.fit_man);
        else if (cinsiyet.toUpperCase(Locale.ROOT).equals("KADIN"))
            binding.imageView.setImageResource(R.drawable.fit_girl);
        else {
            binding.imageView.setVisibility(View.GONE);
            binding.textView5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        float bmi = kilo / ((boy / 100) * (boy / 100));
        float women = 15.8f;
        float man = 16.2f;
        float sex;
        if (cinsiyet.toUpperCase(Locale.ROOT).equals("ERKEK"))
            sex = man;
        else
            sex = women;
        float fatRatio = (1.20f * bmi) + (0.23f * yas) - sex;
        binding.button2.setEnabled(false);
        binding.button3.setEnabled(false);
        if (kilo < 1 | boy < 1) {
            binding.button3.setText("Kilo veya Boy bilgisi eksik!");
        } else {
            binding.textView29.setText("" + bmi);
            binding.button3.setText("Göster");
            binding.button3.setEnabled(true);
        }
        if (kilo < 1 | boy < 1 | yas < 1) {
            binding.button2.setText("Kilo, Boy veya Yaş bilgisi eksik!");
        } else {
            binding.textView28.setText("%" + fatRatio);
            binding.button2.setText("Göster");
            binding.button2.setEnabled(true);
        }
        info = "Yaş: " + CheckNull(yas.intValue()) + "\n" +
                "Cinsiyet: " + CheckNull(cinsiyet) + "\n" +
                "Kilo: " + CheckNull(kilo) + "\n" +
                "Boy: " + CheckNull(boy.intValue()) + "\n" +
                "Boyun: " + CheckNull(boyun) + "\n" +
                "Ön kol: " + CheckNull(onkol) + "\n" +
                "Kol: " + CheckNull(kol) + "\n" +
                "Bicep: " + CheckNull(bicep) + "\n" +
                "Omuz: " + CheckNull(omuz) + "\n" +
                "Göğüs: " + CheckNull(gogus) + "\n" +
                "Karın: " + CheckNull(karin) + "\n" +
                "Kalça: " + CheckNull(kalca) + "\n" +
                "Bacak: " + CheckNull(bacak) + "\n" +
                "Calf: " + CheckNull(calf) + "\n" +
                "Antrenör: " + CheckNull(antrenor);
        binding.textView5.setVisibility(View.VISIBLE);
        binding.button.setText("Yeniden Oluştur");
        binding.textView6.setText("Profil Oluşturuldu");
        binding.button2.setVisibility(View.VISIBLE);
        binding.button3.setVisibility(View.VISIBLE);
        binding.textView28.setVisibility(View.GONE);
        binding.textView30.setVisibility(View.GONE);
        binding.textView29.setVisibility(View.GONE);
        binding.textView31.setVisibility(View.GONE);
        binding.textView5.setText(info);
        binding.textView3.setText(ad);
    }

    private void GetDates() {
        data = dbLocal.getData();
        String[] tarihler = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            String tarih = data.getString(1);
            tarihler[i] = tarih;
            i++;
        }
        if (data.getCount() != 0) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tarihler);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            binding.spinner.setAdapter(spinnerArrayAdapter);
            binding.constraintLayout.setVisibility(View.VISIBLE);
            binding.spinner.setSelection(data.getCount() - 1);
        } else {
            binding.textView3.setText("Profil mevcut değil!\nLütfen profilinizi aratın veya kendiniz oluşturun.");
            binding.spinner.setAdapter(null);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentOlculerimBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void TransferNow() {
        String info = "Transfer işlemini onaylıyor musunuz?";
        MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
        myCustomDialog.setButtons("Onayla", "İptal");
        myCustomDialog.setContent(info);
        myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbLocal.deleteData("uyeler");
                for (ArrayList arrayList : arrayListArrayList
                ) {
                    dbLocal.addData(arrayList.get(1).toString(),
                            arrayList.get(2).toString(),
                            Integer.parseInt(arrayList.get(3).toString()),
                            arrayList.get(4).toString(),
                            Float.parseFloat(arrayList.get(5).toString()),
                            Float.parseFloat(arrayList.get(6).toString()),
                            Float.parseFloat(arrayList.get(7).toString()),
                            Float.parseFloat(arrayList.get(8).toString()),
                            Float.parseFloat(arrayList.get(9).toString()),
                            Float.parseFloat(arrayList.get(10).toString()),
                            Float.parseFloat(arrayList.get(11).toString()),
                            Float.parseFloat(arrayList.get(12).toString()),
                            Float.parseFloat(arrayList.get(13).toString()),
                            Float.parseFloat(arrayList.get(14).toString()),
                            Float.parseFloat(arrayList.get(15).toString()),
                            Float.parseFloat(arrayList.get(16).toString()),
                            arrayList.get(17).toString());
                }
                GetDates();
                myCustomDialog.dissmiss();
            }
        });
        myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog.dissmiss();
            }
        });
        myCustomDialog.show(true);
    }

    private void Verification() {
        String info = (String) arrayListArrayList.get(0).get(2);
        info += " adlı kullanıcının profili bulundu.\n\nProfili sisteminize entegre etmek için Antrenörünüzün QR kodunu okutun veya alternatif olarak doğrulama kodunu isteyin.";
        MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
        myCustomDialog.setButtons("QR Oku", "İptal", "Kodu Elle Gir");
        myCustomDialog.setContent(info);
        myCustomDialog.setCaption("Profil Bulundu");
        myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog.dissmiss();
                ScanOptions scanOptions = new ScanOptions();
                scanOptions.setTimeout(15000);
                scanOptions.setBeepEnabled(false);
                scanOptions.setPrompt("LÜTFEN TARAYICIYI ANTRENÖRÜNÜZÜN SİZE GÖSTERDİĞİ QR KODA DOĞRU TUTUN");
                barcodeLauncher.launch(scanOptions);
            }
        });
        myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog.dissmiss();
                binding.editTextTextPersonName.setText(null);
            }
        });
        myCustomDialog.neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog.dissmiss();
                binding.editTextTextPersonName.setText(null);
                binding.editTextTextPersonName.setHint("Kodu Elle Yazın");
                binding.button36.setText("Kodu Doğrula");
                binding.button36.setTag("1");
            }
        });
        myCustomDialog.show(false);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    binding.editTextTextPersonName.setText(null);
                    new MyCustomDialog(getActivity()).Toast("İptal Edildi");
                } else {

                    if (result.getContents().equals(hashing.TimeBend(false))) {
                        TransferNow();
                    } else {
                        new MyCustomDialog(getActivity()).Toast("Hatalı QR Kod! Lütfen Tekrar Deneyin");

                    }

                }
            });

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JSONWorkbench jsonWorkbench = new JSONWorkbench(getActivity());
        dbLocal = new DBLocal(getActivity());
        hashing = new Hashing();

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = sdf.format(new Date(binding.calendarView.getDate()));
        binding.textView24.setText(selectedDate);
        GetDates();

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-" + month + "-" + dayOfMonth;
                binding.textView24.setText(date);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.button.getTag().equals("0")) {
                    binding.button.setTag("1");
                    binding.constraintLayout2.setVisibility(View.VISIBLE);
                    binding.constraintLayout2.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));
                    binding.button.setText("Oluştur");
                    binding.textView6.setText("Profil Oluşturuluyor");
                } else {
                    String tarih, adSoyad, cinsiyet, info;
                    int yas, boy = 0;
                    float kilo = 0;
                    boolean allOk = false;
                    RadioButton radioButton = binding.getRoot().findViewById(binding.radioGroup.getCheckedRadioButtonId());
                    cinsiyet = radioButton.getText().toString();
                    tarih = "" + binding.textView24.getText().toString();
                    adSoyad = "" + binding.editTextTextPersonName14.getText().toString().trim();
                    yas = Integer.parseInt(binding.spinner2.getSelectedItem().toString());
                    kilo = Float.parseFloat(binding.spinner3.getSelectedItem().toString());
                    boy = Integer.parseInt(binding.spinner4.getSelectedItem().toString());
                    if (!adSoyad.equals("")) {
                        allOk = true;
                    } else {
                        binding.editTextTextPersonName14.setHint("Bu alan boş bırakılamaz!");
                        binding.editTextTextPersonName14.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));
                    }
                    if (allOk) {
                        info = "Yeni kayıt oluşturduğunuz zaman kayıtlı olan bütün veriler silinecektir!" + "\n\n" +
                                "Tarih: " + tarih + "\n" +
                                "Ad Soyad: " + adSoyad + "\n" +
                                "Yaş: " + yas + "\n" +
                                "Cinsiyet: " + cinsiyet + "\n" +
                                "Kilo: " + kilo + "\n" +
                                "Boy: " + boy;
                        int finalBoy = boy;
                        float finalKilo = kilo;
                        MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
                        myCustomDialog.setButtons("Oluştur", "İptal");
                        myCustomDialog.setContent(info);
                        myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myCustomDialog.dissmiss();
                                dbLocal.deleteData("uyeler");
                                dbLocal.addData(tarih, adSoyad, yas, cinsiyet, finalKilo, finalBoy);
                                binding.button.setTag("0");
                                binding.constraintLayout2.setVisibility(View.GONE);
                                binding.constraintLayout2.clearAnimation();
                                binding.button.setText("Yeniden Oluştur");
                                binding.textView6.setText("Profil Oluşturuldu");
                                GetDates();
                            }
                        });
                        myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myCustomDialog.dissmiss();
                            }
                        });
                        myCustomDialog.show(false);
                    }
                }
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textView28.setVisibility(View.VISIBLE);
                binding.textView30.setVisibility(View.VISIBLE);
                binding.button2.setVisibility(View.GONE);
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textView29.setVisibility(View.VISIBLE);
                binding.textView31.setVisibility(View.VISIBLE);
                binding.button3.setVisibility(View.GONE);
            }
        });

        binding.button36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.button36.getTag().equals("0")) {
                    String etPersonName = binding.editTextTextPersonName.getText().toString().trim();
                    if (etPersonName.equals(""))
                        new MyCustomDialog(getActivity()).Toast("İsim Soyisim boş olamaz!");
                    else {
                        arrayListArrayList = jsonWorkbench.GET("SELECT * FROM uyeler WHERE ad='" + etPersonName + "' ORDER BY DATE(tarih)");
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.button36.setVisibility(View.GONE);
                        binding.editTextTextPersonName.setEnabled(false);
                        countDownTimer = new CountDownTimer(10000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timer = true;
                                if (jsonWorkbench.finishStatus) {
                                    cancel();
                                    timer = false;
                                    binding.progressBar.setVisibility(View.GONE);
                                    binding.button36.setVisibility(View.VISIBLE);
                                    binding.editTextTextPersonName.setEnabled(true);
                                    if (arrayListArrayList.size() > 0) {
                                        binding.editTextTextPersonName.setText("");
                                        if (fastVerification)
                                            TransferNow();
                                        else
                                            Verification();
                                    } else
                                        new MyCustomDialog(getActivity()).Toast("Kullanıcı bulunamadı!");
                                }
                            }

                            @Override
                            public void onFinish() {
                                binding.editTextTextPersonName.setEnabled(true);
                                binding.progressBar.setVisibility(View.GONE);
                                binding.button36.setVisibility(View.VISIBLE);
                                timer = false;
                                new MyCustomDialog(getActivity()).Toast("Zaman Aşımı!");

                            }
                        }.start();
                    }
                } else {
                    binding.editTextTextPersonName.setHint("İsim Soyisim");
                    binding.button36.setText("Bul");
                    binding.button36.setTag("0");
                    if (binding.editTextTextPersonName.getText().toString().toUpperCase().equals(hashing.TimeBend(true))) {
                        TransferNow();
                    } else {
                        new MyCustomDialog(getActivity()).Toast("Hatalı Kod! Lütfen Tekrar Deneyin");
                    }
                    binding.editTextTextPersonName.setText(null);
                }
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OlculerimFragment.this)
                        .navigate(R.id.action_olculerimFragment_to_antrenorMenu);
            }
        });

        DBLogin dbLogin = new DBLogin(getActivity());
        if (dbLogin.getClearenceLevel() > 50) {
            binding.floatingActionButton.setVisibility(View.VISIBLE);
        }
        if (dbLogin.getClearenceLevel() > 70) {
            fastVerification = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (timer)
            countDownTimer.cancel();
    }
}