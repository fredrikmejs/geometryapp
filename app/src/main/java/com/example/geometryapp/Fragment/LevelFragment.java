package com.example.geometryapp.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geometryapp.Controllers.AnswerController;
import com.example.geometryapp.Controllers.LevelController;
import com.example.geometryapp.Enum.Categories;
import com.example.geometryapp.GameState;
import com.example.geometryapp.Singleton;
import com.example.geometryapp.ValidatedAnswer;
import com.example.geometryapp.Views.Canvas;
import com.example.geometryapp.R;

import static android.content.Context.MODE_PRIVATE;

public class LevelFragment extends Fragment {

    //THIS IS A FRAGMENT THAT DISPLAYS LEVELS.

    private Boolean nightModeOn = true;
    private Boolean arabicNumeralsOn = true;
    private Boolean purpleColorOn = true;
    private Boolean answeredCorrectly = false;//User has input correct answer
    private Canvas canvas;
    private LinearLayout linearLayout;
    private LevelController levelController = new LevelController();
    private AnswerController AnswerController = new AnswerController();
    private GameState gameState;

    private TextView TVX;
    private TextView TVY;
    private TextView TVValue;//Textview which shows a value. For example area of the rectangle.
    private TextView TVQuestion;
    private ImageView IBTNValidate;
    private TextView TVCoordinateSelected;//This is the Textview user has selected by clicking. It's TVX, TVY Oor TVValue
    private ImageView IVNext;
    private Button BTN1;
    private Button BTN2;
    private Button BTN3;
    private Button BTN4;
    private Button BTN5;
    private Button BTN6;
    private Button BTN7;
    private Button BTN8;
    private Button BTN9;
    private Button BTN0;
    private Button BTNPi;
    private Button BTNDot;
    Button BTNMinus;

    private int categoryIndex = 1;
    private int levelIndex = 1;
    boolean animationGoing = false;

