package io.prototype.package_tracker.app;

import br.nullexcept.mux.widget.TextView;
import io.prototype.package_tracker.models.Order;
import io.prototype.package_tracker.models.OrderManager;
import io.prototype.package_tracker.models.Shipping;
import br.nullexcept.mux.app.Launch;
import br.nullexcept.mux.widget.EditText;

public class CreateActivity extends BaseActivity {
    private Shipping shipping = Shipping.NULL;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView("create_activity");

        EditText titleEdit = findViewById("title");

        findViewById("register").setOnClickListener(v->{
            String trackCode = trackCode();
            String title = titleEdit.getText().toString().trim();
            if (trackCode.length() == 0 || title.length() == 0)
                return;

            OrderManager.register(new Order(title, trackCode, shipping));
            startService(new Launch<>(TrackService.class)).requestRefresh();
            finish();
        });

        findViewById("cancel").setOnClickListener(v-> finish());
        checkForm();
        trackCode();
    }

    private String trackCode() {
        EditText edit = findViewById("trackId");
        String code = edit.getText().toString();
        code = code.toUpperCase();
        while (code.contains(" "))
            code = code.replaceAll(" ","");

        if (code.length() == 13 && code.matches("[A-Z][A-Z]([0-9].*)[A-Z][A-Z]")) { // Correios
            shipping = Shipping.CORREIOS;
            ((TextView)findViewById("shipper_name")).setText(getString("package_by")+" CORREIOS");
            return code;
        } else if (code.length() > 5 && code.matches("([0-9])*")) { // Jadlog
            shipping = Shipping.JADLOG;
            ((TextView)findViewById("shipper_name")).setText(getString("package_by")+" JADLOG");
            return code;
        } else {
            ((TextView)findViewById("shipper_name")).setText("");
        }
        shipping = Shipping.NULL;

        return "";
    }

    private void checkForm() {
        postDelayed(this::checkForm, 500);
        EditText titleEdit = findViewById("title");

        String trackCode = trackCode();
        String title = titleEdit.getText().toString().trim();
        String id = trackCode + "/" + shipping.name();
        if (trackCode.length() == 0 || title.length() == 0 || shipping == Shipping.NULL || OrderManager.has(id)) {
            if (findViewById("register").getAlpha() != 0.2f)findViewById("register").setAlpha(0.2f);
            return;
        }
        if (findViewById("register").getAlpha() != 1f)findViewById("register").setAlpha(1f);

    }
}
