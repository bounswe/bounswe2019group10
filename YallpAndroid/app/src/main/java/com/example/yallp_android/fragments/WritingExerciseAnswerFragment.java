package com.example.yallp_android.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Annotation;
import com.example.yallp_android.models.AnnotationDTO;
import com.example.yallp_android.models.TextAnnotationDeleteDTO;
import com.example.yallp_android.models.WritingListElement;
import com.example.yallp_android.util.RetroClients.AnnotationRetroClient;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WritingExerciseAnswerFragment extends Fragment {

    private TextView answerText;
    private TextView questionText;
    private ScrollView textScrollView;
    private ImageView writingImageView;
    private ConstraintLayout writingImageLayout;
    private ArrayList<Annotation> annotations = new ArrayList<Annotation>();
    private String userOrEvaluator;
    private String token;
    private int writingResultId;
    private String answer;
    private String imageUrl;
    private int clickedAnnoIndex;


    public static WritingExerciseAnswerFragment newInstance(int writingId,String answerText, String token, String userOrEvaluator, String imageUrl, int writingResultId) {
        WritingExerciseAnswerFragment fragment = new WritingExerciseAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("writingId", writingId);
        bundle.putSerializable("answerText", answerText);
        bundle.putSerializable("userOrEvaluator", userOrEvaluator);
        bundle.putSerializable("imageUrl", imageUrl);
        bundle.putSerializable("token", token);
        bundle.putSerializable("writingResultId", writingResultId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_writing_exercise_answer, container, false);
        questionText = view.findViewById(R.id.questionText);
        answerText = view.findViewById(R.id.answerText);
        textScrollView = view.findViewById(R.id.textScrollView);
        writingImageLayout = view.findViewById(R.id.writingImageLayout);
        writingImageView = view.findViewById(R.id.writingImageView);
        imageUrl = (String) getArguments().getSerializable("imageUrl");
        answerText.setMovementMethod(LinkMovementMethod.getInstance());
        final int writingId =(Integer) getArguments().getSerializable("writingId");
        answer = (String) getArguments().getSerializable("answerText");
        token = (String) getArguments().getSerializable("token");
        userOrEvaluator = (String) getArguments().getSerializable("userOrEvaluator");
        writingResultId = (Integer) getArguments().getSerializable("writingResultId"); // 0 for writer

        Call<WritingListElement> call;

        call = WritingRetroClient
                .getInstance()
                .getWritingApi()
                .readDetailsOfOneWriting("Bearer " + token, writingId);

        call.enqueue(new Callback<WritingListElement>() {
            @Override
            public void onResponse(Call<WritingListElement> call, Response<WritingListElement> response) {
                if (response.isSuccessful()) {
                    WritingListElement result = response.body();
                    questionText.setText(result.getWritingDTO().getTaskText());
                    if (imageUrl != null && !imageUrl.equals("")) {

                        /*Picasso.with(getContext())
                                .load(imageUrl)
                                .into(writingImageView);*/

                        Picasso.with(getContext()).load(imageUrl).into(writingImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                textScrollView.setVisibility(View.GONE);
                                writingImageLayout.setVisibility(View.VISIBLE);

                                getAnnotationsList(writingResultId);
                                //if(userOrEvaluator == "evaluator"){
                                //    enableAnnotations(writingResultId);
                                //}
                            }

                            @Override
                            public void onError() {

                            }
                        });

                    } else {
                        answerText.setText(answer);
                        writingImageLayout.setVisibility(View.GONE);
                        textScrollView.setVisibility(View.VISIBLE);
                        if(result.getWritingResultDTO() != null){
                            writingResultId = result.getWritingResultDTO().getId();
                        }

                        getAnnotationsList(writingResultId);

                        if(userOrEvaluator == "evaluator"){
                            enableAnnotations(writingResultId);
                        }

                    }

                } else {
                    Toast.makeText(getContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WritingListElement> call, Throwable t) {

            }
        });


        return view;
    }

    private void enableAnnotations(final int writingResultId) {
        answerText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.writing_annotation, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.addAnnotation) {
                    final int startIndex = answerText.getSelectionStart();
                    final int endIndex = answerText.getSelectionEnd();
                    mode.finish();
                    return annotateText(startIndex, endIndex);
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
    }

    private boolean annotateText(final int startIndex, final int endIndex) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edittext = new EditText(getContext());
        alert.setTitle(answerText.getText().toString().substring(startIndex, endIndex));
        alert.setView(edittext);
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final AnnotationDTO anno = new AnnotationDTO(
                        edittext.getText().toString(),
                        0,null,0, endIndex, startIndex, null, writingResultId
                );

                Call<AnnotationDTO> call;
                call = AnnotationRetroClient.getInstance().getAnnotationApi().createTextAnnotation("Bearer " + token, anno);

                call.enqueue(new Callback<AnnotationDTO>() {
                    @Override
                    public void onResponse(Call<AnnotationDTO> call, Response<AnnotationDTO> response) {
                        Toast.makeText(getContext(), "Annotation created", Toast.LENGTH_LONG).show();
                        getAnnotationsList(writingResultId);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AnnotationDTO> call, Throwable t) {

                    }
                });
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
        return true;
    }

    private void getAnnotationsList(int writingId) {
        annotations.clear();

        if (imageUrl != null && !imageUrl.equals("")) {
            Call<Annotation[]> call;

            call = AnnotationRetroClient.getInstance()
                    .getAnnotationApi()
                    .getAllImageAnnotationsOfWriting("Bearer " + token, imageUrl);

            call.enqueue(new Callback<Annotation[]>() {
                @Override
                public void onResponse(Call<Annotation[]> call, Response<Annotation[]> response) {
                    if(response.isSuccessful()){
                        Collections.addAll(annotations, response.body());

                        showAnnotations();
                    }
                }

                @Override
                public void onFailure(Call<Annotation[]> call, Throwable t) {

                }
            });

        } else {
            Call<Annotation[]> call;

            call = AnnotationRetroClient.getInstance()
                    .getAnnotationApi()
                    .getAllTextAnnotationsOfWriting("Bearer " + token, writingId);

            call.enqueue(new Callback<Annotation[]>() {
                @Override
                public void onResponse(Call<Annotation[]> call, Response<Annotation[]> response) {
                    if(response.isSuccessful()){
                        Collections.addAll(annotations, response.body());

                        showAnnotations();
                    }
                }

                @Override
                public void onFailure(Call<Annotation[]> call, Throwable t) {
                    Log.e("tag", t.getMessage());

                }
            });
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    private void showAnnotations() {
        if (imageUrl != null && !imageUrl.equals("")) {
            if(!annotations.isEmpty()){
                //draw annotations
                Bitmap originalImage = ((BitmapDrawable)writingImageView.getDrawable()).getBitmap();
                int originalWidth = originalImage.getWidth();
                int originalHeight = originalImage.getHeight();
                float viewWidth = convertDpToPixel(writingImageView.getWidth(),getContext());
                float viewHeight = convertDpToPixel(writingImageView.getHeight(),getContext());
                final float xOffset = (viewWidth - originalWidth) / 2;
                final float yOffset = (viewHeight - originalHeight) / 2;
                DisplayMetrics dm = new DisplayMetrics();
                writingImageView.getDisplay().getMetrics(dm);
                Bitmap tempBitmap = Bitmap.createBitmap(dm, originalWidth, originalHeight, Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                Paint drawPaint = new Paint();
                drawPaint.setColor(Color.GREEN);
                drawPaint.setAntiAlias(true);
                drawPaint.setStrokeWidth(10);
                drawPaint.setStyle(Paint.Style.FILL);
                drawPaint.setStrokeJoin(Paint.Join.ROUND);
                drawPaint.setStrokeCap(Paint.Cap.ROUND);
                drawPaint.setAlpha(100);

                final ArrayList<RectF> annoBoxes = new ArrayList<>();
                final ArrayList<String> annoCreators = new ArrayList<>();
                final ArrayList<String> annoMessages = new ArrayList<>();

                for(final Annotation anno : annotations){
                    String[] xywh = anno.getTarget().getSelector().getValue().split("[(xywh=),]+", 0);

                    float x = (float)Integer.parseInt(xywh[1]) / 100f * (float)originalWidth;
                    float y = (float)Integer.parseInt(xywh[2]) / 100f * (float)originalHeight;
                    float w = (float)Integer.parseInt(xywh[3]) / 100f * (float)originalWidth;
                    float h = (float)Integer.parseInt(xywh[4]) / 100f * (float)originalHeight;
                    annoBoxes.add(new RectF(x,y,x+w,y+h));

                    annoCreators.add(anno.getCreator().getNickname());
                    annoMessages.add(anno.getBodyValue());
                }

                tempCanvas.drawBitmap(originalImage, 0, 0, null);

                for(RectF ab : annoBoxes) {
                    tempCanvas.drawRoundRect(ab, 2, 2, drawPaint);
                }
                writingImageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
                writingImageView.bringToFront();

                writingImageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int touchX = (int) (convertDpToPixel(event.getX(), getContext()) - xOffset);
                        int touchY = (int) (convertDpToPixel(event.getY(), getContext()) - yOffset);
                        if(event.getAction() == MotionEvent.ACTION_DOWN){

                                System.out.println("Touch x = " + touchX + " y = " + touchY );
                                for(int i =0; i< annoBoxes.size();i++){
                                    if(annoBoxes.get(i).contains(touchX,touchY)){
                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                        alert.setTitle(annoCreators.get(i) + " said:");
                                        alert.setMessage(annoMessages.get(i));
                                        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        alert.show();
                                        return true;
                                    }
                                }
                        }
                        return true;
                    }
                });

            }
        } else {
            if(!annotations.isEmpty()){
                Spannable newAnswer = new SpannableString(answer);
                for(final Annotation anno : annotations){
                    final int annoId = Integer.parseInt(anno.getId().substring(anno.getId().lastIndexOf('/') + 1));
                    final int startIndex = Math.min(anno.getTarget().getSelector().getStart(), anno.getTarget().getSelector().getEnd());
                    final int endIndex = Math.max(anno.getTarget().getSelector().getStart(), anno.getTarget().getSelector().getEnd());
                    final String annotator = anno.getCreator().getNickname();
                    final String annotationText = anno.getBodyValue();
                    newAnswer.setSpan(new BackgroundColorSpan(Color.GREEN), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    newAnswer.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle(annotator + " said:");
                            alert.setMessage(annotationText);
                            alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            if(userOrEvaluator == "evaluator"){
                                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editTextAnnotation(startIndex,endIndex,annoId);
                                    }
                                });
                                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteTextAnnotation(annoId);
                                    }
                                });
                            }
                            alert.show();
                        }
                    } , startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                answerText.setText(newAnswer);
            } else {
                answerText.setText(answer);
            }
        }
    }



    private boolean deleteTextAnnotation(int annoId) {
        Call<TextAnnotationDeleteDTO> call2;

        call2 = AnnotationRetroClient.getInstance().getAnnotationApi().deleteTextAnnotation("Bearer " + token, annoId);

        call2.enqueue(new Callback<TextAnnotationDeleteDTO>() {
            @Override
            public void onResponse(Call<TextAnnotationDeleteDTO> call, Response<TextAnnotationDeleteDTO> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    getAnnotationsList(writingResultId);
                }
            }

            @Override
            public void onFailure(Call<TextAnnotationDeleteDTO> call, Throwable t) {
                Toast.makeText(getContext(), "There was an error", Toast.LENGTH_LONG).show();
            }
        });


        return true;
    }

    private boolean editTextAnnotation(final int startIndex, final int endIndex, final int annoId){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edittext = new EditText(getContext());
        alert.setTitle(answerText.getText().toString().substring(startIndex, endIndex));
        alert.setView(edittext);
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final AnnotationDTO anno = new AnnotationDTO(
                        edittext.getText().toString(),
                        0,null,annoId, endIndex, startIndex, null, writingResultId
                );

                Call<AnnotationDTO> call;
                call = AnnotationRetroClient.getInstance().getAnnotationApi().updateTextAnnotation("Bearer " + token, anno);

                call.enqueue(new Callback<AnnotationDTO>() {
                    @Override
                    public void onResponse(Call<AnnotationDTO> call, Response<AnnotationDTO> response) {
                        Toast.makeText(getContext(), "Annotation updated", Toast.LENGTH_LONG).show();
                        getAnnotationsList(writingResultId);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AnnotationDTO> call, Throwable t) {
                        Toast.makeText(getContext(), "There was an error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
        return true;
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
