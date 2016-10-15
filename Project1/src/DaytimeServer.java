import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import java.nio.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DaytimeServer {
    static int SERVER_PORT = 3456;
    static int US_STREAM = 0;
    static int FR_STREAM = 1;

    static SimpleDateFormat USformatter = new SimpleDateFormat(
            "h:mm:ss a EEE d MMM yy, zzzz", Locale.US);
    static SimpleDateFormat FRformatter = new SimpleDateFormat(
            "h:mm:ss a EEE d MMM yy, zzzz", Locale.FRENCH);

    public static void main(String[] args) throws IOException {
        SctpServerChannel ssc = SctpServerChannel.open();
        InetSocketAddress serverAddr = new InetSocketAddress(SERVER_PORT);
        ssc.bind(serverAddr);

        ByteBuffer buf = ByteBuffer.allocateDirect(60);
        CharBuffer cbuf = CharBuffer.allocate(60);
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetEncoder encoder = charset.newEncoder();

        while (true) {
            SctpChannel sc = ssc.accept();

            /* get the current date */
            Date today = new Date();
            cbuf.put(USformatter.format(today)).flip();
            encoder.encode(cbuf, buf, true);
            buf.flip();

            /* send the message on the US stream */
            MessageInfo messageInfo = MessageInfo.createOutgoing(null,
                    US_STREAM);
            sc.send(buf, messageInfo);

            /* update the buffer with French format */
            cbuf.clear();
            cbuf.put(FRformatter.format(today)).flip();
            buf.clear();
            encoder.encode(cbuf, buf, true);
            buf.flip();

            /* send the message on the French stream */
            messageInfo.streamNumber(FR_STREAM);
            sc.send(buf, messageInfo);

            cbuf.clear();
            buf.clear();

            sc.close();
        }
    }
}