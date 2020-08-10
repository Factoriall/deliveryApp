package org.techtown.logen.style;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class RialTextView extends androidx.appcompat.widget.AppCompatEditText {
    String rawText;

    public RialTextView(Context context) {
        super(context);
    }

    public RialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        rawText = text.toString();
        String prezzo = text.toString();
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            prezzo = decimalFormat.format(Integer.parseInt(text.toString())) + "원";
        }catch (Exception e){}

        super.setText(prezzo, type);
    }

    @Nullable
    @Override
    public Editable getText() {
        Editable editable = new SpannableStringBuilder(rawText);
        return editable;
    }
}
