package com.beowulfe.hap.sample;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ported code from https://github.com/gtanner/qrcode-terminal
 */
public class QRtoConsole {
    private static final Logger logger = LoggerFactory.getLogger(QRtoConsole.class);

    private static final char WHITE_ALL = '\u2588';
    private static final char WHITE_BLACK = '\u2580';
    private static final char BLACK_WHITE = '\u2584';
    private static final char BLACK_ALL = ' ';


    public static void printQR(String setupURI) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    setupURI, BarcodeFormat.QR_CODE, 10,
                    30);
            for(int y = 0; y < matrix.getHeight();y+=2) {
                for(int x = 0; x < matrix.getWidth(); x++) {
                    boolean firstRow = matrix.get(x,y);
                    boolean secondRow = matrix.get(x,y+1);
                    if(firstRow && secondRow) {
                        System.out.print(BLACK_ALL);
                    } else if(firstRow) {
                        System.out.print(BLACK_WHITE);
                    } else if(secondRow) {
                        System.out.print(WHITE_BLACK);
                    } else {
                        System.out.print(WHITE_ALL);
                    }
                }
                System.out.println();
            }
        } catch(WriterException e) {
            logger.error("error creating qr code", e);
        }
    }
}