    private SharedPreferences prefs;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_level, container, false);
        prefs = getActivity().getSharedPreferences("Modes", MODE_PRIVATE);
        TVQuestion = view.findViewById(R.id.TVQuestion);
        linearLayout = view.findViewById(R.id.LLCoordinateSystem);
        IBTNValidate = view.findViewById(R.id.IBTNValidate);
        TVX = view.findViewById(R.id.TVX);
        TVY = view.findViewById(R.id.TVY);
        TVValue = view.findViewById(R.id.TVValue);
        Bundle bundle = getArguments();
        levelIndex = bundle.getInt("levelIndex");
        categoryIndex = bundle.getInt("categoryIndex");
        arabicNumeralsOn = bundle.getBoolean("englishNumerals", true);
        BTN1 = view.findViewById(R.id.BTN1);
        BTN2 = view.findViewById(R.id.BTN2);
        BTN3 = view.findViewById(R.id.BTN3);
        BTN4 = view.findViewById(R.id.BTN4);
        BTN5 = view.findViewById(R.id.BTN5);
        BTN6 = view.findViewById(R.id.BTN6);
        BTN7 = view.findViewById(R.id.BTN7);
        BTN8 = view.findViewById(R.id.BTN8);
        BTN9 = view.findViewById(R.id.BTN9);
        BTN0 = view.findViewById(R.id.BTN0);
        BTNPi = view.findViewById(R.id.BTNPi);
        BTNDot = view.findViewById(R.id.BTNDot);

        if (categoryIndex == 10 && (levelIndex == 2 || levelIndex == 4 || levelIndex == 6 )){
            BTNDot.setText("x");
        } else {
            BTNDot.setText(".");
        }


        BTNMinus = view.findViewById(R.id.BTNMinus);
        if (categoryIndex == 10){
            BTNMinus.setText("/");
        } else{
            BTNMinus.setText("-");
        }


        if (arabicNumeralsOn) {
            //Setting arabic numerals on
            Typeface typefaceArabic = ResourcesCompat.getFont(getContext(), R.font.bzar);
            BTN1.setTypeface(typefaceArabic);
            BTN2.setTypeface(typefaceArabic);
            BTN3.setTypeface(typefaceArabic);
            BTN4.setTypeface(typefaceArabic);
            BTN5.setTypeface(typefaceArabic);
            BTN6.setTypeface(typefaceArabic);
            BTN7.setTypeface(typefaceArabic);
            BTN8.setTypeface(typefaceArabic);
            BTN9.setTypeface(typefaceArabic);
            BTN0.setTypeface(typefaceArabic);
            BTNPi.setTypeface(typefaceArabic);
            BTNDot.setTypeface(typefaceArabic);
            BTNMinus.setTypeface(typefaceArabic);
            TVX.setTypeface(typefaceArabic);
            TVY.setTypeface(typefaceArabic);
            TVValue.setTypeface(typefaceArabic);
        }
        ImageView IVUp = view.findViewById(R.id.IVUp);
        ImageView IVRestart = view.findViewById(R.id.IVRestart);
        ImageView IVBack = view.findViewById(R.id.IVBack);
        IVNext = view.findViewById(R.id.IVNext);
        ImageButton BTNDelete = view.findViewById(R.id.BTNDelete);
        TVX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameState.getCategory() == Categories.COORDINATESFROMPOINT
                        || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                        || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY) {
                    TVCoordinateSelected = TVX;
                    TVX.setAlpha(0.7f);
                    TVY.setAlpha(1f);

                }
            }
        });

        TVY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameState.getCategory() == Categories.COORDINATESFROMPOINT
                        || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                        || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY) {
                    TVCoordinateSelected = TVY;
                    TVX.setAlpha(1f);
                    TVY.setAlpha(0.7f);
                }
            }
        });

        BTN0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN0.getText().toString());
            }
        });
        BTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN1.getText().toString());
            }
        });
        BTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN2.getText().toString());
            }
        });
        BTN3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN3.getText().toString());
            }
        });
        BTN4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN4.getText().toString());
            }
        });
        BTN5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN5.getText().toString());
            }
        });
        BTN6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN6.getText().toString());
            }
        });
        BTN7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN7.getText().toString());
            }
        });
        BTN8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN8.getText().toString());
            }
        });
        BTN9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTN9.getText().toString());
            }
        });
        BTNPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTNPi.getText().toString());
            }
        });
        BTNDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addTextTVCoordinate(BTNDot.getText().toString());
            }
        });
        BTNMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextTVCoordinate(BTNMinus.getText().toString());
            }
        });
        BTNDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAnswerTVText();
            }
        });
        IVRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLevel();
            }
        });
        IVUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        IVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameState.getSelectedDot().goPreviousCoordinate();
                canvas.postInvalidate();
            }
        });
        IVNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextLevel();
            }
        });
        IBTNValidate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (((TVX.getText().equals("?") || TVY.getText().equals("?")) && !(categoryIndex == 7 || categoryIndex == 8 || categoryIndex == 9 ||
                        categoryIndex == 10 || categoryIndex ==11 || categoryIndex ==5 || categoryIndex == 3)) && gameState.getAttempt() != 3){
                        Toast.makeText(getContext(), "Invalid answer", Toast.LENGTH_LONG).show();
                } else {
                    int attempt = gameState.getAttempt();
                    if (attempt >= 2) {
                        validateAnswer();

                        if (categoryIndex == 4 || categoryIndex == 6){
                            Singleton singleton = Singleton.getInstance();
                            TVQuestion.setText("The answer for this level is: (" + singleton.getXCoordinate() + "," + singleton.getYCoordinate() +")" );
                            TVX.setText(""+singleton.getXCoordinate());
                            TVY.setText(""+singleton.getYCoordinate());
                        }else if (categoryIndex == 2){
                            Singleton singleton = Singleton.getInstance();
                            TVQuestion.setText("The answer for this level is: (" + singleton.getXCoordinate() + "," + singleton.getYCoordinate() +")" );
                            TVX.setText(""+singleton.getXCoordinate());
                            TVY.setText(""+singleton.getYCoordinate());
                        }

                        if (attempt >= 3) {
                            createLevel();
                        }
                    } else {
                        validateAnswer();
                    }
                }
            }
        });
        createLevel();
        Toast.makeText(getContext(), "Category: " + categoryIndex + ", level: " + levelIndex, Toast.LENGTH_LONG).show();// TODO: 8.6.2020 Remove when done!
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void validateAnswer() {
        ValidatedAnswer validatedAnswer = validateAnswer(gameState);
        if (validatedAnswer.getCorrectAnswer() != null) {
            gameState.setCoordinateCorrectAnswer(validatedAnswer.getCorrectAnswer());
        }
        if (answeredCorrectly) {
            moveToNextLevel();
        } else if (validatedAnswer.isAnswerCorrect()) {
            setAnswerTVBackgroundResources(validatedAnswer);
            canvas.setCoordinateXAndYColor(validatedAnswer.isXCorrect(), validatedAnswer.isYCorrect());
            startRightAnswerAnimation();
            answeredCorrectly = true;
        } else {
            if(categoryIndex != 1) {
                if (!validatedAnswer.isXCorrect()) {
                    TVX.setText("?");
                }
                if (!validatedAnswer.isYCorrect()) {
                    TVY.setText("?");
                }
            }
            setAnswerTVBackgroundResources(validatedAnswer);
            canvas.setCoordinateXAndYColor(validatedAnswer.isXCorrect(), validatedAnswer.isYCorrect());
            startWrongAnswerAnimation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startWrongAnswerAnimation() {
        //Animation for wrong answer. Check box changes to X
        if (!animationGoing) {
            animationGoing = true;
            IBTNValidate.setImageResource(R.drawable.avd_anim_wrong);
            IVNext.setImageResource(R.drawable.arrow_forward_dark);
            Drawable drawable = IBTNValidate.getDrawable();
            AnimatedVectorDrawableCompat avd;
            AnimatedVectorDrawable avd2;
            if (drawable instanceof AnimatedVectorDrawableCompat) {
                avd = (AnimatedVectorDrawableCompat) drawable;
                avd.start();
            } else if (drawable instanceof AnimatedVectorDrawable) {
                avd2 = (AnimatedVectorDrawable) drawable;
                avd2.start();
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (gameState.getAttempt() == 3) {
                        startRightAnswerAnimation();
                        animationGoing = false;
                    }else {
                    IBTNValidate.setImageResource(R.drawable.avd_anim_wrong_go_back);
                    Drawable drawable = IBTNValidate.getDrawable();
                    AnimatedVectorDrawableCompat avd;
                    AnimatedVectorDrawable avd2;
                    if (drawable instanceof AnimatedVectorDrawableCompat) {
                        avd = (AnimatedVectorDrawableCompat) drawable;
                        avd.start();
                    } else if (drawable instanceof AnimatedVectorDrawable) {
                        avd2 = (AnimatedVectorDrawable) drawable;
                        avd2.start();
                    }
                    animationGoing = false;
                 }
              }
            }, 1500);   //This must be same as animation length!
            gameState.setAttempt(gameState.getAttempt() + 1);
            updateStars(gameState.getAttempt());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRightAnswerAnimation() {
        //Animation for right answer. Check box changes to move forward.
        IBTNValidate.setImageResource(R.drawable.anim_validate_correct);
        Drawable drawable = IBTNValidate.getDrawable();
        AnimatedVectorDrawableCompat avd;
        AnimatedVectorDrawable avd2;
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        } else if (drawable instanceof AnimatedVectorDrawable) {
            avd2 = (AnimatedVectorDrawable) drawable;
            avd2.start();
        }
    }

    private void setAnswerTVBackgroundResources(ValidatedAnswer validatedAnswer) {
        //Updates views background to green if user has answered correctly
        if (validatedAnswer.isXCorrect()) {
            TVX.setBackgroundResource(R.drawable.valuearealeftcorrect);
        } else {
            TVX.setBackgroundResource(R.drawable.valuearealeftwrong);
        }
        if (validatedAnswer.isYCorrect()) {
            TVY.setBackgroundResource(R.drawable.valuearearightcorrect);
        } else {
            TVY.setBackgroundResource(R.drawable.valuearearightwrong);
        }
        if (validatedAnswer.isAnswerCorrect()) {
            TVValue.setBackgroundResource(R.drawable.answerarearight);
        } else {
            TVValue.setBackgroundResource(R.drawable.answerareawrong);
        }
    }

    private void removeAnswerTVText() {
        //Remove text from the Textview
        if (gameState.getCategory() == Categories.COORDINATESFROMPOINT
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY
                || gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE
                || gameState.getCategory() == Categories.FINDAREAFROMFIGURE) {
            TVCoordinateSelected.setText("?");
        }
    }

    private void addTextTVCoordinate(String addText) {
        //Add text to the Textview which shows coordinate
        if ((gameState.getCategory() == Categories.COORDINATESFROMPOINT
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY
                || gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE
                || gameState.getCategory() == Categories.FINDAREAFROMFIGURE)
                && TVCoordinateSelected.getText().toString().length() < 6) {
            TVCoordinateSelected.setText(TVCoordinateSelected.getText().toString().replace("?", "") + addText);
        }
    }

    private void createLevel() {
        //Creates a level. Updates views according to the gameState.
        gameState = levelController.getLevel(categoryIndex, levelIndex, getContext());
        gameState.getSelectedDot().setPurpleColorOn(!prefs.getBoolean("purpleColorOn", false));
        gameState.setEnglishNumbers(arabicNumeralsOn);
        canvas = new Canvas(getContext(), gameState);
        linearLayout.removeAllViews();
        linearLayout.addView(canvas);
        updateViewsToGameState(levelIndex);
    }

    private void updateStars(final int attempt) {
        //Updates the stars. First attempt one start. Level restart after third attempt
        ImageView IVFirstStar = view.findViewById(R.id.IVFirstStar);
        ImageView IVSecondStar = view.findViewById(R.id.IVSecondStar);
        if (attempt == 1) {
            IVSecondStar.setImageResource(R.drawable.star_green);
            IVFirstStar.setImageResource(R.drawable.star_green_empty);
        } else if (attempt == 2) {
            IVSecondStar.setImageResource(R.drawable.star_green_empty);
            IVFirstStar.setImageResource(R.drawable.star_green_empty);
        } else {
            Toast.makeText(getContext(), "Click next to continue", Toast.LENGTH_LONG).show();
            IVSecondStar.setImageResource(R.drawable.star_red_empty);
            IVFirstStar.setImageResource(R.drawable.star_red_empty);
            IVNext.setImageResource(R.drawable.arrow_forward_green);
        }

    }

    private ValidatedAnswer validateAnswer(GameState gameState) {
        //Returns the validated answer.
        if (gameState.getCategory() == Categories.COORDINATESFROMPOINT
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY) {
            Pair<String, String> pair = new Pair<>(TVX.getText().toString(), TVY.getText().toString());
            gameState.setTypedCoordinateAnswer(pair);
        } else if (gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE
                || gameState.getCategory() == Categories.FINDAREAFROMFIGURE
                || gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
            String value = TVValue.getText().toString();
            gameState.setTypedValueAnswer(value);
        }
        return AnswerController.getAnswer(gameState, categoryIndex, levelIndex);
    }

    @SuppressLint("SetTextI18n")
    private void updateViewsToGameState(int levelIndex) {
        //Updates view according to the gameState. This is called only after restarting level in createLevel() function
        //Hides views and sets question to the question area.
        gameState.setAttempt(0);
        gameState.setLevel(levelIndex);
        answeredCorrectly = false;
        ImageView IVFirstStar = view.findViewById(R.id.IVFirstStar);
        ImageView IVSecondStar = view.findViewById(R.id.IVSecondStar);
        ImageView IVNext = view.findViewById(R.id.IVNext);
        ImageView IVValidate = view.findViewById(R.id.IBTNValidate);
        IVNext.setImageResource(R.drawable.arrow_forward_dark);
        IVSecondStar.setImageResource(R.drawable.star_green);
        IVFirstStar.setImageResource(R.drawable.star_green);
        IVValidate.setImageResource(R.drawable.validate_green);
        TVX.setBackgroundResource(R.drawable.valuearealeft);
        TVY.setBackgroundResource(R.drawable.valuearearight);
        TVValue.setBackgroundResource(R.drawable.answerarea);

        if (categoryIndex == 10 && (levelIndex ==2 || levelIndex == 4)){
            TVQuestion.setText(gameState.getQuestion() + "\n You are allowed to use 'X' for this level");
        } else if (categoryIndex == 10 && (levelIndex ==5 || levelIndex == 6)){
            TVQuestion.setText(gameState.getQuestion() + "\n You are allowed to use '\uD835\uDF0B' for this level");
        } else {
            TVQuestion.setText(gameState.getQuestion());
        }

        //Categories where answer coordinate view is invisible
        if (gameState.getCategory() == Categories.FINDPOINTWITHLINESYMMETRY
                || gameState.getCategory() == Categories.FINDPOINTWITHPOINTSYMMETRY
                || gameState.getCategory() == Categories.FINDFIGUREOFSYMMETRYTHROUGHPOINTOFSYMMETRY
                || gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE
                || gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER
                || gameState.getCategory() == Categories.FINDAREAFROMFIGURE
                || gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
            view.findViewById(R.id.LLCoordinate).setVisibility(View.INVISIBLE);
        }
        //Categories where answer coordinate view is visible
        if (gameState.getCategory() == Categories.POINTFROMCOORDINATES) {
            TVX.setText("" + gameState.getTargetPoint().getX());
            TVY.setText("" + gameState.getTargetPoint().getY());
        } else if (gameState.getCategory() == Categories.COORDINATESFROMPOINT
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY) {
            TVX.setText("?");
            TVY.setText("?");
        }
        if (gameState.getCategory() == Categories.COORDINATESFROMPOINT
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHSYMMETRY
                || gameState.getCategory() == Categories.FINDCOORDINATEWITHPOINTSYMMETRY) {
            TVCoordinateSelected = TVX;
            TVX.setAlpha(0.7f);
            TVY.setAlpha(1f);
        }
        if (gameState.getCategory() == Categories.FINDTHEPERIMETEROFAFIGURE
                || gameState.getCategory() == Categories.FINDAREAFROMFIGURE) {
            view.findViewById(R.id.LLAnswer).setVisibility(View.VISIBLE);
            TVValue.setText("?");
            TVCoordinateSelected = TVValue;
        }
        if (gameState.getCategory() == Categories.COMPLETEFIGUREFROMPERIMETER
                || gameState.getCategory() == Categories.COMPLETEFIGUREFROMAREA) {
            view.findViewById(R.id.LLAnswer).setVisibility(View.VISIBLE);
            TVValue.setText("" + gameState.getTargetAnswer());
        }
    }

    private void moveToNextLevel() {
        try {
            levelController.getLevel(categoryIndex, levelIndex + 1, getContext());
            Bundle bundle = new Bundle();
            bundle.putInt("categoryIndex", categoryIndex);
            bundle.putInt("levelIndex", levelIndex + 1);
            bundle.putBoolean("tealColorOn", purpleColorOn);
            bundle.putBoolean("englishNumerals", arabicNumeralsOn);
            bundle.putBoolean("nightModeOn", nightModeOn);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LevelFragment fragment = new LevelFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.LLFragmentHolder, fragment);
            fragmentManager.popBackStack();
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "There is no more levels in this category!", Toast.LENGTH_LONG).show();
        }
    }
}
