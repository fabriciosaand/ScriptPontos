import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Main {

    private static final int MUTIPLICADOR = 10000;
    private static final double LATITUDE_SAO_PAULO_NE = -23.3251;
    private static final double LONGITUDE_SAO_PAULO_NE = -46.3344;
    private static final double LATITUDE_SAO_PAULO_SW = -24.0350;
    private static final double LONGITUDE_SAO_PAULO_SW = -46.6817;
    public static final int QUANTIDADE_DE_PONTOS = 5000;
    public static final String FORMATO = "##,0000";
    public static final String SQL_INSERT = "insert into ('Ponto %d',%s,%s);\n";
    private static DecimalFormat decimalFormat;
    private static StringBuffer stringBuffer;
    private static int latitude = 0;
    private static int longitude = 0;

    public static void main(String []args){

        decimalFormat = new DecimalFormat(FORMATO);
        stringBuffer = new StringBuffer();

        for (int i = 0; i< QUANTIDADE_DE_PONTOS; i++){
            latitude = rand((int)(LATITUDE_SAO_PAULO_SW* MUTIPLICADOR), (int)(LATITUDE_SAO_PAULO_NE* MUTIPLICADOR));
            longitude = rand((int)(LONGITUDE_SAO_PAULO_SW* MUTIPLICADOR), (int)(LONGITUDE_SAO_PAULO_NE* MUTIPLICADOR));
            stringBuffer.append(insert(decimalFormat.format(latitude),decimalFormat.format(longitude),i));
        }
        System.out.println(stringBuffer.toString());
        salvarArquivo(stringBuffer);
    }

    private static String insert(String latitude,String longitude,int indice){
        return String.format(SQL_INSERT,indice,latitude,longitude);
    }

    private static int rand(int inicio, int fim) {
        return (int) Math.ceil(Math.random() * (fim  - inicio + 1)) - 1 + inicio;
    }

    private static void salvarArquivo(StringBuffer stringBuffer){
        FileWriter arquivo;
        try {
            arquivo = new FileWriter(new File("script.sql"));
            arquivo.write(stringBuffer.toString());
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
