// Generated by view binder compiler. Do not edit!
package com.example.mcnews.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mcnews.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnToPdf;

  @NonNull
  public final EditText etFirstName;

  @NonNull
  public final EditText etLastName;

  @NonNull
  public final ImageView imgAvatar;

  private FragmentProfileBinding(@NonNull LinearLayout rootView, @NonNull Button btnToPdf,
      @NonNull EditText etFirstName, @NonNull EditText etLastName, @NonNull ImageView imgAvatar) {
    this.rootView = rootView;
    this.btnToPdf = btnToPdf;
    this.etFirstName = etFirstName;
    this.etLastName = etLastName;
    this.imgAvatar = imgAvatar;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnToPdf;
      Button btnToPdf = ViewBindings.findChildViewById(rootView, id);
      if (btnToPdf == null) {
        break missingId;
      }

      id = R.id.etFirstName;
      EditText etFirstName = ViewBindings.findChildViewById(rootView, id);
      if (etFirstName == null) {
        break missingId;
      }

      id = R.id.etLastName;
      EditText etLastName = ViewBindings.findChildViewById(rootView, id);
      if (etLastName == null) {
        break missingId;
      }

      id = R.id.imgAvatar;
      ImageView imgAvatar = ViewBindings.findChildViewById(rootView, id);
      if (imgAvatar == null) {
        break missingId;
      }

      return new FragmentProfileBinding((LinearLayout) rootView, btnToPdf, etFirstName, etLastName,
          imgAvatar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
