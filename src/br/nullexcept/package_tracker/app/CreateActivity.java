package br.nullexcept.package_tracker.app;

import br.nullexcept.package_tracker.models.Order;
import br.nullexcept.package_tracker.models.OrderManager;
import br.nullexcept.package_tracker.models.Shipping;
import br.nullexcept.mux.app.Launch;
import br.nullexcept.mux.graphics.Drawable;
import br.nullexcept.mux.widget.EditText;
import br.nullexcept.mux.widget.ImageView;

public class CreateActivity extends BaseActivity {
    private Shipping shipping = Shipping.NULL;
    private Drawable CORREIOS_LOGO;
    private Drawable JADLOG_LOGO;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView("create_activity");

        EditText titleEdit = findViewById("title");

        CORREIOS_LOGO = getResources().getDrawable("track_correios");
        JADLOG_LOGO = getResources().getDrawable("track_jadlog");

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
    }

    private String trackCode() {
        EditText edit = findViewById("trackId");
        String code = edit.getText().toString();
        code = code.toUpperCase();
        while (code.contains(" "))
            code = code.replaceAll(" ","");

        if (code.length() == 13 && code.matches("[A-Z][A-Z]([0-9].*)[A-Z][A-Z]")) { // Correios
            ((ImageView)findViewById("track_icon")).setImageDrawable(CORREIOS_LOGO);
            shipping = Shipping.CORREIOS;
            return code;
        } else if (code.length() > 5 && code.matches("([0-9])*")) { // Jadlog
            ((ImageView)findViewById("track_icon")).setImageDrawable(JADLOG_LOGO);
            shipping = Shipping.JADLOG;
            return code;
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
