/*
                DDS Radio TX
    Copyright (C) March 2016  Edouard Kanku
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact: info@luxjim.com
 */

package com.luxjim.ddsradiotx;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.luxjim.ddsradiotx.driver.UsbSerialPort;

public class MainActivity extends Activity {

    private UsbSerialPort usbPort;
    private int bufferSize = 8;
    private String vid_pid_norm = "vid_04d8&pid_0011";
    private long frequency = 1000000;
    private long step = 100;
    private boolean store = false;
    Paint panel_paint = new Paint();
    private TextView a,b,c,y;
    private Button d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface SCRIPTBL = Typeface.createFromAsset(getAssets(), "androidnation.ttf");
        Typeface FORTE = Typeface.createFromAsset(getAssets(), "Cornerstone.ttf");

                //Initialize widgets...
                a = (TextView) findViewById(R.id.freq_panel);
        a.setTextSize(28);
        a.setText("12.345,578");
        panel_paint.setAntiAlias(true);
        panel_paint.setColor(getResources().getColor(R.color.white));
        a.setTypeface(SCRIPTBL);
        b = (TextView) findViewById(R.id.textview_abst);
        c = (TextView) findViewById(R.id.textview_schr);
        y = (TextView) findViewById(R.id.freq_schrit);
        y.setTextSize(20);
        y.setText("Schrittweite 10000000");

        d = (Button) findViewById(R.id.buttonplus);
        e = (Button) findViewById(R.id.buttonmoins);
        f = (Button) findViewById(R.id.buttonpgauche);
        f.setText("<");
        g = (Button) findViewById(R.id.buttondroit);
        h = (Button) findViewById(R.id.button1);
        i = (Button) findViewById(R.id.button2);
        j = (Button) findViewById(R.id.button3);
        k = (Button) findViewById(R.id.buttonAdd);
        l = (Button) findViewById(R.id.button4);
        m = (Button) findViewById(R.id.button5);
        n = (Button) findViewById(R.id.button6);
        o = (Button) findViewById(R.id.buttonSubtract);
        p = (Button) findViewById(R.id.button7);
        q = (Button) findViewById(R.id.button8);
        r = (Button) findViewById(R.id.button9);
        s = (Button) findViewById(R.id.buttonMultiply);
        t = (Button) findViewById(R.id.buttonNegative);
        u = (Button) findViewById(R.id.button0);
        v = (Button) findViewById(R.id.buttonDot);
        w = (Button) findViewById(R.id.buttonDivide);
        x = (Button) findViewById(R.id.button111);

    }


}
