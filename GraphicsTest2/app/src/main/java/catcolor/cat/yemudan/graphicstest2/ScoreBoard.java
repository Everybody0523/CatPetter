package catcolor.cat.yemudan.graphicstest2;

/**
 * Created by wmf41 on 5/13/2017.
 */

public class ScoreBoard{
    public int score;
    //public ArrayList<Circle> circles;
    public Circle [] circles = new Circle[10];
    private float [] turquoise2 = { 0.8844f, 0.9795f, 0.7663f ,1.0f };
    private float [] turquoise = { 0.6844f, 0.9795f, 0.7663f ,1.0f };

    public ScoreBoard(){
        score = 0;
        int i = 1;
        while (i < 11) {
            float x = -0.55f + 0.1f*i;
            float display[] = {x, 0.7f, 0.05f,0.05f};
            circles[i-1] = new Circle(display, turquoise2);
            i += 1;
        }
    }
    public void update(int i) {
        if (score >= 0 && score <= 9){
            if (i > 0) {
                circles[score].setColor(turquoise);
            }
            else {
                circles[score].setColor(turquoise2);
            }
        }
        score += i;
        if (score < 0) {
            score = 0;
        }
    }
}