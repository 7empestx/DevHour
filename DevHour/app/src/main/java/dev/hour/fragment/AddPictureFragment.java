package dev.hour.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.util.Map;

import dev.hour.R;

public class AddPictureFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    /// --------------
    /// Static Members

    public final static String TAG                      = "AddPictureFragment";
    public final static int STORAGE_PERMISSION_REQUEST = 901;
    public final static int CHOOSE_PICTURE_REQUEST     = 912;
    public final static int STANDARD_WIDTH             = 192;
    public final static int STANDARD_HEIGHT            = 192;

    /// ---------------
    /// Private Members

    private Map<String, Object>     export                  ;
    private ImageView               userImage               ;
    private PointF                  start                   ;
    private Matrix                  startMatrix             ;
    private Matrix                  matrix                  ;
    private ScaleGestureDetector    scaleGestureDetector    ;
    private int                     pointerCount            ;
    private float                   scale                   ;
    private GestureDetector         clickGestureDetector    ;

    /// --------
    /// Fragment

    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup container, final Bundle savedInstanceState) {

        clickGestureDetector = new GestureDetector(requireContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {

                return false;

            }

            @Override
            public void onShowPress(MotionEvent e) { /* Empty */ }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) { /* Empty */ }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });

        final View layout = layoutInflater.inflate(R.layout.fragment_add_picture, container, false);
        final View uploadPictureButton = layout.findViewById(R.id.fragment_add_picture_upload_picture_button);
        final View backButton          = layout.findViewById(R.id.fragment_add_picture_back_button);
        final View savePictureButton   = layout.findViewById(R.id.fragment_add_picture_save_picture_button);

        this.userImage = layout.findViewById(R.id.fragment_add_picture_user_image);

        uploadPictureButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        savePictureButton.setOnClickListener(this);

        this.userImage.setOnTouchListener(this);

        this.scaleGestureDetector = new ScaleGestureDetector(requireContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {

            @Override
            public boolean onScale(final ScaleGestureDetector detector) {

                scale = detector.getScaleFactor();
                return true;

            }

        });

        this.scale          = 1.0f  ;
        this.pointerCount   = 0     ;

        return layout;

    }

    @Override
    public void onClick(final View view) {

        final int id = view.getId();

        switch(id) {

            case R.id.fragment_add_picture_upload_picture_button:

                final Context context = requireContext();

                if(context != null) {

                    if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {

                        requireActivity().requestPermissions(
                                new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                                STORAGE_PERMISSION_REQUEST);

                    } else if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {

                        storagePermissionsGranted();

                    }

                }

                break;

            case R.id.fragment_add_picture_save_picture_button:

                break;

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /// --------------
    /// Public Methods

    public void setExport(final Map<String, Object> export) {

        this.export = export;

    }

    public void storagePermissionsGranted() {

        final Intent intent = new Intent();

        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // If we received an OK code
                    if(result.getResultCode() == Activity.RESULT_OK) {

                        // Attempt to retrieve the uri
                        final Uri filePath = result.getData().getData();

                        if(userImage != null) {

                            userImage.setScaleType(ImageView.ScaleType.MATRIX);
                            userImage.setImageURI(filePath);

                            fitImage();

                        }

                    }

                }

        );

    }

    private void fitImage() {

        final Drawable drawable = this.userImage.getDrawable();

        if(drawable != null) {

            final int viewWidth = this.userImage.getMeasuredWidth();

            final int imageWidth    = drawable.getIntrinsicWidth();
            final int imageHeight   = drawable.getIntrinsicHeight();

            final int scaleDimension = (imageHeight > imageWidth) ? imageWidth : imageHeight;

            final float scale = Float.valueOf(viewWidth) / scaleDimension;
            final Matrix matrix = this.userImage.getImageMatrix();

            matrix.setScale(scale, scale);

            userImage.setImageMatrix(matrix);

        }

    }

}