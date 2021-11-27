package com.example.chessstudyappver11;

public class SaveData {

    /*

      public class BoardView extends View {
        private int width = 0, height = 0;
        private float startx, starty, board_size;
        private Paint black = new Paint();
        private Paint white = new Paint();

        {
            black.setColor(Color.BLACK);
            white.setColor(Color.GRAY);
        }

        public BoardView(Context context) {
            super(context);
        }

        public BoardView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int smaller;
            if  (widthMeasureSpec<heightMeasureSpec)
                smaller = widthMeasureSpec;
            else if (widthMeasureSpec>heightMeasureSpec)
                smaller=heightMeasureSpec;
            else
                smaller=widthMeasureSpec;
            setMeasuredDimension(smaller,smaller);
        }

        @Override
        public void layout(int l, int t, int r, int b) {
            super.layout(l, t, r, b);
            //layout срабатывает, когда уже точно определено положение вьюшки
            //запоминаем в этот момент ширину и высоту
            width = r - l;
            height = b - t;
            //определяем размер доски в пикселях и координаты верхнего левого угла
            if (width > height) {
                starty = 0;
                startx = (width - height) / 2f;
                board_size = height;
            } else {
                startx = 0;
                starty = (height - width) / 2f;
                board_size = width;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //рисуем черный квадрат во всю доску
            canvas.drawRect(startx, starty, startx + board_size, starty + board_size, black);
            //и теперь белые квадратики в шахматном порядке
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if((i + j)%2 == 0)
                        continue;
                    canvas.drawRect(
                            startx + i * board_size / 8f,
                            starty + j * board_size / 8f,
                            startx + (i + 1) * board_size / 8f,
                            starty + (j + 1) * board_size / 8f,
                            white);
                }
            }
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return true;
        }
    }


   /* class DrawView extends View {

        Paint p;
        Rect rect;

        public DrawView(Context context){
            super(context);
            p = new Paint();
            rect = new Rect();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Integer count=0;
            Integer point_x=10;
            Integer point_y=10;
            Integer point_x1=120;
            Integer point_y1=120;

            //canvas.drawColor(Color.GREEN);
           /* canvas.drawARGB(80,102,204,255);
            p.setColor(Color.RED);
            p.setStrokeWidth(10);
            canvas.drawPoint(50,50,p);
            canvas.drawLine(100,100,500,50,p);
            canvas.drawCircle(100,200,50,p);
            canvas.drawRect(200,150,400,200,p);
            rect.set(250,300,350,500);
            canvas.drawRect(rect,p);
            for (int i=0;i<8;i++)
            {
                for (int j=0;j<8;j++)
                {
                    if (count%2==0)
                    {
                        if(j%2==0)
                        {
                            p.setColor(Color.GRAY);
                            canvas.drawRect(point_x,point_y,point_x1,point_y1,p);
                        }
                        else
                        {
                            p.setColor(Color.BLACK);
                            canvas.drawRect(point_x,point_y,point_x1,point_y1,p);
                        }
                    }
                    else
                    {
                        if(j%2==0)
                        {
                            p.setColor(Color.BLACK);
                            canvas.drawRect(point_x,point_y,point_x1,point_y1,p);
                        }
                        else
                        {
                            p.setColor(Color.GRAY);
                            canvas.drawRect(point_x,point_y,point_x1,point_y1,p);
                        }
                    }

                    point_x+=120;
                    point_x1+=120;
                }
                x_corner=point_x;
                count++;
                point_x=10;
                point_x1=120;
                point_y+=120;
                point_y1+=120;
                y_corner=point_y;
            }

        }

}

     */
}