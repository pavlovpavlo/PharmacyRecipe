package com.sklad.er71.presentation.recipe;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.sklad.er71.R;

import net.glxn.qrgen.android.QRCode;


public class RecipeQRDialog extends DialogFragment {

    public static final String TAG = "info_dialog";
    private static String text;

    public static RecipeQRDialog display(FragmentManager fragmentManager, String qrText) {
        RecipeQRDialog infoDialog = new RecipeQRDialog();
        infoDialog.show(fragmentManager, TAG);
        text = qrText;
        return infoDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.6f;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(windowParams);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialogSelect);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_qr, container, false);

        ImageView imageView = view.findViewById(R.id.image);
        Bitmap myBitmap = QRCode.from(text).bitmap();
        imageView.setImageBitmap(myBitmap);

        view.findViewById(R.id.dialog_container).setOnClickListener(v -> {
            dismiss();
        });
        imageView.setOnClickListener(v -> {
        });
        return view;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}