package Utils;

public class UTFCorrectionTranslator {

    public static String translate(String value){

        char i = 173;

        return value.replaceAll("â—„", "◄")
                .replaceAll("â–º", "►")
                .replaceAll("Ä›", "ě")
                .replaceAll("Å¡", "š")
                .replaceAll("◄cc►", "č")
                .replaceAll("Å™", "ř")
                .replaceAll("Å¾", "ž")
                .replaceAll("Ã½", "ý")
                .replaceAll("Ã¡", "á")
                .replaceAll("Ã" + i, "í")
                .replaceAll("Ã©", "é")
                .replaceAll("Ãº", "ú")
                .replaceAll("Å¯", "ů")
                .replaceAll("◄cd►", "ď")
                .replaceAll("Åˆ", "ň")
                .replaceAll("Å¥", "ť")
                .replaceAll("â€“", "-");

    }

}
