package dev.hour.fragment.general;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import dev.hour.R;

public class AddPictureFragment extends Fragment implements
        View.OnClickListener, View.OnTouchListener {

    /// ---------------------
    /// Public Static Members

    public final static String  TAG                         = "AddPictureFragment"  ;
    public final static int     STORAGE_PERMISSION_REQUEST  = 901                   ;
    public final static int     STANDARD_WIDTH              = 192                   ;
    public final static int     STANDARD_HEIGHT             = 192                   ;

    /// ---------------
    /// Private Members

    private Listener                        listener                ;
    private Object                          requestor               ;
    private Map<String, Object>             export                  ;
    private ImageView                       userImage               ;
    private PointF                          start                   ;
    private Matrix                          startMatrix             ;
    private Matrix                          matrix                  ;
    private float                           scale                   ;
    private ActivityResultLauncher<String>  activityResultLauncher  ;

    /// --------
    /// Fragment

    /**
     * Invoked when the [BusinessAddRestaurantPictureFragment] should be created its' view.
     * Registers the [ActivityResultLauncher]
     * @param bundle SavedInstanceState
     */
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        this.activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(), uri -> {

                    if (userImage != null) {

                        userImage.setScaleType(ImageView.ScaleType.MATRIX);
                        userImage.setImageURI(uri);

                        fitImage();

                    }

                });

    }

    /**
     * Invoked when the [BusinessAddRestaurantPictureFragment] should create its' view. Inflates the
     * view and any persist state
     * @param layoutInflater The [LayoutInflater] responsible for inflating the view
     * @param viewGroup The parent
     * @param savedInstanceState SavedInstanceState
     * @return [View] instance
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup,
                             final Bundle savedInstanceState) {

        final GestureDetector clickGestureDetector =
                new GestureDetector(requireContext(), new GestureDetector.OnGestureListener() {
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

        final View layout               =
                layoutInflater.inflate(R.layout.fragment_add_picture, viewGroup, false);
        final View uploadPictureButton  =
                layout.findViewById(R.id.fragment_add_picture_upload_picture_button);
        final View backButton           =
                layout.findViewById(R.id.fragment_add_picture_back_button);
        final View savePictureButton    =
                layout.findViewById(R.id.fragment_add_picture_save_picture_button);

        this.userImage = layout.findViewById(R.id.fragment_add_picture_user_image);

        uploadPictureButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        savePictureButton.setOnClickListener(this);

        this.userImage.setOnTouchListener(this);

        final ScaleGestureDetector scaleGestureDetector =
                new ScaleGestureDetector(requireContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {

            @Override
            public boolean onScale(final ScaleGestureDetector detector) {

                scale = detector.getScaleFactor();
                return true;

            }

        });

        this.scale          = 1.0f  ;

        handleNavigationBarBackButton();

        return layout;

    }

    /**
     * Invoked when a [View] of interest is clicked by the user. Invokes the corresponding
     * callbacks depending on the [View] that was clicked
     * @param view The [View] instance that was clicked
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(final View view) {

        final int id = view.getId();

        switch(id) {

            case R.id.fragment_add_picture_upload_picture_button:

                final Context context = getContext();

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

                if(this.userImage != null) {

                    // Create an empty bitmap
                    final Bitmap bitmap = Bitmap.createBitmap(
                            this.userImage.getWidth(),
                                    this.userImage.getHeight(), Bitmap.Config.ARGB_8888);

                    // Insert it into the canvas
                    final Canvas canvas = new Canvas(bitmap);

                    // Manually draw the contents
                    this.userImage.draw(canvas);

                    // Scale the bitmap
                    final Bitmap scaledBitmap =
                            Bitmap.createScaledBitmap(
                                    bitmap, STANDARD_WIDTH, STANDARD_HEIGHT, false);

                    try {

                        // Create an output stream for the compressed image
                        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                        // Compress the image
                        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                        // Get the bytes and length
                        byte[] data = outputStream.toByteArray();
                        long length = data.length;

                        // Flush
                        outputStream.flush();
                        outputStream.close();

                        // Insert the image into the export
                        this.export.put("picture", new ByteArrayInputStream(data));
                        this.export.put("content_length", length);

                    } catch(final Exception exception) {

                        Log.e(TAG, "Compression Error");

                    }

                    if(this.listener != null)
                        this.listener.onAddPictureReceived(this.requestor);

                }

                break;

            case R.id.fragment_add_picture_back_button:

                if(this.listener != null)
                    this.listener.onAddPictureCancelled(this.requestor);

                break;

        }

    }

    /// --------------------
    /// View.OnTouchListener

    /**
     * Invoked when a [View] of interest is touched by the user. Invokes the corresponding
     * callbacks depending on the [View] that was touched
     * @param view The [View] instance that was touched
     */
    @Override
    public boolean onTouch(final View view, final MotionEvent event) {

        switch(event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                if(this.startMatrix == null)
                    this.startMatrix = new Matrix();

                if(this.start == null)
                    this.start = new PointF();

                if(this.matrix == null)
                    this.matrix = new Matrix();

                this.startMatrix.set(userImage.getImageMatrix());

                this.start.x = (event.getRawX());
                this.start.y = (event.getRawY());

                break;

            case MotionEvent.ACTION_MOVE:

                float deltaX = event.getRawX() - this.start.x;
                float deltaY = event.getRawY() - this.start.y;

                this.matrix.set(this.startMatrix);
                this.matrix.postTranslate(deltaX, deltaY);

                float[] values = new float[9];

                this.matrix.getValues(values);

                final Drawable drawable = this.userImage.getDrawable();

                if(drawable != null) {

                    float thresholdX = this.userImage.getMeasuredWidth() -
                            this.userImage.getDrawable().getIntrinsicWidth() * values[0];

                    float thresholdY = this.userImage.getMeasuredHeight() -
                            this.userImage.getDrawable().getIntrinsicHeight() * values[4];

                    if (values[2] > 0.0f) values[2] = 0.0f;
                    else if (values[2] < thresholdX) values[2] = thresholdX;

                    if (values[5] > 0.0f) values[5] = 0.0f;
                    else if (values[5] < thresholdY) values[5] = thresholdY;

                    this.matrix.setValues(values);

                    this.userImage.setImageMatrix(new Matrix(matrix));

                }

        }

        return true;

    }

    /// --------------
    /// Public Methods

    /**
     * Sets the [Listener] that will receive callbacks when the user interacts with the [Fragment]
     * @param listener The [Listener] that will receive callbacks when the user interacts with the
     *                 fragment
     * @param requestor The instance that requested the [Fragment]
     */
    public void setListener(final Listener listener, final Object requestor) {

        this.listener = listener;
        this.requestor = requestor;

    }

    /**
     * Sets the [Map] export object. The selected image will be placed in the given object
     * to be retrieved by the requestor
     * @param export The [Map] instance that holds the image and content length
     */
    public void setExport(final Map<String, Object> export) {

        this.export = export;

    }

    /**
     * Invoked when the appropriate storage permissions have been
     * granted by the user. Begins the image selection activity.
     */
    public void storagePermissionsGranted() {

        activityResultLauncher.launch("image/*");

    }

    /// ---------------
    /// Private Methods

    /**
     * Fits the current image into the boundaries of the display
     */
    private void fitImage() {

        final Drawable drawable = this.userImage.getDrawable();

        if(drawable != null) {

            final int viewWidth = this.userImage.getMeasuredWidth();

            final int imageWidth    = drawable.getIntrinsicWidth();
            final int imageHeight   = drawable.getIntrinsicHeight();

            final int scaleDimension = Math.min(imageHeight, imageWidth);

            final float scale = (float) viewWidth / scaleDimension;
            final Matrix matrix = this.userImage.getImageMatrix();

            matrix.setScale(scale, scale);

            userImage.setImageMatrix(matrix);

        }

    }

    /// ----------
    /// Interfaces

    /**
     * Defines callbacks to allow generic classes to request an image.
     */
    public interface Listener {

        void onAddPictureReceived(final Object requestor);
        void onAddPictureCancelled(final Object requestor);

    }

    /**
     * Invoked when the onCreateView() method has completed. Callback function
     * for when the back button on the Android OS navigation bar is pressed.
     */
    private void handleNavigationBarBackButton() {
        final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(listener != null)
                    listener.onAddPictureCancelled(requestor);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

}
