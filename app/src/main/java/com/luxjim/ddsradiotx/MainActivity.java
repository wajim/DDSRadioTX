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
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbRequest;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import com.felhr.usbserial.CDCSerialDevice;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "Android USB Serial";
    private int bufferSize = 8;
    UsbDevice device;
    UsbEndpoint epIN = null;
    UsbEndpoint epOUT = null;
    UsbDeviceConnection connection;
    UsbSerialDevice serialPort = null;
    DecimalFormat formatter;
    private long frequency = 1000000;
    private long step = 100;
    private boolean store = false;
    private TextView a,b,c,y;
    private Button d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface SCRIPTBL = Typeface.createFromAsset(getAssets(), "androidnation.ttf");
        Typeface FORTE = Typeface.createFromAsset(getAssets(), "Cornerstone.ttf");
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        //Initialize widgets...
        a = (TextView) findViewById(R.id.freq_panel);
        a.setTextSize(28);
        a.setText("12.345.578");
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
        h.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     byte[] buffer = new byte[bufferSize];
                                     if (buffer != null) {

                                         buffer[0] = 0x01;
                                         buffer[1] = 1;
                                         buffer[2] = 2;
                                         buffer[3] = 3;
                                         buffer[4] = 4;
                                         buffer[5] = (byte) 0xff;
                                         connection.controlTransfer(0x21, 0x9, 0x200, 0, buffer, buffer.length, 0);
                                     }
                                 }
                             });

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

                if(!keep)
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

    //...
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
                   Toast.makeText(this, "Serial port could not be opened",
                 Toast.LENGTH_SHORT).show();
            }
        }else
        {
            // No driver for given device, even generic CDC driver could not be loaded
        }
        return;
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

    @Override
    public void onClick(View v) {
        //code here
        if (v == h) {
        frequency = 148000;
        sendNewFreq(frequency);
        updateDisplay();
        }
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

    private void updateDisplay() {
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


}
