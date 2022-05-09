package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskSolvingActivity extends AppCompatActivity implements ChessDelegate, CheckReturnToScreen, GettingDataFromDialog{
    ChessView mChessView; //доска
    ChessGame chessGame = new ChessGame(); //игровая логика
    ArrayList<ChessPiece> fenPiecesBox = new ArrayList<>(); //массив с фигурами
    List<TaskDescription> chosenTask = new ArrayList<>(); //массив с задачами
    private final String TAG = "TaskSolvingActivity";
    int needs_turns; //необходимое число ходов для того что бы поставить мат
    int turns_left=0; //счетчик для того что бы зафиксировать решена ли задача успешно/неуспешно
    int list_index=0; //счетчик для перехода к следующей задаче
    int id_task;//тип задачи
    boolean solve_flag=false;
    boolean dialogTransformFlag = false;
    TextView mTextView; //тема задачи
    TextView queue_turn; //тема задачи
    private HashMap<String, Chessman> chessman_type= new HashMap<>();
    private HashMap<String, Integer> white_resID = new HashMap<>();
    private HashMap<String, Integer> black_resID = new HashMap<>();
    Square destination_square;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_solving);
        //следующие 2 строки чтобы убрать action bar (верхнюю панель) с экрана
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mTextView = (TextView) findViewById(R.id.textTask);
        queue_turn = (TextView) findViewById(R.id.queueTurn);
        //бандл необходим нам для получение выбранной темы задач с предыдущего экрана (активности)
        Bundle arguments = getIntent().getExtras();
        //получаем json строку из файла
        String json_string = readTaskFile();
        //создаем объект GSON для десериализации нашей строки
        Gson gson = new Gson();
        //растаскиваем в наш объект-"матрешку"
        Task listOfTasks =  gson.fromJson(json_string, Task.class);
        //поулчаем id выбранной темы задачи (от 0 до 4)

        if (arguments==null)
        {
            SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
            id_task=settings.getInt("typeTask", 0);
            list_index=settings.getInt("taskNumber", 0);
        }
        else
        {
            id_task = Integer.parseInt(arguments.get("chosen_type_task").toString());
        }
        //выбрали нужный нам массив с задачами
        chosenTask=initChosenTasks(listOfTasks, id_task);
        //находим нашу виртуальную доску,
        mChessView = findViewById(R.id.chess_task_view);
        //назначаем нашей доске делегат
        mChessView.mChessDelegate=this;
        initBoard(chosenTask);
        //сохраняем тему задач
        SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putInt("typeTask", id_task);
        prefEditor.putString("lastActivity","taskActivity");
        prefEditor.apply();
        init_chessman_type();
    }

    List<TaskDescription> initChosenTasks(Task object, int chosenTaskId)
    {
        List<TaskDescription> t_a_n_d_List = new ArrayList<>();
        switch (chosenTaskId)
        {
            case 0:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0 /*т.к. элемент у нас всего один, то можно и поставить 0
        ну либо через взятие размера массива - 1*/).getList_of_OMM(); //потом через .get(i) где i - номер задачи, сможем переключаться
                mTextView.setText("Мат в 1 ход");
                break;
            case 1:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_TMM();
                mTextView.setText("Мат в 2 хода");
                break;
            case 2:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_ThMM();
                mTextView.setText("Мат в 3 хода");
                break;
            case 3:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_NnRM();
                mTextView.setText("Мат конем и ладьей");
                break;
            case 4:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_MwS();
                mTextView.setText("Мат с жертвой фигуры");
                break;
        }
        return t_a_n_d_List;
    }

    void initBoard(List<TaskDescription> list) //метод который загружает позицию на доску
    {
        String fen_position = list.get(list_index).getFen_or_pgn_str(); //получаем FEN строку
        needs_turns=list.get(list_index).getNeed_turns(); //получаем общее число ходов, которое нужно совершить для решения задачи
        ParseFENotationToSquare convertedFEN = new ParseFENotationToSquare(fen_position); //создаем объект класса-парсера из FEN строки в массив фигур
        fenPiecesBox = convertedFEN.loadPositionsOnBoard();//вызываем метод перевода
        chessGame.setTurnPlayer(convertedFEN.getQueue_turn());//устанавливаем чей ход
        if (chessGame.getTurnPlayer()==Player.WHITE)
            queue_turn.setText("Ход белых");
        else
            queue_turn.setText("Ход черных");
        chessGame.setPiecesBox(fenPiecesBox);//загружаем полученный массив с фигарми на доску
    }

    @Override
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override
    public void movePiece(Square from, Square to) {
        destination_square=new Square(to.getColumn(),to.getRow());
        chessGame.movePiece(from,to); //двигаем фигуры
        if (chessGame.getTurnPlayer()==Player.WHITE)
            queue_turn.setText("Ход белых");
        else
            queue_turn.setText("Ход черных");

        if (chessGame.isMoving())
        {
            if (chessGame.isTransforming())
            {
                FragmentManager manager = getSupportFragmentManager();
                FigureTransformDialog dialogTransform = new FigureTransformDialog(this);
                dialogTransform.show(manager, "transform");
            }

            turns_left++; //прибавляем счетчик
        }

        if ((chessGame.isEndGame())&&(turns_left==needs_turns)) //если поставлен мат
        {
            dialogCallMethod();
        }

        else if (turns_left>needs_turns) //иначе пишем что задача не решена и предлагаем повторить попытку
        {
            FragmentManager manager = getSupportFragmentManager();
            TaskSolveDialog myDialogFragment = new TaskSolveDialog("Задача не решена","Хотите попробовать еще раз?", this, "В меню", "Еще раз", this);
            myDialogFragment.show(manager, "myDialog");
        }
        else //если еще есть ходы, то перерисовываем доску
        {
            mChessView = findViewById(R.id.chess_task_view);
            mChessView.invalidate();
        }


    }
    String readTaskFile()  //метод для чтение json файла
    {
        BufferedReader str = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.chesstasks)));
        StringBuilder total = new StringBuilder();
        try {
            for (String line; (line = str.readLine()) != null; ) {
                total.append(line).append('\n');
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "В вашем файле есть ошибочка)", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

        Log.d("JSONread", total.toString());
        return total.toString();
    }

    @Override
    public void IsReturn(boolean return_to_screen, String buttonText) {
        if (return_to_screen) {
            if (buttonText.equals("Вперед"))
            {
                chessGame=new ChessGame();
                list_index++;//переходим к следующей задаче
                //работа с shared preferences
                SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putInt("taskNumber",list_index);
                prefEditor.putInt("taskSolving", settings.getInt("taskSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                taskSolvingCountToStatistic(id_task);
                //переходим к следующей задаче
                turns_left=0;
                initBoard(chosenTask);
                mChessView.invalidate();
            }
            if (buttonText.equals("Еще раз"))
            {
                chessGame=new ChessGame();
                initBoard(chosenTask);
                turns_left=0;
                mChessView.invalidate();
            }
            if (buttonText.equals("В начало"))
            {
                SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putInt("taskSolving", settings.getInt("taskSolving", 0)+1);//всего решено задач
                prefEditor.putInt("taskTopicSolving", settings.getInt("taskTopicSolving", 0)+1);//сколько раз мы решили тему
                taskTopicSolvingCountToStatistic(id_task);
                taskSolvingCountToStatistic(id_task);
                prefEditor.apply();
                chessGame = new ChessGame();
                list_index=0;
                turns_left=0;
                initBoard(chosenTask);
                mChessView.invalidate();
            }
        }
        else
        {
            if (list_index==chosenTask.size()-1)
            {
                SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putInt("taskSolving", settings.getInt("taskSolving", 0)+1);//всего решено задач
                prefEditor.putInt("taskTopicSolving", settings.getInt("taskTopicSolving", 0)+1);//сколько раз мы решили тему
                taskTopicSolvingCountToStatistic(id_task);
                taskSolvingCountToStatistic(id_task);
                prefEditor.apply();
            }
            if (solve_flag)
            {
                SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = settings.edit();
                prefEditor.putInt("taskSolving", settings.getInt("taskSolving", 0)+1);//всего решено задач
                taskSolvingCountToStatistic(id_task);
                prefEditor.apply();
                solve_flag=false;
            }
            Intent backToTaskMenu = new Intent(TaskSolvingActivity.this, TasksMenuActivity.class);
            startActivity(backToTaskMenu);
            finish();
        }

    }
    //написать метод со switch case который будет подсчитывать число решенных задач по заданной теме
    //так же еще нужно будет подсчитывать сколько пользователь прорешал полностью категорию (с аналогичным методом)
    void taskTopicSolvingCountToStatistic(int id_task)
    {
        SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        switch (id_task)
        {
            case 0:
                prefEditor.putInt("oneMoveMateSolving", settings.getInt("oneMoveMateSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 1:
                prefEditor.putInt("twoMoveMateSolving", settings.getInt("twoMoveMateSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 2:
                prefEditor.putInt("threeMoveMateSolving", settings.getInt("threeMoveMateSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 3:
                prefEditor.putInt("rookAndKnightMoveSolving", settings.getInt("rookAndKnightMoveSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 4:
                prefEditor.putInt("sacrificeMateSolving", settings.getInt("sacrificeMateSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
        }
    }
    void taskSolvingCountToStatistic(int id_task)
    {
        SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        switch (id_task)
        {
            case 0:
                prefEditor.putInt("oneMoveTaskSolving", settings.getInt("oneMoveTaskSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 1:
                prefEditor.putInt("twoMateTaskSolving", settings.getInt("twoMateTaskSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 2:
                prefEditor.putInt("threeMateTaskSolving", settings.getInt("threeMateTaskSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 3:
                prefEditor.putInt("rookAndKnightTaskSolving", settings.getInt("rookAndKnightTaskSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
            case 4:
                prefEditor.putInt("sacrificeTaskSolving", settings.getInt("sacrificeTaskSolving", 0)+1);//всего решено задач
                prefEditor.apply();
                break;
        }
    }


    @Override
    public void getData(String text) {
        Log.d(TAG, "ВЫбрал фигуру: " + text);

        ArrayList<ChessPiece> newPieceBox = chessGame.getPiecesBox();
        ChessPiece newFigure = pieceAt(destination_square);
        newPieceBox.remove(newFigure);
        Chessman newChessman=chessman_type.get(text);
        ChessPiece newChosenPiece = null;
        if (chessGame.getTurnPlayer()==Player.WHITE)
        {
            Integer resID = black_resID.get(text);
            newChosenPiece = new ChessPiece(newFigure.getColumn(), newFigure.getRow()-1, Player.BLACK,newChessman, resID);
        }
        else if (chessGame.getTurnPlayer()==Player.BLACK)
        {
            Integer resID = white_resID.get(text);
            newChosenPiece = new ChessPiece(newFigure.getColumn(), newFigure.getRow()+1,Player.WHITE, newChessman, resID);
        }
        newPieceBox.add(newChosenPiece);
        chessGame.setPiecesBox(newPieceBox);
        chessGame.setTransforming(false);
        mChessView.invalidate();
        if (chessGame.getTurnPlayer()==Player.WHITE)
            chessGame.setTurnPlayer(Player.BLACK);
        else
            chessGame.setTurnPlayer(Player.WHITE);
        //починить костыль а то фигура исчезает
        chessGame.movePiece(new Square(newChosenPiece.getColumn(), newChosenPiece.getRow()),destination_square);
        if ((chessGame.isEndGame())&&(turns_left==needs_turns))
            dialogCallMethod();
    }
    void dialogCallMethod()
    {
        queue_turn.setText("");
        solve_flag=true;
        //то диалоговое окно с поздравлением и предложением перейти к следующей задаче
        if (list_index<chosenTask.size()-1)
        {
            FragmentManager manager = getSupportFragmentManager();
            TaskSolveDialog myDialogFragment = new TaskSolveDialog("Задача решена","Хотите перейти к следующей задаче?", this, "В меню", "Вперед", this);
            myDialogFragment.show(manager, "myDialog");
        }
        else if (list_index==chosenTask.size()-1) //если пользователь решил все задачи
        {
            FragmentManager manager = getSupportFragmentManager();
            TaskSolveDialog myDialogFragment = new TaskSolveDialog("Вы решили все задачи!","Так держать! Хотите прорешать еще раз?", this, "В меню", "В начало", this);
            myDialogFragment.show(manager, "myDialog");
        }
    }

    void init_chessman_type()
    {
        chessman_type.put("Ферзь", Chessman.QUEEN);
        chessman_type.put("Ладья", Chessman.ROOK);
        chessman_type.put("Слон", Chessman.BISHOP);
        chessman_type.put("Конь", Chessman.KNIGHT);
        black_resID.put("Слон", R.drawable.black_bishop);
        black_resID.put("Конь", R.drawable.black_knight);
        black_resID.put("Ладья",R.drawable.black_rook);
        black_resID.put("Ферзь", R.drawable.black_queen);
        white_resID.put("Слон", R.drawable.white_bishop);
        white_resID.put("Конь", R.drawable.white_knight);
        white_resID.put("Ладья",R.drawable.white_rook);
        white_resID.put("Ферзь", R.drawable.white_queen);

    }
}