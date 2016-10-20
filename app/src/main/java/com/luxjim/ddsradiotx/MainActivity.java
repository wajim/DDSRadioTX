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
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.felhr.usbserial.CDCSerialDevice;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    //private static final String TAG = "Android USB Serial";
    private int bufferSize = 8;
    private static final String TAG = "LW BUTTON CRASH.........";
    UsbDevice device;
    UsbEndpoint epIN = null;
    UsbEndpoint epOUT = null;
    UsbDeviceConnection connection;
    UsbSerialDevice serialPort = null;
    DecimalFormat formatter = null;
    private long frequency = 1000000;
    private long step = 100;
    private boolean store = false;
    private TextView a,b,c,y;
    private Button d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = null;
    public static final String FRQ = null;

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface SCRIPTBL = Typeface.createFromAsset(getAssets(), "androidnation.ttf");
        Typeface FORTE = Typeface.createFromAsset(getAssets(), "Cornerstone.ttf");
        final DecimalFormat formatter = new DecimalFormat("###,###,###,###,###.##");
        formatter.setDecimalSeparatorAlwaysShown(true);
        formatter.setMinimumFractionDigits(2);

        a = (TextView) findViewById(R.id.freq_panel);
        a.setTextSize(25);
        a.setText("12.345.578");
        a.setTypeface(SCRIPTBL);
        b = (TextView) findViewById(R.id.textview_abst);
        c = (TextView) findViewById(R.id.textview_schr);
        y = (TextView) findViewById(R.id.freq_schrit);
        y.setTextSize(20);
        y.setText("Schrittweite 10 000 000");

        d = (Button) findViewById(R.id.buttonplus);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long frequency = Long.parseLong(null);
                String Str = a.toString();
                frequency = Long.parseLong(Str);
                d.setText((int) (frequency + step));
            }
        });
        e = (Button) findViewById(R.id.buttonmoins);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long frequency = Long.parseLong(null);
                String Str = a.toString();
                frequency = Long.parseLong(Str) - step;
                a.setText((int) frequency);
                //e.setText((int) (frequency - step));
            }
        });
        f = (Button) findViewById(R.id.buttonpgauche);
        f.setText("<");
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long frequency = Long.parseLong(null);
                String Str = y.toString();
                frequency = Long.parseLong(Str);
                f.setText((int) (frequency * 10));
            }
        });

        g = (Button) findViewById(R.id.buttondroit);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long frequency = Long.parseLong(null);
                String Str = y.toString();
                frequency = Long.parseLong(Str);
                g.setText((int) (frequency / 10));
            }
        });
        h = (Button) findViewById(R.id.button1);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 148000;


                try {
                    sendNewFreq(frequency);
                    a.setText(formatter.format(frequency));
                    y.setText("Schrittweite " + formatter.format(step));
// ...
                } catch (Exception exception) {
                    Log.e(TAG, "Received an exception", exception);
                }
            }
        });

        i = (Button) findViewById(R.id.button2);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 3900000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        j = (Button) findViewById(R.id.button3);
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 9400000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        k = (Button) findViewById(R.id.buttonAdd);  //STO
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store = true;
            }
        });

        l = (Button) findViewById(R.id.button4);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 520000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        m = (Button) findViewById(R.id.button5);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 4750000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        n = (Button) findViewById(R.id.button6);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 11600000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        o = (Button) findViewById(R.id.buttonSubtract);  //M1
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (store = true)  {
                String N = o.getText().toString();
                String F = a.getText().toString();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, N);
                    editor.putString(FRQ, F);
                    editor.commit();
                    a.setText(F);
                }
                else
                {
                   // sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0);
                    //String Name = sharedpreferences.getString(Name, Name);
                    //String FRQ = sharedpreferences.getString(FRQ, FRQ);
                    sendNewFreq(frequency);
                    a.setText(formatter.format(frequency));
                    y.setText("Schrittweite " + formatter.format(step));
                }
            }
        });

        p = (Button) findViewById(R.id.button7);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 2300000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        q = (Button) findViewById(R.id.button8);
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 5900000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        r = (Button) findViewById(R.id.button9);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 13570000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        s = (Button) findViewById(R.id.buttonMultiply);  //M2
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (store = true)  {
                    String N = s.getText().toString();
                    String F = String.valueOf(frequency);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, N);
                    editor.putString(FRQ, F);
                    editor.commit();
                }
                else
                {
                    sendNewFreq(frequency);
                    a.setText(formatter.format(frequency));
                    y.setText("Schrittweite " + formatter.format(step));
                }
            }
        });

        t = (Button) findViewById(R.id.buttonNegative);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 3200000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        u = (Button) findViewById(R.id.button0);
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 7200000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        v = (Button) findViewById(R.id.buttonDot);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = 15100000;
                sendNewFreq(frequency);
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));
            }
        });

        w = (Button) findViewById(R.id.buttonDivide);  //M3
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (store = true)  {
                    String N = w.getText().toString();
                    String F = String.valueOf(frequency);

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, N);
                    editor.putString(FRQ, F);
                    editor.commit();
                }
                else
                {
                    sendNewFreq(frequency);
                    a.setText(formatter.format(frequency));
                    y.setText("Schrittweite " + formatter.format(step));
                }
            }
        });

        x = (Button) findViewById(R.id.button111);  //Current frequency
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frequency = Long.parseLong(a.getText().toString());
                try {
                    sendNewFreqToDDSEEPROM(frequency);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                a.setText(formatter.format(frequency));
                y.setText("Schrittweite " + formatter.format(step));

            }
        });

        // This snippet will open the first usb device connected, excluding usb root hubs
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if(!usbDevices.isEmpty())
        {
            boolean keep = true;
            for(Map.Entry<String, UsbDevice> entry : usbDevices.entrySet())
            {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                int devicePID = device.getProductId();
                if(deviceVID != 0x1d6b || (devicePID != 0x0001 || devicePID != 0x0002 || devicePID != 0x0003))
                {
                    // We are supposing here there is only one device connected and it is our serial device
                    connection = usbManager.openDevice(device);
                    Log.d(TAG, "USB Device Found:***********: " + device);
                    Toast.makeText(this, "USB Device found: " + device,
                    Toast.LENGTH_LONG).show();
                    keep = false;
                }else
                {
                    connection = null;
                    device = null;
                    Toast.makeText(this, "USB Device not found! ",
                    Toast.LENGTH_LONG).show();
                }

                if (!keep)
                    break;
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    // A callback for received data must be defined
    private UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback()
    {
        @Override
        public void onReceivedData(byte[] arg0)
        {

            // Code here
        }
    };

    public void getSerialPort() {
        //CDCSerialDevice serialPort = CDCSerialDevice.createUsbSerialDevice(device, connection);
        CDCSerialDevice serialPort = new CDCSerialDevice(device, connection);
        if(serialPort != null)
        {
            if(serialPort.open())
            {
                // Devices are opened with default values, Usually 9600,8,1,None,OFF
                // CDC driver default values 115200,8,1,None,OFF
                serialPort.setBaudRate(115200);
                serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                serialPort.read(mCallback);
            }else
            {
                   Toast.makeText(this, "No Device Connected To Serial Port...",
                 Toast.LENGTH_SHORT).show();
            }
        }
        return;
    }

    private boolean sendControlTransfer(byte[] dataToSend)  {
        synchronized (this)
        {
            byte[] buffer = new byte[bufferSize];
            if (connection != null) {

                if (buffer != null) {

                    buffer[0] = 0x01;
                    buffer[1] = 1;
                    buffer[2] = 2;
                    buffer[3] = 3;
                    buffer[4] = 4;
                    buffer[5] = (byte) 0xff;
                    connection.controlTransfer(0x21, 0x9, 0x200, 0, buffer, buffer.length, 100);
                }
            }

        }
        return true;
    }

    private boolean sendNewFreq(long frequency) {
        byte[] buffer = new byte[bufferSize];
        if (buffer != null)
        {
            buffer[0] = 0x01;
            buffer[1] = (byte) ((frequency & 0xff000000) >> 24);
            buffer[2] = (byte) ((frequency & 0x00ff0000) >> 16);
            buffer[3] = (byte) ((frequency & 0x0000ff00) >> 8);
            buffer[4] = (byte) ((frequency & 0x000000ff));
            buffer[5] = (byte) 0xff;

            connection.controlTransfer(0x21, 0x9, 0x200, 0, buffer, buffer.length, 0);
            if ((buffer[0] == 0x02) && (buffer[5] == 0x64))
                return true;
            else
                return false;
        }

        return false;
    }

    private boolean sendNewFreqToDDSEEPROM(long freq) throws Exception {
        byte[] buffer = new byte[bufferSize];
        if (buffer != null)
        {
            buffer[0] = 0x03;
            buffer[1] = (byte)((frequency & 0xff000000) >> 24);
            buffer[2] = (byte)((frequency & 0x00ff0000) >> 16);
            buffer[3] = (byte)((frequency & 0x0000ff00) >> 8);
            buffer[4] = (byte)((frequency & 0x000000ff));
            buffer[5] = (byte) 0xff;
            connection.controlTransfer(0x21, 0x9, 0x200, 0, buffer, buffer.length, 0);
            if ((buffer[0] == 0x02) && (buffer[5] == 0x64))
                return true;
            else
                return false;
        }

        return false;
    }

    private void updateDisplay() {
    }

    @Override
    public void onClick(View v) {
        //code here

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
