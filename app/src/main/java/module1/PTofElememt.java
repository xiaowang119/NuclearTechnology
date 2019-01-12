package module1;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.nucleartechnology.R;


/**
 * Created by Administrator on 2016/6/30.
 */
public class PTofElememt extends View{

    public int seleted_element;
    private int row;
    private int col;
    private float current_x = 20;
    private float current_y = 20;
    private float rec_x = 0;
    private float rec_y = 0;
    Paint mypaint = new Paint();
    Bitmap Selected;
    Bitmap[] bitmap;
    Bitmap[] rawBitmap;
    Bitmap[] icon;
    Bitmap[] tag;
    boolean change_activity;
    private float scale_x;
    private float scale_y;

    public void setScale_x(float scale_x) {
        this.scale_x = scale_x;
    }
    public void setScale_y(float scale_y) {
        this.scale_y = scale_y;
    }

    public PTofElememt(Context context) {
        super(context);

    }

    public PTofElememt(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);

        seleted_element=1;
        col=0;
        row=0;
        change_activity=false;

        icon = new Bitmap[9];
        tag = new Bitmap[9];
        bitmap = new Bitmap[112];
        rawBitmap = new Bitmap[112];

        rawBitmap[0]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element1);
        rawBitmap[1]= BitmapFactory.decodeResource(getContext().getResources(), R.drawable.element2);
        rawBitmap[2]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element3);
        rawBitmap[3]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element4);
        rawBitmap[4]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element5);
        rawBitmap[5]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element6);
        rawBitmap[6]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element7);
        rawBitmap[7]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element8);
        rawBitmap[8]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element9);
        rawBitmap[9]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element10);
        rawBitmap[10]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element11);
        rawBitmap[11]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element12);
        rawBitmap[12]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element13);
        rawBitmap[13]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element14);
        rawBitmap[14]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element15);
        rawBitmap[15]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element16);
        rawBitmap[16]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element17);
        rawBitmap[17]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element18);
        rawBitmap[18]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element19);
        rawBitmap[19]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element20);
        rawBitmap[20]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element21);
        rawBitmap[21]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element22);
        rawBitmap[22]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element23);
        rawBitmap[23]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element24);
        rawBitmap[24]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element25);
        rawBitmap[25]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element26);
        rawBitmap[26]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element27);
        rawBitmap[27]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element28);
        rawBitmap[28]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element29);
        rawBitmap[29]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element30);
        rawBitmap[30]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element31);
        rawBitmap[31]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element32);
        rawBitmap[32]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element33);
        rawBitmap[33]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element34);
        rawBitmap[34]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element35);
        rawBitmap[35]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element36);
        rawBitmap[36]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element37);
        rawBitmap[37]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element38);
        rawBitmap[38]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element39);
        rawBitmap[39]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element40);
        rawBitmap[40]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element41);
        rawBitmap[41]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element42);
        rawBitmap[42]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element43);
        rawBitmap[43]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element44);
        rawBitmap[44]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element45);
        rawBitmap[45]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element46);
        rawBitmap[46]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element47);
        rawBitmap[47]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element48);
        rawBitmap[48]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element49);
        rawBitmap[49]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element50);
        rawBitmap[50]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element51);
        rawBitmap[51]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element52);
        rawBitmap[52]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element53);
        rawBitmap[53]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element54);
        rawBitmap[54]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element55);
        rawBitmap[55]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element56);
        rawBitmap[56]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element57);
        rawBitmap[57]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element58);
        rawBitmap[58]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element59);
        rawBitmap[59]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element60);
        rawBitmap[60]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element61);
        rawBitmap[61]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element62);
        rawBitmap[62]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element63);
        rawBitmap[63]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element64);
        rawBitmap[64]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element65);
        rawBitmap[65]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element66);
        rawBitmap[66]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element67);
        rawBitmap[67]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element68);
        rawBitmap[68]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element69);
        rawBitmap[69]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element70);
        rawBitmap[70]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element71);
        rawBitmap[71]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element72);
        rawBitmap[72]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element73);
        rawBitmap[73]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element74);
        rawBitmap[74]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element75);
        rawBitmap[75]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element76);
        rawBitmap[76]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element77);
        rawBitmap[77]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element78);
        rawBitmap[78]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element79);
        rawBitmap[79]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element80);
        rawBitmap[80]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element81);
        rawBitmap[81]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element82);
        rawBitmap[82]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element83);
        rawBitmap[83]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element84);
        rawBitmap[84]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element85);
        rawBitmap[85]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element86);
        rawBitmap[86]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element87);
        rawBitmap[87]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element88);
        rawBitmap[88]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element89);
        rawBitmap[89]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element90);
        rawBitmap[90]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element91);
        rawBitmap[91]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element92);
        rawBitmap[92]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element93);
        rawBitmap[93]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element94);
        rawBitmap[94]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element95);
        rawBitmap[95]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element96);
        rawBitmap[96]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element97);
        rawBitmap[97]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element98);
        rawBitmap[98]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element99);
        rawBitmap[99]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element100);
        rawBitmap[100]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element101);
        rawBitmap[101]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element102);
        rawBitmap[102]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element103);
        rawBitmap[103]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element104);
        rawBitmap[104]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element105);
        rawBitmap[105]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element106);
        rawBitmap[106]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element107);
        rawBitmap[107]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element108);
        rawBitmap[108]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element109);
        rawBitmap[109]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element110);
        rawBitmap[110]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element111);
        rawBitmap[111]= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.element112);

        icon[0] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_qiti);
        icon[1] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_jian);
        icon[2] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_jiantu);
        icon[3] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_guodu);
        icon[4] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_feijs2);
        icon[5] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_houguodu);
        icon[6] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_lusu);
        icon[7] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_duoxing);
        icon[8] = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.sample_a);


    }
    //获取屏幕分辨率

    @Override
    protected void onDraw(Canvas canvas) {
        bitmap[0] = Bitmap.createScaledBitmap(rawBitmap[0],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[1] = Bitmap.createScaledBitmap(rawBitmap[1],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[2] = Bitmap.createScaledBitmap(rawBitmap[2],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[3] = Bitmap.createScaledBitmap(rawBitmap[3],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[4] = Bitmap.createScaledBitmap(rawBitmap[4],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[5] = Bitmap.createScaledBitmap(rawBitmap[5],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[6] = Bitmap.createScaledBitmap(rawBitmap[6],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[7] = Bitmap.createScaledBitmap(rawBitmap[7],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[8] = Bitmap.createScaledBitmap(rawBitmap[8],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[9] = Bitmap.createScaledBitmap(rawBitmap[9],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[10] = Bitmap.createScaledBitmap(rawBitmap[10],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[11] = Bitmap.createScaledBitmap(rawBitmap[11],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[12] = Bitmap.createScaledBitmap(rawBitmap[12],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[13] = Bitmap.createScaledBitmap(rawBitmap[13],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[14] = Bitmap.createScaledBitmap(rawBitmap[14],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[15] = Bitmap.createScaledBitmap(rawBitmap[15],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[16] = Bitmap.createScaledBitmap(rawBitmap[16],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[17] = Bitmap.createScaledBitmap(rawBitmap[17],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[18] = Bitmap.createScaledBitmap(rawBitmap[18],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[19] = Bitmap.createScaledBitmap(rawBitmap[19],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[20] = Bitmap.createScaledBitmap(rawBitmap[20],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[21] = Bitmap.createScaledBitmap(rawBitmap[21],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[22] = Bitmap.createScaledBitmap(rawBitmap[22],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[23] = Bitmap.createScaledBitmap(rawBitmap[23],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[24] = Bitmap.createScaledBitmap(rawBitmap[24],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[25] = Bitmap.createScaledBitmap(rawBitmap[25],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[26] = Bitmap.createScaledBitmap(rawBitmap[26],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[27] = Bitmap.createScaledBitmap(rawBitmap[27],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[28] = Bitmap.createScaledBitmap(rawBitmap[28],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[29] = Bitmap.createScaledBitmap(rawBitmap[29],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[30] = Bitmap.createScaledBitmap(rawBitmap[30],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[31] = Bitmap.createScaledBitmap(rawBitmap[31],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[32] = Bitmap.createScaledBitmap(rawBitmap[32],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[33] = Bitmap.createScaledBitmap(rawBitmap[33],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[34] = Bitmap.createScaledBitmap(rawBitmap[34],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[35] = Bitmap.createScaledBitmap(rawBitmap[35],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[36] = Bitmap.createScaledBitmap(rawBitmap[36],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[37] = Bitmap.createScaledBitmap(rawBitmap[37],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[38] = Bitmap.createScaledBitmap(rawBitmap[38],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[39] = Bitmap.createScaledBitmap(rawBitmap[39],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[40] = Bitmap.createScaledBitmap(rawBitmap[40],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[41] = Bitmap.createScaledBitmap(rawBitmap[41],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[42] = Bitmap.createScaledBitmap(rawBitmap[42],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[43] = Bitmap.createScaledBitmap(rawBitmap[43],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[44] = Bitmap.createScaledBitmap(rawBitmap[44],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[45] = Bitmap.createScaledBitmap(rawBitmap[45],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[46] = Bitmap.createScaledBitmap(rawBitmap[46],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[47] = Bitmap.createScaledBitmap(rawBitmap[47],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[48] = Bitmap.createScaledBitmap(rawBitmap[48],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[49] = Bitmap.createScaledBitmap(rawBitmap[49],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[50] = Bitmap.createScaledBitmap(rawBitmap[50],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[51] = Bitmap.createScaledBitmap(rawBitmap[51],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[52] = Bitmap.createScaledBitmap(rawBitmap[52],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[53] = Bitmap.createScaledBitmap(rawBitmap[53],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[54] = Bitmap.createScaledBitmap(rawBitmap[54],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[55] = Bitmap.createScaledBitmap(rawBitmap[55],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[56] = Bitmap.createScaledBitmap(rawBitmap[56],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[57] = Bitmap.createScaledBitmap(rawBitmap[57],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[58] = Bitmap.createScaledBitmap(rawBitmap[58],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[59] = Bitmap.createScaledBitmap(rawBitmap[59],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[60] = Bitmap.createScaledBitmap(rawBitmap[60],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[61] = Bitmap.createScaledBitmap(rawBitmap[61],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[62] = Bitmap.createScaledBitmap(rawBitmap[62],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[63] = Bitmap.createScaledBitmap(rawBitmap[63],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[64] = Bitmap.createScaledBitmap(rawBitmap[64],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[65] = Bitmap.createScaledBitmap(rawBitmap[65],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[66] = Bitmap.createScaledBitmap(rawBitmap[66],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[67] = Bitmap.createScaledBitmap(rawBitmap[67],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[68] = Bitmap.createScaledBitmap(rawBitmap[68],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[69] = Bitmap.createScaledBitmap(rawBitmap[69],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[70] = Bitmap.createScaledBitmap(rawBitmap[70],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[71] = Bitmap.createScaledBitmap(rawBitmap[71],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[72] = Bitmap.createScaledBitmap(rawBitmap[72],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[73] = Bitmap.createScaledBitmap(rawBitmap[73],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[74] = Bitmap.createScaledBitmap(rawBitmap[74],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[75] = Bitmap.createScaledBitmap(rawBitmap[75],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[76] = Bitmap.createScaledBitmap(rawBitmap[76],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[77] = Bitmap.createScaledBitmap(rawBitmap[77],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[78] = Bitmap.createScaledBitmap(rawBitmap[78],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[79] = Bitmap.createScaledBitmap(rawBitmap[79],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[80] = Bitmap.createScaledBitmap(rawBitmap[80],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[81] = Bitmap.createScaledBitmap(rawBitmap[81],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[82] = Bitmap.createScaledBitmap(rawBitmap[82],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[83] = Bitmap.createScaledBitmap(rawBitmap[83],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[84] = Bitmap.createScaledBitmap(rawBitmap[84],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[85] = Bitmap.createScaledBitmap(rawBitmap[85],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[86] = Bitmap.createScaledBitmap(rawBitmap[86],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[87] = Bitmap.createScaledBitmap(rawBitmap[87],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[88] = Bitmap.createScaledBitmap(rawBitmap[88],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[89] = Bitmap.createScaledBitmap(rawBitmap[89],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[90] = Bitmap.createScaledBitmap(rawBitmap[90],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[91] = Bitmap.createScaledBitmap(rawBitmap[91],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[92] = Bitmap.createScaledBitmap(rawBitmap[92],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[93] = Bitmap.createScaledBitmap(rawBitmap[93],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[94] = Bitmap.createScaledBitmap(rawBitmap[94],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[95] = Bitmap.createScaledBitmap(rawBitmap[95],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[96] = Bitmap.createScaledBitmap(rawBitmap[96],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[97] = Bitmap.createScaledBitmap(rawBitmap[97],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[98] = Bitmap.createScaledBitmap(rawBitmap[98],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[99] = Bitmap.createScaledBitmap(rawBitmap[99],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[100] = Bitmap.createScaledBitmap(rawBitmap[100],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[101] = Bitmap.createScaledBitmap(rawBitmap[101],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[102] = Bitmap.createScaledBitmap(rawBitmap[102],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[103] = Bitmap.createScaledBitmap(rawBitmap[103],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[104] = Bitmap.createScaledBitmap(rawBitmap[104],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[105] = Bitmap.createScaledBitmap(rawBitmap[105],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[106] = Bitmap.createScaledBitmap(rawBitmap[106],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[107] = Bitmap.createScaledBitmap(rawBitmap[107],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[108] = Bitmap.createScaledBitmap(rawBitmap[108],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[109] = Bitmap.createScaledBitmap(rawBitmap[109],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[110] = Bitmap.createScaledBitmap(rawBitmap[110],(int)(90/scale_x),(int)(90/scale_y),true);
        bitmap[111] = Bitmap.createScaledBitmap(rawBitmap[111],(int)(90/scale_x),(int)(90/scale_y),true);

        Selected = Bitmap.createScaledBitmap(rawBitmap[0],(int)(180/scale_x),(int)(180/scale_y),true);
        Selected = Bitmap.createScaledBitmap(rawBitmap[seleted_element-1],(int)(180/scale_x),(int)(180/scale_y),true);
        tag[0] = Bitmap.createScaledBitmap(icon[0],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[1] = Bitmap.createScaledBitmap(icon[1],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[2] = Bitmap.createScaledBitmap(icon[2],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[3] = Bitmap.createScaledBitmap(icon[3],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[4] = Bitmap.createScaledBitmap(icon[4],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[5] = Bitmap.createScaledBitmap(icon[5],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[6] = Bitmap.createScaledBitmap(icon[6],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[7] = Bitmap.createScaledBitmap(icon[7],(int)(45/scale_x),(int)(45/scale_y),true);
        tag[8] = Bitmap.createScaledBitmap(icon[8],(int)(45/scale_x),(int)(45/scale_y),true);

        canvas.drawBitmap(Selected,240/scale_x,40/scale_y,mypaint);
        canvas.drawBitmap(tag[0],590/scale_x,10/scale_y,mypaint);
        canvas.drawBitmap(tag[1],590/scale_x,65/scale_y,mypaint);
        canvas.drawBitmap(tag[2],590/scale_x,120/scale_y,mypaint);
        canvas.drawBitmap(tag[3],590/scale_x,175/scale_y,mypaint);
        canvas.drawBitmap(tag[4],590/scale_x,230/scale_y,mypaint);

        canvas.drawBitmap(tag[5],878/scale_x,10/scale_y,mypaint);
        canvas.drawBitmap(tag[6],878/scale_x,65/scale_y,mypaint);
        canvas.drawBitmap(tag[7],878/scale_x,120/scale_y,mypaint);
        canvas.drawBitmap(tag[8],878/scale_x,170/scale_y,mypaint);
        canvas.drawBitmap(tag[8],878/scale_x,230/scale_y,mypaint);

        mypaint.setColor(Color.BLACK);
        mypaint.setAntiAlias(true);
        mypaint.setStyle(Paint.Style.FILL);
        mypaint.setStrokeWidth(1);
        mypaint.setTextSize(20);

        canvas.drawText("其他非金属",645/scale_x,35/scale_y,mypaint);
        canvas.drawText("碱金属",645/scale_x,90/scale_y,mypaint);
        canvas.drawText("碱土金属",645/scale_x,145/scale_y,mypaint);
        canvas.drawText("过渡元素",645/scale_x,200/scale_y,mypaint);
        canvas.drawText("非金属",645/scale_x,255/scale_y,mypaint);

        canvas.drawText("后过渡金属",933/scale_x,35/scale_y,mypaint);
        canvas.drawText("卤素",933/scale_x,90/scale_y,mypaint);
        canvas.drawText("稀有气体",933/scale_x,145/scale_y,mypaint);
        canvas.drawText("锕系元素",933/scale_x,200/scale_y,mypaint);
        canvas.drawText("镧系元素",933/scale_x,255/scale_y,mypaint);

        canvas.drawBitmap(bitmap[0],3/scale_x,3/scale_y,mypaint);           //H
        canvas.drawBitmap(bitmap[1],1635/scale_x,3/scale_y,mypaint);        //He

        canvas.drawBitmap(bitmap[2],3/scale_x,99/scale_y,mypaint);          //Li
        canvas.drawBitmap(bitmap[3],99/scale_x,99/scale_y,mypaint);         //Be
        canvas.drawBitmap(bitmap[4],1155/scale_x,99/scale_y,mypaint);       //B
        canvas.drawBitmap(bitmap[5],1251/scale_x,99/scale_y,mypaint);       //C
        canvas.drawBitmap(bitmap[6],1347/scale_x,99/scale_y,mypaint);       //N
        canvas.drawBitmap(bitmap[7],1443/scale_x,99/scale_y,mypaint);       //O
        canvas.drawBitmap(bitmap[8],1539/scale_x,99/scale_y,mypaint);       //F
        canvas.drawBitmap(bitmap[9],1635/scale_x,99/scale_y,mypaint);       //Ne


        canvas.drawBitmap(bitmap[10],3/scale_x,195/scale_y,mypaint);        //Na
        canvas.drawBitmap(bitmap[11],99/scale_x,195/scale_y,mypaint);       //Mg
        canvas.drawBitmap(bitmap[12],1155/scale_x,195/scale_y,mypaint);     //Al
        canvas.drawBitmap(bitmap[13],1251/scale_x,195/scale_y,mypaint);     //Si
        canvas.drawBitmap(bitmap[14],1347/scale_x,195/scale_y,mypaint);     //P
        canvas.drawBitmap(bitmap[15],1443/scale_x,195/scale_y,mypaint);     //S
        canvas.drawBitmap(bitmap[16],1539/scale_x,195/scale_y,mypaint);     //Cl
        canvas.drawBitmap(bitmap[17],1635/scale_x,195/scale_y,mypaint);     //Ar

        canvas.drawBitmap(bitmap[18],3/scale_x,291/scale_y,mypaint);       //K
        canvas.drawBitmap(bitmap[19],99/scale_x,291/scale_y,mypaint);       //Ca
        canvas.drawBitmap(bitmap[20],195/scale_x,291/scale_y,mypaint);       //Sc
        canvas.drawBitmap(bitmap[21],291/scale_x,291/scale_y,mypaint);       //Ti
        canvas.drawBitmap(bitmap[22],387/scale_x,291/scale_y,mypaint);       //V
        canvas.drawBitmap(bitmap[23],483/scale_x,291/scale_y,mypaint);       //Cr
        canvas.drawBitmap(bitmap[24],579/scale_x,291/scale_y,mypaint);       //Mn
        canvas.drawBitmap(bitmap[25],675/scale_x,291/scale_y,mypaint);       //Fe
        canvas.drawBitmap(bitmap[26],771/scale_x,291/scale_y,mypaint);       //Co
        canvas.drawBitmap(bitmap[27],867/scale_x,291/scale_y,mypaint);       //Ni
        canvas.drawBitmap(bitmap[28],963/scale_x,291/scale_y,mypaint);      //Cu
        canvas.drawBitmap(bitmap[29],1059/scale_x,291/scale_y,mypaint);      //Zn
        canvas.drawBitmap(bitmap[30],1155/scale_x,291/scale_y,mypaint);      //Ga
        canvas.drawBitmap(bitmap[31],1251/scale_x,291/scale_y,mypaint);      //Ge
        canvas.drawBitmap(bitmap[32],1347/scale_x,291/scale_y,mypaint);      //As
        canvas.drawBitmap(bitmap[33],1443/scale_x,291/scale_y,mypaint);      //Se
        canvas.drawBitmap(bitmap[34],1539/scale_x,291/scale_y,mypaint);      //Br
        canvas.drawBitmap(bitmap[35],1635/scale_x,291/scale_y,mypaint);      //Kr

        canvas.drawBitmap(bitmap[36],3/scale_x,387/scale_y,mypaint);       //Rb
        canvas.drawBitmap(bitmap[37],99/scale_x,387/scale_y,mypaint);       //Sr
        canvas.drawBitmap(bitmap[38],195/scale_x,387/scale_y,mypaint);       //Y
        canvas.drawBitmap(bitmap[39],291/scale_x,387/scale_y,mypaint);       //Zr
        canvas.drawBitmap(bitmap[40],387/scale_x,387/scale_y,mypaint);       //Nb
        canvas.drawBitmap(bitmap[41],483/scale_x,387/scale_y,mypaint);       //Mo
        canvas.drawBitmap(bitmap[42],579/scale_x,387/scale_y,mypaint);       //Tc
        canvas.drawBitmap(bitmap[43],675/scale_x,387/scale_y,mypaint);       //Ru
        canvas.drawBitmap(bitmap[44],771/scale_x,387/scale_y,mypaint);       //Rh
        canvas.drawBitmap(bitmap[45],867/scale_x,387/scale_y,mypaint);       //Pd
        canvas.drawBitmap(bitmap[46],963/scale_x,387/scale_y,mypaint);      //Ag
        canvas.drawBitmap(bitmap[47],1059/scale_x,387/scale_y,mypaint);      //Cd
        canvas.drawBitmap(bitmap[48],1155/scale_x,387/scale_y,mypaint);      //In
        canvas.drawBitmap(bitmap[49],1251/scale_x,387/scale_y,mypaint);      //Sn
        canvas.drawBitmap(bitmap[50],1347/scale_x,387/scale_y,mypaint);      //Sb
        canvas.drawBitmap(bitmap[51],1443/scale_x,387/scale_y,mypaint);      //Te
        canvas.drawBitmap(bitmap[52],1539/scale_x,387/scale_y,mypaint);      //I
        canvas.drawBitmap(bitmap[53],1635/scale_x,387/scale_y,mypaint);      //Xe

        canvas.drawBitmap(bitmap[54],3/scale_x,483/scale_y,mypaint);       //Cs
        canvas.drawBitmap(bitmap[55],99/scale_x,483/scale_y,mypaint);       //Ba
        //canvas.drawBitmap(bitmap[38],295,497,mypaint);
        canvas.drawBitmap(bitmap[71],291/scale_x,483/scale_y,mypaint);       //Hf
        canvas.drawBitmap(bitmap[72],387/scale_x,483/scale_y,mypaint);       //Ta
        canvas.drawBitmap(bitmap[73],483/scale_x,483/scale_y,mypaint);       //W
        canvas.drawBitmap(bitmap[74],579/scale_x,483/scale_y,mypaint);       //Re
        canvas.drawBitmap(bitmap[75],675/scale_x,483/scale_y,mypaint);       //Os
        canvas.drawBitmap(bitmap[76],771/scale_x,483/scale_y,mypaint);       //Ir
        canvas.drawBitmap(bitmap[77],867/scale_x,483/scale_y,mypaint);       //Pt
        canvas.drawBitmap(bitmap[78],963/scale_x,483/scale_y,mypaint);      //Au
        canvas.drawBitmap(bitmap[79],1059/scale_x,483/scale_y,mypaint);      //Hg
        canvas.drawBitmap(bitmap[80],1155/scale_x,483/scale_y,mypaint);      //Ti
        canvas.drawBitmap(bitmap[81],1251/scale_x,483/scale_y,mypaint);      //Pb
        canvas.drawBitmap(bitmap[82],1347/scale_x,483/scale_y,mypaint);      //Bi
        canvas.drawBitmap(bitmap[83],1443/scale_x,483/scale_y,mypaint);      //Po
        canvas.drawBitmap(bitmap[84],1539/scale_x,483/scale_y,mypaint);      //At
        canvas.drawBitmap(bitmap[85],1635/scale_x,483/scale_y,mypaint);      //Rn

        canvas.drawBitmap(bitmap[86],3/scale_x,579/scale_y,mypaint);         //Fr
        canvas.drawBitmap(bitmap[87],99/scale_x,579/scale_y,mypaint);        //Ra
        //canvas.drawBitmap(bitmap[38],295,497,mypaint);
        canvas.drawBitmap(bitmap[103],291/scale_x,579/scale_y,mypaint);      //Rf
        canvas.drawBitmap(bitmap[104],387/scale_x,579/scale_y,mypaint);      //Db
        canvas.drawBitmap(bitmap[105],483/scale_x,579/scale_y,mypaint);      //Sg
        canvas.drawBitmap(bitmap[106],579/scale_x,579/scale_y,mypaint);      //Bh
        canvas.drawBitmap(bitmap[107],675/scale_x,579/scale_y,mypaint);      //Hs
        canvas.drawBitmap(bitmap[108],771/scale_x,579/scale_y,mypaint);      //Mt
        canvas.drawBitmap(bitmap[109],867/scale_x,579/scale_y,mypaint);      //Ds
        canvas.drawBitmap(bitmap[110],963/scale_x,579/scale_y,mypaint);      //Rg
        canvas.drawBitmap(bitmap[111],1059/scale_x,579/scale_y,mypaint);     //Cn

        canvas.drawBitmap(bitmap[56],195/scale_x,703/scale_y,mypaint);       //La
        canvas.drawBitmap(bitmap[57],291/scale_x,703/scale_y,mypaint);       //Ce
        canvas.drawBitmap(bitmap[58],387/scale_x,703/scale_y,mypaint);       //Pr
        canvas.drawBitmap(bitmap[59],483/scale_x,703/scale_y,mypaint);       //Nd
        canvas.drawBitmap(bitmap[60],579/scale_x,703/scale_y,mypaint);       //Pm
        canvas.drawBitmap(bitmap[61],675/scale_x,703/scale_y,mypaint);       //Sm
        canvas.drawBitmap(bitmap[62],771/scale_x,703/scale_y,mypaint);       //Eu
        canvas.drawBitmap(bitmap[63],867/scale_x,703/scale_y,mypaint);       //Gd
        canvas.drawBitmap(bitmap[64],963/scale_x,703/scale_y,mypaint);       //Tb
        canvas.drawBitmap(bitmap[65],1059/scale_x,703/scale_y,mypaint);      //Dy
        canvas.drawBitmap(bitmap[66],1155/scale_x,703/scale_y,mypaint);      //Ho
        canvas.drawBitmap(bitmap[67],1251/scale_x,703/scale_y,mypaint);      //Er
        canvas.drawBitmap(bitmap[68],1347/scale_x,703/scale_y,mypaint);      //Tm
        canvas.drawBitmap(bitmap[69],1443/scale_x,703/scale_y,mypaint);      //Yb
        canvas.drawBitmap(bitmap[70],1539/scale_x,703/scale_y,mypaint);      //Lu

        canvas.drawBitmap(bitmap[88],195/scale_x,799/scale_y,mypaint);       //La
        canvas.drawBitmap(bitmap[89],291/scale_x,799/scale_y,mypaint);       //Ce
        canvas.drawBitmap(bitmap[90],387/scale_x,799/scale_y,mypaint);       //Pr
        canvas.drawBitmap(bitmap[91],483/scale_x,799/scale_y,mypaint);       //Nd
        canvas.drawBitmap(bitmap[92],579/scale_x,799/scale_y,mypaint);       //Pm
        canvas.drawBitmap(bitmap[93],675/scale_x,799/scale_y,mypaint);       //Sm
        canvas.drawBitmap(bitmap[94],771/scale_x,799/scale_y,mypaint);       //Eu
        canvas.drawBitmap(bitmap[95],867/scale_x,799/scale_y,mypaint);       //Gd
        canvas.drawBitmap(bitmap[96],963/scale_x,799/scale_y,mypaint);       //Tb
        canvas.drawBitmap(bitmap[97],1059/scale_x,799/scale_y,mypaint);      //Dy
        canvas.drawBitmap(bitmap[98],1155/scale_x,799/scale_y,mypaint);      //Ho
        canvas.drawBitmap(bitmap[99],1251/scale_x,799/scale_y,mypaint);      //Er
        canvas.drawBitmap(bitmap[100],1347/scale_x,799/scale_y,mypaint);      //Tm
        canvas.drawBitmap(bitmap[101],1443/scale_x,799/scale_y,mypaint);      //Yb
        canvas.drawBitmap(bitmap[102],1539/scale_x,799/scale_y,mypaint);      //Lu



        mypaint.setColor(Color.BLACK);
        mypaint.setStyle(Paint.Style.STROKE);
        mypaint.setStrokeWidth(4);
        canvas.drawRect(rec_x,rec_y,rec_x+(96/scale_x),rec_y+(96/scale_y),mypaint);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            int x;
            int y;
            current_x = event.getX();
            current_y = event.getY();

            if(current_y<676/scale_y){
                x = (int) (current_x)/(int)(96/scale_x);
                y = (int) (current_y)/(int)(96/scale_y);
                //rec_x=(float)x*96;
                //rec_y=(float)y*96;
                col=x;
                row=y;
            }
            else if(current_y>700/scale_y){
                x=(int)current_x/(int)(96/scale_x);
                y=(int)(current_y-700/scale_y)/(int)(96/scale_y);
                //rec_x=(float)x*96;
                //rec_y=(float)y*96+700;
                col=x;
                row=y+7;
            }

            switch (row)
            {
                case 0:
                    switch (col){
                        case 0: seleted_element=1;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 2: change_activity=true;break;
                        case 3: change_activity=true;break;
                        case 4: change_activity=true;break;
                        case 17:seleted_element=2;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        default:change_activity=false;break;
                    }break;

                case 1:
                    switch (col){
                        case 0: seleted_element=3;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 1: seleted_element=4;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 2: change_activity=true;break;
                        case 3: change_activity=true;break;
                        case 4: change_activity=true;break;
                        case 12:seleted_element=5;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 13:seleted_element=6;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 14:seleted_element=7;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 15:seleted_element=8;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 16:seleted_element=9;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        case 17:seleted_element=10;rec_x=col*(96/scale_x);rec_y=(float)row*(96/scale_y);change_activity=false;break;
                        default:change_activity=false;break;
                    }break;

                case 2:
                    switch (col){
                        case 0: seleted_element=11;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 1: seleted_element=12;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 2: change_activity=true;break;
                        case 3: change_activity=true;break;
                        case 4: change_activity=true;break;
                        case 12:seleted_element=13;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 13:seleted_element=14;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 14:seleted_element=15;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 15:seleted_element=16;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 16:seleted_element=17;rec_x=col*(96/scale_x);change_activity=false;break;
                        case 17:seleted_element=18;rec_x=col*(96/scale_x);change_activity=false;break;
                        default:change_activity=false;break;
                    }rec_y=(float)row*(96/scale_y);break;

                case 3:
                    switch (col){
                        case 0: seleted_element=19;rec_x=col*(96/scale_x);break;
                        case 1: seleted_element=20;rec_x=col*(96/scale_x);break;
                        case 2: seleted_element=21;rec_x=col*(96/scale_x);break;
                        case 3: seleted_element=22;rec_x=col*(96/scale_x);break;
                        case 4: seleted_element=23;rec_x=col*(96/scale_x);break;
                        case 5: seleted_element=24;rec_x=col*(96/scale_x);break;
                        case 6: seleted_element=25;rec_x=col*(96/scale_x);break;
                        case 7: seleted_element=26;rec_x=col*(96/scale_x);break;
                        case 8: seleted_element=27;rec_x=col*(96/scale_x);break;
                        case 9: seleted_element=28;rec_x=col*(96/scale_x);break;
                        case 10: seleted_element=29;rec_x=col*(96/scale_x);break;
                        case 11: seleted_element=30;rec_x=col*(96/scale_x);break;
                        case 12: seleted_element=31;rec_x=col*(96/scale_x);break;
                        case 13:seleted_element=32;rec_x=col*(96/scale_x);break;
                        case 14:seleted_element=33;rec_x=col*(96/scale_x);break;
                        case 15:seleted_element=34;rec_x=col*(96/scale_x);break;
                        case 16:seleted_element=35;rec_x=col*(96/scale_x);break;
                        case 17:seleted_element=36;rec_x=col*(96/scale_x);break;
                        default:break;
                    }rec_y=(float)row*(96/scale_y);change_activity=false;break;

                case 4:
                    switch (col){
                        case 0: seleted_element=37;rec_x=col*(96/scale_x);break;
                        case 1: seleted_element=38;rec_x=col*(96/scale_x);break;
                        case 2: seleted_element=39;rec_x=col*(96/scale_x);break;
                        case 3: seleted_element=40;rec_x=col*(96/scale_x);break;
                        case 4: seleted_element=41;rec_x=col*(96/scale_x);break;
                        case 5: seleted_element=42;rec_x=col*(96/scale_x);break;
                        case 6: seleted_element=43;rec_x=col*(96/scale_x);break;
                        case 7: seleted_element=44;rec_x=col*(96/scale_x);break;
                        case 8: seleted_element=45;rec_x=col*(96/scale_x);break;
                        case 9: seleted_element=46;rec_x=col*(96/scale_x);break;
                        case 10: seleted_element=47;rec_x=col*(96/scale_x);break;
                        case 11: seleted_element=48;rec_x=col*(96/scale_x);break;
                        case 12: seleted_element=49;rec_x=col*(96/scale_x);break;
                        case 13:seleted_element=50;rec_x=col*(96/scale_x);break;
                        case 14:seleted_element=51;rec_x=col*(96/scale_x);break;
                        case 15:seleted_element=52;rec_x=col*(96/scale_x);break;
                        case 16:seleted_element=53;rec_x=col*(96/scale_x);break;
                        case 17:seleted_element=54;rec_x=col*(96/scale_x);break;
                        default:break;
                    }rec_y=(float)row*(96/scale_y);change_activity=false;break;

                case 5:
                    switch (col){
                        case 0: seleted_element=55;rec_x=col*(96/scale_x);break;
                        case 1: seleted_element=56;rec_x=col*(96/scale_x);break;
                        //case 2: seleted_element=39;
                        case 3: seleted_element=72;rec_x=col*(96/scale_x);break;
                        case 4: seleted_element=73;rec_x=col*(96/scale_x);break;
                        case 5: seleted_element=74;rec_x=col*(96/scale_x);break;
                        case 6: seleted_element=75;rec_x=col*(96/scale_x);break;
                        case 7: seleted_element=76;rec_x=col*(96/scale_x);break;
                        case 8: seleted_element=77;rec_x=col*(96/scale_x);break;
                        case 9: seleted_element=78;rec_x=col*(96/scale_x);break;
                        case 10: seleted_element=79;rec_x=col*(96/scale_x);break;
                        case 11: seleted_element=80;rec_x=col*(96/scale_x);break;
                        case 12: seleted_element=81;rec_x=col*(96/scale_x);break;
                        case 13:seleted_element=82;rec_x=col*(96/scale_x);break;
                        case 14:seleted_element=83;rec_x=col*(96/scale_x);break;
                        case 15:seleted_element=84;rec_x=col*(96/scale_x);break;
                        case 16:seleted_element=85;rec_x=col*(96/scale_x);break;
                        case 17:seleted_element=86;rec_x=col*(96/scale_x);break;
                        default:break;
                    }rec_y=(float)row*(96/scale_y);change_activity=false;break;

                case 6:
                    switch (col){
                        case 0: seleted_element=87;rec_x=col*(96/scale_x);break;
                        case 1: seleted_element=88;rec_x=col*(96/scale_x);break;
                         //case 2: seleted_element=39;
                        case 3: seleted_element=104;rec_x=col*(96/scale_x);break;
                        case 4: seleted_element=105;rec_x=col*(96/scale_x);break;
                        case 5: seleted_element=106;rec_x=col*(96/scale_x);break;
                        case 6: seleted_element=107;rec_x=col*(96/scale_x);break;
                        case 7: seleted_element=108;rec_x=col*(96/scale_x);break;
                        case 8: seleted_element=109;rec_x=col*(96/scale_x);break;
                        case 9: seleted_element=110;rec_x=col*(96/scale_x);break;
                        case 10: seleted_element=111;rec_x=col*(96/scale_x);break;
                        case 11: seleted_element=112;rec_x=col*(96/scale_x);break;
                        default:break;
                    }rec_y=(float)row*(96/scale_y);change_activity=false;break;

                case 7:
                    switch (col){
                        case 2: seleted_element=57;rec_x=col*(96/scale_x);break;
                        case 3: seleted_element=58;rec_x=col*(96/scale_x);break;
                        case 4: seleted_element=59;rec_x=col*(96/scale_x);break;
                        case 5: seleted_element=60;rec_x=col*(96/scale_x);break;
                        case 6: seleted_element=61;rec_x=col*(96/scale_x);break;
                        case 7: seleted_element=62;rec_x=col*(96/scale_x);break;
                        case 8: seleted_element=63;rec_x=col*(96/scale_x);break;
                        case 9: seleted_element=64;rec_x=col*(96/scale_x);break;
                        case 10: seleted_element=65;rec_x=col*(96/scale_x);break;
                        case 11: seleted_element=66;rec_x=col*(96/scale_x);break;
                        case 12: seleted_element=67;rec_x=col*(96/scale_x);break;
                        case 13:seleted_element=68;rec_x=col*(96/scale_x);break;
                        case 14:seleted_element=69;rec_x=col*(96/scale_x);break;
                        case 15:seleted_element=70;rec_x=col*(96/scale_x);break;
                        case 16:seleted_element=71;rec_x=col*(96/scale_x);break;
                        default:break;
                    }rec_y=((float)row*96+28)/scale_y;change_activity=false;break;

                case 8:
                    switch (col){
                        case 2: seleted_element=89;rec_x=col*(96/scale_x);break;
                        case 3: seleted_element=90;rec_x=col*(96/scale_x);break;
                        case 4: seleted_element=91;rec_x=col*(96/scale_x);break;
                        case 5: seleted_element=92;rec_x=col*(96/scale_x);break;
                        case 6: seleted_element=93;rec_x=col*(96/scale_x);break;
                        case 7: seleted_element=94;rec_x=col*(96/scale_x);break;
                        case 8: seleted_element=95;rec_x=col*(96/scale_x);break;
                        case 9: seleted_element=96;rec_x=col*(96/scale_x);break;
                        case 10: seleted_element=97;rec_x=col*(96/scale_x);break;
                        case 11: seleted_element=98;rec_x=col*(96/scale_x);break;
                        case 12: seleted_element=99;rec_x=col*(96/scale_x);break;
                        case 13:seleted_element=100;rec_x=col*(96/scale_x);break;
                        case 14:seleted_element=101;rec_x=col*(96/scale_x);break;
                        case 15:seleted_element=102;rec_x=col*(96/scale_x);break;
                        case 16:seleted_element=103;rec_x=col*(96/scale_x);break;
                        default:break;
                    }rec_y=((float)row*96+28)/scale_y;break;
                default:change_activity=false;break;
            }
            //Log.i("触控检测","("+String.valueOf(row)+","+String.valueOf(col)+") 原子序数:"+String.valueOf(seleted_element)+"是否浏览详情:"+String.valueOf(change_activity));
        }
        invalidate();
        return true;
    }
}

