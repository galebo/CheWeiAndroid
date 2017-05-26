package com.galebo.www.test2;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.galebo.www.test2.R.id.editText2;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db ;
    static public class Person{
        String name;
        String age;
        long id;
    }
    static public class Car{
        String number;
        String position;
        long id;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TextView dd=(TextView)findViewById(R.id.editText2);
                dd.setText("");
            }
        } );
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TextView dd=(TextView)findViewById(R.id.editText2);
                String ddd=dd.getText().toString();
                queryCar(ddd);
            }
        } );
        try {

            initDb();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭当前数据库
        db.close();
    }

    protected void initDb()
    {
        //打开或创建test.db数据库
        db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS person");
        db.execSQL("DROP TABLE IF EXISTS car");
        //创建person表
        db.execSQL("CREATE TABLE 'person' ('id' INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL ,'person_name' VARCHAR(255),'person_age' VARCHAR(255),'person_number'  int ) ");
        db.execSQL("CREATE TABLE 'car' ('id' INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL ,'own_id' INTEGER,'car_id' VARCHAR(255),'car_brand' VARCHAR(255),'car_price'VARCHAR(255))  ");


        Person per=insertPerson(db,"东库","11");

        initCars(per);

        per=insertPerson(db,"西库","11");

        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE person_age >= ?", new String[]{"33"});
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("person_name"));
            int age = cursor.getInt(cursor.getColumnIndex("person_age"));
            Log.i("db", "_id=>" + _id + ", number=>" + name + ", position=>" + age);
        }
        cursor.close();

        //删除数据
        db.delete("person", "person_age < ?", new String[]{"35"});


    }



    private void update(SQLiteDatabase db) {
        ContentValues cv;

        cv = new ContentValues();
        cv.put("person_age", 35);
        //更新数据
        db.update("person", cv, "person_name = ?", new String[]{"john"});
    }

    private Person insertPerson(SQLiteDatabase db,String name,String age) {
        Person person = new Person();
        person.name = name;
        person.age = age;
        //ContentValues以键值对的形式存放数据
        ContentValues cv = new ContentValues();
        cv.put("person_name", person.name);
        cv.put("person_age", person.age);
        //插入ContentValues中的数据
        long id= db.insert("person", null, cv);
        person.id=id;
        return person;
    }
    private Car insertCar(SQLiteDatabase db,String number,String position,long parentId) {
        Car car = new Car();
        car.number = number;
        car.position = position;
        //ContentValues以键值对的形式存放数据
        ContentValues cv = new ContentValues();
        cv.put("own_id",parentId);
        cv.put("car_brand", car.number);
        cv.put("car_price", car.position);
        //插入ContentValues中的数据
        long id= db.insert("car", null, cv);
        car.id=id;
        return car;
    }

    void query(){
        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE person_age >= ?", new String[]{"33"});
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("person_name"));
            int age = cursor.getInt(cursor.getColumnIndex("person_age"));
            Log.i("db", "_id=>" + _id + ", number=>" + name + ", position=>" + age);
        }
        cursor.close();
    }
    void queryCar(String num){
        String out="请输入信息";
        if(num.length()>0){
            out="";
            Cursor cursor = db.rawQuery("SELECT * FROM car where car_brand like ?",new String[]{"%"+num+"%"});
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex("id"));
                String number = cursor.getString(cursor.getColumnIndex("car_brand"));
                int position = cursor.getInt(cursor.getColumnIndex("car_price"));
                out=out+"\n 车牌号:" + number + ",位置："+position;
            }
            cursor.close();
        }
        new AlertDialog.Builder(MainActivity.this).setTitle("查询结果")//设置对话框标题
                .setMessage(out)//设置显示的内容
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                    }
                }).show();
    }

    private void initCars(Person per) {
        insertCar(db,"京Q9CC78","74",per.id);
        insertCar(db,"京N66FU9","84",per.id);
        insertCar(db,"京NR8462","54",per.id);
        insertCar(db,"京N47F16","144",per.id);
        insertCar(db,"京N42L39","8888",per.id);
        insertCar(db,"京N5AU99","142",per.id);
        insertCar(db,"京NE58V8","147",per.id);
        insertCar(db,"京P19G69","135",per.id);
        insertCar(db,"京QKX563","136",per.id);
        insertCar(db,"京Q3MF85","133",per.id);
        insertCar(db,"京NZ3650","131",per.id);
        insertCar(db,"津BVG913","112",per.id);
        insertCar(db,"京NP4625","121",per.id);
        insertCar(db,"京N7LP78","127",per.id);
        insertCar(db,"京PQQ959","110",per.id);
        insertCar(db,"京JS9107","109",per.id);
        insertCar(db,"京QVP117","99",per.id);
        insertCar(db,"京NCA363","85",per.id);
        insertCar(db,"京L26609","79",per.id);
        insertCar(db,"京N5TT45","3",per.id);
        insertCar(db,"LIAOA5MG92","8888",per.id);
        insertCar(db,"JIRX6D68","90",per.id);
        insertCar(db,"京NWS219","93",per.id);
        insertCar(db,"京Q6GF91","96",per.id);
        insertCar(db,"京N47B83","97",per.id);
        insertCar(db,"京NGX877","70",per.id);
        insertCar(db,"京N6L095","71",per.id);
        insertCar(db,"京KT8467","67",per.id);
        insertCar(db,"京P3JQ68","64",per.id);
        insertCar(db,"京Q88W73","62",per.id);
        insertCar(db,"京N9Q2S9","60",per.id);
        insertCar(db,"京N0UP08","58",per.id);
        insertCar(db,"京AM1018","57",per.id);
        insertCar(db,"京QMJ692","56",per.id);
        insertCar(db,"京Q8P9N0","55",per.id);
        insertCar(db,"京Q3GB78","53",per.id);
        insertCar(db,"京NINT86","50",per.id);
        insertCar(db,"京AA7356","8888",per.id);
        insertCar(db,"京NUN817","28",per.id);
        insertCar(db,"京N0P072","29",per.id);
        insertCar(db,"京JF9288","30",per.id);
        insertCar(db,"京QLG729","38",per.id);
        insertCar(db,"京N3JL49","39",per.id);
        insertCar(db,"JID083CD","40",per.id);
        insertCar(db,"京Q8WP70","41",per.id);
        insertCar(db,"京NG5117","42",per.id);
        insertCar(db,"京N9W7L8","45",per.id);
        insertCar(db,"LUBL3A53","46",per.id);
        insertCar(db,"京A11325","26",per.id);
        insertCar(db,"京NHY937","16",per.id);
        insertCar(db,"京NP8B16","18",per.id);
        insertCar(db,"京NT6H31","14",per.id);
        insertCar(db,"京QE21A9","10",per.id);
        insertCar(db,"京JJ4701","8",per.id);
        insertCar(db,"京N8XQ13","6",per.id);
        insertCar(db,"京NMY983","16",per.id);
        insertCar(db,"京NPB062","18",per.id);
        insertCar(db,"京N22TTO","15",per.id);
        insertCar(db,"京L60300","11",per.id);
        insertCar(db,"京CV5599","6",per.id);
        insertCar(db,"京NN88F0","2",per.id);
        insertCar(db,"京Q3BQ20","31",per.id);
        insertCar(db,"京CC4049","34",per.id);
        insertCar(db,"京JC0600","35",per.id);
        insertCar(db,"京N8DK85","36",per.id);
        insertCar(db,"京NLC340","40",per.id);
        insertCar(db,"京NKS692","41",per.id);
        insertCar(db,"京NE8B12","25",per.id);
        insertCar(db,"京NFL081","26",per.id);
        insertCar(db,"京KM0568","55",per.id);
        insertCar(db,"京N61K88","53",per.id);
        insertCar(db,"京NAU7179","58",per.id);
        insertCar(db,"冀B0JIK6","60",per.id);
        insertCar(db,"京Q2FC50","62",per.id);
        insertCar(db,"京N666M7","89",per.id);
        insertCar(db,"京LD8199","87",per.id);
        insertCar(db,"京NORP95","82",per.id);
        insertCar(db,"京QZ56A9","81",per.id);
        insertCar(db,"京NLN396","80",per.id);
        insertCar(db,"京HH6512","78",per.id);
        insertCar(db,"京C30016","77",per.id);
        insertCar(db,"京NPY950","76",per.id);
        insertCar(db,"京Q2Q0M0","75",per.id);
        insertCar(db,"京NM8N36","72",per.id);
        insertCar(db,"京NUK345","71",per.id);
        insertCar(db,"京H27","8888",per.id);
        insertCar(db,"京N76780","69",per.id);
        insertCar(db,"京NK9550YIR69","92",per.id);
        insertCar(db,"京J98716","93",per.id);
        insertCar(db,"津HDG525","94",per.id);
        insertCar(db,"京N66LV2","95",per.id);
        insertCar(db,"京NNZ213","96",per.id);
        insertCar(db,"京HH0001","99",per.id);
        insertCar(db,"京NM4936","100",per.id);
        insertCar(db,"京Q677G6","103",per.id);
        insertCar(db,"京N5V8T8","104",per.id);
        insertCar(db,"京N0AG06","106",per.id);
        insertCar(db,"京NICIJ7","107",per.id);
        insertCar(db,"京NFS032","108",per.id);
        insertCar(db,"京NIAU26","110",per.id);
        insertCar(db,"京QTIN92","111",per.id);
        insertCar(db,"晋ABR666","113",per.id);
        insertCar(db,"京NFR990","114",per.id);
        insertCar(db,"京N5DR09","117",per.id);
        insertCar(db,"京KE0979","118",per.id);
        insertCar(db,"京KJ8460","119",per.id);
        insertCar(db,"京QM88F7","120",per.id);
        insertCar(db,"京QYX035","122",per.id);
        insertCar(db,"京QX9612","123",per.id);
        insertCar(db,"黑AW237R","124",per.id);
        insertCar(db,"京NL5907","171",per.id);
        insertCar(db,"京NW96L9","174",per.id);
        insertCar(db,"京F22091","175",per.id);
        insertCar(db,"京NC3R11","177",per.id);
        insertCar(db,"京N50M73","179",per.id);
        insertCar(db,"京NLD739","180",per.id);
        insertCar(db,"京N6B277","182",per.id);
        insertCar(db,"京NUM557","185",per.id);
        insertCar(db,"京P620Q7","189",per.id);
        insertCar(db,"京N没看清","191",per.id);
        insertCar(db,"京N429D2","193",per.id);
        insertCar(db,"京QJ79G3","194",per.id);
        insertCar(db,"京PIQZ65","195",per.id);
        insertCar(db,"京N557Z7","196",per.id);
        insertCar(db,"京QB9771","197",per.id);
        insertCar(db,"京HM2217","198",per.id);
        insertCar(db,"京N62639LIQ60","200",per.id);
        insertCar(db,"鲁PTV087","167",per.id);
        insertCar(db,"京N8X510","168",per.id);
        insertCar(db,"冀AC2N60","169",per.id);
        insertCar(db,"京P0BY17","170",per.id);
        insertCar(db,"京NIZ5H2","155",per.id);
        insertCar(db,"京N2HL20","212",per.id);
        insertCar(db,"京KW5259","211",per.id);
        insertCar(db,"京NW5C75","210",per.id);
        insertCar(db,"京N19037","209",per.id);
        insertCar(db,"京P0GF51","207",per.id);
        insertCar(db,"京P86R07","205",per.id);
        insertCar(db,"京N0VJ22","204",per.id);
        insertCar(db,"京PPF399","158",per.id);
        insertCar(db,"京MM6090","161",per.id);
        insertCar(db,"京N29WF5","162",per.id);
        insertCar(db,"京N5M9N5","163",per.id);
        insertCar(db,"京NJK586","215",per.id);
        insertCar(db,"京NB6255","213",per.id);
        insertCar(db,"京QZR277","8888",per.id);
        insertCar(db,"京Q5J7Z6","150",per.id);
        insertCar(db,"京Q7BT86","149",per.id);
        insertCar(db,"京NH3872","144",per.id);
        insertCar(db,"京ND31V8","143",per.id);
        insertCar(db,"京FJ1235","140",per.id);
        insertCar(db,"京Q5Y0W7","137",per.id);
        insertCar(db,"京NS9992","136",per.id);
        insertCar(db,"京Q615U7","134",per.id);
        insertCar(db,"京QZK671","133",per.id);
        insertCar(db,"皖B71517","132",per.id);
        insertCar(db,"京Q没看清","131",per.id);
        insertCar(db,"津DMX078","130",per.id);
        insertCar(db,"京JK4299","235",per.id);
        insertCar(db,"JIGNE052","234",per.id);
        insertCar(db,"京NJE796","233",per.id);
        insertCar(db,"京HG8585","231",per.id);
        insertCar(db,"京NRP602","230",per.id);
        insertCar(db,"京QF67X5","229",per.id);
        insertCar(db,"京NTG309","227",per.id);
        insertCar(db,"京NT3V28","226",per.id);
        insertCar(db,"京QH55J6","222",per.id);
        insertCar(db,"京QKU376","220",per.id);
        insertCar(db,"京QEQ812","219",per.id);
        insertCar(db,"京QT22P9","277",per.id);
        insertCar(db,"京NJ9S92","275",per.id);
        insertCar(db,"京QJ50K0","274",per.id);
        insertCar(db,"京N20J38","272",per.id);
        insertCar(db,"京NYU938","271",per.id);
        insertCar(db,"京JN6625","268",per.id);
        insertCar(db,"京NT87P9","267",per.id);
        insertCar(db,"京N9E640","262",per.id);
        insertCar(db,"29Z62","260",per.id);
        insertCar(db,"京QJ2A17","8888",per.id);
        insertCar(db,"京Q90Z17","255",per.id);
        insertCar(db,"京QZR277","8888",per.id);
        insertCar(db,"京P5ZM13","8888",per.id);
        insertCar(db,"京NVIE17","8888",per.id);
        insertCar(db,"京N289H5","8888",per.id);
        insertCar(db,"京NPC746","8888",per.id);
        insertCar(db,"赣H0A027","8888",per.id);
        insertCar(db,"京NDB994","249",per.id);
        insertCar(db,"京QRI772","247",per.id);
    }
}
