package br.com.crypto_dashboard;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

class CryptoDashboardApplicationTests {




    private void setHourAndMinute(LocalDate date, int i, int i1) {

    }


    public static Date plusMinute(Date data, Integer minutos) {

        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.MINUTE, minutos);
        return c.getTime();

    }

    private void plusHours(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        date.setTime(calendar.getTime().getTime());
    }

    private String dataToString(Date date, String hh) {
        SimpleDateFormat format = new SimpleDateFormat(hh);
        return format.format(date);
    }

    @Test
    void contextLoads() {
        var date = stringToDate("2023-08-10 13:39:59.913", "yyyy-MM-dd HH:mm:ss.SSS");
        Date data2 = new Date();
        assert date != null;
        long diferenca = data2.getTime() - date.getTime();
        long diferencaEmHoras = (diferenca / (1000 * 60));
        System.out.println(diferencaEmHoras);
    }

    public static Date stringToDate(String data, String formato) {
        SimpleDateFormat format = new SimpleDateFormat(formato);
        try {
            return new Date(format.parse(data).getTime());
        } catch (ParseException e) {
            return null;
        }
    }


    @Test
    public void test() {
        File file = new File("C:\\Users\\desenvolvimento05\\Documents\\GIA ST\\GST03_072023_824017675111_10-08-2023_09-53-37-0865.TED");
        try {
            convertByteFileToTxt(file.getAbsolutePath(), "C:\\Users\\desenvolvimento05\\Documents\\GIA ST\\GST03_072023_824017675111_10-08-2023_09-53-37-0865.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void convertByteFileToTxt(String sourcePath, String destinationPath) throws IOException {
        // Reading the file in bytes
        File file = new File(sourcePath);
        byte[] fileBytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(fileBytes);
        fis.close();

        // Writing the bytes to a .txt file as text
        StringBuilder sb = new StringBuilder();
        for (byte b : fileBytes) {

            sb.append(b); // writing each byte in a new line
        }

        FileOutputStream fos = new FileOutputStream(destinationPath);
        fos.write(sb.toString().getBytes());
        fos.close();
    }


    @Test
    void parseDoubleTest() {
        String valor = "0.00";
        String valor2 = "";
        String valor3 = "0.0";
        String valor4 = "0.000";

        var x = Double.parseDouble(valor);
        var x1 = Double.parseDouble(valor2);
        var x2 = Double.parseDouble(valor3);
        var x3 = Double.parseDouble(valor4);

        if (x > 0 || x1 > 0 || x2 > 0 || x3 > 0) {
            System.out.println("nok");
            return;
        }

        System.out.println("ok");

    }

}
