package com.demo.temperatureconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText editC, editF;
    private Button btnCalc, btnClear;
    private TextView tvAbout;
    private String stringC, stringF;
    private double doubleC, doubleF;
    private DecimalFormat decimalFormat;

    private enum Info {Exception, Forget, Error, OutOfRange}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
    }

    public void findViews() {
        editC = (EditText) findViewById(R.id.editC);
        editF = (EditText) findViewById(R.id.editF);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        btnClear = (Button) findViewById(R.id.btnClear);
        tvAbout = (TextView) findViewById(R.id.tvAbout);
    }

    private void setListeners() {
        btnCalc.setOnClickListener(litsener1);
        btnClear.setOnClickListener(litsener2);
        tvAbout.setOnClickListener(litsener3);
    }

    private View.OnClickListener litsener1 = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                stringC = editC.getText().toString();
                stringF = editF.getText().toString();
                decimalFormat = new DecimalFormat("0.00");

                if (stringC.equals("") & stringF.equals(""))
                    showInfo(Info.Forget);
                else if (!stringF.equals("") & stringC.equals(""))
                    FtoC();
                else
                    CtoF();

                tvAbout.setVisibility(TextView.VISIBLE);

            } catch (Exception e) {
                showInfo(Info.Exception);
            }
        }
    };

    private void showInfo(Info show) {
        switch (show) {//三種case分別顯示不同的Toast訊息，例如"發生例外"、"輸入錯誤"、"忘了輸入"
            case Exception:
                Toast.makeText(this, R.string.exception, Toast.LENGTH_LONG).show();
                break;
            case Forget:
                Toast.makeText(this, R.string.forget, Toast.LENGTH_LONG).show();
                break;
            case Error:
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
                break;
            case OutOfRange:
                Toast.makeText(this, R.string.out_of_range, Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void CtoF() {
        if (stringC.matches("[-.]{1}") | stringC.startsWith("-."))
            showInfo(Info.Error);
        else {
            doubleC = Double.parseDouble(stringC);
            if (doubleC >= -273.15) {
                doubleF = doubleC * (9d / 5) + 32;
                editF.setText(decimalFormat.format(doubleF));
            } else {
                showInfo(Info.OutOfRange);
                editF.setText(R.string.not_applicable);
            }
        }
    }

    private void FtoC() {
        if (stringF.matches("[-.]{1}") | stringF.startsWith("-."))
            showInfo(Info.Error);
        else {
            doubleF = Double.parseDouble(stringF);
            if (doubleF >= -459.67) {
                doubleC = (doubleF - 32) * (5d / 9);
                editC.setText(decimalFormat.format(doubleC));
            } else {
                showInfo(Info.OutOfRange);
                editC.setText(R.string.not_applicable);
            }
        }
    }

    private View.OnClickListener litsener2 = new View.OnClickListener() {
        public void onClick(View v) {
            editC.setText("");
            editF.setText("");
        }
    };

    private View.OnClickListener litsener3 = new View.OnClickListener() {
        public void onClick(View v) {
            tvAbout.setText(getString(R.string.text_demo));
            tvAbout.setSelected(true);
            tvAbout.setOnClickListener(null);
        }
    };
}
