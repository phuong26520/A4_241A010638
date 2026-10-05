package vn.edu.vhu.ltdd.a4_241a010638;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LabA4InstrumentedTest {
    private void calc(MainActivity a, String x, String y, int button) {
        ((EditText) a.findViewById(R.id.edtSoA)).setText(x);
        ((EditText) a.findViewById(R.id.edtSoB)).setText(y);
        a.findViewById(button).performClick();
    }
    private void bmi(MainActivity a, String weight, String height) {
        ((EditText) a.findViewById(R.id.edtCanNang)).setText(weight);
        ((EditText) a.findViewById(R.id.edtChieuCao)).setText(height);
        a.findViewById(R.id.btnTinhBmi).performClick();
    }
    private String text(MainActivity a, int id) {
        return ((TextView) a.findViewById(id)).getText().toString();
    }
    private void screenshot(String name) throws Exception {
        Thread.sleep(400);
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        assertNotNull(bitmap);
        File dir = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "lab-a4");
        assertTrue(dir.isDirectory() || dir.mkdirs());
        try (FileOutputStream out = new FileOutputStream(new File(dir, name + ".png"))) {
            assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out));
        }
        bitmap.recycle();
        Thread.sleep(2600);
    }
    @Test public void tenLabCasesAndAdvanced() throws Exception {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(a -> {
                calc(a, "12", "3", R.id.btnChia);
                assertEquals(String.format(Locale.getDefault(), "%.2f %c %.2f = %.2f", 12.0, '/', 3.0, 4.0), text(a, R.id.tvKetQua));
                android.util.Log.i("A4_TEST", "TC1 PASS");
                calc(a, "12", "0", R.id.btnChia);
                assertEquals(a.getString(R.string.err_divide_zero), ((EditText) a.findViewById(R.id.edtSoB)).getError().toString());
                android.util.Log.i("A4_TEST", "TC2 PASS");
            });
            screenshot("02_chia_cho_0");
            scenario.onActivity(a -> {
                calc(a, "", "3", R.id.btnCong);
                assertEquals(a.getString(R.string.err_empty), ((EditText) a.findViewById(R.id.edtSoA)).getError().toString());
                assertTrue(a.findViewById(R.id.edtSoA).hasFocus());
                android.util.Log.i("A4_TEST", "TC3 PASS");
            });
            screenshot("03_de_trong_A");
            scenario.onActivity(a -> {
                String before = text(a, R.id.tvKetQua);
                ((EditText) a.findViewById(R.id.edtSoA)).setError(null);
                ((EditText) a.findViewById(R.id.edtSoB)).setError(null);
                calc(a, "abc", "3", R.id.btnNhan);
                assertEquals(before, text(a, R.id.tvKetQua));
                android.util.Log.i("A4_TEST", "TC4 PASS");
            });
            screenshot("04_sai_dinh_dang");
            scenario.onActivity(a -> {
                calc(a, "-5.5", "2", R.id.btnTru);
                assertEquals(String.format(Locale.getDefault(), "%.2f %c %.2f = %.2f", -5.5, '-', 2.0, -7.5), text(a, R.id.tvKetQua));
                android.util.Log.i("A4_TEST", "TC5 PASS");
                String before = text(a, R.id.tvKetQua);
                calc(a, "1.5.5", "3", R.id.btnCong);
                assertEquals(before, text(a, R.id.tvKetQua));
                android.util.Log.i("A4_TEST", "TC6 PASS");
                bmi(a, "60", "1.70");
                assertEquals(String.format(Locale.getDefault(), "BMI = %.1f", 60.0 / (1.7 * 1.7)), text(a, R.id.tvBmi));
                assertEquals(a.getString(R.string.bmi_normal), text(a, R.id.tvPhanLoai));
                android.util.Log.i("A4_TEST", "TC7 PASS");
                before = text(a, R.id.tvBmi);
                bmi(a, "60", "170");
                assertEquals(before, text(a, R.id.tvBmi));
                android.util.Log.i("A4_TEST", "TC8 PASS");
                bmi(a, "60", "0");
                assertEquals(before, text(a, R.id.tvBmi));
                assertFalse(text(a, R.id.tvBmi).contains("Infinity"));
                android.util.Log.i("A4_TEST", "TC9 PASS");
                before = text(a, R.id.tvKetQua);
                calc(a, "-", "3", R.id.btnCong);
                assertEquals(before, text(a, R.id.tvKetQua));
                android.util.Log.i("A4_TEST", "TC10 PASS");
                for (int i = 1; i <= 6; i++) calc(a, String.valueOf(i), "1", R.id.btnCong);
                assertEquals(5, text(a, R.id.tvLichSu).split("\n").length);
            });
            final String[] history = new String[1];
            scenario.onActivity(a -> history[0] = text(a, R.id.tvLichSu));
            scenario.recreate();
            scenario.onActivity(a -> {
                assertEquals(history[0], text(a, R.id.tvLichSu));
                android.util.Log.i("A4_TEST", "NC2 PASS");
                bmi(a, "60", "1.70");
                assertEquals(a.getColor(R.color.bmi_normal), ((TextView) a.findViewById(R.id.tvPhanLoai)).getCurrentTextColor());
                bmi(a, "68", "1.70");
                assertEquals(a.getColor(R.color.bmi_over), ((TextView) a.findViewById(R.id.tvPhanLoai)).getCurrentTextColor());
                bmi(a, "80", "1.70");
                assertEquals(a.getColor(R.color.bmi_obese), ((TextView) a.findViewById(R.id.tvPhanLoai)).getCurrentTextColor());
                android.util.Log.i("A4_TEST", "NC3 PASS");
            });
        }
    }
}