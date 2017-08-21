package jay.messenger.spare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import jay.messenger.spare.Contact.Contact;
import jay.messenger.spare.Contact.ContactListActivity;
import jay.messenger.spare.util.DialogCallback;
import jay.messenger.spare.util.Values;

/**
 * Created by leejaebeom on 2017. 8. 19..
 */

public class CreateRoomDialog extends DialogFragment {
    Contact contact;
    private DialogCallback callback;
    public static CreateRoomDialog newInstance(Contact contact) {

        Bundle args = new Bundle();

        CreateRoomDialog fragment = new CreateRoomDialog();
        args.putSerializable("contact",contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        callback = (DialogCallback) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        contact = (Contact)getArguments().getSerializable("contact");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_create_room, null);
        final Spinner localeCodeSpinner = dialogView.findViewById(R.id.locale_code_spinner);
        final EditText name = dialogView.findViewById(R.id.create_room_name_edit);
        final EditText phone = dialogView.findViewById(R.id.create_room_phone_edit);
        TextInputLayout nameLayout = dialogView.findViewById(R.id.create_room_name_layout);
        TextInputLayout phoneLayout = dialogView.findViewById(R.id.create_room_phone_layout);

        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        localeCodeSpinner.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, Values.SERVICE_COUNTRY_CODE));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        builder.setTitle("방 생성");
        builder.setPositiveButton("생성", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                callback.onCallback(name.getText().toString(), localeCodeSpinner.getSelectedItem().toString()+ phone.getText().toString().substring(1));  //국가 번호 + 앞에 0을 뺸 전화 번호
            }
        });
        builder.setNegativeButton("취소", null);
        return builder.create();
    }
}
