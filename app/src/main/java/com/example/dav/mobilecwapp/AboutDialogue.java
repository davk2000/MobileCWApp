package com.example.dav.mobilecwapp;

import android.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;



public class AboutDialogue extends DialogFragment {

    // method used to display about dialog box which appears on the action bar for every activity
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder mcAboutDialog = new AlertDialog.Builder(getActivity());
        mcAboutDialog.setMessage(R.string.dialog_About)
                .setPositiveButton(R.string.dialog_About_OK_btn, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        mcAboutDialog.setTitle("About"); // provides heading for dialog box
        mcAboutDialog.setIcon(R.drawable.ic_about);
        // Creates the AlertDialog object and returns it
        return mcAboutDialog.create();
    }


}
