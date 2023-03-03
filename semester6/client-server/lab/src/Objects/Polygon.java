package Objects;

import java.awt.*;
import static java.lang.Math.*;

public class Polygon extends GraphicObject {

    private final int dots;
    private final int[] dotsX;
    private final int[] dotsY;
    
    private enum MoveState { Up, Down, Right, Left  };
    private MoveState[] states = {MoveState.Up, MoveState.Right, MoveState.Down, MoveState.Left };  
    private int curStateId;
    private int minX, maxX, minY, maxY = 0;

    public Polygon(int x, int y, Color color, int dots, int radius) {
      
        super(x, y, color);     
        
        if (dots <= 2 || radius <= 0) throw new IllegalArgumentException();
        
        this.dots = dots;  
        width = radius * 2;
        height = radius * 2;
        
        dotsX = new int[dots];
        dotsY = new int[dots];
 
        int num = 0;
        float step = (float) (2.0 * PI / dots);
        
        for (float a = 0f; a < 2.0 * PI && num < dots; num++) {
        
            dotsX[num] = (int) (x + (radius * cos(a)));
            dotsY[num] = (int) (y + (radius * sin(a)));
            
            if (num == dots - 2)
                step /= 2;
            
            a += step; 
        }
        
        int squareSide = 40;
        minX = x - squareSide;
        maxX = x + squareSide;
        minY = y - squareSide;
        maxY = y + squareSide;
    }
    
    @Override
    public void Draw(Graphics graphics) {
        graphics.setColor(this.color);
        graphics.drawPolygon(dotsX, dotsY, dots);
    }

    @Override
    public void Move() {
        if (!canMove) return;      
        
        int step = 1;
        int xStep = 0, yStep = 0;
        
        switch (states[curStateId]) {
            case Up: yStep = -step; break;
            case Down: yStep = step; break;
            case Right: xStep = step; break;
            case Left: xStep = -step; break;           
        } 
        
        if (yStep != 0) {
            for (int i = 0; i < dots; i++)
                dotsY[i] += yStep;
            center.y += yStep;
        }
        if (xStep != 0) {
            for (int i = 0; i < dots; i++)
                dotsX[i] += xStep;
            center.x += xStep;
        }
        
        boolean switchState = 
        (center.x - width / 2 == minX && states[curStateId] == MoveState.Left) ||
        (center.x + width / 2 == maxX && states[curStateId] == MoveState.Right) ||
        (center.y - height / 2 == minY && states[curStateId] == MoveState.Up) ||
        (center.y + height / 2 == maxY && states[curStateId] == MoveState.Down);
        
        if (switchState)
            curStateId = (curStateId == states.length - 1 ? 0 : curStateId + 1);
    }
    
}
